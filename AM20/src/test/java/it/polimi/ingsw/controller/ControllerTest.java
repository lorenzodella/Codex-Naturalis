package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller c;

    @BeforeEach
    void setUp() {
        c = new Controller();
    }

    @Test
    void newGame() throws InvalidArgumentException {
        assertThrows(InvalidArgumentException.class, ()->{
            c.newGame("Pippo", 1);
        });

        assertThrows(InvalidArgumentException.class, ()->{
            c.newGame("Pippo", 5);
        });

        c.newGame("Pippo", 3);
        assertEquals(3 ,c.getNumPlayers() );
        assertEquals("Pippo", c.getPlayers().get(0));

    }

    @Test
    void joinGame() throws CannotJoinGameException, InvalidArgumentException {

        //non è ancora stato iniziato iun game
        assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });

        c.newGame("Giuseppe", 3);
        //provo a collegarmi con lo stesso nome già usato
        assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });
        c.joinGame("Pippo");
        c.joinGame("Pietro");
        //porvo a collegarmi con una partita già conclusa
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
    void chooseStarterCardSide() throws InvalidArgumentException, CannotJoinGameException {

        c.newGame("Giuseppe", 3);
        c.joinGame("Pippo");
        c.joinGame("Pietro");

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
    void chooseObjective() throws InvalidArgumentException, CannotJoinGameException {
        c.newGame("Giuseppe", 3);
        c.joinGame("Pippo");
        c.joinGame("Pietro");
        assertThrows(InvalidArgumentException.class, ()->{
            c.chooseObjective("Giuseppe", -1);
        });

        assertThrows(InvalidArgumentException.class, ()->{
            c.chooseObjective("Giuseppe", 4);
        });

        c.chooseStarterCardSide("Giuseppe", PlayableCard.BACK);
        c.chooseStarterCardSide("Pippo", PlayableCard.BACK);
        c.chooseStarterCardSide("Pietro", PlayableCard.BACK);

        ObjectiveCard  card = c.getGameModel().getPlayers().get(0).getSecretObjective()[1];
        c.chooseObjective("Giuseppe", 1);
        assertEquals(card,c.getGameModel().getPlayers().get(0).getSecretObjective()[0] );
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