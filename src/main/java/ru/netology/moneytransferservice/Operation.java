package ru.netology.moneytransferservice;


public class Operation {
    private final String cardFromNumber;
    private final String cardToNumber;
    private final String cardFromCVV;
    private final String cardFromValidTill;
    private final Amount amount;

    public Operation(String cardFromNumber,
                     String cardToNumber,
                     String cardFromCVV,
                     String cardFromValidTill,
                     Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
    }

    public String getFromCard() {
        return cardFromNumber;
    }

    public String getToCard() {
        return cardToNumber;
    }

    public String getFromCVV() {
        return cardFromCVV;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public int getMoneyValue() {
        return amount.getAmount() / 100;
    }

    public int getComissionValue() {
        return getMoneyValue() / 100;
    }

    public String toString() {
        return "FromCard: " + cardFromNumber +
                " | ToCard: " + cardToNumber +
                " | FromCVV: " + cardFromCVV +
                " | FromValidTill: " + cardFromValidTill +
                " | MoneyValue: " + getMoneyValue();
    }
}
