package com.hsalih;

/**
 * Created by hsalih on 7/30/19.
 */
public enum VehicleType {
    TRUCK{
        public Vehicle getVehicle(){
            return new Truck();
        }
    },
    CAR{
        public Vehicle getVehicle(){
            return new Car();
        }
    },
    ELECTRIC{
        public Vehicle getVehicle(){
            return new ElectricCar();
        }
    };

    abstract Vehicle getVehicle();
}
