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

    private final InputView inputView;
    private final OutputView outputView;

    public LottoGameController() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    public void startGame() {
        PlayerBuyPrice playerBuyPrice = inputAmount();
        LottoTicket lottoTicket = ticketingLotto(playerBuyPrice);

        Lotto winningBall = createLotto();
        LottoBall bonusBall = createBonusBall();

        LottoDrawingMachine lottoDrawingMachine = createWinningLottoNumbers(winningBall, bonusBall);

        WinningResult winningResult = lottoTicket.calculateWinningStatistic(lottoDrawingMachine);

        printWinningResult(playerBuyPrice, winningResult);
    }

    private PlayerBuyPrice inputAmount() {
        try {
            return new PlayerBuyPrice(inputView.inputAmount());
        } catch (IllegalArgumentException e) {
            outputView.printException(e);
            return inputAmount();
        }
    }

    private LottoTicket ticketingLotto(final PlayerBuyPrice playerBuyPrice) {
        int quantity = playerBuyPrice.calculateLottoQuantity();
        LottoTicket autoTicket = LottoAgency.createAutoTicket(quantity);

        outputView.printBuyingTicketQuantity(playerBuyPrice);
        outputView.printPlayerLottoTicketInfo(autoTicket);

        return autoTicket;
    }

    private Lotto createLotto() {
        List<String> lottoBallList = inputView.inputWiningLottoNumbers();

        try {
            return LottoFactory.createLotto(lottoBallList);
        } catch (IllegalArgumentException e) {
            outputView.printException(e);
            return createLotto();
        }
    }

    private LottoBall createBonusBall() {
        try {
            return new LottoBall(inputView.inputWiningBonusLottoNumber());
        } catch (IllegalArgumentException e) {
            outputView.printException(e);
            return createBonusBall();
        }
    }

    private static LottoDrawingMachine createWinningLottoNumbers(final Lotto winningBall, LottoBall bonusBall) {
        return new LottoDrawingMachine(winningBall, bonusBall);
    }

    private void printWinningResult(PlayerBuyPrice playerBuyPrice, WinningResult winningResult) {
        outputView.winningResultTitle();
        outputView.printWinningStatistic(winningResult);
        outputView.responseYieldOfLotto(playerBuyPrice.calculateProfit(winningResult.calculatePrizeSum()));
    }
}
