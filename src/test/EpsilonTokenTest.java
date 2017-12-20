import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.TerminalToken;

public class EpsilonTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertTrue(token.equals(new EpsilonToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertFalse(token.equals(new TerminalToken("a")));
    }
}
