package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;


import com.sun.security.ntlm.Server;
import it.polimi.ingsw.clientmessage.*;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

/* Threac che ascolta, su un certo socket, e ogni volta che riceve un messaggio lo interpreta e compie
   ciò che gli viene detto di fare  (invocando un metodo del controller)
*/
public class ClientHandler implements Runnable{

    private ServerManager manager;
    private Socket socket;
    private String usernameClient;

    private ObjectInputStream objectInputStream;



    public ClientHandler(Socket socket, ServerManager manager) throws IOException{
        this.socket = socket;
        this.manager = manager;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    
    @Override
    public void run() {

        ClientMessage message;
        while(true){
            try {
                message = (ClientMessage) objectInputStream.readObject();

                if(message.getAction() == ClientMessage.LOGIN){

                    LoginMessage msg = (LoginMessage) message;
                    HashMap<String, ConnectionAckMessage> res;
                    this.usernameClient = msg.getClient();

                    try {
                        res = this.manager.getController().joinGame(msg.getClient());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callConnectionAckMessage(res.get(s));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (CannotJoinGameException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }


                }else if(message.getAction() == ClientMessage.NEWGAME){
                    NewGameMessage msg = (NewGameMessage) message;
                    Message messageToSend;
                    try {
                        messageToSend = this.manager.getController().newGame(msg.getClient(), msg.getNumPlayers());
                        //TODO
                        //for se una volta finita la partita vogliamo mandare il messaggio a tutti i player (diepdne come gestiamo la fine della partita)
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callMessage(messageToSend);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (InvalidArgumentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }



                }else if(message.getAction() == ClientMessage.CHOOSE_OBJECTIVE){
                    ChooseObjectiveMessage msg = (ChooseObjectiveMessage) message;
                    HashMap<String, ObjectiveAckMessage> res;
                    try {
                        res = this.manager.getController().chooseObjective(msg.getNickname(), msg.getIndex());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callObjectiveAckMessage(res.get(s));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (InvalidArgumentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }




                }else if(message.getAction() == ClientMessage.CHOOSE_STARTERCARD_SIDE){
                    ChooseStarterCardSideMessage msg = (ChooseStarterCardSideMessage) message;
                    HashMap<String, StarterCardAckMessage> res;
                    try {
                        res = this.manager.getController().chooseStarterCardSide(msg.getNickname(), msg.getSide());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callStarterCardAckMessage(res.get(s));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (InvalidArgumentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }




                }else if(message.getAction() == ClientMessage.PICK_CARD_DECK){
                    PickCardDeckMessage msg = (PickCardDeckMessage) message;
                    HashMap<String, AcknowledgeMessage> res;
                    try {
                        res = this.manager.getController().pickCard(msg.getPlayerNickname(), msg.getDeck());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callAcknowledgeMessage(res.get(s));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (InvalidArgumentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (FinishedCardStackException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (NoOneIsConnectedException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }



                }else if(message.getAction() == ClientMessage.PICK_CARD_VISIBLE){
                    PickCardVisibleMessage msg = (PickCardVisibleMessage) message;
                    HashMap<String, AcknowledgeMessage> res;
                    try {
                        res = this.manager.getController().pickCard(msg.getPlayerNickname(), msg.getDeck(), msg.getIndex());

                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callAcknowledgeMessage(res.get(s));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (InvalidArgumentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (FinishedCardStackException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (NoOneIsConnectedException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }



                }else if(message.getAction() == ClientMessage.PLAY_CARD){
                    PlayCardMessage msg = (PlayCardMessage) message;
                    HashMap<String, AcknowledgeMessage> res;

                    try {
                        res = this.manager.getController().playCard(msg.getPlayerNickname(),msg.getCardIndex(), msg.getAngle(), msg.getTargetID(),msg.getSide());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : connectedPlayer.keySet()){
                            try {
                                connectedPlayer.get(s).callAcknowledgeMessage(res.get(s));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (InvalidArgumentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (TargetNotPresentException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidAngleCoveredException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPositionException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (RequirementsNotRespectedException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (NoOneIsConnectedException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }



                }else if(message.getAction() == ClientMessage.SEND_CHAT){
                    SendChatMessage msg = (SendChatMessage) message;

                    //non devo chiamare il controller, ma devo semplicemente madnare i dati
                    HashMap<String, Connection> connection = this.manager.getConnections();
                    ChatMessage msgToSend = new ChatMessage(msg.getSender(), msg.getRecipient(), msg.getMessage());
                    connection.get(msg.getRecipient()).callChatMessage(msgToSend);


                }else if(message.getAction() == ClientMessage.SEND_CHAT_BROADCAST){

                    SendChatBroadcastMessage msg = (SendChatBroadcastMessage) message;
                    BroadcastChatMessage broadcastChatMessage = new BroadcastChatMessage(msg.getSender(), msg.getMessage());

                    HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                    for(String s : connectedPlayer.keySet()){

                        connectedPlayer.get(s).callChatMessage(broadcastChatMessage);
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
