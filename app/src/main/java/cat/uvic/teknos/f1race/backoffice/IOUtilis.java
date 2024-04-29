package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.backoffice.exeptions.BackOfficeException;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtilis {
    static String readLine(BufferedReader in ){
        String command;
        try {
            command = in.readLine();
        } catch (IOException e) {
            throw new BackOfficeException("Error while reading the menu option "+e);
        }
        return command;
    }
}
