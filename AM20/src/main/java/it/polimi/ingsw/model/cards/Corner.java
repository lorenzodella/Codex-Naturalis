package it.polimi.ingsw.model.cards;

import java.util.Objects;

public class Corner {
    public static int UL = 0;
    public static int UR = 1;
    public static int DL = 2;
    public static int DR = 3;
    private SpecialObject contentObject;
    private Kingdom contentKingdom;
    private boolean hidden;

    public Corner(){
    }

    public Corner(SpecialObject contentObject) {
        this.contentObject = contentObject;
    }

    public Corner(Kingdom contentKingdom){
        this.contentKingdom = contentKingdom;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corner corner = (Corner) o;
        return contentObject == corner.contentObject && contentKingdom == corner.contentKingdom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentObject, contentKingdom);
    }

    @Override
    public String toString() {
        if(contentKingdom!=null)
            return "Corner{" +
                    contentKingdom +
                    '}';
        else if(contentObject!=null)
            return "Corner{" +
                    contentObject +
                    '}';
        else return "Empty corner";
    }

    public boolean isHidden() {
        return hidden;
    }

    public SpecialObject getContentObject() {
        return contentObject;
    }

    public Kingdom getContentKingdom() {
        return contentKingdom;
    }
}
