import org.junit.Test;

import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarFinalQuoteNestingCorrectAssertion;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarQuoteNestingException;

public class ContextFreeGrammarFinalQuoteNestingCorrectAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testValidateThrowsExceptionWhenInTerminal() throws IncorrectContextFreeGrammarQuoteNestingException
    {
        ContextFreeGrammarFinalQuoteNestingCorrectAssertion assertion = new ContextFreeGrammarFinalQuoteNestingCorrectAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotInTerminal() throws IncorrectContextFreeGrammarQuoteNestingException
    {
        ContextFreeGrammarFinalQuoteNestingCorrectAssertion assertion = new ContextFreeGrammarFinalQuoteNestingCorrectAssertion(false);

        assertion.validate();
    }
}
