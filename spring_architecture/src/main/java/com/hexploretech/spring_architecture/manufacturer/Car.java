package com.hexploretech.spring_architecture.manufacturer;

import java.awt.*;

public class Car {
    private String model;
    private Color color;
    private Engine engine;
    private Manufacturer manufacturer;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", engine=" + engine +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public CarStatus startEngine(Key key) {
        if (key.getManufacturer() == manufacturer) {
            return new CarStatus("Successfully started the engine" + engine);
        } else {
            return new CarStatus("Failed to start the engine");
        }
    }
}
