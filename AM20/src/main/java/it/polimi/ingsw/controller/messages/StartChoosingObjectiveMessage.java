package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

public class StartChoosingObjectiveMessage extends StarterCardAckMessage {
    /**
     * This attribute a 2 items array that contains the 2 common objectives
     */
    private ObjectiveCard[] commonObjectives;
    /**
     * This attribute a 2 items array that contains the 2 secret objectives
     * ps: the player now needs to choose the secret objective they want
     */
    private ObjectiveCard[] secretObjectives;

    @Override
    public boolean shouldChooseObjective() {
        return true;
    }

    @Override
    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    @Override
    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    @Override
    public ObjectiveCard[] getSecretObjectives() {
        return secretObjectives;
    }

    @Override
    public void setSecretObjectives(ObjectiveCard[] secretObjectives) {
        this.secretObjectives = secretObjectives;
    }

    @Override
    public String toString() {
        return "StartChoosingObjectiveMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
