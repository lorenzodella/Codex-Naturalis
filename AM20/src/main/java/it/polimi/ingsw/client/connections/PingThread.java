package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIManager;
import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.controller.messages.PingMessage;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.ServerManager;

import java.io.IOException;
import java.util.Set;

public class PingThread implements Runnable{
    private ClientSender sender; //this
    private UIManager manager; //this

    public PingThread(ClientSender sender, UIManager manager){
        this.sender = sender;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            do {
                sender.sendPingMessage();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
            } while (true);
        }catch (IOException e){
            manager.showError("Server down! You have to relaunch application to play again");
        }
    }
}
