package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoBall;
import lotto.domain.LottoDrawingMachine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LottoDrawingMachineTest {

    @DisplayName("constructor() : 로또 기계가 당첨 번호와 보너스번호를 생성한다.")
    @Test
    void lottoDrawingMachine_constructor_success() throws Exception{
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
}