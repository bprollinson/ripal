import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.grammar.contextfreelanguage.EpsilonToken;
import larp.grammar.contextfreelanguage.IncorrectContextFreeGrammarQuoteNestingException;
import larp.grammar.contextfreelanguage.IncorrectContextFreeGrammarSeparatorException;
import larp.grammar.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;
import larp.grammar.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarSyntaxTokenizerTest
{
    @Test
    public void testTokenizerTokenizesSimpleSingleCharacterNonTerminalProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultiCharacterNonTerminalOnLeftSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("Start:S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("Start"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultiCharacterNonTerminalOnRightSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:Start");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("Start"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerTokenizesEpsilonProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerTokenizesSimpleSingleCharacterTerminalProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"a\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultiCharacterTerminalOnRightSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultipleTerminalsOnRightSideWithoutSpaceBetweenThem() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\"\"terminal2\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerIgnoresSpaceBetweenTerminalTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\" \"terminal2\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesSpaceWithinTerminalToken() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerIgnoresSpaceBetweenNonTerminalTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:nonterminal1 nonterminal2");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("nonterminal1"));
        expectedResult.add(new NonTerminalToken("nonterminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesTerminalFollowedDirectlyByNonterminal() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenBeforeEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenBeforeEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenAfterEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"\"terminal 1\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenAfterEpsilon() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesExtraEpsilonFromRightHandSideContainingMultipleEpsilonTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesExtraEpsilonFromRightHandSideContainingTerminalAndMultipleEpsilonTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerRemovesExtraEpsilonFromRightHandSideContainingNonTerminalAndMultipleEpsilonTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testTokenizerThrowsExceptionForIncorrecTokenSequence() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("");
    }

    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testTokenizerThrowsExceptionForUnclosedQuoteAtEndOfString() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S:\"a");
    }

    @Test(expected = IncorrectContextFreeGrammarSeparatorException.class)
    public void testTokenizerThrowsExceptionForIncorrectNumberofSeparators() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S::");
    }
}
