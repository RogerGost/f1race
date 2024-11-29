package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.dto.TeamDto;
import cat.uvic.teknos.f1race.clients.console.exceptions.RequestException;
import cat.uvic.teknos.f1race.clients.console.utils.Mappers;
import cat.uvic.teknos.f1race.clients.console.utils.RestClientImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.*;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Properties;
import java.util.stream.Collectors;

public class App {
    private static final PublicKey publicServerKey;
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintStream out = System.out;
    private static final RestClientImpl restClient;

    static {
        try {
            Properties properties = new Properties();
            var propertiesStream = App.class.getResourceAsStream("/app.properties");
            if (propertiesStream == null) {
                throw new FileNotFoundException("Error: No s'ha trobat l'arxiu de configuració 'app.properties'.");
            }
            properties.load(propertiesStream);

            String host = properties.getProperty("server.host", "localhost");
            int port = Integer.parseInt(properties.getProperty("server.port", "8080"));
            String certPath = properties.getProperty("server.cert.path", "/server.cert");

            publicServerKey = loadPublicKeyFromCert(certPath);
            restClient = new RestClientImpl(host, port, publicServerKey);
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar l'aplicació: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        var command = "";
        try {
            do {
                showBanner();
                showMainMenu();
                command = readLine(in);

                switch (command) {
                    case "1" -> managerTeam();
                    case "2" -> managerCar();
                    case "3" -> managerDriver();
                    case "4" -> managerRace();
                    case "5" -> managerSponsor();
                }
            } while (!command.equalsIgnoreCase("exit"));

            out.println("Fin del programa");
        } catch (Exception e) {
            out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void showMainMenu() {
        out.println("1. Team");
        out.println("2. Car");
        out.println("3. Driver");
        out.println("4. RaceResult");
        out.println("5. Sponsor");
    }

    private static String readLine(BufferedReader in) {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error al llegir la opció del menú: " + e.getMessage(), e);
        }
    }

    private static void managerTeam() throws RequestException, JsonProcessingException {
        TeamManager teamManager = new TeamManager(restClient, in);
        teamManager.start();
    }

    private static void managerCar() throws RequestException, JsonProcessingException {
        CarManager carManager = new CarManager(restClient, in);
        carManager.start();
    }

    private static void managerDriver() throws RequestException, JsonProcessingException {
        DriverManager driverManager = new DriverManager(restClient, in);
        driverManager.start();
    }

    private static void managerRace() throws RequestException, JsonProcessingException {
        RaceResultManager raceResultManager = new RaceResultManager(restClient, in);
        raceResultManager.start();
    }

    private static void managerSponsor() throws RequestException, JsonProcessingException {
        SponsorManager sponsorManager = new SponsorManager(restClient, in);
        sponsorManager.start();
    }

    private static void showBanner() {
        try (var bannerStream = App.class.getResourceAsStream("/banner.txt")) {
            if (bannerStream == null) {
                out.println("Error: No s'ha trobat l'arxiu del banner.");
                return;
            }

            var banner = new BufferedReader(new InputStreamReader(bannerStream))
                    .lines().collect(Collectors.joining("\n"));

            out.println(banner);
        } catch (IOException e) {
            out.println("Error al cargar el banner: " + e.getMessage());
        }
    }

    public static PublicKey loadPublicKeyFromCert(String certFile) throws Exception {
        try (var certStream = App.class.getResourceAsStream(certFile)) {
            if (certStream == null) {
                throw new FileNotFoundException("Error: No s'ha trobat l'arxiu  del certificat en la ruta especificada: " + certFile);
            }
            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(certStream);
            return certificate.getPublicKey();
        }
    }
}
