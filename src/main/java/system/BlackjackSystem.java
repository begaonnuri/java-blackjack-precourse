package system;

import domain.card.Card;
import domain.card.CardFactory;
import domain.user.Dealer;
import domain.user.Player;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BlackjackSystem {
    private static int CARD_COUNT = 52;
    private static int DISTRIBUTE_CARD_COUNT = 2;
    private static int DEALER_GET_CARD_CONDITION = 16;
    private static int PLAYER_GET_CARD_CONDITION = 21;

    private List<Player> playerList = new ArrayList<>();
    private List<Card> cardList = new ArrayList<>();
    private Dealer dealer = new Dealer();
    private int remainCardMount = CARD_COUNT;
    private char answer;

    public void run() {
        setGame();
        distributeCard();
        printInitStatus();
        getCardDealerIfAvailable();
        for (Player p : playerList) {
            AskPlayerToGetCard(p);
        }
        printResultStatus();
    }

    private void setGame() {
        String names = InputView.inputPlayerName();
        StringTokenizer st = splitPlayerName(names);
        setPlayer(st);
    }

    private StringTokenizer splitPlayerName(String names) {
        return new StringTokenizer(names, ",");
    }

    private void setPlayer(StringTokenizer st) {
        while (st.hasMoreTokens()) {
            String playerName = st.nextToken();
            double bettingMoney = InputView.inputPlayerMoney(playerName);
            addPlayer(new Player(playerName, bettingMoney));
        }
    }

    private void addPlayer(Player player) {
        playerList.add(player);
    }

    private void distributeCard() {
        cardList.addAll(CardFactory.create());
        for (int i = 0; i < DISTRIBUTE_CARD_COUNT; i++) {
            giveCard(dealer);
            giveCard(playerList);
        }
    }

    private void giveCard(Dealer dealer) {
        int randomNumber = getRandomNumber(remainCardMount--);
        dealer.addCard(cardList.get(randomNumber));
        cardList.remove(randomNumber);
    }

    private void giveCard(Player player) {
        int randomNumber = getRandomNumber(remainCardMount--);
        player.addCard(cardList.get(randomNumber));
        cardList.remove(randomNumber);
    }

    private void giveCard(List<Player> playerList) {
        for (Player p : playerList) {
            giveCard(p);
        }
    }

    private int getRandomNumber(int range) {
        return (int) (Math.random() * range);
    }

    private void printInitStatus() {
        OutputView.printInitDistributionMessage(playerList);
        OutputView.printCardStatus(dealer);
        for (Player p : playerList) {
            OutputView.printCardStatus(p);
        }
        System.out.println();
    }

    private void getCardDealerIfAvailable() {
        while (isAvailableGetCard(dealer)) {
            giveCard(dealer);
            OutputView.printDealerGetCardMessage();
        }
    }

    private void AskPlayerToGetCard(Player player) {
        answer = 'y';
        while (isAvailableGetCard(player)) {
            choiceGetCard(player);
        }
    }

    private boolean isAvailableGetCard(Player p) {
        return p.isSumUnderCondition(PLAYER_GET_CARD_CONDITION) && answer == 'y';
    }

    private boolean isAvailableGetCard(Dealer d) {
        return d.isSumUnderCondition(DEALER_GET_CARD_CONDITION);
    }

    private void choiceGetCard(Player player) {
        answer = InputView.inputChoiceGetCard(player);
        if (answer == 'n') return;
        giveCard(player);
        OutputView.printCardStatus(player);
    }

    private void printResultStatus() {
        OutputView.printResultStatus(dealer);
        for (Player player : playerList) {
            OutputView.printResultStatus(player);
        }
    }
}
