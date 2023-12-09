package lotto.controller;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoBall;
import lotto.domain.LottoDrawingMachine;
import lotto.domain.LottoTicket;
import lotto.domain.PlayerBuyPrice;
import lotto.domain.WinningResult;
import lotto.util.LottoAgency;
import lotto.util.LottoFactory;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoGameController {

    public void startGame() {
        PlayerBuyPrice playerBuyPrice = inputAmount();
        LottoTicket lottoTicket = ticketingLotto(playerBuyPrice);

        Lotto winningBall = createLotto();
        LottoBall bonusBall = createBonusBall();

        LottoDrawingMachine lottoDrawingMachine = createWinningLottoNumbers(winningBall, bonusBall);

        WinningResult winningResult = lottoTicket.calculateWinningStatistic(lottoDrawingMachine);

        OutputView.winningResultTitle();
        OutputView.printWinningStatistic(winningResult);
        OutputView.responseYieldOfLotto(playerBuyPrice.calculateProfit(winningResult.calculatePrizeSum()));
    }

    private static PlayerBuyPrice inputAmount() {
        try {
            return new PlayerBuyPrice(InputView.inputAmount());
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return inputAmount();
        }
    }

    private LottoTicket ticketingLotto(final PlayerBuyPrice playerBuyPrice) {
        int quantity = playerBuyPrice.calculateLottoQuantity();
        LottoTicket autoTicket = LottoAgency.createAutoTicket(quantity);

        OutputView.printBuyingTicketQuantity(playerBuyPrice);
        OutputView.printPlayerLottoTicketInfo(autoTicket);

        return autoTicket;
    }

    private Lotto createLotto() {
        List<String> lottoBallList = InputView.inputWiningLottoNumbers();

        try {
            return LottoFactory.createLotto(lottoBallList);
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return createLotto();
        }
    }

    private LottoBall createBonusBall() {
        try {
            return new LottoBall(InputView.inputWiningBonusLottoNumber());
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return createBonusBall();
        }
    }

    private static LottoDrawingMachine createWinningLottoNumbers(final Lotto winningBall, LottoBall bonusBall) {
        return new LottoDrawingMachine(winningBall, bonusBall);
    }
}
