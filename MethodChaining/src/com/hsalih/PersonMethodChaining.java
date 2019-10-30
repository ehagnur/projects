package com.hsalih;

import java.io.Serializable;

/**
 * Created by hsalih on 7/21/19.
 */
public class PersonMethodChaining implements Serializable{
    private String name;
    private int age;

    public PersonMethodChaining setName(String name) {
        this.name = name;
        return this;
    }

    public PersonMethodChaining setAge(int age) {
        this.age = age;
        return this;
    }

    public void introduce(){
        System.out.println("Hello my name is " + name + " and I am " + age + " years old");
    }
}
