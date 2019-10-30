package com.company;

/**
 * Created by hsalih on 7/30/19.
 */
public class PerformanceStage {

    private static PerformanceStage INSTANCE = null;
    private static int counter;
    //No public constructor for Singleton
    private PerformanceStage(){
        counter++;
    }

    //synchronized is used to make it thread safe
    public synchronized static PerformanceStage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PerformanceStage();
        }
        return INSTANCE;
    }

    public void turnOnLight(){
        System.out.println("turned on lights ...");
    }

    public int getCounter(){
        return counter;
    }
}