import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 은행 클래스
class Bank implements BankService {
    private Map<String, Double> accounts; // 고객 계좌번호와 잔액을 저장하는 맵

    public Bank() {
        accounts = new HashMap<>();
    }

    // 계좌 생성
    @Override
    public void createAccount(String accountNumber, double initialBalance) {
        accounts.put(accountNumber, initialBalance);
        System.out.println("계좌가 생성되었습니다.");
    }

    // 입금
    @Override
    public void deposit(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            double balance = accounts.get(accountNumber);
            balance += amount;
            accounts.put(accountNumber, balance);
            System.out.println("입금이 완료되었습니다. 현재 잔액: " + balance);
        } else {
            System.out.println("해당 계좌가 존재하지 않습니다.");
        }
    }

    // 출금
    @Override
    public void withdraw(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            double balance = accounts.get(accountNumber);
            if (balance >= amount) {
                balance -= amount;
                accounts.put(accountNumber, balance);
                System.out.println("출금이 완료되었습니다. 현재 잔액: " + balance);
            } else {
                System.out.println("잔액이 부족합니다.");
            }
        } else {
            System.out.println("해당 계좌가 존재하지 않습니다.");
        }
    }

    // 계좌 이체
    @Override
    public void transfer(String fromAccount, String toAccount, double amount) {
        if (accounts.containsKey(fromAccount) && accounts.containsKey(toAccount)) {
            double fromBalance = accounts.get(fromAccount);
            double toBalance = accounts.get(toAccount);
            if (fromBalance >= amount) {
                fromBalance -= amount;
                toBalance += amount;
                accounts.put(fromAccount, fromBalance);
                accounts.put(toAccount, toBalance);
                System.out.println("이체가 완료되었습니다.");
            } else {
                System.out.println("잔액이 부족합니다.");
            }
        } else {
            System.out.println("계좌번호를 확인해주세요.");
        }
    }

    // 계좌 잔액 확인
    @Override
    public double checkBalance(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber);
        } else {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            return -1; // 잘못된 계좌번호인 경우 -1 반환
        }
    }
}