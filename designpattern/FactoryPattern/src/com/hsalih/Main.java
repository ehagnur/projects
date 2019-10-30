package com.hsalih;

public class Main {

    public static void main(String[] args) {
        VehicleFactory vehicleFactory = new VehicleFactory();
        Vehicle vehicle = vehicleFactory.getVehicle(VehicleType.TRUCK);
        vehicle.startEngine();

        Vehicle eVehicle = vehicleFactory.getVehicle(VehicleType.ELECTRIC);
        eVehicle.startEngine();
    }
}
