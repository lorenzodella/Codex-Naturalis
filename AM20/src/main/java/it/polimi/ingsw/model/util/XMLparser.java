package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.*;
import it.polimi.ingsw.model.cards.playable.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to parse the XML files that contain the cards
 */
public class XMLparser {

    /**
     * This method is used to parse the starter cards
     * @param filePath : the path of the file that contains the starter cards
     * @return the list of starter cards
     */
    public static ArrayList<PlayableCard> parseStarterCards(String filePath) {
        //lista da ritornare
        ArrayList<PlayableCard> starterCards = new ArrayList<>();
        try {
            InputStream file = XMLparser.class.getResourceAsStream(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file); //documento che interessa

            //ottengo la lista di carte
            NodeList cardList = doc.getElementsByTagName("starterCard");
            //per ogni elemento (carta)...
            for (int i = 0; i < cardList.getLength(); i++) {
                //leggo una nuova carda
                Node node = cardList.item(i);

                //questo if si usa solo quando c'è un tag con dentro altri tag
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //fare sempre!
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

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "S" + id;

                    StarterCard sc = new StarterCard(ID, frontCorners, backCorners, resources);
                    //la aggiungo alla lista
                    starterCards.add(sc);
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot read file: "+filePath);
            return null;
        }
        return starterCards;
    }

    /**
     * This method is used to parse the gold cards
     * @param filePath : the path of the file that contains the gold cards
     * @return the list of gold cards
     */
    public static ArrayList<PlayableCard> parseGoldCards(String filePath) {
        ArrayList<PlayableCard> goldCards = new ArrayList<>();

        try {
            InputStream file = XMLparser.class.getResourceAsStream(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            NodeList cardList = doc.getElementsByTagName("objectGoldCard");
            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    NodeList fclist = card.getElementsByTagName("frontCorner");
                    Corner[] frontCorners = getCorners(fclist);

                    NodeList bclist = card.getElementsByTagName("backCorner");
                    Corner[] backCorners = getCorners(bclist);

                    NodeList requirementsList = card.getElementsByTagName("requirements")
                            .item(0).getChildNodes();
                    HashMap<Kingdom, Integer> requirements = getGoldCardRequirements(requirementsList);

                    Node kingdomNode = card.getElementsByTagName("kingdom")
                            .item(0);
                    Kingdom kingdom = Kingdom.parseKingdom(kingdomNode.getTextContent());

                    Node objectNode = card.getElementsByTagName("specialObject")
                            .item(0);
                    SpecialObject specialObject = SpecialObject.parseSpecialObject(objectNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "G" + id;

                    ObjectGoldCard ogc = new ObjectGoldCard(ID, frontCorners, backCorners, kingdom, requirements, specialObject);
                    goldCards.add(ogc);
                }
            }
            //PointsGoldCard
            cardList = doc.getElementsByTagName("pointsGoldCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    NodeList fclist = card.getElementsByTagName("frontCorner");
                    Corner[] frontCorners = getCorners(fclist);

                    NodeList bclist = card.getElementsByTagName("backCorner");
                    Corner[] backCorners = getCorners(bclist);

                    NodeList requirementsList = card.getElementsByTagName("requirements")
                            .item(0).getChildNodes();
                    HashMap<Kingdom, Integer> requirements = getGoldCardRequirements(requirementsList);

                    Node kingdomNode = card.getElementsByTagName("kingdom")
                            .item(0);
                    Kingdom kingdom = Kingdom.parseKingdom(kingdomNode.getTextContent());

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "G" + id;

                    PointsGoldCard pgc = new PointsGoldCard(ID, frontCorners, backCorners, kingdom, requirements, points);
                    goldCards.add(pgc);
                }
            }

            //CornerGldCard
            cardList = doc.getElementsByTagName("cornerGoldCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    NodeList fclist = card.getElementsByTagName("frontCorner");
                    Corner[] frontCorners = getCorners(fclist);

                    NodeList bclist = card.getElementsByTagName("backCorner");
                    Corner[] backCorners = getCorners(bclist);

                    NodeList requirementsList = card.getElementsByTagName("requirements")
                            .item(0).getChildNodes();
                    HashMap<Kingdom, Integer> requirements = getGoldCardRequirements(requirementsList);

                    Node kingdomNode = card.getElementsByTagName("kingdom")
                            .item(0);
                    Kingdom kingdom = Kingdom.parseKingdom(kingdomNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "G" + id;

                    CornerGoldCard cgc = new CornerGoldCard(ID, frontCorners, backCorners, kingdom, requirements);
                    goldCards.add(cgc);
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot read file: "+filePath);
            return null;
        }
        return goldCards;
    }

    /**
     * This method is used to parse the resource cards
     * @param filePath : the path of the file that contains the resource cards
     * @return the list of resource cards
     */
    public static ArrayList<PlayableCard> parseResourceCards(String filePath) {
        ArrayList<PlayableCard> resourceCard = new ArrayList<>();
        try {
            InputStream file = XMLparser.class.getResourceAsStream(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            NodeList cardList = doc.getElementsByTagName("resourceCard");
            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    NodeList fclist = card.getElementsByTagName("frontCorner");
                    Corner[] frontCorners = getCorners(fclist);

                    NodeList bclist = card.getElementsByTagName("backCorner");
                    Corner[] backCorners = getCorners(bclist);

                    Node kingdomNode = card.getElementsByTagName("kingdom")
                            .item(0);
                    Kingdom kingdom = Kingdom.parseKingdom(kingdomNode.getTextContent());

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "R" + id;

                    ResourceCard rc = new ResourceCard(ID, frontCorners, backCorners, kingdom, points);
                    resourceCard.add(rc);
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot read file: "+filePath);
            return null;
        }
        return resourceCard;
    }

    /**
     * This method is used to parse the objective cards
     * @param filePath : the path of the file that contains the objective cards
     * @return the list of objective cards
     */
    public static ArrayList<ObjectiveCard> parseObjectiveCards(String filePath) {
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
        try {
            InputStream file = XMLparser.class.getResourceAsStream(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            //pairOfObjectsCard
            NodeList cardList = doc.getElementsByTagName("pairOfObjectObjectiveCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node objectNode = card.getElementsByTagName("specialObject")
                            .item(0);
                    SpecialObject specialObject = SpecialObject.parseSpecialObject(objectNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "O" + id;

                    PairOfObjectsObjectiveCard poc = new PairOfObjectsObjectiveCard(ID, points, specialObject);
                    objectiveCards.add(poc);
                }
            }
            //TrioOfObjectsObjectivesCard
            cardList = doc.getElementsByTagName("trioOfObjectsObjectiveCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                //questo if si usa solo quando c'è un tag con dentro altri tag
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "O" + id;

                    TrioOfObjectsObjectiveCard tooc = new TrioOfObjectsObjectiveCard(ID, points);
                    objectiveCards.add(tooc);
                }
            }

            //TrioOfResourcesObjectivesCard
            cardList = doc.getElementsByTagName("trioOfResourcesObjectiveCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node kingdomNode = card.getElementsByTagName("resourcesKingdom")
                            .item(0);
                    Kingdom resourcesKingdom = Kingdom.parseKingdom(kingdomNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "O" + id;

                    TrioOfResourcesObjectiveCard torc = new TrioOfResourcesObjectiveCard(ID, points, resourcesKingdom);
                    objectiveCards.add(torc);
                }
            }

            //diagonalConfigurationObjectiveCard
            cardList = doc.getElementsByTagName("diagonalConfigurationObjectiveCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node kingdomNode = card.getElementsByTagName("kingdom")
                            .item(0);
                    Kingdom kingdom = Kingdom.parseKingdom(kingdomNode.getTextContent());

                    Node coveredCornerNode = card.getElementsByTagName("coveredCorner")
                            .item(0);
                    Integer coveredCorner = Integer.parseInt(coveredCornerNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "O" + id;

                    DiagonalConfigurationObjectiveCard dcoc = new DiagonalConfigurationObjectiveCard(ID, points, kingdom, coveredCorner);
                    objectiveCards.add(dcoc);
                }
            }

            //VerticalConfigurationObjectiveCard
            cardList = doc.getElementsByTagName("verticalConfigurationObjectiveCard");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;

                    Node pointsNode = card.getElementsByTagName("points")
                            .item(0);
                    Integer points = Integer.parseInt(pointsNode.getTextContent());

                    Node kingdom1Node = card.getElementsByTagName("kingdom1")
                            .item(0);
                    Kingdom kingdom1 = Kingdom.parseKingdom(kingdom1Node.getTextContent());

                    Node kingdom2Node = card.getElementsByTagName("kingdom2")
                            .item(0);
                    Kingdom kingdom2 = Kingdom.parseKingdom(kingdom2Node.getTextContent());

                    Node coveredCornerNode = card.getElementsByTagName("coveredCorner")
                            .item(0);
                    Integer coveredCorner = Integer.parseInt(coveredCornerNode.getTextContent());

                    Node idNode = card.getElementsByTagName("id")
                            .item(0);
                    Integer id = Integer.parseInt(idNode.getTextContent());
                    //una volta ottenute tutte le cose creo la carta
                    String ID = "O" + id;

                    VerticalConfigurationObjectiveCard vcoc = new VerticalConfigurationObjectiveCard(ID, points, kingdom1, kingdom2, coveredCorner);
                    objectiveCards.add(vcoc);
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot read file: "+filePath);
            return null;
        }
        return objectiveCards;
    }


    //METODI
    /**
     * This method is used to parse the requirements of a gold card
     * @param requirementsList : the list of requirements
     * @return the hashmap that contains the requirements
     */
    private static HashMap<Kingdom, Integer> getGoldCardRequirements(NodeList requirementsList) {
        //inizializzo la lista
        HashMap<Kingdom, Integer> requirements = Kingdom.createEmptyMap();
        //essendo un tag che ha dentro altri tag
        for (int j = 0; j < requirementsList.getLength(); j++) {
            Node n = requirementsList.item(j);
            //if inutile...per ogni sottoelemento
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Kingdom key = Kingdom.parseKingdom(n.getNodeName());
                Integer num = Integer.parseInt(n.getTextContent());
                //riempio hashmap
                requirements.put(key, num);
            }
        }
        return requirements;
    }

    /**
     * This method is used to parse the corners of a card
     * @param list : the list of corners
     * @return the array of corners
     */
    private static Corner[] getCorners(NodeList list) {
        //inizializzo le var tmp
        Corner cornerTmp;
        Corner[] corners = new Corner[4];
        for (int j = 0; j < list.getLength(); j++) {
            Element e = (Element) list.item(j);
            //creo un corner sulla base di quello che contiene

            if (e.hasAttribute("res"))
                cornerTmp = new Corner(Kingdom.parseKingdom(e.getAttribute("res")));
            else if (e.hasAttribute("obj"))
                cornerTmp = new Corner(SpecialObject.parseSpecialObject(e.getAttribute("obj")));
            else
                cornerTmp = new Corner();

            //lo inserisco nel vettore alla posizione
            corners[Integer.parseInt(e.getAttribute("pos"))] = cornerTmp;
        }
        return corners;
    }

    /**
     * This method is used to parse the resources of a starter card
     * @param list : the list of resources
     * @return the list of resources
     */
    private static ArrayList<Kingdom> getStarterCardResources(NodeList list) {
        //inizializzo la lista
        ArrayList<Kingdom> resources = new ArrayList<>();
        //essendo un tag che ha dentro altri tag
        for (int j = 0; j < list.getLength(); j++) {
            Node n = list.item(j);
            //if inutile...per ogni sottoelemento
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                //aggiungo il kingdom alle risorse
                resources.add(Kingdom.parseKingdom(n.getNodeName())); //Insect
            }
        }
        return resources;
    }
}

