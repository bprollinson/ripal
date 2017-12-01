import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.regularlanguage.CharacterToken;
import larp.grammar.regularlanguage.CloseBraceToken;
import larp.grammar.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.grammar.regularlanguage.OpenBraceToken;
import larp.grammar.regularlanguage.OrToken;
import larp.grammar.regularlanguage.KleeneClosureToken;
import larp.grammar.regularlanguage.RegularExpressionSyntaxToken;
import larp.grammar.regularlanguage.RegularExpressionSyntaxTokenizer;
import larp.grammar.regularlanguage.RegularExpressionSyntaxTokenizerException;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionSyntaxTokenizerTest
{
    @Test
    public void testTokenizerTokenizesEmptyString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        List<RegularExpressionSyntaxToken> result = tokenizer.tokenize("");
        List<RegularExpressionSyntaxToken> expectedResult = new ArrayList();

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerTokenizesString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        List<RegularExpressionSyntaxToken> result = tokenizer.tokenize("(a|b)*");
        List<RegularExpressionSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new OpenBraceToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('b'));
        expectedResult.add(new CloseBraceToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizerThrowsExceptionForNegativeBracketNesting() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize(")(");
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizerThrowsExceptionForUnclosedBracketAtEndOfString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize("(");
    }

    @Test(expected = RegularExpressionSyntaxTokenizerException.class)
    public void testTokenizerThrowsExceptionForIncorrecTokenSequence() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize("*");
    }
}
