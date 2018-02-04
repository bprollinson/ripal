import org.junit.Test;

import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarEscapeCharacterPositionCorrectAssertion;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarEscapeCharacterPositionException;

public class ContextFreeGrammarEscapeCharacterPositionCorrectAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarEscapeCharacterPositionException.class)
    public void testValidateThrowsExceptionWhenNotInTerminal() throws IncorrectContextFreeGrammarEscapeCharacterPositionException
    {
        ContextFreeGrammarEscapeCharacterPositionCorrectAssertion assertion = new ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(false);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenInTerminal() throws IncorrectContextFreeGrammarEscapeCharacterPositionException
    {
        ContextFreeGrammarEscapeCharacterPositionCorrectAssertion assertion = new ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(true);

        assertion.validate();
    }
}
