package com.hexploretech.spring_architecture.manufacturer.api;

import com.hexploretech.spring_architecture.manufacturer.CarStatus;
import com.hexploretech.spring_architecture.manufacturer.Engine;
import com.hexploretech.spring_architecture.manufacturer.HondaHRV;
import com.hexploretech.spring_architecture.manufacturer.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class ManufacturerController {
    @Autowired
    @Electric
    private Engine engine;

    @PostMapping
    public CarStatus startCarEngine(@RequestBody Key key) {
        var car = new HondaHRV(engine);
        return car.startEngine(key);
    }
}
