package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lotto.util.GenerateLottoNumbersSuccessTest;
import lotto.util.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class LottoTest {
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void createLottoByOverSize() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void createLottoByDuplicatedNumber() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("constructor() : 무작위 로또 번호 생성 테스트")
    @Test
    void lottoNumbers_constructor_success() throws Exception {
        //given
        RandomUtils randomUtils = new GenerateLottoNumbersSuccessTest();

        //when
        Lotto lottoNumbers = new Lotto(randomUtils);

        //then
        assertThat(lottoNumbers.getNumbers()).isEqualTo(List.of(1, 3, 5, 7, 9, 11));
    }

    @DisplayName("contains() : 당첨로또와 같은 로또 볼을 가진 경우")
    @ParameterizedTest
    @CsvSource({"7,true", "7,false"})
    void contains_otherLottoBall_success(String otherLottoNumber, boolean expectedCondition) throws Exception {
        //given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        //when
        boolean hasLottoBall = lotto.contains(new LottoBall(otherLottoNumber));

        //then
        assertThat(hasLottoBall).isEqualTo(expectedCondition);
    }

    @DisplayName("calculateSameCount() : 당첨로또와 같은 로또 번호 개수 계산")
    @ParameterizedTest
    @MethodSource("generateData")
    void calculateSameCount_success(List<Integer> lottoNumbers, int count) throws Exception {
        //given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        //when
        int sameCount = lotto.calculateSameCount(lottoNumbers);

        //then
        assertThat(sameCount).isEqualTo(count);
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 6), 6),
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 45), 5),
                Arguments.of(Arrays.asList(1, 2, 3, 4, 44, 45), 4),
                Arguments.of(Arrays.asList(1, 2, 3, 43, 44, 45), 3),
                Arguments.of(Arrays.asList(1, 2, 42, 43, 44, 45), 2),
                Arguments.of(Arrays.asList(1, 41, 42, 43, 44, 45), 1),
                Arguments.of(Arrays.asList(40, 41, 42, 43, 44, 45), 0)
        );
    }
}