package com.aa.mtg.cards;

import com.aa.mtg.cards.ability.Ability;
import com.aa.mtg.cards.ability.type.AbilityType;
import com.aa.mtg.cards.properties.Color;
import com.aa.mtg.cards.properties.Cost;
import com.aa.mtg.cards.properties.Rarity;
import com.aa.mtg.cards.properties.Subtype;
import com.aa.mtg.cards.properties.Type;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aa.mtg.cards.ability.type.AbilityType.TAP_ADD_MANA;
import static com.aa.mtg.cards.properties.Cost.COLORLESS;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

@ToString
@EqualsAndHashCode
public class Card {
    private final String name;
    private final Set<Color> colors;
    private final List<Cost> cost;
    private final List<Type> types;
    private final List<Subtype> subtypes;
    private final Rarity rarity;
    private final String ruleText;
    private final int power;
    private final int toughness;
    private final List<Ability> abilities;

    public Card(String name, Set<Color> colors, List<Cost> cost, List<Type> types, List<Subtype> subtypes, Rarity rarity, String ruleText, int power, int toughness, List<Ability> abilities) {
        this.name = name;
        this.colors = colors;
        this.cost = cost;
        this.types = types;
        this.subtypes = subtypes;
        this.rarity = rarity;
        this.ruleText = ruleText;
        this.power = power;
        this.toughness = toughness;
        this.abilities = abilities;
    }

    public Card(Card card) {
        this(card.getName(), new HashSet<>(card.getColors()), new ArrayList<>(card.getCost()), new ArrayList<>(card.getTypes()),
                new ArrayList<>(card.getSubtypes()), Rarity.COMMON, card.getRuleText(), card.getPower(), card.getToughness(), card.getAbilities());
    }

    public static Card hiddenCard() {
        return new Card("card", emptySet(), emptyList(), emptyList(), emptyList(), Rarity.COMMON, "", 0, 0, emptyList());
    }

    public String getName() {
        return name;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public boolean isColorless() {
        return getCost().stream().noneMatch(cost -> cost != COLORLESS);
    }

    public List<Cost> getCost() {
        return cost;
    }

    public List<Type> getTypes() {
        return types;
    }

    public List<Subtype> getSubtypes() {
        return subtypes;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getRuleText() {
        return ruleText;
    }

    public int getPower() {
        return power;
    }

    public int getToughness() {
        return toughness;
    }

    public boolean isInstantSpeed() {
        return types.contains(Type.INSTANT);
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public boolean hasAbility(AbilityType abilityType) {
        return abilities.stream().map(Ability::getAbilityType).anyMatch(currentAbilityType -> currentAbilityType == abilityType);
    }

    public List<Color> colorsOfManaThatCanGenerate() {
        return abilities.stream()
                .filter(ability -> ability.getAbilityType().equals(TAP_ADD_MANA))
                .flatMap(ability -> ability.getParameters().stream())
                .map(Color::valueOf)
                .collect(Collectors.toList());
    }

    public boolean isOfType(Type type) {
        return types.contains(type);
    }

    public boolean isNotOfType(Type type) {
        return !isOfType(type);
    }

    public boolean isOfColor(Color color) {
        return colors.contains(color);
    }

    public boolean ofAnyOfTheColors(List<Color> colors) {
        for (Color color : colors) {
            if (isOfColor(color)) {
                return true;
            }
        }
        return false;
    }
}
