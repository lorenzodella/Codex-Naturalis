package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//TODO: ELEONORA

public class SocketConnection implements Connection  {

    private ObjectOutputStream outputStream;

    public SocketConnection(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());

    }


    @Override
    public synchronized void callChatMessage(ChatMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public synchronized void callStopGame(StopGameMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public synchronized void callConnectionAckMessage(ConnectionAckMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public synchronized void callAcknowledgeMessage(AcknowledgeMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public synchronized void callStarterCardAckMessage(StarterCardAckMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public synchronized void callObjectiveAckMessage(ObjectiveAckMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public synchronized void callMessage(Message message) throws IOException{
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public synchronized void callErrorMessage(ErrorMessage message) throws IOException{
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /* se il messaggio viene inviato ritorna vero altrimenti manda exc */
    @Override
    public synchronized boolean callPingMessage(PingMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
        return true;
    }


}
