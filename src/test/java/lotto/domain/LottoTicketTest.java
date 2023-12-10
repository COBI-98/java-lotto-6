package lotto.domain;

import static lotto.domain.Ranking.FIFTH;
import static lotto.domain.Ranking.FIRST;
import static lotto.domain.Ranking.FOURTH;
import static lotto.domain.Ranking.NONE;
import static lotto.domain.Ranking.SECOND;
import static lotto.domain.Ranking.THIRD;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoTicketTest {

    @DisplayName("constructor() : 로또 티켓 발급 테스트")
    @Test
    void lottoTicket_constructor_success() throws Exception {
        //given
        Lotto lottoNumbers1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto lottoNumbers2 = new Lotto(List.of(2, 3, 4, 5, 6, 7));
        LottoTicket lottoTicket = new LottoTicket(List.of(lottoNumbers1, lottoNumbers2));

        //when //then
        assertThat(lottoTicket.lottoTicket()).hasSize(2);
    }

    @DisplayName("calculateWinningStatistic() : 발행한 로또 티켓의 로또 별 순위 리스트 생성")
    @Test
    void calculateWinningStatistic_success() throws Exception{
        //given
        LottoTicket autoTicket = createAutoTicket();
        LottoDrawingMachine lottoDrawingMachine = startLottoDrawingMachine();

        //when
        List<Ranking> rankings = autoTicket.calculateWinningStatistic(lottoDrawingMachine);

        //then
        assertThat(rankings).hasSize(6)
                .containsExactlyInAnyOrder(
                        FIRST, SECOND, THIRD, FOURTH, FIFTH, NONE
                );
    }

    private static LottoTicket createAutoTicket() {
        Lotto createLotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto createLotto2 = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Lotto createLotto3 = new Lotto(List.of(1, 2, 3, 4, 5, 45));
        Lotto createLotto4 = new Lotto(List.of(1, 2, 3, 4, 44, 45));
        Lotto createLotto5 = new Lotto(List.of(1, 2, 3, 42, 43, 45));
        Lotto createLotto6 = new Lotto(List.of(1, 2, 41, 42, 43, 45));


        return new LottoTicket(List.of(createLotto1, createLotto2, createLotto3,
                createLotto4, createLotto5, createLotto6));
    }

    private static LottoDrawingMachine startLottoDrawingMachine() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        LottoBall bonusBall = new LottoBall("7");
        return new LottoDrawingMachine(lotto, bonusBall);
    }
}