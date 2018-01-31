import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarQuoteNestingException;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarSeparatorException;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarSyntaxTokenizerTest
{
    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterNonTerminalProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnLeftSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("Start:S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("Start"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnRightSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:Start");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("Start"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEpsilonProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterTerminalProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"a\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterTerminalOnRightSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultipleTerminalsOnRightSideWithoutSpaceBetweenThem() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\"\"terminal2\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenTerminalTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\" \"terminal2\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesSpaceWithinTerminalToken() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenNonTerminalTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:nonterminal1 nonterminal2");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("nonterminal1"));
        expectedResult.add(new NonTerminalToken("nonterminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesTerminalFollowedDirectlyByNonterminal() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenBeforeEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenBeforeEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenAfterEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"\"terminal 1\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenAfterEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingMultipleEpsilonTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingTerminalAndMultipleEpsilonTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingNonTerminalAndMultipleEpsilonTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testTokenizeThrowsExceptionForIncorrecTokenSequence() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("");
    }

    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfString() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S:\"a");
    }

    @Test(expected = IncorrectContextFreeGrammarSeparatorException.class)
    public void testTokenizeThrowsExceptionForIncorrectNumberofSeparators() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S::");
    }

    @Test
    public void testTokenizeTokenizesColonWithinTerminalString() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\":\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken(":"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEscapedSpecialCharactersWithinTerminalString() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\\\"\\\\\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("\""));
        expectedResult.add(new TerminalToken("\\"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeGeneratesStandardCharacterTokenFromCharacterAfterEscapeCharacter() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\\a\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }
}
