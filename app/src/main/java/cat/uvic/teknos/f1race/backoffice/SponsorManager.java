package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Arrays;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.readLine;

public class SponsorManager {
    private final PrintStream out;
    private final BufferedReader in;
    private final SponsorRepository sponsorRepository;
    private final ModelFactory modelFactory;

    public SponsorManager(BufferedReader in, PrintStream out, SponsorRepository sponsorRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.sponsorRepository = sponsorRepository;
        this.modelFactory = modelFactory;
    }

    public void start() {
        out.println("Sponsor");

        var command = "";
        do {
            showSponsorMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> insert();
                case "2" -> update();
                case "3" -> deleteSponsor();
                case "4" -> getAll();
            }

        } while (!command.equals("exit"));

        out.println("End of program");
    }

    private void getAll() {
        out.println("\n List of Sponsors \n");

        var sponsors = sponsorRepository.getAll();

        String table = AsciiTable.getTable(sponsors, Arrays.asList(
                new Column().header("Id").with(sponsor -> String.valueOf(sponsor.getId())),
                new Column().header("Name").with(sponsor -> sponsor.getName()),
                new Column().header("Country").with(sponsor -> sponsor.getCountry()),
                new Column().header("Phone").with(sponsor -> String.valueOf(sponsor.getPhone())),
                new Column().header("Sponsor Type").with(sponsor -> sponsor.getSponsorType())
        ));

        out.println(table);
    }

    private void deleteSponsor() {
        var sponsor = modelFactory.createSponsor();

        out.println("Enter the ID of the sponsor to delete:");
        int id = Integer.parseInt(readLine(in));
        sponsor.setId(id);

        sponsorRepository.delete(sponsor);
    }

    private void update() {
        try {
            var sponsor = modelFactory.createSponsor();

            out.println("Enter the ID of the sponsor to update:");
            int id = Integer.parseInt(readLine(in));
            sponsor.setId(id);

            out.println("Name:");
            sponsor.setName(readLine(in));

            out.println("Country:");
            sponsor.setCountry(readLine(in));

            out.println("Phone:");
            sponsor.setPhone(Integer.parseInt(readLine(in)));

            out.println("Sponsor Type:");
            sponsor.setSponsorType(readLine(in));

            sponsorRepository.save(sponsor);

            out.println("Update successful");
        } catch (NumberFormatException e) {
            out.println("Invalid sponsor ID. Please enter a valid integer ID.");
        } catch (Exception e) {
            out.println("An error occurred while updating the sponsor: " + e.getMessage());
        }
    }

    private void insert() {
        var sponsor = modelFactory.createSponsor();

        out.println("Name:");
        sponsor.setName(readLine(in));

        out.println("Country:");
        sponsor.setCountry(readLine(in));

        out.println("Phone:");
        sponsor.setPhone(Integer.parseInt(readLine(in)));

        out.println("Sponsor Type:");
        sponsor.setSponsorType(readLine(in));

        sponsorRepository.save(sponsor);

        out.println("Insert successful");
    }

    private void showSponsorMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}

