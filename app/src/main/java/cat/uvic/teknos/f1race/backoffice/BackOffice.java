package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.backoffice.exeptions.BackOfficeException;

import java.io.*;

public class BackOffice {
    private final BufferedReader in;
    private final PrintStream out;


    public BackOffice(InputStream inputStream, OutputStream outputStream) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintStream(outputStream);
    }

    public void start(){
        showWelcomeMessage();

        var command = "";
        do {
            showMainMenu();
            command = readLine();
        }

        while (!command.equals("exit"));

        out.println("Fi del programa");

    }

    private String readLine() {
        String command;
        try {
            command = in.readLine();
        } catch (IOException e) {
            throw new BackOfficeException("Error while reading the menu option "+e);
        }
        return command;
    }

    private void showWelcomeMessage() {
        out.println("Welcome to the F1Race Black Office");
        out.println("Select a menu option");
        out.println(" ");
    }

    private void showMainMenu() {
        out.println("1. Team");
        out.println("2. Car");
        out.println("3. Driver");
        out.println("4. Sponsor");
        out.println("5. SponsorShip");
    }
}
