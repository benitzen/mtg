package com.aa.mtg.game.player;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.CardInstanceFactory;
import com.aa.mtg.cards.CardListComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class Hand extends CardListComponent {

    private final CardInstanceFactory cardInstanceFactory;

    @Autowired
    public Hand(CardInstanceFactory cardInstanceFactory) {
        this.cardInstanceFactory = cardInstanceFactory;
    }

    public List<CardInstance> maskedHand() {
        return cardInstanceFactory.mask(this.cards);
    }
}
