package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.messages.BroadcastChatMessage;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.server.ServerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;

public class TUIController extends ClientController implements Runnable{

    private String username;

    private BufferedReader br;

    private TUI myTUI;

    public TUIController(ClientSender sender, TUI tui){
        super(sender);
        this.myTUI = tui;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }


    public void run(){
        while(true){
            System.out.println("Decide which command you want to do:\n");
            try {

                String input = br.readLine();
                String[] command = input.split(" ");
                if(command[0].equals("/help")){
                    myTUI.viewCommand();
                }else if(command[0].equals("/join")){
                    this.username = command[1];
                    this.clientSender.login(command[1]);
                    myTUI.setNickname(username);
                }else if(command[0].equals("/newGame")){
                    this.username = command[1];
                    this.clientSender.startNewGame(command[1], Integer.parseInt(command[2]));
                    myTUI.setNickname(username);
                }else if(command[0].equals("/chooseObjective")){

                    this.clientSender.chooseObjective(this.username, Integer.parseInt(command[1]));

                }else if(command[0].equals("/chooseStarterSide")){
                    this.clientSender.chooseStarterCardSide(this.username, Integer.parseInt(command[1]));

                }else if(command[0].equals("/pickCardDeck")){
                    this.clientSender.pickCard(this.username, Integer.parseInt(command[1]));

                }else if(command[0].equals("/pickCardVisible")){
                    this.clientSender.pickCard(this.username, Integer.parseInt(command[1]), Integer.parseInt(command[2]));

                }else if(command[0].equals("/playCard")){
                    this.clientSender.playCard(this.username, Integer.parseInt(command[1]), Integer.parseInt(command[2]), command[3], Integer.parseInt(command[4]));

                }else if(command[0].equals("/chat")){
                    if(command[1]. equals("broadcast")) {
                        this.clientSender.sendBroadcastChatMessage(this.username, command[2]);
                        myTUI.updateChatMessage(new BroadcastChatMessage(this.username, command[2]));
                    }else {
                        this.clientSender.sendChatMessage(this.username, command[1], command[2]);
                        myTUI.updateChatMessage(new ChatMessage(this.username, command[1], command[2]));
                    }

                }else if(command[0].equals("/myPlayerInfo")){
                    this.myTUI.viewPlayerInfo();

                }else if(command[0].equals("/playerInfo")){
                    this.myTUI.viewOtherPlayerInfo(command[1]);

                }else if(command[0].equals("/placement")){
                    this.myTUI.viewPlacement();
                }
                //TODO manca la gestione del caso in cui il comando non è valido
                //TODO manca il comando per vedere i deck
                //TODO manca il comando per vedere gli obiettivi (comuni e non)
                //TODO manca il comando per vedere la propria starter card
                //TODO manca il comando per vedere le proprie carte
                //TODO manca il comando per aprire la chat


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


}
