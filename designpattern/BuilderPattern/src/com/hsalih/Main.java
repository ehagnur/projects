package com.hsalih;

public class Main {

    public static void main(String[] args) {
        User test_user1 = new User.Builder("user123", "user123@company.com").build();
        User test_user2 = new User.Builder("user245", "user245@anothercompany.com").firstName("first_name").lastName("last_name").phoneNumber(1234567890).address("1234 main st., City,ST").build();
        System.out.println(test_user1);
        System.out.println(test_user2);
    }
}
