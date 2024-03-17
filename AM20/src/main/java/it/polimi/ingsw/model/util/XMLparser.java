package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.Kingdom;
import it.polimi.ingsw.model.SpecialObject;
import it.polimi.ingsw.model.StarterCard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class XMLparser {

    public static ArrayList<StarterCard> parseStarterCards(String filePath){
        //lista da ritornare
        ArrayList<StarterCard> starterCards = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file); //documento che interessa

            //ottengo la lista di carte
            NodeList cardList = doc.getElementsByTagName("starterCard");
            //per ogni elemento...
            for (int i = 0; i < cardList.getLength(); i++) {
                //leggo una nuova carda
                Node node = cardList.item(i);

                //questo if si usa solo quando c'è un tag con dentro altri tag
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    //ottengo tutti gli elementi frontCorner
                    NodeList fclist = card.getElementsByTagName("frontCorner");
                    //creo il vettore di fc
                    Corner[] frontCorners = getCorners(fclist);

                    //ottengo tutti gli elementi backCorner
                    NodeList bclist = card.getElementsByTagName("backCorner");
                    //creo il vettore di bc
                    Corner[] backCorners = getCorners(bclist);

                    //***
                    //ottengo l'elemento resources e i suoi sottoelementi
                    NodeList resourcesArray = card.getElementsByTagName("resources")
                            .item(0).getChildNodes(); //item(0) perchè ho solo un tag resources
                    //creo la lista di resources
                    ArrayList<Kingdom> resources = getStarterCardResources(resourcesArray);

                    //una volta ottenute tutte le cose creo la carta
                    String ID = "S"+i;
                    StarterCard sc = new StarterCard(ID, frontCorners, backCorners, resources);
                    //la aggiungo alla lista
                    starterCards.add(sc);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return starterCards;
    }

    private static Corner[] getCorners(NodeList list){
        //inizializzo le var tmp
        Corner cornerTmp;
        Corner[] corners = new Corner[4];
        for(int j = 0; j < list.getLength(); j++){
            Element e = (Element) list.item(j);
            //creo un corner sulla base di quello che contiene
            cornerTmp = new Corner();
            if(e.hasAttribute("res"))
                cornerTmp = new Corner(Kingdom.parseKingdom(e.getAttribute("res")));
            if(e.hasAttribute("obj"))
                cornerTmp = new Corner(SpecialObject.parseSpecialObject(e.getAttribute("obj")));
            //lo inserisco nel vettore alla posizione
            corners[Integer.parseInt(e.getAttribute("pos"))] = cornerTmp;
        }
        return corners;
    }

    private static ArrayList<Kingdom> getStarterCardResources(NodeList list){
        //inizializzo la lista
        ArrayList<Kingdom> resources = new ArrayList<>();
        //essendo un tag che ha dentro altri tag
        for (int j=0; j < list.getLength(); j++) {
            Node n = list.item(j);
            //per ogni sottoelemento...
            if(n.getNodeType() == Node.ELEMENT_NODE){
                //aggiungo il kingdom alle risorse
                resources.add(Kingdom.parseKingdom(n.getNodeName())); //Insect
            }
        }
        return  resources;
    }
}
