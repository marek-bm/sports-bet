package pl.bets365mj.bet;

public class BetNotFoundException extends RuntimeException {

    public BetNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

}
