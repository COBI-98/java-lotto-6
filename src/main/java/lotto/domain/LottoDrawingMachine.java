package lotto.domain;

import static lotto.message.ErrorMessages.INVALID_DUPLICATION_LOTTO_BALL_AND_BONUS_BALL;

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

    public Ranking calculateRanking(final Lotto playerLotto) {
        int count = playerLotto.calculateSameCount(lotto.getNumbers());
        return Ranking.findByRanking(count, playerLotto.contains(bonusBall));
    }

    public Lotto getLotto() {
        return lotto;
    }

    public LottoBall getLottoBonusBall() {
        return bonusBall;
    }
}
