import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private Socket client = null;
    private ObjectOutputStream outputStream = null;
    private boolean estaConnectat = false;
    private static final String HOST = "localhost";
    private static final int PORT = 4445;

    public Cliente() {
    }

    public void communicate() {
        while (!estaConnectat) {
            try {
                client = new Socket(HOST, PORT);
                System.out.println("Cannectat");
                estaConnectat = true;

                outputStream = new ObjectOutputStream(client.getOutputStream());

                Persona persona = new Persona("Pepe", 25);
                System.out.printf("Objecte a enviar: %s - %d\n", persona.getNom(), persona.getEdad());

                outputStream.flush();

                outputStream.writeObject(persona);
                System.out.println("Enviat");

                outputStream.close();
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
