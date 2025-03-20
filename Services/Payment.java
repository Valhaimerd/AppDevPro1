package Services;

import Accounts.Account;
import Accounts.IllegalAccountType;

public interface Payment {

    /**
     * Pay a certain amount of money into a given account object.
     * This is different from Fund Transfer as paying does not have any sort of
     * processing fee.
     * @param account Target account to pay money into.
     * @param amount The amount of money to pay.
     * @throws IllegalAccountType Payment can only be processed between legal account types.
     */
    boolean pay(Account account, double amount) throws IllegalAccountType;

}
