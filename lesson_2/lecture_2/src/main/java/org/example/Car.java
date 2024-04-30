package org.example;

public class Car {
    public String name;
    public String price;
    public String engineType;
    public String engPower;
    public int maxSpeed;

    public Car(String name) {
        this.name = name;
        this.engineType = "V8";
        this.engPower = "123";
        this.maxSpeed = 100;
        this.price = "100000";
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
