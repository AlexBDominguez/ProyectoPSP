package psp.practica2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Master {

    public static void main(String[] args) throws IOException, InterruptedException {


        //Solicitar información al usuario

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce la ruta del archivo de texto: ");
        String filePath = sc.nextLine();

        System.out.println("Introduce cuántos procesos se va a dividir el trabajo: ");
        int numProcesses = sc.nextInt();
        int contadorLineas = 0;

        //Dividir el archivo en N bloques de líneas
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            while ((reader.readLine()) != null) {
                contadorLineas++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Líneas de cada parrafo
        int lineasParrafo = contadorLineas / numProcesses;

        //Concatenamos los párrafos para enviarlos al worker
        StringBuilder cadena = new StringBuilder();

        int contadorDePase = 0;
        String linea;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            for (int i = 0; i < numProcesses; i++) {
                while (((linea = reader.readLine()) != null) && (contadorDePase < lineasParrafo)) {
                    cadena.append(linea);
                    if(!(contadorDePase==lineasParrafo-1)){
                        cadena.append("ñ");
                    }
                    contadorDePase++;
                }
                //Obtenemos el classpath del proyecto
                String classpath = System.getProperty("java.class.path");
                //Comando para iniciar la segunda JVM
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", classpath, "src/main/java/psp/practica2/Worker.java", cadena.toString()
                );
                pb.inheritIO();
                Process process = pb.start();
                int exitCode = process.waitFor();

                //Reiniciamos los argumentos
                cadena.setLength(0);
                lineasParrafo+=lineasParrafo;

            }
        }
    }
}