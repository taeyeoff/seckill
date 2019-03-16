package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.dto.SecKillResult;
import org.seckill.entity.SecKill;
import org.seckill.enums.SecKillStateEnum;
import org.seckill.exception.InsertException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    private SecKillService secKillService;
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/{secKillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("secKillId") Long secKillId, Model model){
        if(secKillId==null){
            return "redirect:/seckill/list";
        }
        SecKill secKill=secKillService.getSecKillById(secKillId);
        if(secKill==null){
            return "redirect:/seckill/list";
        }
        model.addAttribute("secKill",secKill);
        return "/detail";
    }
    @RequestMapping(value = "/{secKillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable("secKillId") Long secKillId){
        SecKillResult<Exposer> result;
        try{
           Exposer exposer=secKillService.exportSecKillUrl(secKillId);
           result=new SecKillResult<Exposer>(true,exposer);
        }catch (Exception e){
           logger.error(e.getMessage(),e);
           result=new SecKillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/{secKillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(@PathVariable("secKillId") Long secKillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killphone",required = false) String phone){
        if(phone==null){
            return new SecKillResult<SecKillExecution>(false,"未注册");
        }
        SecKillResult<SecKillExecution> result;
        try{
            SecKillExecution secKillExecution=secKillService.executeSecKillUseP(secKillId,phone,md5);
            return result=new SecKillResult<SecKillExecution>(true,secKillExecution);
        }catch (InsertException e){
            SecKillExecution execution=new SecKillExecution(secKillId, SecKillStateEnum.INSERT_ERROR);
            return result=new SecKillResult<SecKillExecution>(true,execution);
        }catch (SecKillCloseException e){
            SecKillExecution execution=new SecKillExecution(secKillId, SecKillStateEnum.END);
            return result=new SecKillResult<SecKillExecution>(true,execution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            SecKillExecution execution=new SecKillExecution(secKillId, SecKillStateEnum.INNER_ERROR);
            return result=new SecKillResult<SecKillExecution>(true,execution);
        }

    }
    @RequestMapping(value = "/now",method = RequestMethod.GET)
    @ResponseBody
    public SecKillResult<Long> time(){
        Date now=new Date();
        return new SecKillResult(true,now.getTime());
    }
}
