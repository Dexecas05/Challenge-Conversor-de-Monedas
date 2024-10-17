package com.daletguimel.conversor.consultas;

import com.daletguimel.conversor.respuestas.ExchangeRateResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SolicitudStandard {
    private final String APIKEY = System.getenv("EXCHANGE_RATE_APIKEY");
    private String currency;

    public SolicitudStandard(String currency) {
        this.currency = currency;
    }

    public ExchangeRateResponse obtenerDatos() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + APIKEY + "/latest/" + currency;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Gson gson = new Gson();
        return gson.fromJson(responseBody, ExchangeRateResponse.class);
    }

    public String formatearRespuesta(ExchangeRateResponse respuesta){
        return "\nResultado: " + respuesta.result() + "\n" +
                "Última actualización: " + respuesta.time_last_update_utc() + "\n" +
                "Próxima actualización: " + respuesta.time_next_update_utc() + "\n" +
                "Moneda base: " + respuesta.base_code() + "\n" +
                "Indices de conversión: " + respuesta.conversion_rates();
    }

    @Override
    public String toString() {
        try {
            return "Resultado: " + obtenerDatos().result() + "\n" +
                    "Última actualización: " + obtenerDatos().time_last_update_utc() + "\n" +
                    "Próxima actualización: " + obtenerDatos().time_next_update_utc() + "\n" +
                    "Moneda base: " + obtenerDatos().base_code() + "\n" +
                    "Indices de conversión: " + obtenerDatos().conversion_rates();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
