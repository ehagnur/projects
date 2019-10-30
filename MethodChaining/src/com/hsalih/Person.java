package com.hsalih;

/**
 * Created by hsalih on 7/21/19.
 */
public class Person {
    private String name;
    private int age;
    private Person p;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void introduce(){
        System.out.println("Hello my name is " + name + " and I am " + age + " years old");
    }
}
