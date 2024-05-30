package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.controller.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SKTClientReceiver implements Runnable {

    private ObjectInputStream objectInputStream;
    private UIUpdater uiUpdater;

    private Socket socket;
    public SKTClientReceiver(Socket socket, UIUpdater uiUpdater) throws IOException {
        this.socket = socket;
        this.uiUpdater = uiUpdater;
        //this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run(){
        try {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
        Message message;
        while(true){
            try {
                message = new Message();
                message = (Message) objectInputStream.readObject();
                if(message.getType().equals(Message.CONNECTIONACK)){
                    ConnectionAckMessage msg = (ConnectionAckMessage) message;
                    if(!msg.doesGameStarts()){ //false è ConnectionAckMessage
                        this.uiUpdater.connectionAck(msg);
                    }else if(msg.isReconnection()){ //true è ReconnectionMessage
                        this.uiUpdater.reconnection((ReconnectionMessage) message);
                    }else if(msg.doesGameStarts()){ //true è StartGameMessage
                        this.uiUpdater.startGame((StartGameMessage) message);
                    }

                }else if(message.getType().equals(Message.ACKNOWLEDGE)){
                    AcknowledgeMessage msg = (AcknowledgeMessage) message;
                    if(msg.getAction().equals(AcknowledgeMessage.DISCONNECTION))
                        this.uiUpdater.disconnectionAck((DisconnectionMessage) msg);
                    if(msg.getAction().equals(AcknowledgeMessage.PLAY))
                        this.uiUpdater.playAck((PlayAckMessage) msg);
                    if(msg.getAction().equals(AcknowledgeMessage.PICK))
                        this.uiUpdater.pickAck((PickAckMessage) msg);

                }else if(message.getType().equals(Message.STARTERCARDACK)){
                    StarterCardAckMessage msg = (StarterCardAckMessage) message;
                    if(msg.shouldChooseObjective()) //true quindi è startChoosignObjectiveMessage
                        this.uiUpdater.startChoosingObjective((StartChoosingObjectiveMessage) message);
                    else
                        this.uiUpdater.starterCard(msg);

                }else if(message.getType().equals(Message.CHAT)){
                    ChatMessage msg = (ChatMessage) message;
                    this.uiUpdater.chatMessage(msg);

                }else if(message.getType().equals(Message.OBJECTIVEACK)){
                    ObjectiveAckMessage msg = (ObjectiveAckMessage) message;
                    if(msg.shouldStartPlaying())//true quindi è una StartPlayingMessage
                        this.uiUpdater.startPlaying((StartPlayingMessage) message);
                    else
                        this.uiUpdater.objectiveMessage(msg);

                }else if(message.getType().equals(Message.STOPGAME)){
                    StopGameMessage msg = (StopGameMessage) message;
                    this.uiUpdater.stopGame(msg);

                }else if(message.getType().equals(Message.ERROR)){
                    ErrorMessage msg = (ErrorMessage) message;
                    this.uiUpdater.errorMessage(msg);

                }else if(message.getType().equals(Message.PING)){
                }else if(message.getType().equals(Message.GENERIC)){
                    Message msg = (Message) message;
                    this.uiUpdater.message(msg);
                }
            } catch (IOException | ClassNotFoundException e) {
                uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
                break;
            }

        }
    }
}
