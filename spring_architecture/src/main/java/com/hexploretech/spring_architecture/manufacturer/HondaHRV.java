package com.hexploretech.spring_architecture.manufacturer;

import java.awt.*;

public class HondaHRV extends Car {

    public HondaHRV(Engine engine) {
        super(engine);
        setModel("HRV");
        setColor(Color.BLACK);
        setManufacturer(Manufacturer.HONDA);
    }
}
