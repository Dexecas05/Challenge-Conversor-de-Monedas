package com.daletguimel.conversor.historial;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class HistorialManager {
    private static final String HISTORIAL_FILE = "historial.json";
    private Gson gson = new Gson();

    public void guardarHistorial(List<HistorialDeConversiones> historial) throws IOException {
        try (FileWriter writer = new FileWriter(HISTORIAL_FILE)) {
            gson.toJson(historial, writer);
        }
    }

    public List<HistorialDeConversiones> cargarHistorial() throws IOException {
        try (Reader reader = new FileReader(HISTORIAL_FILE)) {
            Type listType = new TypeToken<List<HistorialDeConversiones>>(){}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
