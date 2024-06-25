package it.polimi.ingsw.client.clientmessage;

import it.polimi.ingsw.model.PawnColor;

public class LoginMessage extends ClientMessage{
    /**
     * this string represents the client that wants to join the game
     */
    String client;
    /**
     * this attribute stands for the color of the pawn of this specific player
     */
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
