package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent, to all players, when a player disconnects
 */
public class DisconnectionMessage extends PickAckMessage {
    /**
     * this attribute is true every time that a player disconnected right after playing a card (without picking
     * another card from the deck) so that the game picks the first card of the resource deck.
     * otherwise this attribute is always false.
     */
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
