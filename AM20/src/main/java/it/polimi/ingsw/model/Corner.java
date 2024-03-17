package it.polimi.ingsw.model;

import java.util.Objects;

public class Corner {
    private SpecialObject contentObject;
    private Kingdom contentKingdom;
    private boolean hidden;

    public Corner(){
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

    public Corner(SpecialObject contentObject) {
        this.contentObject = contentObject;
    }

    public Corner(Kingdom contentKingdom){
        this.contentKingdom = contentKingdom;
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
}
