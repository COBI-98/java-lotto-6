package lotto.view;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lotto.domain.Lotto;
import lotto.domain.LottoTicket;
import lotto.domain.PlayerBuyPrice;
import lotto.domain.Ranking;
import lotto.domain.WinningResult;

public class OutputView {

    private static final String TICKET_BUY_RESULT_FORMAT = "%d개를 구매했습니다.\n";
    private static final String WINNING_RESULT_TITLE = "당첨 통계";
    private static final String WINNING_RESULT_CONTOUR = "---";
    public static final String CORRECT_COUNT_FORMAT = "%d개 일치";
    public static final String WINNING_PRICE_AND_WINNING_COUNT_FORMAT = " (%,d원) - %d개%n";
    public static final String CORRECT_BONUS_BALL = ", 보너스 볼 일치";
    private static final String TOTAL_REVENUE_FORMAT = "총 수익률은 %s%%입니다.";

    public static void printBuyingTicketQuantity(final PlayerBuyPrice playerBuyPrice) {
        System.out.println();
        System.out.printf(TICKET_BUY_RESULT_FORMAT, playerBuyPrice.calculateLottoQuantity());

    }

    public static void printPlayerLottoTicketInfo(final LottoTicket lottoTicket) {
        lottoTicket.lottoTicket().stream()
                .map(Lotto::getNumbers)
                .forEach(System.out::println);
        System.out.println();
    }

    public static void winningResultTitle() {
        System.out.println();
        System.out.println(WINNING_RESULT_TITLE);
        System.out.println(WINNING_RESULT_CONTOUR);
    }

    public static void printWinningStatistic(WinningResult winningResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Ranking, Integer> entry : getWinningResultEntryList(winningResult)) {
            Ranking ranking = entry.getKey();
            int count = entry.getValue();
            generateResultContent(ranking, count, stringBuilder);
        }

        System.out.print(stringBuilder);
    }

    private static List<Map.Entry<Ranking, Integer>> getWinningResultEntryList(WinningResult winningResult) {
        List<Entry<Ranking, Integer>> collect = winningResult.getWinningResult().entrySet()
                .stream()
                .filter(entry -> entry.getKey() != Ranking.NONE)
                .sorted(Comparator.comparingInt(a -> a.getKey().getWinningMoney()))
                .collect(Collectors.toList());
        return collect;
    }

    private static void generateResultContent(Ranking ranking, int count, StringBuilder stringBuilder) {
        String countSentence = String.format(CORRECT_COUNT_FORMAT, ranking.getMatchedCount());
        stringBuilder.append(countSentence);

        if (ranking.hasBonusBall()) {
            stringBuilder.append(CORRECT_BONUS_BALL);
        }

        String str = String.format(WINNING_PRICE_AND_WINNING_COUNT_FORMAT, ranking.getWinningMoney(), count);
        stringBuilder.append(str);
    }

    public static void responseYieldOfLotto(final String yieldOfLotto) {
        System.out.printf(TOTAL_REVENUE_FORMAT, yieldOfLotto);
    }

    public static void printException(final Exception exception) {
        System.out.println(exception.getMessage());
    }
}
