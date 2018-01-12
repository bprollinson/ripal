import org.junit.Test;

import larp.syntaxtokenizer.regularlanguage.DanglingRegularExpressionEscapeCharacterException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionFinalEscapingStatusValidAssertion;

public class RegularExpressionFinalEscapingStatusValidAssertionTest
{
    @Test(expected = DanglingRegularExpressionEscapeCharacterException.class)
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingRegularExpressionEscapeCharacterException
    {
        RegularExpressionFinalEscapingStatusValidAssertion assertion = new RegularExpressionFinalEscapingStatusValidAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingRegularExpressionEscapeCharacterException
    {
        RegularExpressionFinalEscapingStatusValidAssertion assertion = new RegularExpressionFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
