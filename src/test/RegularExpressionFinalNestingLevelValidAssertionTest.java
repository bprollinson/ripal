import org.junit.Test;

import larp.syntaxtokenizer.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionFinalNestingLevelValidAssertion;

public class RegularExpressionFinalNestingLevelValidAssertionTest
{
    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testValidateThrowsExceptionForPositiveNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(1);

        assertion.validate();
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(-1);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(0);

        assertion.validate();
    }
}
