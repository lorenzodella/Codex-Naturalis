package it.polimi.ingsw.client.tui.clientcard;

import it.polimi.ingsw.client.tui.ConsoleColors;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.DynamicMap;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class CardPrinter {

    private static String getKingdomColor(Kingdom k){
        if(k==null)
            return ConsoleColors.TEXT_RESET;
        switch(k){
            case Fungi:
                return ConsoleColors.TEXT_RED;
            case Animal:
                return ConsoleColors.TEXT_CYAN;
            case Plant:
                return ConsoleColors.TEXT_GREEN;
            case Insect:
                return ConsoleColors.TEXT_PURPLE;
            default:
                return ConsoleColors.TEXT_RESET;
        }
    }

    private static String getSpecialObject(SpecialObject o){
        if(o==null)
            return " ";
        switch(o){
            case Quill:
                return "Q";
            case Manuscript:
                return "M";
            case Inkwell:
                return "I";
            default:
                return " ";
        }
    }

    private static String parseCorner(Corner c, char simbol){
        if(c==null)
            return "─";
        else if(c.isHidden())
            return " ";
        else if(c.getContentKingdom()!=null)
            return getKingdomColor(c.getContentKingdom())+simbol+ConsoleColors.TEXT_RESET;
        else if(c.getContentObject()!=null)
            return getSpecialObject(c.getContentObject())+ConsoleColors.TEXT_RESET;
        return String.valueOf(simbol);
    }

    private static String decodeCardCorner(PlayableCard c, int corner){
        Corner[] corners = c.getSide()==PlayableCard.FRONT ? c.getFrontCorners() : c.getBackCorners();
        StringBuilder s = new StringBuilder();
        switch(corner){
            case Corner.UL:
                s.append("┌");
                s.append(parseCorner(corners[Corner.UL], '▄'));
                return s.toString();
            case Corner.UR:
                s.append(parseCorner(corners[Corner.UR], '▄'));
                s.append("┐");
                return s.toString();
            case Corner.DL:
                s.append("└");
                s.append(parseCorner(corners[Corner.DL], '▀'));
                return s.toString();
            case Corner.DR:
                s.append(parseCorner(corners[Corner.DR], '▀'));
                s.append("┘");
                return s.toString();
            default:
                return "  ";
        }

    }

    public static void printMap(DynamicMap<String, PlayableCard> map){
        int min = map.min();
        int tmp;
        List<Map<Point, PlayableCard>> m = map.getMapElementsLocation();
        StringBuilder s = new StringBuilder();
        for (Map<Point, PlayableCard> orderedMap : m) {
            tmp = min;
            for (Map.Entry<Point, PlayableCard> t : orderedMap.entrySet()) {
                if(t.getValue().isValid()) {
                    for (int i = tmp; i < t.getKey().x; i++) {
                        s.append("    ");
                    }
                    tmp = t.getKey().x+2;
                    s.append(decodeCardCorner(t.getValue(), Corner.UL));
                    s.append("───");
                    s.append(decodeCardCorner(t.getValue(), Corner.UR));
                    s.append(" ");
                }
            }
            s.append("\n");
            tmp = min;
            for (Map.Entry<Point, PlayableCard> t : orderedMap.entrySet()) {
                if(t.getValue().isValid()) {
                    for (int i = tmp; i < t.getKey().x; i++) {
                        s.append("    ");
                    }
                    tmp = t.getKey().x+2;
                    String id = t.getValue().getID();
                    if(id.length()==2)
                        id += " ";
                    s.append("│ " + id + " │");
                    s.append(" ");
                }
            }
            s.append("\n");
            tmp = min;
            for (Map.Entry<Point, PlayableCard> t : orderedMap.entrySet()) {
                if(t.getValue().isValid()) {
                    for (int i = tmp; i < t.getKey().x; i++) {
                        s.append("    ");
                    }
                    tmp = t.getKey().x+2;
                    s.append(decodeCardCorner(t.getValue(), Corner.DL));
                    s.append("───");
                    s.append(decodeCardCorner(t.getValue(), Corner.DR));
                    s.append(" ");
                }
            }
            s.append("\n");
        }
        s.append("\n");
        System.out.println(s);
    }
}
