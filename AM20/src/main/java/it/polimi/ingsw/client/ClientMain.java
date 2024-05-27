package it.polimi.ingsw.client;

import it.polimi.ingsw.client.connections.ClientRMI;
import it.polimi.ingsw.client.connections.ClientSKT;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.tui.TUI;
import it.polimi.ingsw.client.tui.TUIController;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {


    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input;
        if(args.length>3) {
            input = args[3];
        }
        else {
            System.out.println("Insert IP of the server:");
            try {
                input = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        ClientRMI clientRMI;
        ClientSKT clientSKT;

        UIManager manager = null;
        UIUpdater updater;

        ClientSender sender = null;
        ClientController clientController;

        //--------TEST SU DUE SCHERMI UNO SOPRA L'ALTRO--------
//        if(args[2].equals("2"))
//            GUIUtils.location = new Point(0, -1050);
//        else
            GUIUtils.location = new Point(0, 0);


        try {
            String ui;
            do {
                if(args.length>1){
                    ui = args[1];
                }else {
                    System.out.println("Choose user interface:");
                    System.out.println("1) TUI");
                    System.out.println("2) GUI");
                    ui = br.readLine();
                }

                //create ui
                if (ui.equals("1")) {
                    manager = new TUI();
                } else if (ui.equals("2")) {
                    manager = new GUI();
                }
            }while(manager==null);
            updater = new UIUpdater(manager);

            do {
                String scelta;
                if(args.length>2) {
                    scelta = args[2];
                }else {
                    System.out.println("Choose how to connect to server:");
                    System.out.println("1) RMI");
                    System.out.println("2) Socket");

                    scelta = br.readLine();
                }

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
                clientController = new GUIController(sender, (GUI) manager);
                ((GUI) manager).showStartScreen();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
