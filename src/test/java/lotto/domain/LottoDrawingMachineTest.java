package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LottoDrawingMachineTest {

    @DisplayName("constructor() : 로또 기계가 당첨 번호와 보너스번호를 생성한다.")
    @Test
    void lottoDrawingMachine_constructor_success() throws Exception {
        //given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        LottoBall bonusBall = new LottoBall("7");

        //when
        LottoDrawingMachine lottoDrawingMachine = new LottoDrawingMachine(lotto, bonusBall);

        //then
        assertThat(lottoDrawingMachine.getLotto().getNumbers()).hasSize(6);
        assertThat(lottoDrawingMachine.getLottoBonusBall().lottoBall()).isEqualTo("7");
    }

    @DisplayName("validateDuplicationNumbers() : 당첨 번호와 중복으로 사용될 수 없다.")
    @Test
    void validateDuplicationNumbers_Fail() throws Exception {
        //given
        String exceptionMessage = "[ERROR] 보너스 번호는 당첨 번호와 중복으로 사용할 수 없습니다.";
        Lotto lottoNumber = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        LottoBall bonusBall = new LottoBall("6");

        //when //then
        assertThatThrownBy(() -> new LottoDrawingMachine(lottoNumber, bonusBall))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);

    }

    @DisplayName("calculateRanking() : 플레이어가 발행한 로또와 로또 당첨 번호를 비교하여 순위 계산")
    @ParameterizedTest
    @MethodSource("generateData")
    void calculateRanking_success(List<Integer> playerLotto, Ranking expectedRanking) throws Exception {
        //given
        LottoDrawingMachine lottoDrawingMachine = startLottoDrawingMachine();
        Lotto lotto = new Lotto(playerLotto);

        //when
        Ranking ranking = lottoDrawingMachine.calculateRanking(lotto);

        //then
        assertThat(ranking).isEqualTo(expectedRanking);
    }

    private static LottoDrawingMachine startLottoDrawingMachine() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        LottoBall bonusBall = new LottoBall("7");
        return new LottoDrawingMachine(lotto, bonusBall);
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 6), Ranking.FIRST),
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 7), Ranking.SECOND),
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 45), Ranking.THIRD),
                Arguments.of(Arrays.asList(1, 2, 3, 4, 44, 45), Ranking.FOURTH),
                Arguments.of(Arrays.asList(1, 2, 3, 43, 44, 45), Ranking.FIFTH),
                Arguments.of(Arrays.asList(1, 2, 42, 43, 44, 45), Ranking.NONE),
                Arguments.of(Arrays.asList(1, 41, 42, 43, 44, 45), Ranking.NONE),
                Arguments.of(Arrays.asList(40, 41, 42, 43, 44, 45), Ranking.NONE)
        );
    }
}