package de.htwberlin.webtech.LeiLei.HelloWorld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @GetMapping(path="/favicon.ico")
    public ModelAndView showHelloWorldPage () {
        return new ModelAndView("helloworld");
    }
}
