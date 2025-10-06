package psp.practica2;

public class Worker {
    public static void main(String[] args) {

        String[] lines = args[0].split("ñ");

        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("FIN DE TRAMO");
    }

}

