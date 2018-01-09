import org.junit.Test;

import larp.syntaxtokenizer.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionIntermediateNestingLevelValidAssertion;

public class RegularExpressionIntermediateNestingLevelValidAssertionTest
{
    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionIntermediateNestingLevelValidAssertion assertion = new RegularExpressionIntermediateNestingLevelValidAssertion(-1);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionIntermediateNestingLevelValidAssertion assertion = new RegularExpressionIntermediateNestingLevelValidAssertion(0);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForPositiveNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionIntermediateNestingLevelValidAssertion assertion = new RegularExpressionIntermediateNestingLevelValidAssertion(1);

        assertion.validate();
    }
}
