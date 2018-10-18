package pl.coderslab.sportsbet2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "home page";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin page";
    }

    @RequestMapping("/home")





}
