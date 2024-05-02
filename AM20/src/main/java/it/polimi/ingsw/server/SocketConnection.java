package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection implements Connection  {

    private ObjectOutputStream outputStream;

    public SocketConnection(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());

    }


    @Override
    public void callChatMessage(ChatMessage message) throws IOException {
        outputStream.writeObject(message);
    }

    @Override
    public void callStopGame(Message message) throws IOException {
        outputStream.writeObject(message);

    }

    @Override
    public void callConnectionAckMessage(ConnectionAckMessage message) throws IOException {
        outputStream.writeObject(message);

    }

    @Override
    public void callAcknowledgeMessage(AcknowledgeMessage message) throws IOException {
        outputStream.writeObject(message);

    }

    @Override
    public void callStarterCardAckMessage(StarterCardAckMessage message) throws IOException {
        outputStream.writeObject(message);

    }

    @Override
    public void callObjectiveAckMessage(ObjectiveAckMessage message) throws IOException {
        outputStream.writeObject(message);

    }

    public void callMessage(Message message) throws IOException{
        outputStream.writeObject(message);
    }

    public void callErrorMessage(ErrorMessage message) throws IOException{
        outputStream.writeObject(message);
    }


}
