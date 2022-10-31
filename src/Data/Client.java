package Data;

import java.util.HashSet;
import java.util.Set;

public class Client {
    public Client(String name, String address) {
        this.name = name;
        this.address = address;

        accounts.add(new Account("EUR", "01.01.2020", 499));
    }

    public String getName() {
        return name;
    }

    public void extractFundsAccount(int index, float funds) {
        accounts.forEach((Account a) -> {
            if(a.getID() == index)a.extractFunds(funds);
        });
    }

    public void addFundsAccount(int index, float funds) {
        accounts.forEach((Account a) -> {
            if(a.getID() == index)a.addFunds(funds);
        });
    }

    public boolean addAccount(Account data) {
        if (accounts.size() > 5) return false;

        accounts.add(data);
        return true;
    }

    public void printAccounts() {
        accounts.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return String.format("%s with address %s", name, address);
    }

    String name;
    String address;
    Set<Account> accounts = new HashSet<>();
}
