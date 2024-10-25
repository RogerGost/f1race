package cat.uvic.teknos.f1race.services;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.services.controllers.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));

        var repositoryFactory = (RepositoryFactory) Class.forName(properties.getProperty("repositoryFactory")).getConstructor().newInstance();
        var modelFactory = (ModelFactory) Class.forName(properties.getProperty("modelFactory")).getConstructor().newInstance();

        var controllers =  new HashMap<String, Controller>();
        controllers.put("teams", new TeamController(repositoryFactory, modelFactory));
        controllers.put("drivers", new DriverController(repositoryFactory, modelFactory));
        controllers.put("cars", new CarController(repositoryFactory, modelFactory));
        controllers.put("racesResults", new RaceResultController(repositoryFactory, modelFactory));
        controllers.put("sponsors", new SponsorController(repositoryFactory, modelFactory));
        var requestRouter = new RequestRouterImplementation(controllers);
        new Server(requestRouter).start();
    }
}
