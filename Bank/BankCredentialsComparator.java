package Bank;

import java.util.Comparator;

public class BankCredentialsComparator implements Comparator<Bank> {
    @Override
    public int compare(Bank b1, Bank b2) {
        BankComparator name = new BankComparator();
        int compareName = name.compare(b1, b2);

        if (compareName != 0) {
            return compareName;
        }

        return b1.getBankId().compareTo(b2.getBankId());
    }
}