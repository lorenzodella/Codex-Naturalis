package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;

import java.util.HashMap;

public class PlayAckMessage extends AcknowledgeMessage{
    private PlayerInfo yourPlayerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    private boolean mustPick;

    @Override
    public PlayerInfo getYourPlayerInfo() {
        return yourPlayerInfo;
    }

    @Override
    public void setYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        this.yourPlayerInfo = yourPlayerInfo;
    }

    @Override
    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    @Override
    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
    }

    @Override
    public boolean mustPick() {
        return mustPick;
    }

    @Override
    public void setMustPick(boolean mustPick) {
        this.mustPick = mustPick;
    }

    @Override
    public String toString() {
        return "PlayAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
