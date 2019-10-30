package com.company;

import org.junit.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BankAccountTest {

    private BankAccount account;
    private static int count;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("This executes before any test. Count = " + count++);
    }

    @Before
    public void setup(){
        account = new BankAccount("Hagos", "Salih", 1000.00, BankAccount.CHECKING);
        System.out.println("Running a test ... ");
    }

    @Test
    public void Deposit() throws Exception {
        BankAccount account = new BankAccount("Hagos", "Salih", 1000.00, BankAccount.CHECKING);
        double balance = account.deposit(200, true);
        assertEquals(1200.00, balance, 0);
    }

    @Test
    public void Withdraw_branch() throws Exception {
        double balance = account.withdraw(600.00, true);
        assertEquals(400.00, balance, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdraw_notBranch() throws Exception {
        account.withdraw(600.00, false);
    }

    @Test
    public void GetBalance_deposit() throws Exception {
        account.deposit(200, true);
        assertEquals(1200.00, account.getBalance(), 0);
    }

    @Test
    public void GetBalance_withdraw() throws Exception {
        account.withdraw(200, true);
        assertEquals(800.00, account.getBalance(), 0);
    }

    @Test
    public void isChecking_true() {
        assertTrue("The account is not checking account",account.isChecking());
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("This executes after all tests. Count = " + count++);
    }

    @After
    public void tearDown(){
        System.out.println("Count = " + count++);
    }
}