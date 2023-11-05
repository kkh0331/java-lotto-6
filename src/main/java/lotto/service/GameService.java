package lotto.service;

import lotto.constant.NumberConstant;
import lotto.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    private PurchaseAmount purchaseAmount;
    private List<Lotto> issuedLotto;
    private Lotto winningNumbers;
    private BonusNumber bonusNumber;
    private MatchingCount matchingCount;

    public int inputPurchaseAmount(String input){
        purchaseAmount = new PurchaseAmount(input);
        return purchaseAmount.getPurchaseAmount();
    }

    public List<Lotto> createIssuedLotto(){
        int issuedLottoCount = purchaseAmount.getPurchaseAmount()/ NumberConstant.LOTTO_ONE_PRICE;
        issuedLotto = new ArrayList<>();
        for(int i=1; i<=issuedLottoCount; i++){
            issuedLotto.add(new Lotto());
        }
        return issuedLotto;
    }

    public void inputWinningNumbers(String input){
        List<Integer> tempWinningNumbers = LottoInputParser.notNumber(input);
        winningNumbers = new Lotto(tempWinningNumbers);
    }

    public void inputBonusNumber(String input){
        bonusNumber = new BonusNumber(input, winningNumbers);
    }

    public List<Integer> calculateMatchingCount(){
        matchingCount = new MatchingCount();
        for(Lotto lotto : issuedLotto){
            matchingCount.updateMatchingCounts(lotto, winningNumbers, bonusNumber);
        }
        return matchingCount.getMatchingCounts();
    }

    public double calculateTotalReturnRate(){
        double sum = calculateTotalProfit();
        int initialPrice = purchaseAmount.getPurchaseAmount();
        return sum/initialPrice*100;
    }

    public double calculateTotalProfit(){
        double total = 0;
        List<Integer> matchingCounts = matchingCount.getMatchingCounts();
        for(int i=0; i<matchingCounts.size(); i++){
            total += Statistic.values()[i].getPrize() * matchingCounts.get(i);
        }
        return total;
    }

}
