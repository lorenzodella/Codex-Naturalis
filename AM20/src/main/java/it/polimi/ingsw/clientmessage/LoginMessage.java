package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.server.Connection;

public class LoginMessage extends ClientMessage{
    String client;

    public LoginMessage(String client){
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAction(){
        return LoginMessage.LOGIN;
    }
}
