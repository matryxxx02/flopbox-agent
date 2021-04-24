package agent_flopbox.API;

import agent_flopbox.Services.Controllers;

public class main {


    public static void main(String[] args) {
        Controllers controllers = new Controllers();

        controllers.get("home", "/deleted/chose.txt");
    }
}
