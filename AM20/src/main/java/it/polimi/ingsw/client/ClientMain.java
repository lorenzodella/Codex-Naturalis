package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {


    public static void main(String[] args) {

        ClientRMI clientRMI;
        ClientSKT clientSKT;

        System.out.println("Scegli la configurazione per connetterti al client:");
        System.out.println("1) RMI");
        System.out.println("2) Socket");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String scelta = br.readLine();
            if(scelta.equals("1")){
                System.out.println("Ha scelto RMI");
                clientRMI = new ClientRMI();

            }else {
                System.out.println("Ha scelto Socket");
                clientSKT = new ClientSKT();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Scegli la configurazione per connetterti al client:");
        System.out.println("1) RMI");
        System.out.println("2) Socket");




    }


}
