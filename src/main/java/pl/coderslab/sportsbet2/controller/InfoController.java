package pl.coderslab.sportsbet2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InfoController {

    @RequestMapping ("/instruction")
    public String howToPlay(){
        return "instruction";
    }

    @RequestMapping ("/about-me")
    public String aboutMe(){
        return "about-me";
    }


    @RequestMapping ("/contact")
    public String contactDetails(){
        return "contact";
    }
}
