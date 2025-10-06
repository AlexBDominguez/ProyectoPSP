package psp.practica1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchAndDestroy {
    public static void main(String[] args) {
        System.out.println("Listando procesos...");
        try {
            ProcessBuilder pb = new ProcessBuilder("tasklist");
            Process process = pb.start();

            // Captura y guarda la salida del comando "tasklist" en una List
            List<String> outputLines = getProcessOutputLines(process);

            String notepad = "notepad.exe";
            String calculadora = "calculatorapp.exe";
            for (String item: outputLines){

                if(item.toLowerCase().contains(notepad)){
                    System.out.println("Proceso encontrado: " + notepad);
                    killerFunction(notepad);
                } else {
                    System.out.println("No se encontraron instancias de " + notepad);
                }
                if(item.toLowerCase().contains(calculadora)){
                    System.out.println("Proceso encontrado: CalculatorApp.exe");
                    killerFunction(calculadora);
                } else {
                    System.out.println("No se encontraron instancias de " + calculadora);
                }
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getProcessOutputLines(Process process) throws IOException {

        List<String> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String linea;
        while (((linea = br.readLine()) != null) && (linea.toLowerCase().contains("notepad") || linea.toLowerCase().contains("calculator"))){
            lista.add(linea);
            System.out.println(linea);
        }
        return lista;
    }

    private static void killerFunction(String nombreProceso) throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", nombreProceso);
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Proceso " + nombreProceso + " ha sido terminado.");
        } else {
            System.out.println("Error al eliminar el proceso " + nombreProceso);
        }
    }
}
