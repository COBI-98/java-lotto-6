package lotto.domain;

import static lotto.message.ErrorMessages.INVALID_DUPLICATION_LOTTO_BALL_AND_BONUS_BALL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LottoDrawingMachine {

    private Lotto lotto;
    private LottoBall bonusBall;

    public LottoDrawingMachine(final Lotto lottoNumbers, final LottoBall bonusBall) {
        validateDuplicationNumbers(lottoNumbers, bonusBall);
        this.lotto = lottoNumbers;
        this.bonusBall = bonusBall;
    }

    private void validateDuplicationNumbers(Lotto lottoNumbers, LottoBall bonusNumber) {
        if (lottoNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(INVALID_DUPLICATION_LOTTO_BALL_AND_BONUS_BALL.getMessage());
        }
    }

    public Lotto getLotto() {
        return lotto;
    }

    public LottoBall getLottoBonusBall() {
        return bonusBall;
    }
}
