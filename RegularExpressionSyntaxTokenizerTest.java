import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.CharacterToken;
import larp.grammar.CloseBraceToken;
import larp.grammar.IncorrectRegularExpressionNestingException;
import larp.grammar.OpenBraceToken;
import larp.grammar.OrToken;
import larp.grammar.KleeneClosureToken;
import larp.grammar.RegularExpressionSyntaxToken;
import larp.grammar.RegularExpressionSyntaxTokenizer;
import larp.grammar.RegularExpressionSyntaxTokenizerException;

import java.util.Vector;

public class RegularExpressionSyntaxTokenizerTest
{
    @Test
    public void testTokenizerTokenizesString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize("(a|b)*");
        Vector<RegularExpressionSyntaxToken> expectedResult = new Vector();
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

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize(")(");
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizerThrowsExceptionForUnclosedBracketAtEndOfString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize("(");
    }

    @Test(expected = RegularExpressionSyntaxTokenizerException.class)
    public void testTokenizerThrowsExceptionForIncorrecTokenSequence() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize("*");
    }
}
