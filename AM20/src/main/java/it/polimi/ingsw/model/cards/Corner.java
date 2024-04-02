package it.polimi.ingsw.model.cards;

import java.util.Objects;

public class Corner {
    public static final int UL = 0;
    public static final int UR = 1;
    public static final int DL = 2;
    public static final int DR = 3;
    /**
     * this attribute says what kind of object is found inside this specific corner
     */
    private SpecialObject contentObject;
    /**
     * this attribute says what kind of resource is found inside this specific corner
     */
    private Kingdom contentKingdom;
    /**
     * this attribute is a boolean that says if the corner is visible or hidden (...)
     */
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
