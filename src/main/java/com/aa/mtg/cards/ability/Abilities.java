package com.aa.mtg.cards.ability;

import com.aa.mtg.cards.ability.target.Target;
import com.aa.mtg.cards.ability.target.TargetPowerToughnessConstraint;
import com.aa.mtg.cards.ability.target.TargetSelectionConstraint;
import com.aa.mtg.cards.ability.target.TargetType;
import com.aa.mtg.cards.ability.type.AbilityType;

import static com.aa.mtg.cards.ability.target.TargetPowerToughnessConstraint.PowerOrToughness.POWER;
import static com.aa.mtg.cards.ability.trigger.Trigger.WHEN_IT_ENTERS_THE_BATTLEFIELD;
import static com.aa.mtg.cards.ability.type.AbilityType.*;
import static com.aa.mtg.cards.properties.Type.CREATURE;
import static com.aa.mtg.game.player.PlayerType.PLAYER;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class Abilities {
    public static Ability WHEN_IT_ENTERS_THE_BATTLEFIELD_CREATURES_YOU_CONTROL_GET_PLUS_1_1_UNTIL_END_OF_TURN = new Ability(CREATURES_YOU_CONTROL_GET_PLUS_X_UNTIL_END_OF_TURN, emptyList(), singletonList("+1/+1"), WHEN_IT_ENTERS_THE_BATTLEFIELD);
    public static Ability DEAL_1_DAMAGE_TO_CREATURE_YOU_CONTROL_THAT_CREATURE_GAINS_TRAMPLE = new Ability(asList(DEALS_X_DAMAGE_TO_TARGET, THAT_TARGETS_GET_X), singletonList(Target.builder().targetType(TargetType.PERMANENT).ofType(singletonList(CREATURE)).targetControllerType(PLAYER).build()), asList("1", "Trample"));
    public static Ability DEAL_3_DAMAGE_TO_ANY_TARGET = new Ability(DEALS_X_DAMAGE_TO_TARGET, singletonList(Target.builder().targetType(TargetType.ANY).build()), singletonList("3"));
    public static Ability DEATHTOUCH = new Ability(AbilityType.DEATHTOUCH);
    public static Ability DESTROY_TARGET_CREATURE_WITH_POWER_GREATER_OR_EQUAL_4 = new Ability(DESTROY_TARGET, singletonList(Target.builder().targetType(TargetType.PERMANENT).ofType(singletonList(CREATURE)).targetPowerToughnessConstraint(new TargetPowerToughnessConstraint(POWER, TargetSelectionConstraint.GREATER_OR_EQUAL, 4)).build()), emptyList());
    public static Ability DRAW_1_CARD = new Ability(DRAW_X_CARDS, emptyList(), singletonList("1"));
    public static Ability FLYING = new Ability(AbilityType.FLYING);
    public static Ability HASTE = new Ability(AbilityType.HASTE);
    public static Ability REACH = new Ability(AbilityType.REACH);
    public static Ability SHUFFLE_GRAVEYARD_INTO_LIBRARY_OF_TARGET_PLAYER = new Ability(SHUFFLE_GRAVEYARD_INTO_LIBRARY_FOR_TARGET_PLAYER, singletonList(Target.builder().targetType(TargetType.PLAYER).build()), emptyList());
    public static Ability TRAMPLE = new Ability(AbilityType.TRAMPLE);
    public static Ability VIGILANCE = new Ability(AbilityType.VIGILANCE);
}