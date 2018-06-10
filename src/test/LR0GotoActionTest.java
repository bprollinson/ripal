import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LR0GotoActionTest
{
    @Test
    public void testEqualsReturnsTrueForGotoActionWithSameStateIndex()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForGotoActionWithDifferentStateIndex()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForActionOfOtherType()
    {
        assertEquals(0, 1);
    }
}
