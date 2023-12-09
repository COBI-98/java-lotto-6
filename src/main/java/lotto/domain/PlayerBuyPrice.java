package lotto.domain;

import static lotto.message.ErrorMessages.INVALID_AMOUNT_EMPTY;
import static lotto.message.ErrorMessages.INVALID_AMOUNT_FORMAT;
import static lotto.message.ErrorMessages.INVALID_AMOUNT_RANGE;
import static lotto.message.ErrorMessages.INVALID_AMOUNT_UNIT;

import java.text.DecimalFormat;

public record PlayerBuyPrice(String amount) {

    private static int MIN_AMOUNT = 1_000;
    private static int MAX_AMOUNT = 10_000_000;
    private static final String DECIMAL_FORMAT = "###,##0.0";

    public PlayerBuyPrice {
        validateEmptyFromAmount(amount);
        validateCharacterFromAmount(amount);
        validateRangeFromAmount(amount);
        validateUnitFromAmount(amount);
    }

    private void validateEmptyFromAmount(String amount) {
        if (amount.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AMOUNT_EMPTY.getMessage());
        }
    }

    private void validateCharacterFromAmount(String input) {
        for (int idx = 0; idx < input.length(); idx++) {
            if (!Character.isDigit(input.charAt(idx))) {
                throw new IllegalArgumentException(INVALID_AMOUNT_FORMAT.getMessage());
            }
        }
    }

    private void validateRangeFromAmount(String input) {
        int buyAmount = Integer.parseInt(input);
        if (buyAmount < MIN_AMOUNT || buyAmount > MAX_AMOUNT) {
            throw new IllegalArgumentException(INVALID_AMOUNT_RANGE.getMessage());
        }
    }

    private void validateUnitFromAmount(String input) {
        int buyAmount = Integer.parseInt(input);
        if (buyAmount % 1_000 != 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT_UNIT.getMessage());
        }
    }

    public String calculateProfit(long totalPrice) {
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
        return decimalFormat.format((double) totalPrice / Integer.parseInt(amount) * 100);
    }

    public int calculateLottoQuantity() {
        return Integer.parseInt(amount) / MIN_AMOUNT;
    }
}
