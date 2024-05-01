package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.Message;

import java.io.IOException;
import java.rmi.RemoteException;

public class EndGameTimer {

    private Thread t;
    private static final int SEC = 30;

    public void startCountdown(Connection callback) {
        t = new Thread(()->{
            try {
                for (int i = SEC; i > 0; i--) {
                    Thread.sleep(1000);
                    System.err.println("Game ends in "+i+" seconds...");
                }
                Message m = new Message();
                m.setResult("You won because everyone left the game");
                callback.callStopGame(m);
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
