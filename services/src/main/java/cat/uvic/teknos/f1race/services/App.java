package cat.uvic.teknos.f1race.services;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.services.controllers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));

        String p12File = Objects.requireNonNull(App.class.getResource("/keystore/client1.p12")).getPath();
        if (p12File == null) {
            throw new RuntimeException("Error: No s'ha trobat l'arxiu .p12 en el directori de recursos.");
        }        char[] password = "Teknos01.".toCharArray();

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(p12File)) {
            keyStore.load(fis, password);
        }

        String alias = "client1";
        PrivateKey serverPrivateKey = (PrivateKey) keyStore.getKey(alias, password);

        if (serverPrivateKey == null) {
            throw new RuntimeException("No es pot carregar la clau privada de l'arxiu .p12");
        }

        var repositoryFactory = (RepositoryFactory) Class.forName(properties.getProperty("repositoryFactory")).getConstructor().newInstance();
        var modelFactory = (ModelFactory) Class.forName(properties.getProperty("modelFactory")).getConstructor().newInstance();

        var controllers = new HashMap<String, Controller>();
        controllers.put("teams", new TeamController(repositoryFactory, modelFactory));
        controllers.put("drivers", new DriverController(repositoryFactory, modelFactory));
        controllers.put("cars", new CarController(repositoryFactory, modelFactory));
        controllers.put("racesResults", new RaceResultController(repositoryFactory, modelFactory));
        controllers.put("sponsors", new SponsorController(repositoryFactory, modelFactory));

        var requestRouter = new RequestRouterImplementation(controllers, serverPrivateKey);

        new Server(requestRouter).start();
    }
}
