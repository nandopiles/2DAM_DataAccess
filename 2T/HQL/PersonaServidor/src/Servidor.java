import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private ServerSocket server = null;
    private Socket client = null;
    private ObjectInputStream inStream = null;

    public void comunica() {
        try {
            server = new ServerSocket();
            System.out.println("Servidor en espera de connexió");
            client = server.accept();
            System.out.println("Rebut");

            Persona persona = (Persona) inStream.readObject();
            System.out.printf("Objecte rebut: %s - %d\n", persona.getNom(), persona.getEdad());

            inStream.close();
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.comunica();
    }
}
