package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.DynamicMap;

public class PlayerInfo {
    private int score;
    private DynamicMap<String, PlayableCard> map;
    private PlayerStats stats;

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
