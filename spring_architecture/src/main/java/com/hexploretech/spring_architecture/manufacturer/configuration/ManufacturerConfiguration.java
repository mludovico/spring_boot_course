package com.hexploretech.spring_architecture.manufacturer.configuration;

import com.hexploretech.spring_architecture.manufacturer.Engine;
import com.hexploretech.spring_architecture.manufacturer.EngineType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManufacturerConfiguration {

    @Bean(name = "petrolEngine")
    public Engine petrolEngine() {
        var engine = new Engine();
        engine.setEngineType(EngineType.PETROL);
        engine.setHorsePower(120);
        engine.setCylinders(4);
        engine.setDisplacement(1.5);
        engine.setModel("L15B7");
        return engine;
    }

    @Bean(name = "dieselEngine")
    public Engine dieselEngine() {
        var engine = new Engine();
        engine.setEngineType(EngineType.DIESEL);
        engine.setHorsePower(100);
        engine.setCylinders(4);
        engine.setDisplacement(1.6);
        engine.setModel("N16A7");
        return engine;
    }

    @Bean(name = "electricEngine")
    public Engine electricEngine() {
        var engine = new Engine();
        engine.setEngineType(EngineType.ELECTRIC);
        engine.setHorsePower(150);
        engine.setCylinders(0);
        engine.setDisplacement(0);
        engine.setModel("E150");
        return engine;
    }
}
