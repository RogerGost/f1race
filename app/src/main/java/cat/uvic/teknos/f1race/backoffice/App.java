/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cat.uvic.teknos.f1race.backoffice;


import cat.uvic.teknos.f1race.domain.jbdc.models.JdbcModelFactory;
import cat.uvic.teknos.f1race.domain.jbdc.repositories.JdbcRepositoryFactory;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;

public class App {

    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = new JdbcRepositoryFactory();
        ModelFactory modelFactory = new JdbcModelFactory();

        var backOffice = new BackOffice(System.in, System.out, repositoryFactory, modelFactory);

        backOffice.start();
    }
}
