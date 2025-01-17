package com.aa.mtg.game.turn.action.life;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.ability.Ability;
import com.aa.mtg.cards.ability.action.AbilityAction;
import com.aa.mtg.game.player.Player;
import com.aa.mtg.game.status.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddXLifeAction implements AbilityAction {
    private final LifeService lifeService;

    @Autowired
    public AddXLifeAction(LifeService lifeService) {
        this.lifeService = lifeService;
    }

    @Override
    public void perform(CardInstance cardInstance, GameStatus gameStatus, Ability ability) {
        int lifeToAdd = Integer.valueOf(ability.getParameter(0));
        Player controller = gameStatus.getPlayerByName(cardInstance.getController());
        lifeService.add(controller, lifeToAdd, gameStatus);
    }
}
