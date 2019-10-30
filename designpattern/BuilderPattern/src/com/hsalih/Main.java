package com.hsalih;

public class Main {

    public static void main(String[] args) {
        User test_user1 = new User.Builder("user123", "user123@company.com").build();
        User test_user2 = new User.Builder("user245", "user245@anothercompany.com").firstName("bob").lastName("Mayer").phoneNumber(408900800).address("1700 Park Ave, San Jose,CA").build();
        System.out.println(test_user1);
        System.out.println(test_user2);
    }
}
