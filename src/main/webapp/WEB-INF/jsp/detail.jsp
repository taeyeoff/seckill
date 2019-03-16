<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp"%>
    <script src="${pageContext.request.contextPath}/resources/script/seckill.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.countdown.js"></script>
    <script type="text/javascript">//页面加载完后执行，初始化
        $(function(){
            seckill.detail.init({
                seckillId:${secKill.seckillId},
                startTime:${secKill.startTime.time},
                endTime:${secKill.endTime.time},
                path:"${pageContext.request.contextPath}"
            });
        });
    </script>
</head>
<body>
 <div class="container">
     <div class="panel panel-default text-center">
         <div class="panel-heading">
             <h1>${secKill.name}</h1>
         </div>
         <div class="panel-body">
             <h2 class="text-danger">
                 <span class="glyphicon glyphicon-time"></span>
                 <span class="glyphicon" id="seckill-box"></span>
             </h2>
         </div>
     </div>
 </div>
<%--弹出输入手机号--%>
<div id="input_phone" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone">
                    </span>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="phone" id="phone" placeholder="填写手机号"
                        class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="phone_message" class="glyphicon"></span>
                <button type="button" id="phone_btn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    submit
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
