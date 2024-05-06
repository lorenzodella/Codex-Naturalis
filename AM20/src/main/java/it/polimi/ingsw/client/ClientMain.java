package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMain {


    public static void main(String[] args) {

//        RMIClientReceiver clientRMI;
//        ClientSKT clientSKT;
//
//        System.out.println("Scegli la configurazione per connetterti al client:");
//        System.out.println("1) RMI");
//        System.out.println("2) Socket");
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            String scelta = br.readLine();
//            if(scelta.equals("1")){
//                System.out.println("Ha scelto RMI");
//                clientRMI = new RMIClientReceiver();
//
//            }else {
//                System.out.println("Ha scelto Socket");
//                clientSKT = new ClientSKT();
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("Scegli la configurazione per connetterti al client:");
//        System.out.println("1) RMI");
//        System.out.println("2) Socket");

        try {
            RMIClientSender clientRMI = new RMIClientSender(args[0], Integer.parseInt(args[1]));
            clientRMI.startNewGame("Lollo", 4);

            RMIClientSender clientRMI1 = new RMIClientSender(args[0], Integer.parseInt(args[1]));
            clientRMI1.login("Pino");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }


    }


}
