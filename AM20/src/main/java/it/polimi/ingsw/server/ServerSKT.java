package it.polimi.ingsw.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServerSKT {

    /**
     * this attribute stands for the server's port
     */
    private int port;
    /**
     * this attribute stands as a reference to the serverManager
     */
    private ServerManager manager;


    /**
     * This method creates a thread pool and the object serverSocket that allows to accept connections from the clients.
     * @param port the server's port
     * @param manager the reference to the serverManager
     */
    public ServerSKT(int port, ServerManager manager) {
        this.manager = manager;
        this.port = port;
    }

    //spiegazione metodo
    public void startServer() {
        //creazione insieme di thread
        //ExecutorService executor = Executors.newCachedThreadPool();
        //crea oggetto serverSocket che permette di accettare le connessioni
        ServerSocket serverSocket;
        try {
            //apriamo serverSocket da una porta port
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        //ciclo infinito che aspetta
        while (true) {
            try {
                System.out.println("Waiting for connection...");
                //metodo che aspetta, interrompe il processo finchè qualcuno non si connette e ritorna il socket
                //specifico del client (con "tubi" di andata e di ritorno)
                Socket socket = serverSocket.accept();

                //thread parte e ascolta sul socket grazie alla socketconnection
                new Thread(new ClientHandler(socket, manager)).start();
            } catch(IOException e) {
                break;
            }
        }
        //executor.shutdown();
    }

}
