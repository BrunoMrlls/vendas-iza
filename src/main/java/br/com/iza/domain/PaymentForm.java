package br.com.iza.domain;

public enum PaymentForm {
    CREDIT_CARD("Cartão de crédito"),
    DEBIT_CARD("Cartão de débito"),
    CASH("Dinheiro"),
    GIFT_TICKET("Vale presente");

    private String description;

    PaymentForm(String description) {
        this.description = description;
    }
}
