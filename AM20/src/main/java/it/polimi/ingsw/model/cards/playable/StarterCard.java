package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;

import java.util.*;

public class StarterCard extends PlayableCard{
    //TODO: sei sicuro che sia corretto ArrayList di Kingdom?? io ho scritto il codice con arrayList<Kingdom>
    private ArrayList<Kingdom> resources;

    public StarterCard(String ID, Corner[] frontCorners, Corner[] backCorners, ArrayList<Kingdom> resources) {
        super(ID, frontCorners, backCorners);
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StarterCard that = (StarterCard) o;
        return Objects.equals(resources, that.resources);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(resources);
    }

    @Override
    public String toString() {
        return "StarterCard{" +
                super.toString() +
                "resources=" + resources +
                "}";
    }

    /*public String getID(){
        return
    }*/


    @Override
    public HashMap<Kingdom, Integer> getKingdoms(){
        HashMap<Kingdom, Integer> res = new HashMap<>(super.getKingdoms());
        if(getSide() == PlayableCard.FRONT)
            for(Kingdom kingdom : resources){
                res.computeIfPresent(kingdom, (k,v)->v+1);
            }
        return res;
    }
}
