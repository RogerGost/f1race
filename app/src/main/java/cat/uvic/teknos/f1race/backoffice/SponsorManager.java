package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;

import java.io.BufferedReader;
import java.io.PrintStream;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.readLine;
import static cat.uvic.teknos.f1race.backoffice.IOUtilis.*;


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

    public void start(){
        out.println("Sponsor");


        var command = "";
        do {
            showSponsorMenu();
            command = readLine(in);

            switch (command){
                case "1" -> insert();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> getAll();
            }

        }
        while (!command.equals("exit"));

        out.println("Fi del programa");
    }

    private void getAll() {
    }

    private void delete() {
    }

    private void update() {
    }

    private void insert() {
        var sponsor = modelFactory.createSponsor();

        out.println("Name");
        sponsor.setName(readLine(in));

        out.println("Country");
        sponsor.setCountry(readLine(in));

        out.println("Phone");
        sponsor.setPhone(Integer.valueOf(readLine(in)));

        out.println("SponsorType");
        sponsor.setSponsorType(readLine(in));


        sponsorRepository.save(sponsor);

        out.println("Insert OK");
    }

    private void showSponsorMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
