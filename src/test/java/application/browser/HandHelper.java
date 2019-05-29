package application.browser;

import com.aa.mtg.game.player.PlayerType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static application.browser.CardHelper.cardNames;

public class HandHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandHelper.class);

    private MtgBrowser mtgBrowser;
    private PlayerType playerType;

    HandHelper(MtgBrowser mtgBrowser, PlayerType playerType) {
        this.mtgBrowser = mtgBrowser;
        this.playerType = playerType;
    }

    public void handContainsExactly(List<String> expectedCards) {
        mtgBrowser.wait(driver -> {
            List<String> actualCardNames = cardNames(handElement());
            LOGGER.info("actualCardNames={}   expectedCards={}", actualCardNames, expectedCards);
            return expectedCards.equals(actualCardNames);
        });
    }

    private WebElement handElement() {
        if (playerType == PlayerType.PLAYER) {
            return mtgBrowser.findElement(By.id("player-hand"));
        } else {
            return mtgBrowser.findElement(By.id("opponent-hand"));
        }
    }
}