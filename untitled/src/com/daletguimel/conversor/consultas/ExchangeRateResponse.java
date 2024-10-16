package com.daletguimel.conversor.consultas;

public record ExchangeRateResponse(
        String result,
        String documentation,
        String terms_of_use,
        long time_last_update_unix,
        String time_last_update_utc,
        long time_next_update_unix,
        String time_next_update_utc,
        String base_code,
        ConversionRates conversion_rates) {
}
