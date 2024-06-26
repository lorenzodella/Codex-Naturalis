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

/**
 * This class is used to print the map of the cards in the TUI.
 */
public class CardPrinter {

    /**
     * This method is used to get the color of the kingdom.
     * @param k is the kingdom.
     * @return the color of the kingdom.
     */
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

    /**
     * This method is used to get the char representation of a special object.
     * @param o is the special object.
     * @return the special object representation.
     */
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

    /**
     * This method is used to parse a corner of a card given its content.
     * @param c is the corner.
     * @param symbol is the symbol to use.
     * @return the corner representation.
     */
    private static String parseCorner(Corner c, char symbol){
        if(c==null)
            return "─";
        else if(c.isHidden())
            return " ";
        else if(c.getContentKingdom()!=null)
            return getKingdomColor(c.getContentKingdom())+symbol+ConsoleColors.TEXT_RESET;
        else if(c.getContentObject()!=null)
            return getSpecialObject(c.getContentObject())+ConsoleColors.TEXT_RESET;
        return String.valueOf(symbol);
    }

    /**
     * This method is used to decode a card corner and print his representation.
     * @param c is the card.
     * @param corner is the corner to decode.
     * @return the corner representation.
     */
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

    /**
     * This method is used to print the map of the cards.
     * @param map is the map of the cards.
     */
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
