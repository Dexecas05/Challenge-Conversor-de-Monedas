package com.daletguimel.conversor.historial;

public class HistorialDeConversiones {
    private String baseCurrency;
    private String targetCurrency;
    private double amount;
    private double result;
    private String timestamp;

    public HistorialDeConversiones(String baseCurrency,
                                   String targetCurrency,
                                   double amount,
                                   double result,
                                   String timestamp) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.result = result;
        this.timestamp = timestamp;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public double getResult() {
        return result;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
