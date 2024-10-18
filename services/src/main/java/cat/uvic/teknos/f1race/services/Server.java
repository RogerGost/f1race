package cat.uvic.teknos.f1race.services.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static final int PORT = 80;

    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(PORT);

        while (true) {
            try (var clientSocket = serverSocket.accept()) {

            }
        }
    }
}
