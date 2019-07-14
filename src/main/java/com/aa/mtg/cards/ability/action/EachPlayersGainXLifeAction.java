package com.aa.mtg.cards.ability.action;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.ability.Ability;
import com.aa.mtg.game.player.Player;
import com.aa.mtg.game.status.GameStatus;
import com.aa.mtg.game.status.GameStatusUpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EachPlayersGainXLifeAction implements AbilityAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(EachPlayersGainXLifeAction.class);

    private final GameStatusUpdaterService gameStatusUpdaterService;

    @Autowired
    public EachPlayersGainXLifeAction(GameStatusUpdaterService gameStatusUpdaterService) {
        this.gameStatusUpdaterService = gameStatusUpdaterService;
    }

    @Override
    public void perform(Ability ability, CardInstance cardInstance, GameStatus gameStatus) {
        int lifeToGain = Integer.valueOf(ability.getParameters().get(0));

        Player currentPlayer = gameStatus.getCurrentPlayer();
        currentPlayer.increaseLife(lifeToGain);
        gameStatusUpdaterService.sendUpdatePlayerLife(gameStatus, currentPlayer);

        Player nonCurrentPlayer = gameStatus.getNonCurrentPlayer();
        nonCurrentPlayer.increaseLife(lifeToGain);
        gameStatusUpdaterService.sendUpdatePlayerLife(gameStatus, nonCurrentPlayer);

        LOGGER.info("Each players gain {} life.", lifeToGain);
    }
}
