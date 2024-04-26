package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.exceptions.StopGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.exceptions.InvalidConnectionStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller c;
    private HashMap<String, ConnectionAckMessage> connectionAckMessages;
    private HashMap<String, StarterCardAckMessage> starterCardAckMessages;
    private HashMap<String, ObjectiveAckMessage> objectiveAckMessages;
    private HashMap<String, AcknowledgeMessage> acknowledgeMessages;

    void printMessages(HashMap<String, ? extends Message> msg){
        System.out.println(msg.toString().replace("},", "}\n"));
    }

    @BeforeEach
    void setUp() {
        c = new Controller();
    }

    void _simulateNewGame() throws InvalidArgumentException, CannotJoinGameException, InvalidPlayingException {
        c.newGame("Giuseppe", 3);
        c.joinGame("Pippo");
        c.joinGame("Pietro");
    }

    void _simulateStarterCards() throws InvalidArgumentException, InvalidPlayingException {
        c.chooseStarterCardSide("Giuseppe", PlayableCard.BACK);
        c.chooseStarterCardSide("Pippo", PlayableCard.BACK);
        c.chooseStarterCardSide("Pietro", PlayableCard.BACK);
    }

    void _simulateObjectives() throws InvalidArgumentException, InvalidPlayingException {
        c.chooseObjective("Giuseppe", 1);
        c.chooseObjective("Pippo", 1);
        c.chooseObjective("Pietro", 1);
    }

    //-------------------------------------TEST----------------------------------

    @Test
    void newGame() throws InvalidArgumentException, InvalidPlayingException {
        assertThrows(InvalidArgumentException.class, ()->{
            c.newGame("Pippo", 1);
        });

        assertThrows(InvalidArgumentException.class, ()->{
            c.newGame("Pippo", 5);
        });

        Message m = c.newGame("Pippo", 3);
        assertEquals(3 ,c.getNumPlayers() );
        assertEquals("Pippo", c.getPlayers().get(0));

        assertThrows(InvalidPlayingException.class, ()->{
            c.newGame("Pippo", 4);
        });

        System.out.println(m.getResult());
    }

    @Test
    void joinGame() throws CannotJoinGameException, InvalidArgumentException, InvalidPlayingException {
        //non è ancora stato iniziato un game
        assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });

        c.newGame("Giuseppe", 3);
        //provo a collegarmi con lo stesso nome già usato
        assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });

        connectionAckMessages = c.joinGame("Pippo");
        assertEquals(2, connectionAckMessages.size());
        assertFalse(connectionAckMessages.get("Pippo").isGameStarts());
        printMessages(connectionAckMessages);

        connectionAckMessages = c.joinGame("Pietro");
        assertEquals(3, connectionAckMessages.size());
        assertTrue(connectionAckMessages.get("Pietro").isGameStarts());
        printMessages(connectionAckMessages);

        //provo a collegarmi con una partita già piena
        assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Mattia");
        });

        assertEquals(3, c.getNumPlayers());
        assertEquals("Giuseppe", c.getPlayers().get(0));
        assertEquals("Pippo", c.getPlayers().get(1));
        assertEquals("Pietro", c.getPlayers().get(2));
        assertNotNull(c.getMessageBuilder());
        assertNotNull(c.getGameModel());
        assertEquals( "Giuseppe" ,c.getGameModel().getPlayers().get(0).getNickname());
        assertNotNull(c.getGameModel().getPlayers().get(0).getTable());
        assertNotNull(c.getGameModel().getPlayers().get(0).getCards());
        assertEquals(0,c.getGameModel().getPlayers().get(0).getScore());
        assertNotNull(c.getGameModel().getPlayers().get(0).getStarterCard());
        //dobbiamo controllare anche i sottometodi chiamati dal game model oppure essnedo già testati va bene così?
    }

    @Test
    void reconnectPlayer() throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException {
        _simulateNewGame();
        _simulateStarterCards();
        _simulateObjectives();

        //someone tries to connect with a nickname of a player already online
        CannotJoinGameException e1 = assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });
        assert e1.toString().contains("already playing");
    }

    @Test
    void disconnectPlayer()
            throws InvalidArgumentException, CannotJoinGameException, StopGameException, InvalidConnectionStateException, InvalidPlayingException {
        _simulateNewGame();

        InvalidArgumentException e = assertThrows(InvalidArgumentException.class, ()->{
            c.disconnectPlayer("Gianni");
        });
        assert e.toString().contains("nickname");

        _simulateStarterCards();
        
        //a player disconnects during preliminary phase
        StopGameException e1 = assertThrows(StopGameException.class, ()->{
            c.disconnectPlayer("Giuseppe");
        });
        assert e1.toString().contains("preliminary");

        _simulateObjectives();

        List<String> playerOrder = c.getGameModel().getPlayers().stream().map(Player::getNickname).collect(Collectors.toList());
        System.out.println(playerOrder);

        //one player disconnects
        acknowledgeMessages = c.disconnectPlayer(playerOrder.get(1));
        assertEquals(2, c.getGameModel().getConnectedPlayers().size());
        printMessages(acknowledgeMessages);
        //re-disconnects
        assertThrows(InvalidConnectionStateException.class, ()->{
            c.disconnectPlayer(playerOrder.get(1));
        });
        //someone tries to connect
        CannotJoinGameException e2 = assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Gianni");
        });
        assert e2.toString().contains("full");
        //the player reconnects
        connectionAckMessages = c.joinGame(playerOrder.get(1));
        assertEquals(3, c.getGameModel().getConnectedPlayers().size());
        printMessages(connectionAckMessages);

        //two players disconnect
        c.disconnectPlayer(playerOrder.get(2));
        acknowledgeMessages = c.disconnectPlayer(playerOrder.get(1));
        assertEquals(1, acknowledgeMessages.size());
        assertEquals(1, acknowledgeMessages.get(playerOrder.get(0)).getNumOfConnectedPlayers());
        printMessages(acknowledgeMessages);
        c.joinGame(playerOrder.get(1));
        c.joinGame(playerOrder.get(2));
        assertEquals(3, c.getGameModel().getConnectedPlayers().size());

        //everyone disconnects
        c.disconnectPlayer(playerOrder.get(0));
        c.disconnectPlayer(playerOrder.get(1));
        //curplayer is the third
        assertEquals(playerOrder.get(2), c.getGameModel().getCurrPlayer().getNickname());
        assertThrows(StopGameException.class, ()->{
            c.disconnectPlayer(playerOrder.get(2));
        });
    }

    @Test
    void chooseStarterCardSide() throws InvalidArgumentException, CannotJoinGameException, InvalidPlayingException {
        //choose starter card before new game
        assertThrows(InvalidPlayingException.class, ()->{
            c.chooseStarterCardSide("Giuseppe", 3);
        });

        _simulateNewGame();

        assertThrows( InvalidArgumentException.class, ()->{
            c.chooseStarterCardSide("Giuseppe", 3);
        });
        c.chooseStarterCardSide("Giuseppe", PlayableCard.BACK);
        assertNull(c.getGameModel().getPlayers().get(0).getSecretObjective());
        c.chooseStarterCardSide("Pippo", PlayableCard.BACK);
        c.chooseStarterCardSide("Pietro", PlayableCard.BACK);
        assertNotNull(c.getGameModel().getCommonObjectives());
        assertNotNull(c.getGameModel().getPlayers().get(0).getSecretObjective());
        assertNotNull(c.getGameModel().getPlayers().get(1).getSecretObjective());
        assertNotNull(c.getGameModel().getPlayers().get(2).getSecretObjective());
    }

    //da finire
    @Test
    void chooseObjective() throws InvalidArgumentException, CannotJoinGameException, InvalidPlayingException {
        _simulateNewGame();
        //choose objective before starter card
        assertThrows(InvalidPlayingException.class, ()->{
            c.chooseObjective("Giuseppe", 1);
        });

        _simulateStarterCards();
        //wrong nickname
        assertThrows(InvalidArgumentException.class, ()->{
            c.chooseObjective("Pino", 0);
        });
        //wrong index
        assertThrows(InvalidArgumentException.class, ()->{
            c.chooseObjective("Giuseppe", 4);
        });

        ObjectiveCard  card = c.getGameModel().getPlayers().get(0).getSecretObjective()[1];
        c.chooseObjective("Giuseppe", 1);
        assertEquals(card,c.getGameModel().getPlayers().get(0).getSecretObjective()[0] );

        c.chooseObjective("Giuseppe", 0);
        c.chooseObjective("Pietro", 1);
    }

    @Test
    void playCard() {
        
    }

    @Test
    void pickCard() {
    }

    @Test
    void testPickCard() {
    }
}