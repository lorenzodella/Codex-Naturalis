package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.server.Connection;

public class LoginMessage extends ClientMessage{
    String client;
    Connection callback;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Connection getCallback() {
        return callback;
    }

    public void setCallback(Connection callback) {
        this.callback = callback;
    }

    public String getAction(){
        return LoginMessage.LOGIN;
    }
}
