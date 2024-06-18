package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.server.Connection;

public class LoginMessage extends ClientMessage{
    String client;
    PawnColor color;

    public LoginMessage(String client, PawnColor color){
        this.client = client;
        this.color = color;
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

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }
}
