package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.DynamicMap;

import java.io.Serializable;

//TODO: ELEONORA

public class PlayerInfo implements Serializable {
    /**
     * This attribute stands for the score .....
     */
    private int score;
    /**
     * This attribute is a map that shows the whole player table
     */
    private DynamicMap<String, PlayableCard> map;
    /**
     * This attribute shows the player's statistics:
     * It says the occurrences of every kingdom and the occurrences of every objects
     */
    private PlayerStats stats;

    private PawnColor color;

    public PlayerInfo(PawnColor color) {
        this.score = 0;
        this.map = null;
        this.stats = new PlayerStats();
        this.color = color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DynamicMap<String, PlayableCard> getMap() {
        return map;
    }

    public void setMap(DynamicMap<String, PlayableCard> map) {
        this.map = map;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "score=" + score +
                ", map=" + map +
                ", stats=" + stats +
                '}';
    }
}
