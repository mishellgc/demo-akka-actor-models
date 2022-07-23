/*
* El método main de Akka recibe como argumento el nombre de la clase del actor principal de la aplicación
* */
public final class Main {
    public static void main(String[] args) {
        akka.Main.main(new String[]{Cliente.class.getName()});
    }
}