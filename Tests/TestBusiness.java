package Tests;

import Accounts.BusinessAccount;
import Accounts.IllegalAccountType;
import Accounts.SavingsAccount;
import Launchers.BankLauncher;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestBusiness {

    @Test
    public void test() throws IllegalAccountType {
        // Simulating Bank and Business Account Creation
        String bankSetup = "MetroBank\n12345678\nY\n50000.0\n50000.0\n200000.0\n10.0\n";
        String bankLogin = "MetroBank\n12345678\n";
        String businessAccountSetup = "2\n4\n";
        String businessAccountDetails = "30010-00001\n1234\nElon\nMusk\nelon@spacex.com\n50000.0\n";
        String logout = "5\n3\n";

        String input = bankSetup + bankLogin + businessAccountSetup + businessAccountDetails + logout;
        InputStream original = System.in;

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            BankLauncher.createNewBank();
            BankLauncher.bankLogin();

            BusinessAccount businessAcc = (BusinessAccount) BankLauncher.findAccount("30010-00001");

            Assert.assertNotNull(businessAcc);
            Assert.assertEquals(50000.0, businessAcc.getLoan(), 0.00001);

            // Ensure Business Credit Limit is Enforced
            Assert.assertFalse(businessAcc.canBusinessCredit(500000.0)); // Should fail
            Assert.assertTrue(businessAcc.canBusinessCredit(100000.0)); // Should work within limits

            // Test Business Payments
            SavingsAccount recipient = new SavingsAccount(businessAcc.getBank(), "30010-00002", "Jeff", "Bezos", "jeff@amazon.com", "5678", 1000.0);
            Assert.assertTrue(businessAcc.pay(recipient, 10000.0)); // Should work
            Assert.assertFalse(businessAcc.pay(recipient, 500000.0)); // Should fail due to limit

            // Test Recompense (Loan Payment)
            Assert.assertTrue(businessAcc.recompense(5000.0)); // Should work
            Assert.assertEquals(45000.0, businessAcc.getLoan(), 0.00001);

            // Check Transaction Logs
            int businessLogCount = businessAcc.getTransactionsInfo().split("\n").length;
            Assert.assertTrue(businessLogCount > 0);

        } finally {
            System.setIn(original);
        }
    }
}
