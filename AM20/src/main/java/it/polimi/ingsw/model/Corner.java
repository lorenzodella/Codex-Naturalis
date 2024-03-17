package it.polimi.ingsw.model;

public class Corner {
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

    @Override
    public String toString() {
        if(contentKingdom!=null)
            return "Corner{" +
                    "contentKingdom=" + contentKingdom +
                    '}';
        else if(contentObject!=null)
            return "Corner{" +
                    "contentObject=" + contentObject +
                    '}';
        else return "Empty corner";
    }
}
