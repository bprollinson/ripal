import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NonTerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNonTerminalNodeWithSameName()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForNonTerminalNodeWithDifferentCharacter()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        assertEquals(0, 1);
    }
}
