package lotto.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SplitLottoNumberTest {

    @DisplayName("constructor() : 로또 볼은 쉼표(,)를 기준으로 구분하여 생성된다.")
    @Test
    void splitLottoNumber_constructor_success() throws Exception {
        //given
        String playerInput = "1,2,3,4,5,6";
        List<String> splitLottoNumberList = SplitLottoNumber.splitLottoNumber(playerInput);

        //when //then
        assertThat(splitLottoNumberList).hasSize(6)
                .containsExactlyInAnyOrder(
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6"
                );
    }

    @DisplayName("validateDuplicateDelimiter() : 연속된 구분자가 사용되는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,,,3,4,5,6", "1,2,3,4,5,6,,,,,,,,,"})
    void validateDuplicateDelimiter_fail(String playerInput) throws Exception {
        //given
        String exceptionMessage = "[ERROR] 중복으로 구분자를 사용할 수 없습니다.";

        //when //then
        assertThatThrownBy(() -> SplitLottoNumber.splitLottoNumber(playerInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }

}