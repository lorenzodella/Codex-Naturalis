package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMain {


    public static void main(String[] args) {

        RMIClientReceiver clientRMI;
        ClientSKT clientSKT;

        UIManager manager;
        UIUpdater updater;


        System.out.println("Choose user interface:");
        System.out.println("1) TUI");
        System.out.println("2) GUI");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String scelta = br.readLine();
            if(scelta.equals("1")){
                manager = new TUI();
            }else {
                manager = new GUI();
            }
            updater = new UIUpdater(manager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        System.out.println("Choose how to connect to server:");
        System.out.println("1) RMI");
        System.out.println("2) Socket");

        try {
            String scelta = br.readLine();
            if(scelta.equals("1")){
                System.out.println("Ha scelto RMI");
                //clientRMI = new RMIClientReceiver();

            }else {
                System.out.println("Ha scelto Socket");
                clientSKT = new ClientSKT(updater);
                clientSKT.connect(args[0], Integer.parseInt(args[1]));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
