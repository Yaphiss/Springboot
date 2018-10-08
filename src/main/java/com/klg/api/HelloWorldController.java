package com.klg.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by yaphiss on 2018/6/10.
 */

@RestController
public class HelloWorldController {
    private static Logger logger = Logger.getLogger(HelloWorldController.class);

    @Autowired private Environment environment;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index (){
        return "FINISH! SYSTEM ON "+environment.getProperty("env")+" IS RUNNING ";
    }

}
