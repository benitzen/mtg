package com.aa.mtg.game.deck;

import com.aa.mtg.cards.Card;
import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.CardInstanceFactory;
import com.aa.mtg.cards.properties.Color;
import com.aa.mtg.cards.properties.Type;
import com.aa.mtg.cards.search.CardSearch;
import com.aa.mtg.cards.sets.MtgSets;
import com.aa.mtg.game.security.SecurityToken;
import com.aa.mtg.game.status.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.aa.mtg.cards.Cards.*;
import static com.aa.mtg.cards.properties.Color.*;
import static java.util.Arrays.asList;

@Component
public class DeckRetrieverService {

    private final MtgSets mtgSets;
    private final CardInstanceFactory cardInstanceFactory;

    @Autowired
    public DeckRetrieverService(MtgSets mtgSets, CardInstanceFactory cardInstanceFactory) {
        this.mtgSets = mtgSets;
        this.cardInstanceFactory = cardInstanceFactory;
    }

    public List<CardInstance> retrieveDeckForUser(SecurityToken token, String playerName, GameStatus gameStatus) {
        return randomDeck(playerName, gameStatus);
    }

    private List<CardInstance> randomDeck(String playerName, GameStatus gameStatus) {
        List<CardInstance> cards = new ArrayList<>();

        List<Color> deckColors = randomTwoColors();

        for (Color color : deckColors) {
            addNCards(gameStatus, cards, playerName, 11, getBasicLandForColor(color));
        }

        for (Card randomCard : getRandomSpellsForColors(deckColors)) {
            addNCards(gameStatus, cards, playerName, 4, randomCard);
        }

        addNCards(gameStatus, cards, playerName, 2, getRandomNonBasicLandOfTheseColors(deckColors));

        addNCards(gameStatus, cards, playerName, 4, getRandomColorlessCard());

        Collections.shuffle(cards);
        return cards;
    }

    private Card getBasicLandForColor(Color color) {
        switch (color) {
            case WHITE:
                return PLAINS;
            case BLUE:
                return ISLAND;
            case BLACK:
                return SWAMP;
            case RED:
                return MOUNTAIN;
            case GREEN:
                return FOREST;
            default:
                throw new RuntimeException("Basic Land for color " + color + " does not exist.");
        }
    }

    private List<Color> randomTwoColors() {
        List<Color> colors = asList(WHITE, BLUE, BLACK, RED, GREEN);
        Collections.shuffle(colors);
        return colors.subList(0, 2);
    }

    private List<Card> getRandomSpellsForColors(List<Color> deckColors) {
        ArrayList<Card> selectedCards = new ArrayList<>();

        List<Card> creatureCardsOfTheseColors = new CardSearch(mtgSets.getAllCards())
                .ofAnyOfTheColors(deckColors)
                .ofType(Type.CREATURE)
                .getCards();
        Collections.shuffle(creatureCardsOfTheseColors);
        selectedCards.addAll(creatureCardsOfTheseColors.subList(0, 5));

        List<Card> nonCreatureCardsOfTheseColors = new CardSearch(mtgSets.getAllCards())
                .ofAnyOfTheColors(deckColors)
                .notOfType(Type.CREATURE)
                .getCards();
        Collections.shuffle(nonCreatureCardsOfTheseColors);
        selectedCards.addAll(nonCreatureCardsOfTheseColors.subList(0, 3));

        return selectedCards;
    }

    private Card getRandomNonBasicLandOfTheseColors(List<Color> deckColors) {
        List<Card> nonBasicLands = new CardSearch(mtgSets.getAllCards())
                .ofType(Type.LAND)
                .notOfType(Type.BASIC)
                .getCards()
                .stream()
                .filter(card -> card.colorsOfManaThatCanGenerate().size() == 0 || deckColors.contains(card.colorsOfManaThatCanGenerate().get(0)))
                .collect(Collectors.toList());
        Collections.shuffle(nonBasicLands);
        return nonBasicLands.get(0);
    }

    private Card getRandomColorlessCard() {
        List<Card> allColorlessCards = new CardSearch(mtgSets.getAllCards())
                .colorless()
                .getCards();
        Collections.shuffle(allColorlessCards);

        return allColorlessCards.get(0);
    }

    private void addNCards(GameStatus gameStatus, List<CardInstance> cards, String playerName, int n, Card plains) {
        for (int i = 0; i < n; i++) {
            cards.add(cardInstanceFactory.create(gameStatus, gameStatus.nextCardId(), plains, playerName));
        }
    }
}
