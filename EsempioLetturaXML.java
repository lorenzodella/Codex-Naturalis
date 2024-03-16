package org.example;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.*;

public class EsempioLetturaXML
{
    public static void main( String[] args )
    {
        starterCards();
        //goldCards();
    }

    public static void starterCards(){
        try {
            File file = new File("starterCards.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file); //documento che interessa

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            //ottengo la lista di carte
            NodeList cardList = doc.getElementsByTagName("starterCard");
            System.out.println("----------------------------");

            for (int i = 0; i < cardList.getLength(); i++) {
                Node node = cardList.item(i);
                System.out.println("---STARTERCARD---");
                //leggo una nuova carda
                //questo if si usa solo quando c'è un tag con dentro altri tag
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;
                    //ottengo tutti gli elementi frontCorner
                    NodeList frontCorners = card.getElementsByTagName("frontCorner");
                    System.out.println("\nfrontCorners:");
                    for(int j = 0; j < frontCorners.getLength(); j++){
                        //qui non c'è l'if perché frontCorners non hanno altri tag
                        Element corner = (Element) frontCorners.item(j);
                        System.out.println("corner"+corner.getAttribute("pos"));
                        if(corner.hasAttribute("res"))
                            System.out.println("resource: "+corner.getAttribute("res"));
                        if(corner.hasAttribute("obj"))
                            System.out.println("object: "+corner.getAttribute("obj"));
                    }
                    //ottengo tutti gli elementi backCorner
                    NodeList backCorners = card.getElementsByTagName("backCorner");
                    System.out.println("\nbackCorners:");
                    for(int j = 0; j < backCorners.getLength(); j++){
                        Element corner = (Element) backCorners.item(j);
                        System.out.println("corner"+corner.getAttribute("pos"));
                        if(corner.hasAttribute("res"))
                            System.out.println("resource: "+corner.getAttribute("res"));
                        if(corner.hasAttribute("obj"))
                            System.out.println("object: "+corner.getAttribute("obj"));
                    }
                    //***
                    //ottengo l'elemento resources e i suoi sottoelementi
                    NodeList resourcesArray = card.getElementsByTagName("resources")
                            .item(0).getChildNodes(); //item(0) perchè ho solo un tag resources
                    System.out.println("\nresourcesArray:");
                    //essendo un tag che ha dentro altri tag
                    for (int j=0; j < resourcesArray.getLength(); j++) {
                        Node n = resourcesArray.item(j);
                        //per ogni sottoelemento...
                        if(n.getNodeType()==Node.ELEMENT_NODE){
                            Element resource = (Element) n;
                            System.out.println(n.getNodeName()); //Insect
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void goldCards(){
        try {
            File file = new File("goldCards.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            //ottengo la lista di carte
            NodeList cardList = doc.getElementsByTagName("objectGoldCard");
            System.out.println("----------------------------");

            for (int temp = 0; temp < cardList.getLength(); temp++) {
                Node node = cardList.item(temp);
                System.out.println("---GOLDCARD---");
                //leggo una nuova carda
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element card = (Element) node;
                    //ottengo tutti gli elementi frontCorner
                    NodeList frontCorners = card.getElementsByTagName("frontCorner");
                    System.out.println("\nfrontCorners:");
                    for(int i = 0; i < frontCorners.getLength(); i++){
                        Element corner = (Element) frontCorners.item(i);
                        System.out.println("corner"+corner.getAttribute("pos"));
                        if(corner.hasAttribute("res"))
                            System.out.println("resource: "+corner.getAttribute("res"));
                        if(corner.hasAttribute("obj"))
                            System.out.println("object: "+corner.getAttribute("obj"));
                    }
                    //ottengo tutti gli elementi backCorner
                    NodeList backCorners = card.getElementsByTagName("backCorner");
                    System.out.println("\nbackCorners:");
                    for(int i = 0; i < backCorners.getLength(); i++){
                        Element corner = (Element) backCorners.item(i);
                        System.out.println("corner"+corner.getAttribute("pos"));
                        if(corner.hasAttribute("res"))
                            System.out.println("resource: "+corner.getAttribute("res"));
                        if(corner.hasAttribute("obj"))
                            System.out.println("object: "+corner.getAttribute("obj"));
                    }

                    //**
                    //ottengo il kingdom
                    Element k = (Element) card.getElementsByTagName("kingdom").item(0);
                    System.out.println("\nkingdom: "+k.getTextContent());

                    //ottengo l'elemento requirements e i suoi sottoelementi
                    //*
                    NodeList req = card.getElementsByTagName("requirements")
                            .item(0).getChildNodes();
                    System.out.println("\nrequirements:");
                    for (int i=0; i < req.getLength(); i++) {
                        Node n = req.item(i);
                        //per ogni sottoelemento...
                        if(n.getNodeType()==Node.ELEMENT_NODE){
                            Element resource = (Element) n;
                            //nb
                            System.out.println(n.getNodeName()+" "+resource.getTextContent());
                        }
                    }

                    //ottengo l'oggetto speciale
                    //simile a **
                    Element obj = (Element) card.getElementsByTagName("specialObject").item(0);
                    System.out.println("\nspecialObject: "+obj.getTextContent());
                }
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

