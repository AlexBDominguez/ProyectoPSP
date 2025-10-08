package psp.practica2;

import java.io.*;
import java.lang.classfile.instruction.NewObjectInstruction;
import java.util.ArrayList;
import java.util.List;

public class Worker {
    public static void main(String[] args) {

        System.out.println("Proceso Worker ejecutándose...");

        // Obtenemos los argumentos
        String filePath = args[0];
        int inicioLinea = Integer.parseInt(args[1]);
        int finLinea = Integer.parseInt(args[2]);
        String archivoTemporal = args[3];

        //leemos el fichero y metemos sus líneas en un ArrayList
        File file = new File(filePath);
        String linea;
        int contador = 0;
        List<String> parrafo = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while ((linea= bufferedReader.readLine())!= null){
                if ((contador >= inicioLinea) && contador <= finLinea){
                    //limpiamos la línea
                    linea = linea.replaceAll("[\\p{Punct}’'“.]", "");

                    //La metemos en el arrayList
                    parrafo.add(linea.toLowerCase());
                }
                contador++;
            }
            for (String item: parrafo){
                //Separamos las palabras de cada línea del ArrayList
                String[] palabras = item.split(" ");

            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Para poder contar las palabras habrá que dividir por espacios y eliminar los signos de puntuación
        //¿Usamos un Map de <String, Integer> para guardar la palabra y su frecuencia?

    }

}

