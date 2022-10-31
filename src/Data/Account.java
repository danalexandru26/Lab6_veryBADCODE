package Data;

import Interfaces.Operations;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Account implements Operations {
    public Account(String currency, String openDate, float funds) {
        this.accountID = String.valueOf(counterID);
        this.currency = currency;
        this.funds = funds;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.openDate = LocalDate.parse(openDate, formatter);

        ++counterID;
        lastOperation = LocalDate.now();
    }

    public int getID() {
        return Integer.parseInt(accountID);
    }

    @Override
    public float interest() {
        var days = (float) ChronoUnit.DAYS.between(openDate, LocalDate.now());

        if (currency.equals("RON")) {
            if (funds < 500) return 0.3f * days;
            else return 0.8f * days;
        }
        return 0.1f * days;
    }

    @Override
    public float updateFunds() {
        funds += interest();
        lastOperation = LocalDate.now();

        return funds;
    }

    @Override
    public void addFunds(float funds) {
        this.funds += funds;
        lastOperation = LocalDate.now();
    }

    @Override
    public void extractFunds(float funds) {
        if (funds <= this.funds) this.funds -= funds;
        lastOperation = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("\t\tAccount %s with open date %s and currency %s has %f funds",
                accountID, openDate.toString(), currency, funds);
    }

    private String accountID;
    private String currency;
    private LocalDate openDate;
    private LocalDate lastOperation;
    private float funds;
    private static int counterID;
}