package it.polimi.ingsw.controller.messages;

public class DisconnectionMessage extends PickAckMessage {
    private boolean decksModified;

    @Override
    public void setDecksModified(boolean decksModified) {
        this.decksModified = decksModified;
    }

    @Override
    public boolean areDecksModified() {
        return decksModified;
    }

    @Override
    public String getAction() {
        return DISCONNECTION;
    }
}
