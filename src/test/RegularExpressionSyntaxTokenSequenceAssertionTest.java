import org.junit.Test;

import larp.grammar.IncorrectCloseBraceApplicationException;
import larp.grammar.IncorrectKleeneClosureApplicationException;
import larp.grammar.IncorrectOrApplicationException;
import larp.grammar.RegularExpressionSyntaxTokenizerException;
import larp.grammar.RegularExpressionSyntaxTokenSequenceAssertion;

public class RegularExpressionSyntaxTokenSequenceAssertionTest
{
    @Test(expected = IncorrectKleeneClosureApplicationException.class)
    public void testValidateThrowsExceptionForKleeneClosureAtStartOfString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion('(', ')', '*', '|');

        assertion.validate(null, '*');
    }

    @Test(expected = IncorrectKleeneClosureApplicationException.class)
    public void testValidateThrowsExceptionForKleeneClosureAfterOpenBracket() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion('(', ')', '*', '|');

        assertion.validate('(', '*');
    }

    @Test(expected = IncorrectOrApplicationException.class)
    public void testValidateThrowsExceptionForOrAtStartOfString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion('(', ')', '*', '|');

        assertion.validate(null, '|');
    }

    @Test(expected = IncorrectOrApplicationException.class)
    public void testValidateThrowsExceptionForOrAfterOpenBracket() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion('(', ')', '*', '|');

        assertion.validate('(', '|');
    }

    @Test(expected = IncorrectCloseBraceApplicationException.class)
    public void testValidateThrowsExceptionForCloseBraceAfterOr() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion('(', ')', '*', '|');

        assertion.validate('|', ')');
    }
}
