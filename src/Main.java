import Data.Bank;
import Data.Client;

import java.io.*;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Vector<Bank> banks = new Vector<>();

        try {
            readBank(banks);

            readClient(banks);

            readAccount(banks);

            printAllData(banks);

            printBankData(banks);

            printBankClients(banks, "Bulzan Bank");

            printClientOfBank(banks, "Bulzan Bank", "Bulzan Dan-Alexandru");

            printAllClientAccounts(banks, "Bulzan Dan-Alexandru");

            addFunds(banks, "Bulzan Dan-Alexandru", "Bulzan Bank", 7, 1301.4f);

            printAllClientAccounts(banks, "Bulzan Dan-Alexandru");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void extractFunds(Vector<Bank> banks, String clientName, String bankName, int index, float funds) {
        banks.forEach((Bank b) -> {
            if (b.getName().equals(bankName)) b.extractClientFunds(clientName, index, funds);
        });
    }

    public static void addFunds(Vector<Bank> banks, String clientName, String bankName, int index, float funds) {
        banks.forEach((Bank b) -> {
            if (b.getName().equals(bankName)) b.addClientFunds(clientName, index, funds);
        });
    }

    public static void printAllClientAccounts(Vector<Bank> banks, String clientName) {
        banks.forEach((Bank b) -> {
            b.findClientPrint(clientName);
        });
    }

    public static void printClientOfBank(Vector<Bank> banks, String bankName, String clientName) {
        banks.forEach((Bank b) -> {
            if (b.getName().equals(bankName)) b.findClientPrint(clientName);
        });
    }

    public static void printBankClients(Vector<Bank> banks, String name) {
        banks.forEach((Bank b) -> {
            if (b.getName().equals(name)) {
                System.out.format("\n%s has the following clients: \n", name);
                b.printClients();
            }
        });
    }

    public static void printBankData(Vector<Bank> banks) {
        banks.forEach((Bank b) -> System.out.format("%s has %d clients\n", b.getName(), b.clientCount()));
    }

    public static void printAllData(Vector<Bank> banks) {
        banks.forEach((Bank b) -> {
            System.out.print(b);
            System.out.println(" has the following clients");
            b.printClientAccounts();
            System.out.println();
        });
    }

    public static void readBank(Vector<Bank> banks) throws IOException {
        File bankIN = new File("bankIN.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(bankIN))) {
            String line;

            while ((line = br.readLine()) != null) {
                var bank = new Bank(line);

                if (banks.stream()
                        .filter((Bank b) -> b.getName().equals(bank.getName()))
                        .toList().size() == 0
                ) banks.add(bank);
            }
        }
    }

    public static void readClient(Vector<Bank> banks) throws IOException {
        File clientIN = new File("clientIN.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(clientIN))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";+");
                var client = new Client(data[0], data[1]);

/*                banks.stream()
                        .filter((Bank b) -> b.getName().equals(data[2]))
                        .forEach((Bank b) -> b.addClient(client));*/

                banks.forEach((Bank b) -> {
                    if(b.getName().equals(data[2]))b.addClient(client);
                });
            }
        }
    }

    public static void readAccount(Vector<Bank> banks) throws IOException {
        File accountIN = new File("contIN.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(accountIN))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";+");
                var bank = banks.stream().filter((Bank b) -> b.getName().equals(data[1])).toList();
                if (bank.size() != 0) bank.get(0).addAccountForClient(data);
            }
        }
    }
}