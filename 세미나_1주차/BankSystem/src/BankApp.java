import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// BankApp 클래스
class BankApp {
    private BankService bank;

    public BankApp(BankService bank) {
        this.bank = bank;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // 테스트를 위해 미리 계좌 생성
        bank.createAccount("1111", 1000);
        bank.createAccount("2222", 2000);

        while (true) {
            System.out.println("===== 은행 프로그램 =====");
            System.out.println("1. 계좌 생성");
            System.out.println("2. 입금");
            System.out.println("3. 출금");
            System.out.println("4. 계좌 이체");
            System.out.println("5. 계좌 잔액 확인");
            System.out.println("6. 종료");
            System.out.print("메뉴를 선택하세요: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("계좌번호를 입력하세요: ");
                    String newAccountNumber = scanner.next();
                    System.out.print("초기 잔액을 입력하세요: ");
                    double initialBalance = scanner.nextDouble();
                    bank.createAccount(newAccountNumber, initialBalance);
                    break;
                case 2:
                    System.out.print("입금할 계좌번호를 입력하세요: ");
                    String depositAccount = scanner.next();
                    System.out.print("입금할 금액을 입력하세요: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(depositAccount, depositAmount);
                    break;
                case 3:
                    System.out.print("출금할 계좌번호를 입력하세요: ");
                    String withdrawAccount = scanner.next();
                    System.out.print("출금할 금액을 입력하세요: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(withdrawAccount, withdrawAmount);
                    break;
                case 4:
                    System.out.print("이체할 송금 계좌번호를 입력하세요: ");
                    String fromAccount = scanner.next();
                    System.out.print("이체할 수신 계좌번호를 입력하세요: ");
                    String toAccount = scanner.next();
                    System.out.print("이체할 금액을 입력하세요: ");
                    double transferAmount = scanner.nextDouble();
                    bank.transfer(fromAccount, toAccount, transferAmount);
                    break;
                case 5:
                    System.out.print("잔액을 확인할 계좌번호를 입력하세요: ");
                    String checkAccount = scanner.next();
                    double balance = bank.checkBalance(checkAccount);
                    if (balance != -1) {
                        System.out.println("계좌 잔액: " + balance);
                    }
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
            }
        }
    }
}