package integration.mtg.game.turn.action.cast;

import com.aa.mtg.cards.CardInstance;
import com.aa.mtg.cards.CardInstanceFactory;
import com.aa.mtg.cards.properties.Cost;
import com.aa.mtg.game.player.Player;
import com.aa.mtg.game.status.GameStatus;
import com.aa.mtg.game.turn.action.cast.ManaCountService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import integration.TestUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.aa.mtg.cards.Cards.ISLAND;
import static com.aa.mtg.cards.Cards.PLAINS;
import static com.aa.mtg.cards.properties.Cost.BLUE;
import static com.aa.mtg.cards.properties.Cost.WHITE;
import static com.aa.mtg.cards.sets.CoreSet2020.DARK_REMEDY;
import static com.aa.mtg.cards.sets.RavnicaAllegiance.AZORIUS_GUILDGATE;
import static com.aa.mtg.cards.sets.RavnicaAllegiance.GYRE_ENGINEER;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CastTestConfiguration.class)
public class ManaCountServiceTest {

    @Autowired
    private ManaCountService manaCountService;
    
    @Autowired
    private CardInstanceFactory cardInstanceFactory;

    @Autowired
    private TestUtils testUtils;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void countManaPaidForSimpleLands() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("WHITE"),
                2, ImmutableList.of("WHITE"),
                3, ImmutableList.of("BLUE")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, PLAINS, player.getName(), player.getName()));
        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 2, PLAINS, player.getName(), player.getName()));
        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 3, ISLAND, player.getName(), player.getName()));

        // When
        ArrayList<Cost> colors = manaCountService.verifyManaPaid(mana, player);

        // Then
        assertThat(colors).isEqualTo(ImmutableList.of(WHITE, WHITE, BLUE));
    }

    @Test
    public void countManaPaidTappingInstant() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("WHITE")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, DARK_REMEDY, player.getName(), player.getName()));

        thrown.expectMessage("\"1 - Dark Remedy\" cannot be tapped for mana.");

        // When
        manaCountService.verifyManaPaid(mana, player);
    }

    @Test
    public void countManaPaidTappingAlreadyTappedLand() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("WHITE")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        CardInstance plains = cardInstanceFactory.create(gameStatus, 1, PLAINS, player.getName(), player.getName());
        plains.getModifiers().tap();
        player.getBattlefield().addCard(plains);

        thrown.expectMessage("\"1 - Plains\" is already tapped.");

        // When
        manaCountService.verifyManaPaid(mana, player);
    }

    @Test
    public void countManaPaidTappingLandForWrongColor() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("BLUE")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, PLAINS, player.getName(), player.getName()));

        thrown.expectMessage("\"1 - Plains\" cannot produce BLUE");

        // When
        manaCountService.verifyManaPaid(mana, player);
    }

    @Test
    public void countManaPaidTappingLandForDualLand() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("BLUE")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, AZORIUS_GUILDGATE, player.getName(), player.getName()));

        // When
        ArrayList<Cost> colors = manaCountService.verifyManaPaid(mana, player);

        // Then
        assertThat(colors).isEqualTo(ImmutableList.of(BLUE));
    }

    @Test
    public void countManaPaidTappingLandForDualLandError() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("BLACK")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, AZORIUS_GUILDGATE, player.getName(), player.getName()));

        thrown.expectMessage("\"1 - Azorius Guildgate\" cannot produce BLACK");

        // When
        manaCountService.verifyManaPaid(mana, player);
    }

    @Test
    public void countManaPaidTappingCreatureWhichGeneratesTwoMana() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("GREEN", "BLUE")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, GYRE_ENGINEER, player.getName(), player.getName()));

        // When
        manaCountService.verifyManaPaid(mana, player);
    }

    @Test
    public void countManaPaidTappingCreatureWhichGeneratesTwoManaException() {
        // Given
        Map<Integer, List<String>> mana = ImmutableMap.of(
                1, ImmutableList.of("GREEN", "BLACK")
        );
        GameStatus gameStatus = testUtils.testGameStatus();
        Player player = gameStatus.getPlayer1();

        player.getBattlefield().addCard(cardInstanceFactory.create(gameStatus, 1, GYRE_ENGINEER, player.getName(), player.getName()));

        thrown.expectMessage("\"1 - Gyre Engineer\" cannot produce BLACK");

        // When
        manaCountService.verifyManaPaid(mana, player);
    }
}