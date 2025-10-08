package psp.practica2;

import java.io.*;
import java.util.Scanner;

public class Master {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        //Solicitamos información al usuario
        //System.out.println("Introduce la ruta del archivo de texto: ");
        //String filePath = sc.nextLine();
        String filePath = "src/main/java/resources/FrankensteinPSP.txt";

        System.out.println("Introduce cuántos procesos se va a dividir el trabajo: ");
        int numProcesos = sc.nextInt();

        //Validamos el archivo
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.err.println("Error: El archivo especificado no existe o no es válido.");
            return;
        }

        if (numProcesos <= 0) {
            System.err.println("Error: El número de procesos debe ser mayor que 0.");
            return;
        }

        //Contador de líneas del archivo
        int contadorLineas = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                contadorLineas++;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        if (contadorLineas == 0) {
            System.err.println("Error: El archivo está vacío.");
            return;
        }

        //Calcular líneas por proceso
        int lineasPorProceso = contadorLineas / numProcesos;
        int lineasRestantes = contadorLineas % numProcesos;

        System.out.println("Total de líneas: " + contadorLineas);
        System.out.println("Líneas por proceso: " + lineasPorProceso);
        System.out.println("Iniciando " + numProcesos + " procesos Worker...");
        System.out.println();

        //Procesos Worker
        for (int i = 0; i < numProcesos; i++) {
            //Multiplicamos el índice por el número de líneas de cada proceso
            //Así sabemos en qué número de línea empieza cada uno
            int inicioLinea = i * lineasPorProceso;
            int finLinea = inicioLinea + lineasPorProceso - 1;

            // Asignar líneas restantes al último proceso
            if (i == numProcesos - 1) {
                finLinea += lineasRestantes;
            }

            String archivoTemporal = "temp_worker_" + i + ".txt";

            System.out.println("Proceso " + (i + 1) + ": líneas " + inicioLinea + " a " + finLinea);

            //Obtener el classpath del proyecto
            String classpath = System.getProperty("java.class.path");

            //Iniciamos el Worker
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", classpath, "psp.practica2.Worker",
                    filePath, String.valueOf(inicioLinea), String.valueOf(finLinea), archivoTemporal
            );

            pb.inheritIO();
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                System.err.println("Error: El proceso Worker " + (i + 1) + " terminó con código: " + exitCode);
            } else {
                System.out.println("Proceso Worker " + (i + 1) + " completado exitosamente.");
            }
        }

        System.out.println("Todos los procesos Worker han terminado.");
        sc.close();
    }
}