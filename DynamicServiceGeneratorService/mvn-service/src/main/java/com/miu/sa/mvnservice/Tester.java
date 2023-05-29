package com.miu.sa.mvnservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class Tester {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
//        Tester tester = new Tester();
//        String os = System.getProperty("os.name");
//
//        if (os.contains("Mac")) {
//            System.out.println(tester.env.getProperty("mvn.home.mac"));
//        } else {
//            System.out.println(tester.env.getProperty("mvn.home.windows"));
//        }


        Set<String> testerString = Set.of("adfsasf","wsdfadf");
        System.out.println(testerString);
    }
}
