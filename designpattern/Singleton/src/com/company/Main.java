package com.company;

public class Main {

    public static void main(String[] args) {
        PerformanceStage stage = PerformanceStage.getInstance();
        stage.turnOnLight();
        System.out.println(stage.getCounter());

        PerformanceStage stage2 = PerformanceStage.getInstance();
        System.out.println(stage2.getCounter());

    }
}
