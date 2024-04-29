package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;

import java.util.HashMap;

public class RestartGameMessage extends StartGameMessage{
    private PlayerInfo playerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;



    @Override
    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    @Override
    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    @Override
    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    @Override
    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
    }
}
