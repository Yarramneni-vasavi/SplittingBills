import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SplitBillsTest {

    @Test
    public void testAmountEntered()
    {
        assertEquals(true, SplittingBills.get_Amount(100));
    }

    @Test
    public void testPersonEntered()
    {
        SplittingBills.total_num_persons = 4;
        Assert.assertTrue("true", SplittingBills.get_personNumber(2) >= 0 && SplittingBills.get_personNumber(2) <= SplittingBills.total_num_persons);
    }
}
