package com.aa.mtg.cards.sets;

import com.aa.mtg.cards.Card;
import com.aa.mtg.cards.properties.Color;
import com.aa.mtg.cards.properties.Cost;
import com.aa.mtg.cards.properties.Type;

import java.util.ArrayList;
import java.util.List;

import static com.aa.mtg.cards.ability.Abilities.ENCHANTED_CREATURE_GETS_PLUS_2_2;
import static com.aa.mtg.cards.ability.Abilities.ENCHANTED_CREATURE_GETS_PLUS_7_7_AND_TRAMPLE;
import static com.aa.mtg.cards.ability.Abilities.FLYING;
import static com.aa.mtg.cards.ability.Abilities.WHEN_IT_ENTERS_THE_BATTLEFIELD_CREATURES_YOU_CONTROL_GET_PLUS_1_1_AND_VIGILANCE_UNTIL_END_OF_TURN;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class CoreSet2019 implements MtgSet {

    public static final String M19 = "M19";

    public static Card ANGEL_OF_THE_DAWN = new Card("Angel of the Dawn", singletonList(Color.WHITE), asList(Cost.WHITE, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(Type.CREATURE), singletonList("Angel"), "Flying. When Angel of the Dawn enters the battlefield, creatures you control get +1/+1 and gain vigilance until end of turn.", 3, 3, asList(FLYING, WHEN_IT_ENTERS_THE_BATTLEFIELD_CREATURES_YOU_CONTROL_GET_PLUS_1_1_AND_VIGILANCE_UNTIL_END_OF_TURN));
    public static Card KNIGHTS_PLEDGE = new Card("Knight's Pledge", singletonList(Color.WHITE), asList(Cost.WHITE, Cost.COLORLESS), singletonList(Type.ENCHANTMENT), singletonList("Aura"), "Enchant creature. Enchanted creature gets +2/+2.", 0, 0, singletonList(ENCHANTED_CREATURE_GETS_PLUS_2_2));
    public static Card PRODIGIOUS_GROWTH = new Card("Prodigious Growth", singletonList(Color.GREEN), asList(Cost.GREEN, Cost.GREEN, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS, Cost.COLORLESS), singletonList(Type.ENCHANTMENT), singletonList("Aura"), "Enchant creature. Enchanted creature gets +7/+7 and has trample.", 0, 0, singletonList(ENCHANTED_CREATURE_GETS_PLUS_7_7_AND_TRAMPLE));

    private static CoreSet2019 instance;

    private List<Card> cards = new ArrayList<>();

    private CoreSet2019() {
        cards.add(ANGEL_OF_THE_DAWN);
        cards.add(KNIGHTS_PLEDGE);
        cards.add(PRODIGIOUS_GROWTH);
    }

    @Override
    public String getName() {
        return "Core Set 2019";
    }

    @Override
    public String getCode() {
        return M19;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    public static CoreSet2019 m19() {
        if (instance == null) {
            instance = new CoreSet2019();
        }
        return instance;
    }
}
