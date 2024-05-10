package it.polimi.ingsw.client;

import it.polimi.ingsw.clientmessage.ClientMessage;
import it.polimi.ingsw.clientmessage.PlayCardMessage;
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
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run(){

        Message message;
        while(true){
            try {
                message = (Message) objectInputStream.readObject();
                if(message.getType().equals(Message.CONNECTIONACK)){
                    ConnectionAckMessage msg = (ConnectionAckMessage) message;
                    if(!msg.doesGameStarts()){ //false è ConnectionAckMessage
                        this.uiUpdater.connectionAck(msg);
                    }else if(msg.doesGameStarts()){ //true è StartGameMessage
                        this.uiUpdater.startGame((StartGameMessage) message);
                    }

                }else if(message.getType().equals(Message.ACKNOWLEDGE)){
                    AcknowledgeMessage msg = (AcknowledgeMessage) message;
                    if(msg.getAction().equals("Disconnection"))
                        this.uiUpdater.acknowledge(msg);
                    if(msg.getAction().equals("Play"))
                        this.uiUpdater.playAck((PlayAckMessage) message);
                    if(msg.getAction().equals("Pick"))
                        this.uiUpdater.pickAck((PickAckMessage) message);

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
                        this.uiUpdater.objectivemessage(msg);

                }else if(message.getType().equals(Message.STOPGAME)){
                    StopGameMessage msg = (StopGameMessage) message;
                    this.uiUpdater.message(msg);

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
            }

        }
    }
}
