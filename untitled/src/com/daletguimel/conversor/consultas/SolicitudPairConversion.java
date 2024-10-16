package com.daletguimel.conversor.consultas;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SolicitudPairConversion {
    private final String APIKEY = System.getenv("EXCHANGE_RATE_APIKEY");
    private String baseCurrency;
    private String targetCurrency;
    private double amount;

    public SolicitudPairConversion(String baseCurrency, String targetCurrency, double amount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    public ExchangeRateResponse obtenerDatos() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + APIKEY + "/pair/" + baseCurrency + "/" + targetCurrency + "/" + amount;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Gson gson = new Gson();
        return gson.fromJson(responseBody, ExchangeRateResponse.class);
    }

    public String formatearRespuesta(ExchangeRateResponse respuesta) {
        return "Resultado: " + respuesta.result() + "\n" +
                "Última actualización: " + respuesta.time_last_update_utc() + "\n" +
                "Próxima actualización: " +respuesta.time_next_update_utc() + "\n" +
                "Moneda base: " + respuesta.base_code() + "\n" +
                "Moneda objetivo: " + respuesta.target_code() + "\n" +
                "Ratio de conversión: " + respuesta.conversion_rate() + "\n" +
                "Resultado de la conversión: " + respuesta.conversion_result();
    }

    @Override
    public String toString() {
        try {
            return "Resultado: " + obtenerDatos().result() + "\n" +
                    "Última actualización: " + obtenerDatos().time_last_update_utc() + "\n" +
                    "Próxima actualización: " + obtenerDatos().time_next_update_utc() + "\n" +
                    "Moneda base: " + obtenerDatos().base_code() + "\n" +
                    "Moneda objetivo: " + obtenerDatos().target_code() + "\n" +
                    "Ratio de conversión: " + obtenerDatos().conversion_rate() + "\n" +
                    "Resultado de la conversión: " + obtenerDatos().conversion_result();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
