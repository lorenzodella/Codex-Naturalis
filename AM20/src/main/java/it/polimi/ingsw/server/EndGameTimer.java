package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.controller.messages.StopGameMessage;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This class is the EndGameTimer class of the server side of the application.
 * It allows the server to understand if the game has ended because all players left the game.
 * It starts a countdown of 60 seconds and if no one has reconnected in that time, the game ends.
 */
public class EndGameTimer {

    private Thread t;
    private ServerManager manager;
    private static final int SEC = 60;

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
                //manager.reset();
            } catch (InterruptedException ignored) {}
            catch (IOException e) {
                manager.reset();
            }
        });
        t.start();
    }

    public void stop(){
        if(t!=null && t.isAlive())
            t.interrupt();
    }
}
