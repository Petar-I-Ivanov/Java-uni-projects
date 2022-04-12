package bank;

import bank.money.Money;

import java.util.ArrayList;
import java.util.Scanner;

public class Wallet {

    private String userName;
    private String bankName;

    private boolean isBlocked = false;

    private ArrayList<Money> wallet = new ArrayList<>();

    public Wallet (String userName, String bankName) {
        this.userName = userName;
        this.bankName = bankName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public void setBlocked() {
        this.isBlocked = true;
    }

    public void listWallet() {

        System.out.println(this.userName + ":");

        for (Money m : this.wallet) {
            System.out.println(m.getCode() + " - " + m.getValue());
        }
    }

    public void addOrSubMoney(Money money, boolean isAdding) {

        for (Money m : this.wallet) {

            if (m.getCode().equals(money.getCode())) {

                if (isAdding) {
                    m.addValue(money.getValue());
                }
                else {
                    m.subValue(money.getValue());
                }

                return;
            }
        }

        this.wallet.add(money);
    }

    public Money pickMoney() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Изберете от налична валута за изпращане:");
            int i = 1;

            for (Money m : this.wallet) {
                System.out.println(i++ + ". " + m.getCode() + " - " + m.getValue());
            }

            choice = scanner.nextInt();

        } while (choice > this.wallet.size() || choice < 1);

        return this.wallet.get(choice - 1);
    }

    public boolean isMoneyAvailable(Money money, Money tax) {

        boolean isMoneyEnough = false;
        boolean isTaxEnough   = false;

        for (Money m : this.wallet) {

            if (m.getCode().equals(money.getCode()) && m.getCode().equals(tax.getCode())) {
                return m.getValue() >= (money.getValue() + tax.getValue());
            }

            if (m.getCode().equals(money.getCode())) {
                isMoneyEnough = true;
            }

            if (m.getCode().equals(tax.getCode())) {
                isTaxEnough = true;
            }
        }

        return isMoneyEnough && isTaxEnough;
    }
}