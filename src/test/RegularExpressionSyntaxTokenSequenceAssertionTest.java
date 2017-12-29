import org.junit.Test;

import larp.syntaxtokenizer.regularlanguage.IncorrectCloseBraceApplicationException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenizerException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenSequenceAssertion;

public class RegularExpressionSyntaxTokenSequenceAssertionTest
{
    @Test(expected = IncorrectCloseBraceApplicationException.class)
    public void testValidateThrowsExceptionForCloseBraceAfterOr() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion(')', '|');

        assertion.validate('|', ')');
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion(')', '|');

        assertion.validate('a', 'b');
    }
}
