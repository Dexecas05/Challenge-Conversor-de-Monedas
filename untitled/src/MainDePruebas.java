import com.daletguimel.conversor.consultas.SolicitudStandard;

import java.io.IOException;

public class MainDePruebas {
    public static void main(String[] args) {

        SolicitudStandard solicitudStandard = new SolicitudStandard("ARS");
        try {
            solicitudStandard.obtenerDatos();
            System.out.println("¡Solicitud exitosa!");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
