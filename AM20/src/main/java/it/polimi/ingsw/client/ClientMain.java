package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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

        UIManager manager;
        UIUpdater updater;

        ClientSender sender;
        ClientController clientController;





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
                clientRMI.connect(input, Integer.parseInt(args[0]));
                sender = clientRMI.getSender();
            }else {
                System.out.println("Ha scelto Socket");
                clientSKT = new ClientSKT(updater);
                clientSKT.connect(input, Integer.parseInt(args[0]));
                sender = clientSKT.getSender();
            }

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
