package org.seckill.web;

import org.seckill.entity.SecKill;
import org.seckill.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private SecKillService secKillService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<SecKill> list=secKillService.getSecKillList();
        model.addAttribute("list",list);
        return "/list";
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addItem(){
        Date date=new Date();
        long time=date.getTime()+1000L*60*60*24;
        SecKill secKill=new SecKill();
        secKill.setName("300元秒杀");
        secKill.setQuantity(20);
        secKill.setStartTime(date);
        secKill.setEndTime(new Date(time));
        secKillService.addItem(secKill);
        return "redirect:/list";
    }
}
