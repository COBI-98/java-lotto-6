package lotto.domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record LottoTicket(List<Lotto> lottoTicket) {

    public WinningResult calculateWinningStatistic(LottoDrawingMachine winningNumbers) {
        List<Ranking> rankings = lottoTicket.stream()
                .map(lotto -> winningNumbers.calculateRanking(lotto))
                .filter(Objects::nonNull)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
        return new WinningResult(rankings);
    }

}
