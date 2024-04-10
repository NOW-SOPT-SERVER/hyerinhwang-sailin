import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        BankApp bankApp = new BankApp(bank);
        bankApp.start();
    }
}