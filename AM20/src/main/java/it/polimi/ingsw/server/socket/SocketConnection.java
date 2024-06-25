package it.polimi.ingsw.server.socket;

import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.server.Connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//TODO: ELEONORA

public class SocketConnection implements Connection {

    /**
     * The output stream accepts output bytes
     */
    private ObjectOutputStream outputStream;

    public SocketConnection(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());

    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    @Override
    public synchronized void callChatMessage(ChatMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    @Override
    public synchronized void callStopGame(StopGameMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    @Override
    public synchronized void callConnectionAckMessage(ConnectionAckMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    @Override
    public synchronized void callAcknowledgeMessage(AcknowledgeMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    @Override
    public synchronized void callStarterCardAckMessage(StarterCardAckMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    @Override
    public synchronized void callObjectiveAckMessage(ObjectiveAckMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    public synchronized void callMessage(Message message) throws IOException{
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @throws IOException
     */
    public synchronized void callErrorMessage(ErrorMessage message) throws IOException{
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * This method refresh the output stream, it sends the message and finally
     * flushes the output stream and forces any buffered output bytes to be written out.
     * @param message to send to the client
     * @return true if the message has been sent correctly
     * @throws IOException
     */
    /* se il messaggio viene inviato ritorna vero altrimenti manda exc */
    @Override
    public synchronized boolean callPingMessage(PingMessage message) throws IOException {
        outputStream.reset();
        outputStream.writeObject(message);
        outputStream.flush();
        return true;
    }


}
