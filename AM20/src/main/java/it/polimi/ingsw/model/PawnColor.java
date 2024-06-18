package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.SpecialObject;

public enum PawnColor {
    ROUGE,
    BLEU,
    VERT,
    JAUNE;

    public static PawnColor parsePawnColor(String s){
        switch(s){
            case "rouge":
                return ROUGE;
            case "bleu":
                return BLEU;
            case "vert":
                return VERT;
            case "jaune":
                return JAUNE;
            default:
                return null;
        }
    }
}
