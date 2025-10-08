package psp.practica2;

public class Worker {
    public static void main(String[] args) {

        System.out.println("Proceso Worker ejecutándose...");

        // Obtenemos los argumentos
        String filePath = args[0];
        int inicioLinea = Integer.parseInt(args[1]);
        int finLinea = Integer.parseInt(args[2]);
        String archivoTemporal = args[3];


        //Para poder contar las palabras habrá que dividir por espacios y eliminar los signos de puntuación
        //¿Usamos un Map de <String, Integer> para guardar la palabra y su frecuencia?

    }

}

