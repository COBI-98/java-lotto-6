package lotto.domain;

import java.util.Arrays;

public enum Ranking {
    FIRST(6, 2_000_000_000, false),
    SECOND(5, 30_000_000, true),
    THIRD(5, 1_500_000, false),
    FOURTH(4, 50_000, false),
    FIFTH(3, 5_000, false),
    NONE(0, 0, false);

    private final int matchedCount;
    private final int winningMoney;
    private final boolean hasBonusBall;

    Ranking(int matchedCount, int winningMoney, boolean hasBonusBall) {
        this.matchedCount = matchedCount;
        this.winningMoney = winningMoney;
        this.hasBonusBall = hasBonusBall;
    }


    public static Ranking findByRanking(int count, boolean hasBonusBall) {

        return Arrays.stream(Ranking.values())
                .filter(ranking -> ranking.hasBonusBall(count, hasBonusBall))
                .findAny()
                .orElse(NONE);

    }

    private boolean hasBonusBall(int count, boolean hasBonusNumber) {
        if (matchedCount != count) {
            return false;
        }

        if (count == SECOND.getMatchedCount()) {
            return hasBonusBall == hasBonusNumber;
        }

        return true;
    }

    public long multiple(Integer count) {
        return (long) count * winningMoney;
    }

    public int getWinningMoney() {
        return winningMoney;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public boolean hasBonusBall() {
        return hasBonusBall;
    }
}
