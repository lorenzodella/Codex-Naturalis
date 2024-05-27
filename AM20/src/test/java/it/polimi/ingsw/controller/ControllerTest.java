package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller c;
    private HashMap<String, ConnectionAckMessage> connectionAckMessages;
    private HashMap<String, StarterCardAckMessage> starterCardAckMessages;
    private HashMap<String, ObjectiveAckMessage> objectiveAckMessages;
    private HashMap<String, AcknowledgeMessage> acknowledgeMessages;
    private List<String> playerOrder;

    void printMessages(HashMap<String, ? extends Message> msg){
        System.out.println(msg.toString().replace("},", "}\n"));
    }

    @BeforeEach
    void setUp() {
        c = new Controller();
    }

    public static Stream<Arguments> whoIsWinner() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(2));
    }

    void _simulateNewGame() throws InvalidArgumentException, CannotJoinGameException, InvalidPlayingException {
        c.newGame("Giuseppe", 3);
        c.joinGame("Pippo");
        c.joinGame("Pietro");
    }

    void _simulateStarterCards() throws InvalidArgumentException, InvalidPlayingException {
        c.chooseStarterCardSide("Giuseppe", PlayableCard.FRONT);
        c.chooseStarterCardSide("Pippo", PlayableCard.FRONT);
        c.chooseStarterCardSide("Pietro", PlayableCard.FRONT);
    }

    void _simulateObjectives() throws InvalidArgumentException, InvalidPlayingException {
        c.chooseObjective("Giuseppe", 1);
        c.chooseObjective("Pippo", 1);
        c.chooseObjective("Pietro", 1);

        playerOrder = c.getGameModel().getPlayers().stream().map(Player::getNickname).collect(Collectors.toList());
        System.out.println("order: "+playerOrder);
    }

    void _simulatePlayCard(int pl, List<PlayableCard> oldc, List<PlayableCard> newc, int cardToPlay, Player cur) throws InvalidArgumentException, NoOneIsConnectedException, RequirementsNotRespectedException, InvalidPlayingException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        newc.set(pl, cur.getCards().get(cardToPlay));
        addRequirementsOfGoldCard(cur.getTable(), newc.get(pl));
        if(newc.get(pl).getID().equals("G79")){ //this card can't be covered UR and UL
            acknowledgeMessages = c.playCard(playerOrder.get(pl), cardToPlay, Corner.DR, cur.getStarterCard().getID(), PlayableCard.FRONT);
        }
        else{
            try {
                acknowledgeMessages = c.playCard(playerOrder.get(pl), cardToPlay, Corner.UR, oldc.get(pl).getID(), PlayableCard.FRONT);
            } catch (InvalidAngleCoveredException e) {
                acknowledgeMessages = c.playCard(playerOrder.get(pl), cardToPlay, Corner.UL, oldc.get(pl).getID(), PlayableCard.FRONT);
            }
            oldc.set(pl, newc.get(pl));
        }
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

        Message m = c.newGame("Giuseppe", 3);
        System.out.println(m);
        //provo a collegarmi con lo stesso nome già usato
        assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });

        connectionAckMessages = c.joinGame("Pippo");
        assertEquals(2, connectionAckMessages.size());
        assertFalse(connectionAckMessages.get("Pippo").doesGameStarts());
        printMessages(connectionAckMessages);

        connectionAckMessages = c.joinGame("Pietro");
        assertEquals(3, connectionAckMessages.size());
        printMessages(connectionAckMessages);

        //everything is set
        assert connectionAckMessages.values().stream().allMatch(x->3==x.getNumOfConnectedPlayers());
        assert connectionAckMessages.values().stream().allMatch(ConnectionAckMessage::doesGameStarts);
        assert connectionAckMessages.values().stream().map(ConnectionAckMessage::getGoldTop).allMatch(Objects::nonNull);
        assert connectionAckMessages.values().stream().map(ConnectionAckMessage::getResourceTop).allMatch(Objects::nonNull);
        assert connectionAckMessages.values().stream().map(ConnectionAckMessage::getGoldVisible).allMatch(Objects::nonNull);
        assert connectionAckMessages.values().stream().map(ConnectionAckMessage::getResourceVisible).allMatch(Objects::nonNull);
        assert connectionAckMessages.values().stream().map(ConnectionAckMessage::getInitialCards).allMatch(Objects::nonNull);
        assert connectionAckMessages.values().stream().map(ConnectionAckMessage::getStarterCard).allMatch(Objects::nonNull);
        assert Stream.of(
                Stream.of(connectionAckMessages.values().stream().findFirst().map(ConnectionAckMessage::getGoldTop).get()),
                Stream.of(connectionAckMessages.values().stream().findFirst().map(ConnectionAckMessage::getResourceTop).get()),
                Stream.of(connectionAckMessages.values().stream().findFirst().map(ConnectionAckMessage::getGoldVisible).get()),
                Stream.of(connectionAckMessages.values().stream().findFirst().map(ConnectionAckMessage::getResourceVisible).get()),
                connectionAckMessages.values().stream().map(ConnectionAckMessage::getInitialCards)
        ).flatMap(Function.identity()).allMatch(new HashSet<>()::add);

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
    }

    @Test
    void reconnectPlayer() throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException {
        _simulateNewGame();

        //someone tries to connect with a nickname of a player already online
        CannotJoinGameException e = assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });
        assert e.toString().contains("already playing");

        _simulateStarterCards();

        //someone tries to connect with a nickname of a player already online
        CannotJoinGameException e1 = assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });
        assert e1.toString().contains("already playing");

        _simulateObjectives();

        //someone tries to connect with a nickname of a player already online
        CannotJoinGameException e2 = assertThrows(CannotJoinGameException.class, ()->{
            c.joinGame("Giuseppe");
        });
        assert e2.toString().contains("already playing");
    }

    @Test
    void disconnectPlayer()
            throws InvalidArgumentException, CannotJoinGameException, NoOneIsConnectedException, InvalidConnectionStateException, InvalidPlayingException, InvalidDisconnectionException {
        _simulateNewGame();

        InvalidArgumentException e = assertThrows(InvalidArgumentException.class, ()->{
            c.disconnectPlayer("Gianni");
        });
        assert e.toString().contains("nickname");

        _simulateStarterCards();
        
        //a player disconnects during preliminary phase
        InvalidDisconnectionException e1 = assertThrows(InvalidDisconnectionException.class, ()->{
            c.disconnectPlayer("Giuseppe");
        });
        assert e1.toString().contains("preliminary");

        _simulateNewGame();
        _simulateStarterCards();
        _simulateObjectives();

        //one player disconnects
        acknowledgeMessages = c.disconnectPlayer(playerOrder.get(1));
        assertEquals(2, c.getGameModel().getConnectedPlayers().size());
        assertEquals(2, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
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
        //everything is set
        assertEquals(3, c.getGameModel().getConnectedPlayers().size());
        assertEquals(3, connectionAckMessages.values().iterator().next().getNumOfConnectedPlayers());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getGoldTop());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getResourceTop());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getGoldVisible());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getResourceVisible());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getInitialCards());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getStarterCard());
        assertNotNull(connectionAckMessages.get(playerOrder.get(1)).getPlayerInfo());
        assertEquals(2, connectionAckMessages.get(playerOrder.get(1)).getOthersPlayerInfo().size());
        assert connectionAckMessages.get(playerOrder.get(1)).getOthersPlayerInfo().values().stream().allMatch(Objects::nonNull);
        assert !connectionAckMessages.get(playerOrder.get(1)).getOthersPlayerInfo().containsKey(playerOrder.get(1));
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
        acknowledgeMessages = c.disconnectPlayer(playerOrder.get(0));
        //curplayer disconnects -> pass turn
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(1)));
        c.disconnectPlayer(playerOrder.get(1));
        //curplayer is the third
        assertEquals(playerOrder.get(2), c.getGameModel().getCurrPlayer().getNickname());
        assertThrows(NoOneIsConnectedException.class, ()->{
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

        //first choose
        starterCardAckMessages = c.chooseStarterCardSide("Giuseppe", PlayableCard.BACK);
        assertNull(c.getGameModel().getPlayers().get(0).getSecretObjective());
        assertEquals(3, starterCardAckMessages.size());
        assertFalse(starterCardAckMessages.get("Giuseppe").shouldChooseObjective());
        //check info of startercard
        assert starterCardAckMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals("Giuseppe") ?
                        e.getValue().getPlayerInfo()!=null :
                        e.getValue().getOthersPlayerInfo().get("Giuseppe")!=null);
        printMessages(starterCardAckMessages);

        //second choose
        starterCardAckMessages = c.chooseStarterCardSide("Pippo", PlayableCard.BACK);
        assertEquals(3, starterCardAckMessages.size());
        //check info of startercard
        assert starterCardAckMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals("Pippo") ?
                        e.getValue().getPlayerInfo()!=null :
                        e.getValue().getOthersPlayerInfo().get("Pippo")!=null);
        printMessages(starterCardAckMessages);

        //last choose
        starterCardAckMessages = c.chooseStarterCardSide("Pietro", PlayableCard.BACK);
        assertEquals(starterCardAckMessages.size(), starterCardAckMessages.values().iterator().next().getNumOfConnectedPlayers());
        //check objectives
        assert starterCardAckMessages.values().stream().allMatch(StarterCardAckMessage::shouldChooseObjective);
        assert starterCardAckMessages.values().stream().map(StarterCardAckMessage::getCommonObjectives).allMatch(Objects::nonNull);
        assert starterCardAckMessages.values().stream().map(StarterCardAckMessage::getSecretObjectives).allMatch(Objects::nonNull);
        //check info of startercard
        assert starterCardAckMessages.entrySet().stream().allMatch(e ->
                        e.getKey().equals("Pietro") ?
                        e.getValue().getPlayerInfo()!=null :
                        e.getValue().getOthersPlayerInfo().get("Pietro")!=null);
        printMessages(starterCardAckMessages);

        assertNotNull(c.getGameModel().getCommonObjectives());
        assertNotNull(c.getGameModel().getPlayers().get(0).getSecretObjective());
        assertNotNull(c.getGameModel().getPlayers().get(1).getSecretObjective());
        assertNotNull(c.getGameModel().getPlayers().get(2).getSecretObjective());
    }

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

        //first choose
        ObjectiveCard  card = c.getGameModel().getPlayers().get(1).getSecretObjective().get(1);
        objectiveAckMessages = c.chooseObjective("Pippo", 1);
        assertEquals(card, c.getGameModel().getPlayers().get(1).getSecretObjective().get(0));
        assertEquals(1, objectiveAckMessages.size());
        assertFalse(objectiveAckMessages.get("Pippo").shouldStartPlaying());
        //check info of objectives
        assert objectiveAckMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals("Pippo") == (e.getValue().getSecretObjectives() != null));
        printMessages(objectiveAckMessages);

        //second choose
        objectiveAckMessages = c.chooseObjective("Giuseppe", 0);
        assertEquals(1, objectiveAckMessages.size());
        assertEquals(3, objectiveAckMessages.values().iterator().next().getNumOfConnectedPlayers());
        //check info of objectives
        assert objectiveAckMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals("Giuseppe") == (e.getValue().getSecretObjectives() != null));
        printMessages(objectiveAckMessages);

        //last choose
        objectiveAckMessages = c.chooseObjective("Pietro", 1);
        assertEquals(3, objectiveAckMessages.size());
        //check startgame
        assert objectiveAckMessages.values().stream().allMatch(ObjectiveAckMessage::shouldStartPlaying);
        assert objectiveAckMessages.values().stream().map(ObjectiveAckMessage::getFirstPlayer).allMatch(Objects::nonNull);
        //check info of objectives
        assert objectiveAckMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals("Pietro") == (e.getValue().getSecretObjectives() != null));
        printMessages(objectiveAckMessages);
    }

    @Test
    void playCard() throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException, RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, NoOneIsConnectedException {
        _simulateNewGame();

        //play during preliminary phase
        assertThrows(InvalidPlayingException.class, ()->{
            c.playCard("Giuseppe", 1, Corner.UR, "any", PlayableCard.FRONT);
        });

        _simulateStarterCards();
        _simulateObjectives();

        //play during someone else's turn
        assertThrows(InvalidPlayingException.class, ()->{
            c.playCard(playerOrder.get(1), 1, Corner.UR, "any", PlayableCard.FRONT);
        });

        //normal playing
        StarterCard sc = c.getGameModel().getCurrPlayer().getStarterCard();
        acknowledgeMessages = c.playCard(playerOrder.get(0), 0, Corner.UR, sc.getID(), PlayableCard.FRONT);
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(0)));
        assert acknowledgeMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals(playerOrder.get(0)) ?
                        //player who played
                        e.getValue().getCards().size()==2
                                && e.getValue().getYourPlayerInfo()!=null
                                && e.getValue().mustPick() :
                        //others
                        e.getValue().getCards()==null
                                && e.getValue().getOthersPlayerInfo().get(playerOrder.get(0))!=null
                                && !e.getValue().mustPick());
        printMessages(acknowledgeMessages);

        //play two times
        assertThrows(InvalidPlayingException.class, ()->{
            StarterCard s = c.getGameModel().getCurrPlayer().getStarterCard();
            c.playCard(playerOrder.get(0), 0, Corner.UR, s.getID(), PlayableCard.FRONT);
        });

        //empty decks
        try {
            c.pickCard(playerOrder.get(0), Deck.RESOURCE_CARDS);
            while(true) {
                c.getGameModel().getGoldCardDeck().draw();
            }
        } catch (FinishedCardStackException ignored) {}
        try {
            while(true) {
                c.getGameModel().getResourceCardDeck().draw();
            }
        } catch (FinishedCardStackException ignored) {}
        try {
            c.getGameModel().getGoldCardDeck().drawVisibleCard(0);
            c.getGameModel().getGoldCardDeck().drawVisibleCard(1);
            c.getGameModel().getResourceCardDeck().drawVisibleCard(0);
            c.getGameModel().getResourceCardDeck().drawVisibleCard(1);
        } catch (FinishedCardStackException ignored1) {}

        sc = c.getGameModel().getCurrPlayer().getStarterCard();
        acknowledgeMessages = c.playCard(playerOrder.get(1), 0, Corner.UL, sc.getID(), PlayableCard.FRONT);
        //next turn
        assertEquals(3, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(2)));
        assert acknowledgeMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals(playerOrder.get(1)) ?
                        //player who played
                        e.getValue().getCards().size()==2
                                && e.getValue().getYourPlayerInfo()!=null
                                && !e.getValue().mustPick() :
                        //others
                        e.getValue().getCards()==null
                                && e.getValue().getOthersPlayerInfo().get(playerOrder.get(1))!=null
                                && !e.getValue().mustPick());
        printMessages(acknowledgeMessages);

        //pick card after playing but decks are empty
        assertThrows(InvalidPlayingException.class, ()->{
            c.pickCard(playerOrder.get(1), Deck.RESOURCE_CARDS, 1);
        });
    }

    @Test
    void pickCard() throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException, NoOneIsConnectedException, RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, FinishedCardStackException {
        _simulateNewGame();

        //pick during preliminary phase
        assertThrows(InvalidPlayingException.class, ()->{
            c.pickCard("Giuseppe", Deck.RESOURCE_CARDS);
        });

        _simulateStarterCards();
        _simulateObjectives();

        //pick during someone else's turn
        assertThrows(InvalidPlayingException.class, ()->{
            c.pickCard(playerOrder.get(1), Deck.RESOURCE_CARDS);
        });

        //pick before play
        assertThrows(InvalidPlayingException.class, ()->{
            c.pickCard(playerOrder.get(0), Deck.RESOURCE_CARDS);
        });

        //normal playing
        StarterCard sc = c.getGameModel().getCurrPlayer().getStarterCard();
        c.playCard(playerOrder.get(0), 0, Corner.UR, sc.getID(), PlayableCard.FRONT);
        acknowledgeMessages = c.pickCard(playerOrder.get(0), Deck.RESOURCE_CARDS);
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(1)));
        assert acknowledgeMessages.values().stream().map(AcknowledgeMessage::getGoldTop).allMatch(Objects::nonNull);
        assert acknowledgeMessages.values().stream().map(AcknowledgeMessage::getResourceTop).allMatch(Objects::nonNull);
        assert acknowledgeMessages.values().stream().flatMap(m -> Arrays.stream(m.getGoldVisible())).allMatch(Objects::nonNull);
        assert acknowledgeMessages.values().stream().flatMap(m -> Arrays.stream(m.getResourceVisible())).allMatch(Objects::nonNull);
        assert acknowledgeMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals(playerOrder.get(0)) ?
                        //player who played
                        e.getValue().getCards().size()==3 :
                        //others
                        e.getValue().getCards()==null);
        printMessages(acknowledgeMessages);

        //empty decks
        try {
            while(true) {
                c.getGameModel().getGoldCardDeck().draw();
            }
        } catch (FinishedCardStackException ignored) {}
        try {
            while(true) {
                c.getGameModel().getResourceCardDeck().draw();
            }
        } catch (FinishedCardStackException ignored) {}
        try {
            c.getGameModel().getGoldCardDeck().drawVisibleCard(0);
            //last card to pick is getGoldCardDeck().getVisibleCard(1);
            c.getGameModel().getResourceCardDeck().drawVisibleCard(0);
            c.getGameModel().getResourceCardDeck().drawVisibleCard(1);
        } catch (FinishedCardStackException ignored1) {}

        sc = c.getGameModel().getCurrPlayer().getStarterCard();
        c.playCard(playerOrder.get(1), 0, Corner.UL, sc.getID(), PlayableCard.FRONT);
        acknowledgeMessages = c.pickCard(playerOrder.get(1), Deck.GOLD_CARDS, 1); //last card
        assertEquals(3, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(2)));
        assert acknowledgeMessages.values().stream().map(AcknowledgeMessage::getGoldTop).allMatch(Objects::isNull);
        assert acknowledgeMessages.values().stream().map(AcknowledgeMessage::getResourceTop).allMatch(Objects::isNull);
        assert acknowledgeMessages.values().stream().flatMap(m -> Arrays.stream(m.getGoldVisible())).allMatch(Objects::isNull);
        assert acknowledgeMessages.values().stream().flatMap(m -> Arrays.stream(m.getResourceVisible())).allMatch(Objects::isNull);
        assert acknowledgeMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals(playerOrder.get(1)) ?
                        //player who played
                        e.getValue().getCards().size()==3 :
                        //others
                        e.getValue().getCards()==null);
        printMessages(acknowledgeMessages);
    }

    @Test
    void disconnectPlayerDuringPlaying() throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException, NoOneIsConnectedException, InvalidConnectionStateException, RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, FinishedCardStackException, InvalidDisconnectionException {
        _simulateNewGame();
        _simulateStarterCards();
        _simulateObjectives();

        //play card and disconnect
        StarterCard sc = c.getGameModel().getCurrPlayer().getStarterCard();
        c.playCard(playerOrder.get(0), 0, Corner.UR, sc.getID(), PlayableCard.FRONT);
        acknowledgeMessages = c.disconnectPlayer(playerOrder.get(0));
        assertEquals(2, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
        assertNotNull(acknowledgeMessages.get(playerOrder.get(2)).getNextPlayer());
        printMessages(acknowledgeMessages);

        //now it's turn of next player
        assertEquals(playerOrder.get(1), c.getGameModel().getCurrPlayer().getNickname());
        //play card
        sc = c.getGameModel().getCurrPlayer().getStarterCard();
        acknowledgeMessages = c.playCard(playerOrder.get(1), 0, Corner.UR, sc.getID(), PlayableCard.FRONT);
        assertEquals(2, acknowledgeMessages.size());
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(1)));
        assert acknowledgeMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals(playerOrder.get(1)) ?
                        //player who played
                        e.getValue().getCards().size()==2
                                && e.getValue().getYourPlayerInfo()!=null
                                && e.getValue().mustPick() :
                        //others
                        e.getValue().getCards()==null
                                && e.getValue().getOthersPlayerInfo().get(playerOrder.get(1))!=null
                                && !e.getValue().mustPick());
        printMessages(acknowledgeMessages);
        //pick card
        acknowledgeMessages = c.pickCard(playerOrder.get(1), Deck.RESOURCE_CARDS);
        assertEquals(2, acknowledgeMessages.size());
        assertEquals(2, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
        assert acknowledgeMessages.values().stream().allMatch(m -> m.getNextPlayer().equals(playerOrder.get(2)));
        assert acknowledgeMessages.entrySet().stream().allMatch(e ->
                e.getKey().equals(playerOrder.get(1)) ?
                        //player who played
                        e.getValue().getCards().size()==3 :
                        //others
                        e.getValue().getCards()==null);
        printMessages(acknowledgeMessages);
        //disconnect
        acknowledgeMessages = c.disconnectPlayer(playerOrder.get(1));
        assertNull(acknowledgeMessages.get(playerOrder.get(2)).getNextPlayer());
        printMessages(acknowledgeMessages);

        //now it's turn of next player
        assertEquals(playerOrder.get(2), c.getGameModel().getCurrPlayer().getNickname());
        //you must wait other because you are the only one connected
        InvalidPlayingException e = assertThrows(InvalidPlayingException.class, ()->{
            StarterCard s = c.getGameModel().getCurrPlayer().getStarterCard();
            c.playCard(playerOrder.get(2), 0, Corner.UR, s.getID(), PlayableCard.FRONT);
        });
        assert e.toString().contains("wait");

        //player reconnect
        c.joinGame(playerOrder.get(0));
        //now you can play
        StarterCard s = c.getGameModel().getCurrPlayer().getStarterCard();
        c.playCard(playerOrder.get(2), 0, Corner.UR, s.getID(), PlayableCard.FRONT);
        //player re-disconnect
        c.disconnectPlayer(playerOrder.get(0));
        InvalidPlayingException e1 = assertThrows(InvalidPlayingException.class, ()->{
            c.pickCard(playerOrder.get(2), Deck.RESOURCE_CARDS);
        });
        assert e1.toString().contains("wait");
        //player reconnect
        c.joinGame(playerOrder.get(0));
        //now you can pick
        c.pickCard(playerOrder.get(2), Deck.RESOURCE_CARDS);
        assertEquals(playerOrder.get(0), c.getGameModel().getCurrPlayer().getNickname());
    }

    @ParameterizedTest
    @MethodSource("whoIsWinner")
    void endGameDecks(int winner) throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException, NoOneIsConnectedException, RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, FinishedCardStackException {
        _simulateNewGame();
        _simulateStarterCards();
        _simulateObjectives();

        List<PlayableCard> sc = c.getGameModel().getPlayers().stream().map(Player::getStarterCard).collect(Collectors.toList());
        List<PlayableCard> oldc = new ArrayList<>(sc);
        List<PlayableCard> newc = new ArrayList<>(sc);
        int pl=0;

        //player before winner play
        for(int i=0; i<winner; i++){
            _simulatePlayCard(pl, oldc, newc, 0, c.getGameModel().getCurrPlayer());
            c.pickCard(playerOrder.get(pl), Deck.RESOURCE_CARDS);
            pl = (pl+1)%playerOrder.size();
        }
        //empty decks
        try {
            while(true) {
                c.getGameModel().getGoldCardDeck().draw();
            }
        } catch (FinishedCardStackException ignored) {}
        try {
            while(true) {
                c.getGameModel().getResourceCardDeck().draw();
            }
        } catch (FinishedCardStackException ignored) {}
        try {
            c.getGameModel().getGoldCardDeck().drawVisibleCard(0);
            //last card to pick is getGoldCardDeck().getVisibleCard(1);
            c.getGameModel().getResourceCardDeck().drawVisibleCard(0);
            c.getGameModel().getResourceCardDeck().drawVisibleCard(1);
        } catch (FinishedCardStackException ignored1) {}
        //winner finishes cards
        assertEquals(winner, pl); //curplayer is the winner
        _simulatePlayCard(pl, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        acknowledgeMessages = c.pickCard(playerOrder.get(pl), Deck.GOLD_CARDS, 1); // last card
        assertEquals(3, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
        printMessages(acknowledgeMessages);
        //player after winner play
        pl = (pl+1)%playerOrder.size();
        for(int i=0; i<playerOrder.size()-1-winner; i++){
            _simulatePlayCard(pl, oldc, newc, 0, c.getGameModel().getCurrPlayer());
            pl = (pl+1)%playerOrder.size();
        }

        //another round
        //player0
        _simulatePlayCard(0, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        //player1
        _simulatePlayCard(1, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        //player2 (last)
        _simulatePlayCard(2, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        printMessages(acknowledgeMessages);
        //final score
        c.getGameModel().getPlayers().forEach(p -> System.out.println(p.getNickname() + p.getScore()));
    }

    @ParameterizedTest
    @MethodSource("whoIsWinner")
    void endGameWinner(int winner) throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException, NoOneIsConnectedException, RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, FinishedCardStackException {
        _simulateNewGame();
        _simulateStarterCards();
        _simulateObjectives();

        List<PlayableCard> sc = c.getGameModel().getPlayers().stream().map(Player::getStarterCard).collect(Collectors.toList());
        int pl = 0, cardToPlay;

        List<PlayableCard> oldc = new ArrayList<>(sc);
        List<PlayableCard> newc = new ArrayList<>(sc);
        //play normally while someone doesn't reach 20 points
        do{
            //I want /winner/ to win (he will play only goldcards)
            cardToPlay = pl==winner ? 2 : 0;
            _simulatePlayCard(pl, oldc, newc, cardToPlay, c.getGameModel().getCurrPlayer());
            acknowledgeMessages = c.pickCard(playerOrder.get(pl), pl == winner ? Deck.GOLD_CARDS : Deck.RESOURCE_CARDS);
            assertEquals(3, acknowledgeMessages.values().iterator().next().getNumOfConnectedPlayers());
            pl = (pl+1)%playerOrder.size();
        }while(!acknowledgeMessages.get(playerOrder.get(0)).getResult().contains("done..."));

        //winner reached 20 points, finish round
        assertEquals((winner+1)%playerOrder.size(), pl); //curplayer is the next of the winner
        for(int i=0; i<playerOrder.size()-1-winner; i++){
            _simulatePlayCard(pl, oldc, newc, 0, c.getGameModel().getCurrPlayer());
            c.pickCard(playerOrder.get(pl), Deck.RESOURCE_CARDS);
            pl = (pl+1)%playerOrder.size();
        }

        //last round
        assertEquals(0, pl);
        lastRound(oldc, newc, winner);
    }

    void lastRound(List<PlayableCard> oldc, List<PlayableCard> newc, int winner) throws InvalidArgumentException, NoOneIsConnectedException, RequirementsNotRespectedException, InvalidPlayingException, TargetNotPresentException, InvalidPositionException, InvalidAngleCoveredException, FinishedCardStackException {
        //player0
        _simulatePlayCard(0, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        c.pickCard(playerOrder.get(0), Deck.RESOURCE_CARDS);
        //player1
        _simulatePlayCard(1, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        c.pickCard(playerOrder.get(1), Deck.RESOURCE_CARDS);
        //player2
        _simulatePlayCard(2, oldc, newc, 0, c.getGameModel().getCurrPlayer());
        acknowledgeMessages = c.pickCard(playerOrder.get(2), Deck.RESOURCE_CARDS);
        //result message should contain nickname of the winner
        assert acknowledgeMessages.get(playerOrder.get((winner+1)%playerOrder.size()))
                .getResult().contains(playerOrder.get(winner));
        printMessages(acknowledgeMessages);
    }

    void addRequirementsOfGoldCard(PlayerTable playerTable, PlayableCard gc){
        for(Map.Entry<Kingdom, Integer> e : gc.getRequirements().entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                playerTable.getStats().addKingdom(e.getKey());
            }
        }
    }

    @Test
    void afterEndGame() throws InvalidArgumentException, RequirementsNotRespectedException, InvalidPlayingException, NoOneIsConnectedException, FinishedCardStackException, TargetNotPresentException, CannotJoinGameException, InvalidAngleCoveredException, InvalidPositionException {
        endGameWinner(1);

        assertThrows(CannotJoinGameException.class, ()->c.joinGame("Ugo"));
        //i must wait that all players disconnected
        assertThrows(InvalidPlayingException.class, ()->c.newGame("Ugo", 2));
    }

    @Test
    void afterStopGame() throws InvalidArgumentException, InvalidPlayingException, CannotJoinGameException, InvalidConnectionStateException, NoOneIsConnectedException, InvalidDisconnectionException {
        _simulateNewGame();

        //if some disconnects during preliminary phase
        assertThrows(InvalidDisconnectionException.class, ()->c.disconnectPlayer("Giuseppe"));

        assertThrows(CannotJoinGameException.class, ()->c.joinGame("Ugo"));
        c.newGame("Ugo", 2);
        c.joinGame("Uga");
        c.chooseStarterCardSide("Ugo", PlayableCard.FRONT);
        c.chooseStarterCardSide("Uga", PlayableCard.FRONT);
        c.chooseObjective("Ugo", 1);
        c.chooseObjective("Uga", 1);
        c.disconnectPlayer("Ugo");
        assertThrows(NoOneIsConnectedException.class, ()->c.disconnectPlayer("Uga"));
        //if everyone disconnect
        assertThrows(CannotJoinGameException.class, ()->c.joinGame("Ugo"));
        c.newGame("Ugo", 2);
    }

}