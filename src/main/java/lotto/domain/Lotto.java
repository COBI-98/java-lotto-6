package lotto.domain;

import static lotto.message.ErrorMessages.INVALID_DUPLICATION_LOTTO_BALLS;
import static lotto.message.ErrorMessages.INVALID_LOTTO_BALL_SIZE;

import java.util.List;
import lotto.util.RandomUtils;

public class Lotto {
    private static final int LOTTO_BALL_COUNT = 6;
    private final List<Integer> numbers;

    public Lotto(final List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public Lotto(RandomUtils randomUtils) {
        this.numbers = randomUtils.generateRandomNumbers();
    }

    private void validate(final List<Integer> numbers) {
        validateLength(numbers);
        validateDifferentNumber(numbers);
    }

    private void validateLength(final List<Integer> numbers) {
        if (numbers.size() != LOTTO_BALL_COUNT) {
            throw new IllegalArgumentException(INVALID_LOTTO_BALL_SIZE.getMessage());
        }
    }

    private void validateDifferentNumber(final List<Integer> numbers) {
        long distinctCount = numbers.stream()
                .distinct()
                .count();

        if (distinctCount != LOTTO_BALL_COUNT) {
            throw new IllegalArgumentException(INVALID_DUPLICATION_LOTTO_BALLS.getMessage());
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public boolean contains(final LottoBall otherLottoNumber) {
        return numbers.stream()
                .anyMatch(lottoNumber -> lottoNumber.equals(otherLottoNumber.toInt()));
    }
}
