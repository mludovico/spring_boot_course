package com.hexploretech.spring_architecture;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringArchitectureApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringArchitectureApplication.class);
        builder.bannerMode(Banner.Mode.OFF);
        builder.run(args);
    }

}
