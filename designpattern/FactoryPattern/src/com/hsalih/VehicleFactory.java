package com.hsalih;

/**
 * Created by hsalih on 7/30/19.
 */
public class VehicleFactory {
    public Vehicle getVehicle(VehicleType vehicleType){
        return vehicleType.getVehicle();
    }
}
