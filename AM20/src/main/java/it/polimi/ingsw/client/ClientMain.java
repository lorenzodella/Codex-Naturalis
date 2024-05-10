package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMain {


    public static void main(String[] args) {

        ClientRMI clientRMI;
        ClientSKT clientSKT;

        UIManager manager;
        UIUpdater updater;

        ClientSender sender;
        ClientController clientController;




        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Choose user interface:");
            System.out.println("1) TUI");
            System.out.println("2) GUI");
            String ui = br.readLine();

            //create ui
            if(ui.equals("1")){
                manager = new TUI();
            }else {
                manager = new GUI();
            }
            updater = new UIUpdater(manager);

            System.out.println("Choose how to connect to server:");
            System.out.println("1) RMI");
            System.out.println("2) Socket");

            String scelta = br.readLine();

            //create client
            if(scelta.equals("1")){
                System.out.println("Ha scelto RMI");
                clientRMI = new ClientRMI(updater);
                clientRMI.connect(args[0], Integer.parseInt(args[1]));
                sender = clientRMI.getSender();
            }else {
                System.out.println("Ha scelto Socket");
                clientSKT = new ClientSKT(updater);
                clientSKT.connect(args[0], Integer.parseInt(args[1]));
                sender = clientSKT.getSender();
            }

            //create controller
            if(ui.equals("1")){
                clientController = new TUIController(sender, (TUI) manager);
            }else {
                //clientController = new GUIController(sender);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
