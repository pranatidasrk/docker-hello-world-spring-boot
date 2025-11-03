package com.dockerforjavadevelopers.hello;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "congrats!! pranati and sangu and selena , your first deployment to the minikube and bless us to call your name every moment . please give us peace";
    }
    
}
