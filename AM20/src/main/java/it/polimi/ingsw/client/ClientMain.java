package it.polimi.ingsw.client;

import it.polimi.ingsw.client.connections.ClientRMI;
import it.polimi.ingsw.client.connections.ClientSKT;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.tui.TUI;
import it.polimi.ingsw.client.tui.TUIController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {


    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Insert IP of the server:");
        String input;
        try {
             input = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        ClientRMI clientRMI;
        ClientSKT clientSKT;

        UIManager manager = null;
        UIUpdater updater;

        ClientSender sender = null;
        ClientController clientController;





        try {
            String ui;
            do {
                System.out.println("Choose user interface:");
                System.out.println("1) TUI");
                System.out.println("2) GUI");
                ui = br.readLine();

                //create ui
                if (ui.equals("1")) {
                    manager = new TUI();
                } else if (ui.equals("2")) {
                    manager = new GUI();
                }
            }while(manager==null);
            updater = new UIUpdater(manager);

            do {
                System.out.println("Choose how to connect to server:");
                System.out.println("1) RMI");
                System.out.println("2) Socket");

                String scelta = br.readLine();

                //create client
                if (scelta.equals("1")) {
                    System.out.println("Ha scelto RMI");
                    clientRMI = new ClientRMI(updater);
                    clientRMI.connect(input.trim(), Integer.parseInt(args[0]));
                    sender = clientRMI.getSender();
                } else if (scelta.equals("2")) {
                    System.out.println("Ha scelto Socket");
                    clientSKT = new ClientSKT(updater);
                    clientSKT.connect(input.trim(), Integer.parseInt(args[0]));
                    sender = clientSKT.getSender();
                }
            }while(sender==null);

            //create controller
            if(ui.equals("1")){
                clientController = new TUIController(sender, (TUI) manager);
                new Thread((TUIController) clientController).start();
            }else {
                //clientController = new GUIController(sender);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
