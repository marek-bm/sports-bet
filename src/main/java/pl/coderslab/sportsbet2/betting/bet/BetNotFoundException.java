package pl.coderslab.sportsbet2.betting.bet;

public class BetNotFoundException extends RuntimeException {

    public BetNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

}
