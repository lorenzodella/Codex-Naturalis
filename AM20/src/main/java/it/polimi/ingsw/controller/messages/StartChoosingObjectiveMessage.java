package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

public class StartChoosingObjectiveMessage extends StarterCardAckMessage {

    private ObjectiveCard[] commonObjectives;
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
