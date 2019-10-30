package com.hsalih;

public class Main {

    public static void main(String[] args) {
        PersonMethodChaining p1 = new PersonMethodChaining();
        p1.setName("Hagos").setAge(37);
        p1.introduce();

        Person p2 = new Person();
        p2.setName("Seid");
        p2.setAge(34);
        p2.introduce();;
    }
}
