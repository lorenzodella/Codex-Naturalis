package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


import it.polimi.ingsw.clientmessage.*;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

//TODO: ELEONORA

/* Threac che ascolta, su un certo socket, e ogni volta che riceve un messaggio lo interpreta e compie
   ciò che gli viene detto di fare  (invocando un metodo del controller)
*/
public class ClientHandler implements Runnable{
    /**
     * This attribute stands for the serverManager reference
     */
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
        SocketConnection connection;
        try {
            connection = new SocketConnection(this.socket);
        } catch (IOException e) {
            System.err.println("Client disconnected, client handler will be closed");
            return;
        }
        while(true){
            System.out.println("Waiting for message...");
            try {
                message = (ClientMessage) objectInputStream.readObject();

                if(message.getAction().equals(ClientMessage.LOGIN)){

                    LoginMessage msg = (LoginMessage) message;
                    if(!manager.getConnections().containsKey(msg.getClient())){
                        this.manager.addConnection(msg.getClient(),connection);
                    }
                    HashMap<String, ConnectionAckMessage> res;
                    this.usernameClient = msg.getClient();


                    try {
                        res = this.manager.getController().joinGame(msg.getClient(), msg.getColor());
                        this.manager.resetTimer();
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : res.keySet()){
                            try {
                                connectedPlayer.get(s).callConnectionAckMessage(res.get(s));
                            } catch (IOException e) {
                                this.manager.detectDisconnection(s);
                            }
                        }
                    } catch (CannotJoinGameException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                        this.manager.getConnections().remove(this.usernameClient);
                    }


                }else if(message.getAction().equals(ClientMessage.NEWGAME)){
                    NewGameMessage msg = (NewGameMessage) message;
                    ConnectionAckMessage messageToSend;
                    try {
                        this.usernameClient = msg.getClient();
                        if(!manager.getConnections().containsKey(msg.getClient())){
                            this.manager.addConnection(msg.getClient(),connection);
                        }
                        messageToSend = this.manager.getController().newGame(msg.getClient(),msg.getColor(), msg.getNumPlayers());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        connectedPlayer.get(this.usernameClient).callConnectionAckMessage(messageToSend);
                    } catch (InvalidArgumentException | InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                        this.manager.getConnections().remove(this.usernameClient);
                    }


                }else if(message.getAction().equals(ClientMessage.CHOOSE_OBJECTIVE)){
                    ChooseObjectiveMessage msg = (ChooseObjectiveMessage) message;
                    HashMap<String, ObjectiveAckMessage> res;
                    try {
                        res = this.manager.getController().chooseObjective(msg.getNickname(), msg.getIndex());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : res.keySet()){
                            try {
                                connectedPlayer.get(s).callObjectiveAckMessage(res.get(s));
                            } catch (IOException e) {
                                this.manager.detectDisconnection(s);
                            }
                        }
                    } catch (InvalidArgumentException | InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }


                }else if(message.getAction().equals(ClientMessage.CHOOSE_STARTERCARD_SIDE)){
                    ChooseStarterCardSideMessage msg = (ChooseStarterCardSideMessage) message;
                    HashMap<String, StarterCardAckMessage> res;
                    try {
                        res = this.manager.getController().chooseStarterCardSide(msg.getNickname(), msg.getSide());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : res.keySet()){
                            try {
                                connectedPlayer.get(s).callStarterCardAckMessage(res.get(s));
                            } catch (IOException e) {
                                this.manager.detectDisconnection(s);
                            }
                        }
                    } catch (InvalidArgumentException | InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }


                }else if(message.getAction().equals(ClientMessage.PICK_CARD_DECK)){
                    PickCardDeckMessage msg = (PickCardDeckMessage) message;
                    HashMap<String, AcknowledgeMessage> res;
                    try {
                        res = this.manager.getController().pickCard(msg.getPlayerNickname(), msg.getDeck());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : res.keySet()){
                            try {
                                connectedPlayer.get(s).callAcknowledgeMessage(res.get(s));
                            } catch (IOException e) {
                                this.manager.detectDisconnection(s);
                            }
                        }
                    } catch (InvalidArgumentException | FinishedCardStackException | InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }  catch (NoOneIsConnectedException e1){
                        manager.reset();
                    }


                }else if(message.getAction().equals(ClientMessage.PICK_CARD_VISIBLE)){
                    PickCardVisibleMessage msg = (PickCardVisibleMessage) message;
                    HashMap<String, AcknowledgeMessage> res;
                    try {
                        res = this.manager.getController().pickCard(msg.getPlayerNickname(), msg.getDeck(), msg.getIndex());

                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : res.keySet()){
                            try {
                                connectedPlayer.get(s).callAcknowledgeMessage(res.get(s));
                            } catch (IOException e) {
                                this.manager.detectDisconnection(s);
                            }
                        }
                    } catch (InvalidArgumentException | FinishedCardStackException | InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    }  catch (NoOneIsConnectedException e1){
                        manager.reset();
                    }


                }else if(message.getAction().equals(ClientMessage.PLAY_CARD)){
                    PlayCardMessage msg = (PlayCardMessage) message;
                    HashMap<String, AcknowledgeMessage> res;

                    try {
                        res = this.manager.getController().playCard(msg.getPlayerNickname(),msg.getCardIndex(), msg.getAngle(), msg.getTargetID(),msg.getSide());
                        HashMap<String, Connection> connectedPlayer = this.manager.getConnections();
                        for(String s : res.keySet()){
                            try {
                                connectedPlayer.get(s).callAcknowledgeMessage(res.get(s));
                            } catch (IOException e) {
                                this.manager.detectDisconnection(s);

                            }
                        }
                    } catch (InvalidArgumentException | TargetNotPresentException | InvalidAngleCoveredException |
                             InvalidPositionException | RequirementsNotRespectedException | InvalidPlayingException e) {
                        this.manager.getConnections().get(this.usernameClient).callErrorMessage(new ErrorMessage(e));
                    } catch (NoOneIsConnectedException e1){
                        manager.reset();
                    }


                }else if(message.getAction().equals(ClientMessage.SEND_CHAT)){
                    SendChatMessage msg = (SendChatMessage) message;

                    //non devo chiamare il controller, ma devo semplicemente madnare i dati
                    HashMap<String, Connection> connections = this.manager.getConnections();
                    ChatMessage msgToSend = new ChatMessage(msg.getSender(), msg.getRecipient(), msg.getMessage());
                    try {
                        connections.get(msg.getRecipient()).callChatMessage(msgToSend);
                    } catch (IOException | NullPointerException e) {
                        Message m = new Message();
                        m.setResult("Recipient is not online");
                        connections.get(msg.getSender()).callMessage(m);
                    }


                }else if(message.getAction().equals(ClientMessage.SEND_CHAT_BROADCAST)){

                    SendChatBroadcastMessage msg = (SendChatBroadcastMessage) message;
                    BroadcastChatMessage broadcastChatMessage = new BroadcastChatMessage(msg.getSender(), msg.getMessage());

                    HashMap<String, Connection> connections = this.manager.getConnections();
                    Message m = new Message();
                    m.setResult("Message sent to all");
                    for (Map.Entry<String, Connection> entry: connections.entrySet()) {
                        if(!entry.getKey().equals(msg.getSender())) {
                            try {
                                entry.getValue().callChatMessage(broadcastChatMessage);
                            } catch (IOException e) {
                                if (m.getResult().contains("except")) {
                                    m.setResult(m.getResult() + ", " + entry.getKey());
                                } else {
                                    m.setResult(m.getResult() + " except " + entry.getKey());
                                }
                            }
                        }
                    }
                    connections.get(msg.getSender()).callMessage(m);


                }

            }catch (IOException | ClassNotFoundException e) {
                //thrown when the player disconnect
                this.manager.detectDisconnection(this.usernameClient);
                break;
            }catch(NullPointerException e){
                try {
                    socket.close();
                    System.err.println("Client disconnected, client handler will be closed");
                    break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }

    }
}