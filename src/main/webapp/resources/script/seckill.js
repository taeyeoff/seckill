var projectName;
var seckill={
    url:{
        now:function () {
            return projectName+"/seckill/now";
        },
        exposer:function(seckillId){
            return projectName+"/seckill/"+seckillId+"/exposer";
        },
        execution:function(id,md5){
            return projectName+"/seckill/"+id+"/"+md5+"/execution";
        }

    },
    handleSecKill:function(id,box){
        box.hide().html("<button class='btn btn-primary btn-lg' id='node_btn'>开始秒杀</button>");
        $.post(seckill.url.exposer(id),{},function (result) {
            if(result&&result["success"]){
                var exposer =result["data"];
                if(exposer["exposed"]){
                    var secKillId=exposer["secKillId"];
                    var md5=exposer["md5"];
                    var killUrl=seckill.url.execution(secKillId,md5);
                    $("#node_btn").one("click",function () {
                        $(this).addClass("disabled");
                        $.post(killUrl,{},function (result) {
                           if(result&&result["success"]){
                                var killResult=result["data"];
                                var state=killResult["state"];
                                var info=killResult["stateInfo"];
                                box.html("<span class='label label-success'>"+info+"</span>");
                           }else{
                               console.log("kill_result:"+result["error"]);
                           }
                        });
                    });
                    box.show();
                }else{
                    var now=exposer["now"];
                    var start=exposer["start"];
                    var end=exposer["end"];
                    var secKillId=exposer["secKillId"];
                    seckill.timedown(secKillId,now,start,end);
                }
            }else{
                console.log("result:"+result["error"]);
            }
        });
    },
    timedown:function(id,now,start,end){
        var box= $("#seckill-box");
        if(now>end){
           box.html("秒杀结束");
        }else if(now<start){
            var killTime=new Date(start+1000);
            box.countdown(killTime,function (event) {
                var format=event.strftime("秒杀倒计时：%D天 %H时 %M分 %S秒");
                box.html(format);
            }).on("finish.countdown",function () {
                seckill.handleSecKill(id,box);
            });
        }else{
            seckill.handleSecKill(id,box);
        }
    },
    validatePhone:function(phone){
        if(phone&&phone.length>0&&!isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    detail:{
        init:function (params){
            var killphone=$.cookie("killphone");
            projectName=params["path"];
            if(!seckill.validatePhone(killphone)){
                var input_phone_modal=$("#input_phone");
                input_phone_modal.modal({
                    show:true,
                    backdrop:"static",//禁止位置关闭
                    keyboard:false//键盘不可用
                });
                $("#phone_btn").click(function(){
                    var input=$("#phone").val();
                    if(seckill.validatePhone(input)){
                        $.cookie("killphone",input,{expires:7,path:projectName+"/seckill"});
                        window.location.reload();//刷新页面
                    }else{
                        $("#phone_message").hide().html("<label class='label label-danger'>手机号错误！</label>").show(300);
                    }
                });
            }
            var startTime=params["startTime"];
            var endTime=params["endTime"];
            var id=params["seckillId"];
            $.get(seckill.url.now(),{},function (result) {
               if(result&&result["success"]){
                    var time=result["data"];
                    seckill.timedown(id,time,startTime,endTime);
               }else{
                   console.log("message:"+result["error"]);
               }
            });
        }
    }
};
