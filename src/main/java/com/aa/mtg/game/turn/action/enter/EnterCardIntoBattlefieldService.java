package com.aa.mtg.game.turn.action.enter;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.ability.Ability;
import com.aa.mtg.cards.ability.type.AbilityType;
import com.aa.mtg.game.status.GameStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.aa.mtg.cards.ability.trigger.TriggerSubtype.WHEN_IT_ENTERS_THE_BATTLEFIELD;
import static com.aa.mtg.cards.ability.type.AbilityType.ENTERS_THE_BATTLEFIELD_TAPPED;
import static com.aa.mtg.cards.properties.Type.CREATURE;
import static java.util.stream.Collectors.toList;

@Component
public class EnterCardIntoBattlefieldService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnterCardIntoBattlefieldService.class);

    public void enter(GameStatus gameStatus, CardInstance cardInstance) {
        String controller = cardInstance.getController();
        gameStatus.getPlayerByName(controller).getBattlefield().addCard(cardInstance);

        cardInstance.getModifiers().setPermanentId(gameStatus.nextCardId());

        if (cardInstance.isOfType(CREATURE)) {
            cardInstance.getModifiers().setSummoningSickness(true);
        }

        if (cardInstance.hasAbility(ENTERS_THE_BATTLEFIELD_TAPPED)) {
            cardInstance.getModifiers().tap();
        }

        List<Ability> entersTheBattlefieldAbilities = cardInstance.getAbilities().stream()
                .filter(ability -> ability.hasTriggerOfSubtype(WHEN_IT_ENTERS_THE_BATTLEFIELD))
                .collect(toList());

        if (!entersTheBattlefieldAbilities.isEmpty()) {
            cardInstance.getTriggeredAbilities().addAll(entersTheBattlefieldAbilities);
            List<AbilityType> abilityTypes = entersTheBattlefieldAbilities.stream().map(Ability::getAbilityType).collect(toList());
            LOGGER.info("Event {} triggered with abilities {} for {}.", WHEN_IT_ENTERS_THE_BATTLEFIELD, abilityTypes, cardInstance.getModifiers());
            gameStatus.getStack().add(cardInstance);
        }
    }

}
