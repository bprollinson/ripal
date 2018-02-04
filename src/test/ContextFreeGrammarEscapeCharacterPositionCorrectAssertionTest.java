import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ContextFreeGrammarEscapeCharacterPositionCorrectAssertionTest
{
    @Test
    public void testValidateThrowsExceptionWhenNotInTerminal()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenInTerminal()
    {
        assertEquals(0, 1);
    }
}
