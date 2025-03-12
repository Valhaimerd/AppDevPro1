package Bank;

import java.util.Comparator;

public class BankComparator implements Comparator<Bank> {
    @Override
    public int compare(Bank b1, Bank b2) {
        return b1.getBankName().compareTo(b2.getBankName());
    }
}