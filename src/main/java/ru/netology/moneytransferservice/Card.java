package ru.netology.moneytransferservice;

import java.util.concurrent.atomic.AtomicInteger;

public class Card {
    private final String cardNumber;
    private final String expiryDate;
    private final String cvv;
    private final AtomicInteger balance;
    private final AtomicInteger changeBalance;

    public Card(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        balance = new AtomicInteger(0);
        changeBalance = new AtomicInteger(0);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public int getBalance() {
        return balance.get();
    }

    public void incrementMoney(Integer money) {
        this.balance.getAndAdd(money);
    }

    public void decrementMoney(Integer money) {
        this.balance.getAndAdd(-money);
    }

    public int getChangeBalance() {
        return changeBalance.get();
    }

    public void setChangeBalance(Integer money) {
        this.changeBalance.getAndSet(money);
    }
}
