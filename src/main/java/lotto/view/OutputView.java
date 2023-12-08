package lotto.view;

import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
import lotto.domain.Player;
import lotto.domain.Ranking;

public class OutputView {

    private static final int AMOUNT_UNIT = 1_000;
    private static final String TICKET_BUY_RESULT_FORMAT = "개를 구매했습니다.";
    private static final String WINNING_RESULT_TITLE = "당첨 통계";
    private static final String WINNING_RESULT_CONTOUR = "---";


    public static void printBuyingTicketQuantity(final Player player) {
        System.out.println();
        System.out.println(player.getPlayerTicketQuantity() + TICKET_BUY_RESULT_FORMAT);
    }

    public static void printPlayerLottoTicketInfo(final Player player) {
        player.getLottoTicket().stream()
                .map(Lotto::getNumbers)
                .forEach(System.out::println);
    }

    public static void printWinningHistory(final Map<Ranking, Integer> countOfWinning) {
        winningResultTitle();
        List<Ranking> resultRanks = List.of(Ranking.FIFTH, Ranking.FOURTH, Ranking.THIRD, Ranking.SECOND,
                Ranking.FIRST);

        resultRanks.stream()
                .map(rank -> rank.getResultFormat() + "- " + countOfWinning.getOrDefault(rank, 0) + "개")
                .forEach(System.out::println);
    }

    private static void winningResultTitle() {
        System.out.println();
        System.out.println(WINNING_RESULT_TITLE);
        System.out.println(WINNING_RESULT_CONTOUR);
    }

    public static void responseYieldOfLotto(final String yieldOfLotto) {
        System.out.println("총 수익률은 " + yieldOfLotto + "%입니다.");
    }

    public static void printException(final Exception exception) {
        System.out.println(exception.getMessage());
    }
}
