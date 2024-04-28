package it.polimi.ingsw.controller.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.server.Callback;
import it.polimi.ingsw.server.Loggable;

public class ClientRMI extends UnicastRemoteObject implements Callback {


    protected ClientRMI() throws RemoteException {
    }

    public static void main(String[] args) {
        System.out.println("Hello from RMIClient");
        try {
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            Loggable stub = (Loggable) registry.lookup("Loggable");

//            int port = ((new Random().nextInt(16383)) + 49152);
//            Registry exportedRegistry = LocateRegistry.createRegistry(port);
//            exportedRegistry.rebind("Lollo", UnicastRemoteObject.exportObject(new RMIClient(), port));

//            boolean logged = stub.login("Lollo", new ClientRMI());
//            System.out.println(logged);

            ConnectionAckMessage msg = stub.login("Lollo", new ClientRMI());
            System.out.println("stub.login: \n"+ msg);

            Message msg2 = stub.starNewGame("Lollo", 4, new ClientRMI());
            System.out.println("stub.starNewGame: \n"+ msg2);
            ConnectionAckMessage msg3 = stub.login("Pietro", new ClientRMI());
            System.out.println("stub.login: \n"+ msg3);
            ConnectionAckMessage msg4 = stub.login("Genoveffa", new ClientRMI());
            System.out.println("stub.login: \n"+ msg4);
            ConnectionAckMessage msg5 = stub.login("Alessia", new ClientRMI());
            System.out.println("stub.login: \n"+ msg5);

            StarterCardAckMessage msgStarterSide = stub.chooseStarterCardSide("Lollo", PlayableCard.FRONT);
            System.out.println("stub.chooseStarterCardSide: \n"+ msgStarterSide);
            StarterCardAckMessage msgStarterSide2 = stub.chooseStarterCardSide("Pietro", PlayableCard.BACK);
            System.out.println("stub.chooseStarterCardSide: \n"+ msgStarterSide2);
            StarterCardAckMessage msgStarterSide3 = stub.chooseStarterCardSide("Alessia", PlayableCard.BACK);
            System.out.println("stub.chooseStarterCardSide: \n"+ msgStarterSide3);
            StarterCardAckMessage msgStarterSide4 = stub.chooseStarterCardSide("Genoveffa", PlayableCard.BACK);
            System.out.println("stub.chooseStarterCardSide: \n"+ msgStarterSide4);

            ObjectiveAckMessage msgObjective = stub.chooseObjective("Lollo", PlayableCard.BACK);
            System.out.println("stub.chooseObjective: \n"+ msgObjective);
            ObjectiveAckMessage msgObjective2 = stub.chooseObjective("Pietro", PlayableCard.FRONT);
            System.out.println("stub.chooseObjective: \n"+ msgObjective2);
            ObjectiveAckMessage msgObjective3 = stub.chooseObjective("Genoveffa", PlayableCard.BACK);
            System.out.println("stub.chooseObjective: \n"+ msgObjective3);
            ObjectiveAckMessage msgObjective4 = stub.chooseObjective("Alessia", PlayableCard.BACK);
            System.out.println("stub.chooseObjective: \n"+ msgObjective4);







        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        } catch (CannotJoinGameException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (InvalidPlayingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void callMessage(Message message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void callConnectionAckMessage(ConnectionAckMessage message) throws RemoteException {
        System.out.println("Game starts : " + message.isGameStarts() + "\n");
        if(message.isGameStarts()){
            System.out.println("GoldTop: " + message.getGoldTop().getID() + "\n" +
                    "ResourceTop: " + message.getResourceTop().getID() + "\n" +
                    "GoldVisible[0]: " + message.getGoldVisible()[0].getID() + "\n" +
                    "GoldVisible[1]: " + message.getGoldVisible()[1].getID() + "\n" +
                    "ResourceVisible[0]: " + message.getResourceVisible()[0].getID() + "\n" +
                    "ResourceVisible[1]: " + message.getResourceVisible()[1].getID() + "\n" +
                    "StarterCard: " + message.getStarterCard().getID() + "\n" +
                    "InitialCards[0]: " + message.getInitialCards().get(0).getID() + "\n"+
                    "InitialCards[1]: " + message.getInitialCards().get(1).getID() + "\n"+
                    "InitialCards[2]: " + message.getInitialCards().get(2).getID() + "\n");
        }

    }

    @Override
    public void callAcknowledgeMessage(AcknowledgeMessage message) throws RemoteException {
        System.out.println(message);
        if(message instanceof AcknowledgeMessage){
            System.out.println("nextPlayer: "+ message.getNextPlayer()+"\n"+
                    "cards[0]: "+ message.getCards().get(0)+"\n"+
                    "cards[1]: "+ message.getCards().get(1)+"\n"+
                    "cards[2]: "+ message.getCards().get(2)+"\n"+
                    "numOfConnectedPlayer: " + message.getNumOfConnectedPlayers());
        }
        if(message instanceof PlayAckMessage){
            System.out.println("YourPlayerInfo: " + message.getYourPlayerInfo()+"\n"+
                    "MustPick: "+message.isMustPick());
        }
        if(message instanceof PickAckMessage){
            System.out.println("GoldTop: "+ message.getGoldTop().getID()+"\n"+
                    "ResourceTop: "+ message.getResourceTop().getID()+ "\n"+
                    "GoldVisible[0]: " + message.getGoldVisible()[0].getID() + "\n" +
                    "GoldVisible[1]: " + message.getGoldVisible()[1].getID() + "\n" +
                    "ResourceVisible[0]: " + message.getResourceVisible()[0].getID() + "\n" +
                    "ResourceVisible[1]: " + message.getResourceVisible()[1].getID() + "\n");
        }

    }

    @Override
    public void callStarterCardAckMessage(StarterCardAckMessage message) throws RemoteException {
        System.out.println("ChooseObjective: " + message.isChooseObjective() + "\n");
        if(message.isChooseObjective()){
            System.out.println( "CommonObjective[0]: " + message.getCommonObjectives()[0].getID() + "\n" +
                    "CommonObjective[1]: " + message.getCommonObjectives()[1].getID() + "\n" +
                    "SecretObjective[0]: " + message.getSecretObjectives()[0].getID() + "\n" +
                    "SecretObjective[1]: " + message.getSecretObjectives()[1].getID() + "\n");
        }else {
            System.out.println("PlayerInfo: "+ message.getPlayerInfo()+"\n");
        }

    }

    @Override
    public void callObjectiveAckMessage(ObjectiveAckMessage message) throws RemoteException {
        System.out.println("StartPlaying: " + message.isStartPlaying() + "\n");
        if(message.isStartPlaying()){
            System.out.println("FirstPlayer: " + message.getFirstPlayer());
        }else {
            System.out.println("SecretObjective[0]: " + message.getSecretObjectives()[0].getID() + "\n" +
                    "SecretObjective[1]: " + message.getSecretObjectives()[1].getID() + "\n");
        }

    }
}