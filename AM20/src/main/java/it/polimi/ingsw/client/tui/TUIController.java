package it.polimi.ingsw.client.tui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.controller.messages.BroadcastChatMessage;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.PawnColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is the controller of the TUI. It reads the input from the terminal and calls the server to do the specific action
 */
public class TUIController extends ClientController implements Runnable{

    //private String username;

    /**
     * The buffer reader reads text from the character-input stream. In these specific cases,
     * it reads a single line from the terminal
     */
    private BufferedReader br;

    /**
     * Reference to the class TUI. It allows to print the information the user require
     */
    private TUI myTUI;

    public TUIController(ClientSender sender, TUI tui){
        super(sender);
        this.myTUI = tui;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }


    /**
     * This is the method of the thread that read from the terminal. It reads the single line, using a case switch it is possible to understand which is the command
     * required from the user. Now it tries to call the server to do the specific action through the clientSender; the server now responds with an exception or a message
     * built by the messageBuilder if everything works well
     */
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
                    else if(command.length==3){
                        //this.username = command[1];
                        PawnColor color = PawnColor.parsePawnColor(command[2]);
                        String nick = command[1].substring(0,1).toUpperCase() + command[1].substring(1);
                        if(color!=null)
                            this.clientSender.login(nick, color);
                        else
                            myTUI.viewErrorCommand();
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/newGame")){
                    if(command.length == 4){
                        //this.username = command[1];
                        PawnColor color = PawnColor.parsePawnColor(command[2]);
                        String nick = command[1].substring(0,1).toUpperCase() + command[1].substring(1);
                        if(color!=null)
                            try{
                                this.clientSender.startNewGame(nick, color, Integer.parseInt(command[3]));
                            }catch(NumberFormatException e){
                                this.myTUI.viewErrorCommand();
                            }
                        else
                            myTUI.viewErrorCommand();
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/chooseObjective")){
                    if(myTUI.getNickname()==null)
                        myTUI.showError("You must join a game");
                    else if(command.length == 2){
                        try{
                            this.clientSender.chooseObjective(myTUI.getNickname(), Integer.parseInt(command[1]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();


                }else if(command[0].equals("/chooseStarterSide")){
                    if(myTUI.getNickname()==null)
                        myTUI.showError("You must join a game");
                    else if(command.length == 2){
                        try{
                            this.clientSender.chooseStarterCardSide(myTUI.getNickname(), Integer.parseInt(command[1]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();


                }else if(command[0].equals("/pickCardDeck")){
                    if(myTUI.getNickname()==null)
                        myTUI.showError("You must join a game");
                    else if(command.length == 2){
                        try{
                            this.clientSender.pickCard(myTUI.getNickname(), Integer.parseInt(command[1]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/pickCardVisible")){
                    if(myTUI.getNickname()==null)
                        myTUI.showError("You must join a game");
                    else if(command.length == 3){
                        try{
                            this.clientSender.pickCard(myTUI.getNickname(), Integer.parseInt(command[1]), Integer.parseInt(command[2]));

                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/playCard")){
                    if(myTUI.getNickname()==null)
                        myTUI.showError("You must join a game");
                    else if(command.length == 5){
                        try{
                            this.clientSender.playCard(myTUI.getNickname(), Integer.parseInt(command[1]), Integer.parseInt(command[2]), command[3], Integer.parseInt(command[4]));
                        }catch (NumberFormatException e){
                            this.myTUI.viewErrorCommand();
                        }
                    }else
                        myTUI.viewErrorCommand();
                }else if(command[0].equals("/chat")){
                    if(myTUI.getNickname()==null)
                        myTUI.showError("You must join a game");
                    else if(command.length == 2 && msg.length == 2){
                        if(myTUI.getNickname().equals(command[1].substring(0,1).toUpperCase() + command[1].substring(1))){
                            myTUI.showError("You are trying to send a message to yourself");
                        }else {
                            if(command[1].equals("broadcast")) {
                                this.clientSender.sendBroadcastChatMessage(myTUI.getNickname(), msg[1]);
                                myTUI.updateChatMessage(new BroadcastChatMessage(myTUI.getNickname(), msg[1]));
                            }else if(myTUI.getOthersPlayerInfo().containsKey(command[1].substring(0,1).toUpperCase() + command[1].substring(1))) {
                                String dest = command[1].substring(0,1).toUpperCase() + command[1].substring(1);
                                this.clientSender.sendChatMessage(myTUI.getNickname(), dest, msg[1]);
                                myTUI.updateChatMessage(new ChatMessage(myTUI.getNickname(), dest, msg[1]));
                            }else {
                                myTUI.showError("The command executed is wrong (maybe you have made a mistake typing something) or the player specified isn't in the game");
                            }
                        }
                    }else
                        myTUI.viewErrorCommand();

                }else if(command[0].equals("/myPlayerInfo")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        this.myTUI.viewPlayerInfo();
                        this.myTUI.showCommand();
                    }
                }else if(command[0].equals("/playerInfo")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(myTUI.getNickname().equals(command[1].substring(0,1).toUpperCase() + command[1].substring(1))){
                            myTUI.showError("You are trying to obtain your information");
                        }else {
                            if(command.length == 2){
                                this.myTUI.viewOtherPlayerInfo(command[1]);
                                this.myTUI.showCommand();
                            }else
                                myTUI.viewErrorCommand();
                        }
                    }


                }else if(command[0].equals("/placement")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length==1){
                            this.myTUI.viewPlacement();
                            this.myTUI.showCommand();
                        }else
                            myTUI.showError("The placement command doesn't need any parameters");
                    }

                }else if(command[0].equals("/viewDeck")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length == 2){
                            if(command[1].equals("0"))
                                this.myTUI.viewGold();
                            else
                                this.myTUI.viewResource();
                            this.myTUI.showCommand();
                        }else
                            myTUI.viewErrorCommand();
                    }

                }else if(command[0].equals("/viewCommonObjective")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length==1){
                            int res = this.myTUI.viewCommonObjective();
                            if(res == 1)
                                this.myTUI.showCommand();
                        }else
                            myTUI.showError("The viewCommonObjective command doesn't need any parameters");

                    }

                }else if(command[0].equals("/viewSecretObjective")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length == 1){
                            int res = this.myTUI.viewSecretObjective();
                            if(res == 1)
                                this.myTUI.showCommand();
                        }else
                            myTUI.showError("The viewSecretObjective command doesn't need any parameters");
                    }


                }else if(command[0].equals("/viewStarterCard")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length == 1){
                            this.myTUI.viewStarterCard();
                            this.myTUI.showCommand();
                        }else
                            myTUI.showError("The viewStarterCard command doesn't need any parameters");
                    }

                }else if(command[0].equals("/viewChat")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length == 1){
                            this.myTUI.viewChat();
                            this.myTUI.showCommand();
                        }else
                            myTUI.showError("The viewStarterCard command doesn't need any parameters");
                    }

                }else if(command[0].equals("/viewHand")) {
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length == 1){
                            this.myTUI.viewHandCards();
                            this.myTUI.showCommand();
                        } else {
                            myTUI.showError("The viewStarterCard command doesn't need any parameters");
                        }
                    }
                }else if(command[0].equals("/currPlayer")){
                    if(myTUI.getNickname() == null)
                        myTUI.showError("You must join a game");
                    else {
                        if(command.length == 1){
                            this.myTUI.viewCurrPlayer();
                            this.myTUI.showCommand();
                        }else {
                            myTUI.showError("The viewStarterCard command doesn't need any parameters");
                        }
                    }
                }else {
                    this.myTUI.viewErrorCommand();

                }
            } catch (IOException e) {
                System.err.println("Error reading from console");
            }

        }

    }


}
