import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.CharacterToken;
import larp.grammar.CloseBraceToken;
import larp.grammar.IncorrectCloseBraceApplicationException;
import larp.grammar.IncorrectKleeneClosureApplicationException;
import larp.grammar.IncorrectOrApplicationException;
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
    public void testTokenizerTokenizesSimpleString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize("test");
        Vector<RegularExpressionSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new CharacterToken('t'));
        expectedResult.add(new CharacterToken('e'));
        expectedResult.add(new CharacterToken('s'));
        expectedResult.add(new CharacterToken('t'));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizerThrowsExceptionForUnclosedBracketAtEndOfString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize("(");
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizerThrowsExceptionForNegativeBracketNesting() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector<RegularExpressionSyntaxToken> result = tokenizer.tokenize(")(");
    }

    @Test
    public void testTokenizerTokenizesKleeneClosureFollowingCharacter() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector result =  tokenizer.tokenize("a*");
        Vector expectedResult = new Vector();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerTokenizesKleeneClosureFollowingCloseBracket() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector result = tokenizer.tokenize("(a)*");
        Vector expectedResult = new Vector();
        expectedResult.add(new OpenBraceToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CloseBraceToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectKleeneClosureApplicationException.class)
    public void testTokenizerThrowsExceptionForKleeneClosureFollowingOpenBracket() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize("(*)");
    }

    @Test(expected = IncorrectKleeneClosureApplicationException.class)
    public void testTokenizerThrowsExceptionForKleeneClosureAtStartOfString() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize("*");
    }

    @Test
    public void testTokenizerTokenizesOr() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        Vector result = tokenizer.tokenize("a|b");
        Vector expectedResult = new Vector();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('b'));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectOrApplicationException.class)
    public void testTokenizerThrowsExceptionForOrFollowingOpenBracket() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize("(|a)");
    }

    @Test(expected = IncorrectCloseBraceApplicationException.class)
    public void testTokenizerThrowsExceptionForCloseBracketFollowingOr() throws RegularExpressionSyntaxTokenizerException
    {
        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();

        tokenizer.tokenize("(a|)");
    }
}
