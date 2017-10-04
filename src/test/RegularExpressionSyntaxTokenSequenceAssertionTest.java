import org.junit.Test;

import larp.grammar.regularlanguage.IncorrectCloseBraceApplicationException;
import larp.grammar.regularlanguage.IncorrectKleeneClosureApplicationException;
import larp.grammar.regularlanguage.IncorrectOrApplicationException;
import larp.grammar.regularlanguage.RegularExpressionSyntaxTokenizerException;
import larp.grammar.regularlanguage.RegularExpressionSyntaxTokenSequenceAssertion;

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

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenSequenceAssertion assertion = new RegularExpressionSyntaxTokenSequenceAssertion('(', ')', '*', '|');

        assertion.validate('a', 'b');
    }
}
