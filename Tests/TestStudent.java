package Tests;

import Accounts.IllegalAccountType;
import Accounts.SavingsAccount;
import Accounts.StudentAccount;
import Launchers.BankLauncher;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestStudent {

    @Test
    public void test() throws IllegalAccountType {
        // Simulating Bank and Student Account Creation
        String bankSetup = "MetroBank\n12345678\nY\n50000.0\n50000.0\n200000.0\n10.0\n";
        String bankLogin = "MetroBank\n12345678\n";
        String studentAccountSetup = "2\n3\n";
        String studentAccountDetails = "30010-00003\n4321\nJohn\nDoe\njohn.doe@gmail.com\n1000.0\n";
        String logout = "5\n3\n";

        String input = bankSetup + bankLogin + studentAccountSetup + studentAccountDetails + logout;
        InputStream original = System.in;

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            BankLauncher.createNewBank();
            BankLauncher.bankLogin();

            StudentAccount studentAcc = (StudentAccount) BankLauncher.findAccount("30010-00003");

            Assert.assertNotNull(studentAcc);
            Assert.assertEquals(1000.0, studentAcc.getAccountBalance(), 0.00001);

            // Testing Withdraw Limit
            Assert.assertFalse(studentAcc.withdrawal(2000.0)); // Should fail
            Assert.assertTrue(studentAcc.withdrawal(500.0)); // Should succeed
            Assert.assertEquals(500.0, studentAcc.getAccountBalance(), 0.00001);

            // Testing Fund Transfer Restrictions
            SavingsAccount recipient = new SavingsAccount(studentAcc.getBank(), "30010-00004", "Jane", "Smith", "jane.smith@gmail.com", "5678", 1000.0);
            Assert.assertTrue(studentAcc.transfer(recipient, 500.0)); // Should work
            Assert.assertFalse(studentAcc.transfer(recipient, 1500.0)); // Should fail

            // Check Transaction Logs
            int studentLogCount = studentAcc.getTransactionsInfo().split("\n").length;
            Assert.assertTrue(studentLogCount > 0);

        } finally {
            System.setIn(original);
        }
    }
}
