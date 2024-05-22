package it.polimi.ingsw.client.tui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.controller.messages.BroadcastChatMessage;
import it.polimi.ingsw.controller.messages.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        System.out.println(ConsoleColors.TEXT_YELLOW+ "\nFor obtaining the full list of command type /help while for obtaining the parameter of a specific action type /help [command]" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:");
        while(true){
            try {

                String input = br.readLine();
                String[] msg = input.split("\"");
                String[] command = msg[0].split(" ");
                if(command[0].equals("/help")){
                    if(command.length==1)
                        myTUI.viewCommand();
                    else if(command.length==2)
                        myTUI.viewCommandParam(command[1]);
                    else
                        myTUI.viewErrorCommand();

                    myTUI.showCommand();
                }else if(command[0].equals("/join")){
                    if(myTUI.getNickname()!=null)
                        myTUI.showError("You already joined the game");
                    else if(command.length==2){
                        this.username = command[1];
                        this.clientSender.login(command[1]);
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/newGame")){
                    if(command.length == 3){
                        this.username = command[1];
                        try{
                            this.clientSender.startNewGame(command[1], Integer.parseInt(command[2]));
                        }catch(NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                        myTUI.setNickname(username);
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/chooseObjective")){
                    if(command.length == 2){
                        try{
                            this.clientSender.chooseObjective(this.username, Integer.parseInt(command[1]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();


                }else if(command[0].equals("/chooseStarterSide")){
                    if(command.length == 2){
                        try{
                            this.clientSender.chooseStarterCardSide(this.username, Integer.parseInt(command[1]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();


                }else if(command[0].equals("/pickCardDeck")){
                    if(command.length == 2){
                        try{
                            this.clientSender.pickCard(this.username, Integer.parseInt(command[1]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/pickCardVisible")){
                    if(command.length == 3){
                        try{
                            this.clientSender.pickCard(this.username, Integer.parseInt(command[1]), Integer.parseInt(command[2]));

                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/playCard")){
                    if(command.length == 5){
                        try{
                            this.clientSender.playCard(this.username, Integer.parseInt(command[1]), Integer.parseInt(command[2]), command[3], Integer.parseInt(command[4]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/chat")){
                    if(command.length == 2 && msg.length == 2){
                        if(command[1].equals("broadcast")) {
                            this.clientSender.sendBroadcastChatMessage(this.username, msg[1]);
                            myTUI.updateChatMessage(new BroadcastChatMessage(this.username, msg[1]));
                        }else {
                            this.clientSender.sendChatMessage(this.username, command[1], msg[1]);
                            myTUI.updateChatMessage(new ChatMessage(this.username, command[1], msg[1]));
                        }
                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/myPlayerInfo")){
                    this.myTUI.viewPlayerInfo();

                }else if(command[0].equals("/playerInfo")){
                    if(command.length == 2){
                        this.myTUI.viewOtherPlayerInfo(command[1]);

                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/placement")){
                    this.myTUI.viewPlacement();
                }else if(command[0].equals("/viewDeck")){
                    if(command.length == 2){
                        if(command[1].equals("0"))
                            this.myTUI.viewGold();
                        else
                            this.myTUI.viewResource();
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/viewCommonObjective")){
                    this.myTUI.viewCommonObjective();
                }else if(command[0].equals("/viewSecretObjective")){
                    this.myTUI.viewSecretObjective();
                }else if(command[0].equals("/viewStarterCard")){
                    this.myTUI.viewStarterCard();
                }else if(command[0].equals("/viewChat")){
                    this.myTUI.viewChat();
                }else if(command[0].equals("/viewHand")) {
                    this.myTUI.viewHandCards();
                }else if(command[0].equals("/currPlayer")){
                    this.myTUI.viewCurrPlayer();
                }else {
                    this.myTUI.viewErrorCommand();
                    this.myTUI.showCommand();

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


}
