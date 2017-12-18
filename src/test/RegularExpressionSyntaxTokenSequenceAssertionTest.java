import org.junit.Test;

import larp.tokenizer.regularlanguage.IncorrectCloseBraceApplicationException;
import larp.tokenizer.regularlanguage.IncorrectKleeneClosureApplicationException;
import larp.tokenizer.regularlanguage.IncorrectOrApplicationException;
import larp.tokenizer.regularlanguage.RegularExpressionSyntaxTokenizerException;
import larp.tokenizer.regularlanguage.RegularExpressionSyntaxTokenSequenceAssertion;

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
