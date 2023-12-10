package lotto.domain;

import static lotto.domain.Ranking.FIFTH;
import static lotto.domain.Ranking.FIRST;
import static lotto.domain.Ranking.FOURTH;
import static lotto.domain.Ranking.SECOND;
import static lotto.domain.Ranking.THIRD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WinningResultTest {

    @DisplayName("constructor() : 등수 별 당첨 결과 생성")
    @Test
    void winningResult_constructor_success() throws Exception {
        //given
        int limitWins = 5;
        WinningResult winningResult = new WinningResult(List.of(FIRST, THIRD));

        //when //then
        assertThat(winningResult.getWinningResult()).hasSize(limitWins)
                .contains(entry(FIRST, 1), entry(SECOND, 0), entry(THIRD, 1)
                        , entry(FOURTH, 0), entry(FIFTH, 0));
    }

    @DisplayName("calculatePrizeSum() : 당첨된 수령금 계산")
    @ParameterizedTest
    @MethodSource("generateRankingData")
    void calculatePrizeSum_success(List<Ranking> rankings, long prize) throws Exception {
        //given
        WinningResult winningResult = new WinningResult(rankings);

        //when
        long prizeSum = winningResult.calculatePrizeSum();

        //then
        assertThat(prizeSum).isEqualTo(prize);
    }

    static Stream<Arguments> generateRankingData() {
        return Stream.of(
                Arguments.of(Arrays.asList(FIRST, THIRD), 2_001_500_000L),
                Arguments.of(Arrays.asList(SECOND, FIFTH), 30_005_000L),
                Arguments.of(Arrays.asList(FIFTH, FIFTH, FIFTH), 15_000L)
        );
    }
}