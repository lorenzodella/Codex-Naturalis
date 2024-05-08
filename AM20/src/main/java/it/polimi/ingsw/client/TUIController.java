package it.polimi.ingsw.client;

import it.polimi.ingsw.server.ServerManager;
import jdk.tools.jmod.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;

public class TUIController implements Runnable{

    private String username;

    private ClientSender clientSender;

    private Socket socket;

    private BufferedReader br;

    public TUIController(Socket socket){
        this.br = new BufferedReader(new jdk.internal.org.jline.utils.InputStreamReader(System.in));
        this.socket = socket;
    }


    public void run(){
        while(true){
            System.out.println("Decidi che comando vuoi eseguire");
            try {
                String input = br.readLine();
                if(input.equals("/join")){
                    this.join();

                }else if(input.equals("/newGame")){
                    this.newGame();

                }else if(input.equals("/chooseObjective")){
                    this.chooseObjective();

                }else if(input.equals("/chooseStarterSide")){
                    this.chooseStarterSide();

                }else if(input.equals("/pickCardDeck ")){
                    this.pickCardDeck();

                }else if(input.equals("/pickCardVisible")){
                    this.pickCardVisible();

                }else if(input.equals("/playCard")){
                    this.playCard();

                }else if(input.equals("/chat")){
                    this.chat();
                }//else if(input.equals("/board")){

                //}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void join(){
        System.out.println("Per fare la join devi specificare il nickname con cui ti vuoi collegare alla partita:");
        try {
            String input = br.readLine();
            this.username = input;
            this.clientSender.login(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void newGame(){
        System.out.println("Per creare un nuovo gioco devi speciificare il tuo nickname: ");
        try {
            String inputNickname = br.readLine();
            this.username = inputNickname;
            System.out.println("Ora devi speicifcare quantio gicoatori vuoi che ci siano colelgati alla partita: ");
            String inputNum = br.readLine();
            this.clientSender.startNewGame(inputNickname, Integer.parseInt(inputNum));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //TODO
    //public perchè deve poter essere chiamato da RMIClientReceivere e SKTClientReceiver
    public void chooseObjective(){
        System.out.println("Specifica quale delle due carte obiettivo che hai ricevuto vuoi usare (0 per sinistra e 1 per destra)");

        try {
            String inputSecretObjective = br.readLine();
            this.clientSender.chooseObjective(this.username, Integer.parseInt(inputSecretObjective));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    //TODO
    //public per lo stesso motivo di chooseObjective
    public void chooseStarterSide(){
        System.out.println("Specifica quale lato della carta iniziale vuoi giocare (0 per il dietro della carta e 1 per il fronte della carta): ");
        try {
            String inputSide = br.readLine();
            this.clientSender.chooseStarterCardSide(this.username, Integer.parseInt(inputSide));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pickCardDeck(){

        System.out.println("Specifica da quale deck si vuole pescare (0 per le gold card e 1 per le resource card): ");
        try {
            String inputDeck = br.readLine();
            this.clientSender.pickCard(this.username, Integer.parseInt(inputDeck));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void pickCardVisible(){

        System.out.println("Specifica da quale deck si vuole pescare (0 per le gold card e 1 per le resource card): ");
        try {
            String inputDeck = br.readLine();
            System.out.println("Specifica quale carta visibile vuoi pescare (0 oppure 1)");
            String inputIndex = br.readLine();
            this.clientSender.pickCard(this.username, Integer.parseInt(inputDeck), Integer.parseInt(inputIndex));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    private void playCard(){

        System.out.println("Specifica carta si vuole giocare (0 per quella più a sinistra, 1 per quella in mezzo e 2 per quella di destra): ");
        try {
            String inputCard = br.readLine();
            System.out.println("Specifica su quale angolo vuoi posizionare la carta si vuole pescare (0 per UL, 1 per UR, 2 per DL e 3 per DR):");
            String inputAngle = br.readLine();
            System.out.println("Specifica su quale carta si vuole giocare la carta scelta: ");
            String inputTargetcard = br.readLine();
            System.out.println("Specifica con quale lato si vuole giocare la carta (0 per il dietro della carta e 1 per il fronte della carta): ");
            String inputSide = br.readLine();
            //metodo che ti peremtte di ottenre l'ID della carta da giocare: la TUA avrà HashMap<Integer, ID>
            this.clientSender.playCard(this.username, Integer.parseInt(inputCard), Integer.parseInt(inputAngle), "S0", Integer.parseInt(inputSide));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void chat(){
        System.out.println("Specifica se si vuole amdnare un messaggio in broadcast (scrivi /broadcast oppure l'username del ricevitore del messaggio");
        try {
            String decisione = br.readLine();
            String messagge;
            if(decisione.equals("/broadcast")){
                System.out.println("Scrivi il messaggio che si vuole mandare: ");
                messagge = br.readLine();
                this.clientSender.sendBroadcastChatMessage(this.username, messagge);

            }else {
                System.out.println("Scrivi il messaggio che si vuole mandare: ");
                messagge = br.readLine();
                this.clientSender.sendChatMessage(this.username, decisione , messagge);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}
