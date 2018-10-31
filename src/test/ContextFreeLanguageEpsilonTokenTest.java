import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.TerminalToken;

public class ContextFreeLanguageEpsilonTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertEquals(new EpsilonToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertNotEquals(new TerminalToken("a"), token);
    }
}
