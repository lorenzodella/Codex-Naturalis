package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp() {
        game = new Game(Arrays.asList(
                new Player("p1"),
                new Player("p2"),
                new Player("p3"),
                new Player("p4")
        ));
        game.initDecks();
        game.giveStarterCards();
        game.giveInitialCards();
        game.initObjectiveCards();
        game.chooseFirstPlayer();
    }

    @Test
    void initTest(){
        List<Player> playersList = game.getPlayers();
        for(Player p : playersList){
            //no null card
            assertNotNull(p.getStarterCard());
            //correct number of cards
            assertEquals(3, p.getCards().stream().filter(Objects::nonNull).distinct().count());
            //two objectives per player
            assertEquals(2, Arrays.stream(p.getSecretObjective()).filter(Objects::nonNull).distinct().count());
        }
        //no duplicate card
        assertEquals(3L*playersList.size(), playersList.stream().flatMap(p -> p.getCards().stream()).distinct().count());
        assertEquals(playersList.size(), playersList.stream().map(Player::getStarterCard).distinct().count());
        //two common objectives
        assertEquals(2, Arrays.stream(game.getCommonObjectives()).filter(Objects::nonNull).distinct().count());
        //40-2-4 gold
        assertEquals(34, game.getGoldCardDeck().getCards().size());
        //40-2-8 res
        assertEquals(30, game.getResourceCardDeck().getCards().size());
    }

    @Test
    void chooseStarterCardSide() throws InvalidArgumentException {
        assertThrows(InvalidArgumentException.class, ()->game.chooseStarterCardSide(PlayableCard.BACK, "ppp"));

        game.chooseStarterCardSide(PlayableCard.BACK, "p2");
        StarterCard s = game.getPlayers().stream()
                .filter(p -> p.getNickname().equals("p2"))
                .findFirst().get()
                .getStarterCard();
        assertEquals(PlayableCard.BACK, s.getSide());
        assertEquals(0, s.getOrder());
    }

    @Test
    void chooseObjective() throws InvalidArgumentException {
        assertThrows(InvalidArgumentException.class, ()->game.chooseObjective(1, "ppp"));
        assertThrows(InvalidArgumentException.class, ()->game.chooseObjective(4, "p2"));

        Player p2 = game.getPlayers().stream()
                .filter(p -> p.getNickname().equals("p2"))
                .findFirst().get();
        ObjectiveCard[] old = p2.getSecretObjective().clone();
        game.chooseObjective(1, "p2");
        assertEquals(old[1], p2.getSecretObjective()[0]);
        assertNull(p2.getSecretObjective()[1]);
    }

    @Test
    void invalidPlaying() throws InvalidArgumentException {
        game.chooseStarterCardSide(PlayableCard.FRONT, game.getCurrPlayer().getNickname());

        StarterCard s_ok = game.getCurrPlayer().getStarterCard();
        StarterCard s_notok = game.getPlayers().stream()
                .filter(p -> !p.getNickname().equals(game.getCurrPlayer().getNickname()))
                .findFirst().get().getStarterCard();
        assertThrows(InvalidArgumentException.class, ()->game.playCard(10, Corner.UL, s_ok.getID(), PlayableCard.FRONT));
        assertThrows(InvalidArgumentException.class, ()->game.playCard(1, Corner.UL, s_ok.getID(), 3));
        assertThrows(TargetNotPresentException.class, ()->game.playCard(1, Corner.UL, s_notok.getID(), PlayableCard.FRONT));
        assertThrows(InvalidPositionException.class, ()->game.playCard(1, 4, s_ok.getID(), PlayableCard.FRONT));

        assertThrows(InvalidArgumentException.class, ()->game.pickCard(5));
        assertThrows(InvalidArgumentException.class, ()->game.pickCard(Deck.RESOURCE_CARDS, 4));
    }

    @Test
    void nextTurn(){
        List<Player> p = game.getPlayers();
        for (int i = 0; i < game.getPlayers().size()-1; i++) {
            game.nextTurn();
            assertEquals(p.get(i+1), game.getCurrPlayer());
        }
        assertTrue(game.nextTurn());
        assertEquals(p.get(0), game.getCurrPlayer());
    }

    @Test
    void endDecks() throws InvalidArgumentException, FinishedCardStackException {
        for (int i = 0; i < 34; i++) {
            game.pickCard(Deck.GOLD_CARDS);
            assertFalse(game.checkTheEnd());
        }
        for (int i = 0; i < 30; i++) {
            game.pickCard(Deck.RESOURCE_CARDS);
            assertFalse(game.checkTheEnd());
        }
        assertThrows(FinishedCardStackException.class, ()->game.pickCard(Deck.GOLD_CARDS));
        assertThrows(FinishedCardStackException.class, ()->game.pickCard(Deck.RESOURCE_CARDS));

        game.pickCard(Deck.GOLD_CARDS, 0);
        game.pickCard(Deck.GOLD_CARDS, 1);
        game.pickCard(Deck.RESOURCE_CARDS, 0);
        game.pickCard(Deck.RESOURCE_CARDS, 1);

        assertThrows(FinishedCardStackException.class, ()->game.pickCard(Deck.GOLD_CARDS, 0));
        assertThrows(FinishedCardStackException.class, ()->game.pickCard(Deck.RESOURCE_CARDS, 0));

        assertTrue(game.checkTheEnd());
    }

    @Test
    void endWinner() throws InvalidArgumentException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, FinishedCardStackException, RequirementsNotRespectedException {
        Player p = game.getCurrPlayer();
        game.chooseStarterCardSide(PlayableCard.FRONT, p.getNickname());
        PlayableCard oldc = p.getStarterCard();
        PlayableCard newc = p.getCards().get(2);
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) newc);
        game.playCard(2, Corner.UR, oldc.getID(), PlayableCard.FRONT);
        while(!game.checkTheEnd()) {
            assertFalse(p.getScore()>=20);
            game.pickCard(Deck.GOLD_CARDS);
            oldc = newc;
            newc = p.getCards().get(2);
            addRequirementsOfGoldCard(p.getTable(), (GoldCard) newc);
            try {
                game.playCard(2, Corner.UR, oldc.getID(), PlayableCard.FRONT);
            } catch (InvalidAngleCoveredException e) {
                game.playCard(2, Corner.UL, oldc.getID(), PlayableCard.FRONT);
            }
        }
        assertTrue(p.getScore()>=20);
        assertEquals(p, game.checkWinner());
    }

    void addRequirementsOfGoldCard(PlayerTable playerTable, GoldCard gc){
        for(Map.Entry<Kingdom, Integer> e : gc.getRequirements().entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                playerTable.getStats().addKingdom(e.getKey());
            }
        }
    }
}