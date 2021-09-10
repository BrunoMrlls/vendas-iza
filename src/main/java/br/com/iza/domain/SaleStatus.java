package br.com.iza.domain;

public enum SaleStatus {
    OPENED("Aberta"),
    CLOSED("Fechada"),
    CANCELED("Cancelada");

    private String description;

    SaleStatus(String description) {
        this.description = description;
    }
}
