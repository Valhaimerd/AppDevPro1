package Services;

public interface Withdrawal {

    /**
     * Withdraws an amount of money using a given medium.
     * @param amount Amount of money to be withdrawn from.
     */
    boolean withdrawal(double amount);

}
