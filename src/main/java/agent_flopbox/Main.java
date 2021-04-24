package agent_flopbox;


import agent_flopbox.Services.Controllers;

import java.io.IOException;

/**
 * Main class.
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Controllers controllers = new Controllers();
        Controllers api = new Controllers();

        api.deleteFile("home", "/Second.py");
    }
}

