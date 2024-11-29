package cat.uvic.teknos.f1race.services;

import cat.uvic.teknos.f1race.services.exeption.ServerExeption;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final String Path = "services/src/main/resources/server.properties";
    private int PORT;
    private final RequestRouter requestRouter;
    private volatile boolean shutdownServer;
    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    public Server(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(Path)) {
            properties.load(fis);
            this.PORT = Integer.parseInt(properties.getProperty("server.port", "8080"));
            int threadPoolSize = Integer.parseInt(properties.getProperty("server.threadPoolSize", "10"));
            this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        } catch (IOException e) {
            throw new ServerExeption("Error loading server properties", e);
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            new Thread(this::monitorShutdown).start();

            while (!shutdownServer) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.execute(() -> handleClientRequest(clientSocket));
                } catch (IOException e) {
                    if (shutdownServer) {
                        System.out.println("Server s'esta apagant...");
                    } else {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new ServerExeption(e);
        } finally {
            shutdown();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try (clientSocket) {
            var rawHttp = new RawHttp(RawHttpOptions.newBuilder().doNotInsertHostHeaderIfMissing().build());
            var request = rawHttp.parseRequest(clientSocket.getInputStream());

            var response = requestRouter.execRequest(request);
            response.writeTo(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        }
    }

    private void monitorShutdown() {
        while (!shutdownServer) {
            try {
                Properties properties = new Properties();
                try (FileInputStream fis = new FileInputStream(Path)) {
                    properties.load(fis);
                    String shutdownValue = properties.getProperty("shutdown", "false");
                    if ("true".equalsIgnoreCase(shutdownValue)) {
                        shutdownServer = true;
                        shutdown();
                    }
                }
                Thread.sleep(5000);
            } catch (IOException | InterruptedException e) {
                System.err.println("Error checking shutdown status: " + e.getMessage());
            }
        }
    }

    public void shutdown() {
        shutdownServer = true;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
        threadPool.shutdown();
        System.out.println("Server apagat.");
    }
}