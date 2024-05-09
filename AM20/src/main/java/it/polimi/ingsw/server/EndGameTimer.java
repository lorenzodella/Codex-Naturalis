package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.controller.messages.StopGameMessage;

import java.io.IOException;
import java.rmi.RemoteException;

public class EndGameTimer {

    private Thread t;
    private ServerManager manager;
    private static final int SEC = 30;

    public EndGameTimer(ServerManager manager){
        this.manager = manager;
    }

    public void startCountdown(Connection callback) {
        t = new Thread(()->{
            try {
                for (int i = SEC; i > 0; i--) {
                    Thread.sleep(1000);
                    System.err.println("Game ends in "+i+" seconds...");
                }
                StopGameMessage m = new StopGameMessage();
                m.setResult("You won because everyone left the game");
                callback.callStopGame(m);
                manager.reset();
            } catch (InterruptedException ignored) {}
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }

    public void stop(){
        if(t!=null && t.isAlive())
            t.interrupt();
    }
}
