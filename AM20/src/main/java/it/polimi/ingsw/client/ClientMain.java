package it.polimi.ingsw.client;

import it.polimi.ingsw.client.connections.*;
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

        Client client = null;

        UIManager manager = null;
        UIUpdater updater;

        ClientSender sender = null;
        ClientController clientController;

        //--------TEST SU DUE SCHERMI UNO SOPRA L'ALTRO--------
//        if(args[1].equals("2"))
//            GUIUtils.location = new Point(0, -1050);
//        else
            GUIUtils.location = new Point(0, 0);

        try {
            String ui;
            do {
                if(args.length>0){
                    ui = args[0];
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

            String con;
            do {
                if(args.length>1) {
                    con = args[1];
                }else {
                    System.out.println("Choose how to connect to server:");
                    System.out.println("1) RMI");
                    System.out.println("2) Socket");

                    con = br.readLine();
                }

                //create client
                if (con.equals("1")) {
                    client = new ClientRMI(updater);
                } else if (con.equals("2")) {
                    client = new ClientSKT(updater);
                }
            }while(client==null);

            String host;
            if(args.length>2) {
                host = args[2];
            }
            else {
                System.out.println("Insert IP of the server:");
                host = br.readLine();
            }
            String port;
            if(args.length>3) {
                port = args[3];
            }
            else {
                System.out.println("Insert PORT of the server (" + (con.equals("1")?"RMI":"Socket") + "):");
                port = br.readLine();
            }

            client.connect(host.trim(), Integer.parseInt(port));
            sender = client.getSender();

            //create controller
            if(ui.equals("1")){
                clientController = new TUIController(sender, (TUI) manager);
                new Thread((TUIController) clientController).start();
            }else {
                clientController = new GUIController(sender, (GUI) manager);
                ((GUI) manager).showStartScreen();
            }
            new PingThread(sender, manager).run();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
