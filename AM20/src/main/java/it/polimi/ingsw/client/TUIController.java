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
                    try{
                        this.clientSender.startNewGame(command[1], Integer.parseInt(command[2]));
                    }catch(NumberFormatException e){
                        this.myTUI.viewErrorCommand();
                    }
                    myTUI.setNickname(username);
                }else if(command[0].equals("/chooseObjective")){

                    try{
                        this.clientSender.chooseObjective(this.username, Integer.parseInt(command[1]));
                    }catch (NumberFormatException e){
                        this.myTUI.viewErrorCommand();
                    }
                }else if(command[0].equals("/chooseStarterSide")){
                    try{
                        this.clientSender.chooseStarterCardSide(this.username, Integer.parseInt(command[1]));
                    }catch (NumberFormatException e){
                        this.myTUI.viewErrorCommand();
                    }

                }else if(command[0].equals("/pickCardDeck")){
                    try{
                        this.clientSender.pickCard(this.username, Integer.parseInt(command[1]));
                    }catch (NumberFormatException e){
                        this.myTUI.viewErrorCommand();
                    }

                }else if(command[0].equals("/pickCardVisible")){
                    try{
                        this.clientSender.pickCard(this.username, Integer.parseInt(command[1]), Integer.parseInt(command[2]));

                    }catch (NumberFormatException e){
                        this.myTUI.viewErrorCommand();
                    }

                }else if(command[0].equals("/playCard")){
                    try{
                        this.clientSender.playCard(this.username, Integer.parseInt(command[1]), Integer.parseInt(command[2]), command[3], Integer.parseInt(command[4]));
                    }catch (NumberFormatException e){
                        this.myTUI.viewErrorCommand();
                    }

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
                }else if(command[0].equals("/viewDeck")){
                    if(command[1].equals("0"))
                        this.myTUI.viewResourceTop();
                    else
                        this.myTUI.viewGoldTop();
                }else if(command[0].equals("/viewCommonObjective")){
                    this.myTUI.viewCommonObjective();
                }else if(command[0].equals("/viewSecretObjective")){
                    this.myTUI.viewSecretObjective();
                }else if(command[0].equals("/viewStarterCard")){
                    this.myTUI.viewStarterCard();
                }else if(command[0].equals("/viewResourceVisibile")){
                    this.myTUI.viewResourceVisibleCards();
                }else if(command[1].equals("/viewGoldVisible")){
                    this.myTUI.viewGoldVisibleCards();
                }else if(command[0].equals("/viewChat")){
                    this.myTUI.viewChat();
                }else if(command[0].equals("/viewHand")){
                    this.myTUI.viewHandCards();
                }else {
                    this.myTUI.viewErrorCommand();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


}
