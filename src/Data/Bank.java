package Data;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int clientCount() {
        return clients.size();
    }

    public void extractClientFunds(String clientName, int index, float funds) {
        clients.forEach((Client c) -> {
            if (c.getName().equals(clientName)) c.extractFundsAccount(index, funds);
        });
    }

    public void addClientFunds(String clientName, int index, float funds) {
        clients.forEach((Client c) -> {
            if (c.getName().equals(clientName)) c.addFundsAccount(index, funds);
        });
    }

    public void addClient(Client client) {
        if (clients.stream()
                .filter((Client c) -> c.getName().equals(client.getName()))
                .toList().size() == 0
        ) clients.add(client);
    }

    public void addAccountForClient(String[] formattedData) {
        String clientName = formattedData[0];
        var client = findClient(clientName);
        if (client == null) return;

        var account = new Account(formattedData[2], formattedData[3], Float.parseFloat(formattedData[4]));
        if (!client.addAccount(account)) System.out.println("Could not add account, too many exist");
    }

    private Client findClient(String clientName) {
        for (var client : clients) {
            if (client.getName().equals(clientName)) return client;
        }
        return null;
    }

    public void findClientPrint(String clientName) {
        clients.forEach((Client c) -> {
            if (c.getName().equals(clientName)) {
                System.out.format("\n%s has the following accounts in Bank %s\n", c.getName(), name);
                c.printAccounts();
            }
        });
    }

    public void printClients() {
        clients.forEach(System.out::println);
    }

    public void printClientAccounts() {
        clients.forEach((Client c) -> {
            System.out.print("\t" + c);
            System.out.println(" with the following accounts");
            c.printAccounts();
        });
    }

    @Override
    public String toString() {
        return String.format("Bank %s", name);
    }

    private String name;
    private List<Client> clients = new ArrayList<>();
}
