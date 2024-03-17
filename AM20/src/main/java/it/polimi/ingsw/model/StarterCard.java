package it.polimi.ingsw.model;

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
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
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


    // TODO: da controllare e da fare JAVADoc e da fare testing (forse si può snellire il codice)
    public HashMap getKingdoms(){
        HashMap<Kingdom, Integer> res = new HashMap<>();
        int numFungi = 0;
        int numAnimal = 0;
        int numPlant = 0;
        int numInsect = 0;

        for(int j=0; j<resources.size();j++){
            if(this.resources.get(j) == Kingdom.Fungi)
                numFungi++;
            if(this.resources.get(j) == Kingdom.Animal)
                numAnimal++;
            if(this.resources.get(j) == Kingdom.Plant)
                numPlant++;
            if(this.resources.get(j) == Kingdom.Insect)
                numInsect++;
        }

        if(this.isFront()){
            for (Corner tmp : this.getFrontCorners()){
                if(tmp!=null){
                    if(!tmp.isHidden()){
                        if(tmp.getContentKingdom() == Kingdom.Fungi)
                            numFungi++;
                        if(tmp.getContentKingdom() == Kingdom.Animal)
                            numAnimal++;
                        if(tmp.getContentKingdom() == Kingdom.Plant)
                            numPlant++;
                        if(tmp.getContentKingdom() == Kingdom.Insect)
                            numInsect++;
                    }
                }

            }

        }else {
            for (Corner tmp : this.getBackCorners()){
                if(tmp!=null){
                    if(!tmp.isHidden()){
                        if(tmp.getContentKingdom() == Kingdom.Fungi)
                            numFungi++;
                        if(tmp.getContentKingdom() == Kingdom.Animal)
                            numAnimal++;
                        if(tmp.getContentKingdom() == Kingdom.Plant)
                            numPlant++;
                        if(tmp.getContentKingdom() == Kingdom.Insect)
                            numInsect++;
                    }
                }
            }
        }

        res.put(Kingdom.Fungi, numFungi);
        res.put(Kingdom.Animal, numAnimal);
        res.put(Kingdom.Plant, numPlant);
        res.put(Kingdom.Insect, numInsect);

        return res;
    }
}
