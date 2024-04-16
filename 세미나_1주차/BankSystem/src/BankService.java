// 은행 서비스 인터페이스
interface BankService {
    void createAccount(String accountNumber, double initialBalance);
    void deposit(String accountNumber, double amount);
    void withdraw(String accountNumber, double amount);
    void transfer(String fromAccount, String toAccount, double amount);
    double checkBalance(String accountNumber);
}