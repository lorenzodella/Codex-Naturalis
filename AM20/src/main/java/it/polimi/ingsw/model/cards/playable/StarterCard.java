package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;

import java.util.*;

public class StarterCard extends PlayableCard{
    /**
     * List of all the kingdoms that are shown in the center of the FRONT SIDE of the card.
     * PS: the rulebook says that these resources are shown on the BACK SIDE of the card but, we decided
     * to do the exact same thing that is shown in the pdf (pdf of every image and side of every card).
     */
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

    /**
     * This method calls the upper class method to get kingdoms that are drawn in the corner
     * of this specific card.
     * If this card is played on the front side, this method also adds the kingdoms that are in resources[]
     * @return a map with the occurrences per each kingdom
     */
    @Override
    public HashMap<Kingdom, Integer> getKingdoms(){
        HashMap<Kingdom, Integer> res = new HashMap<>(super.getKingdoms());
        if(getSide() == PlayableCard.BACK)
            for(Kingdom kingdom : resources){
                res.computeIfPresent(kingdom, (k,v)->v+1);
            }
        return res;
    }
}
