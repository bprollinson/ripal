import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.grammar.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;
import larp.grammar.contextfreelanguage.TerminalToken;

import java.util.Vector;

public class ContextFreeGrammarSyntaxTokenizerTest
{
    @Test
    public void testTokenizerTokenizesSimpleSingleCharacterNonTerminalProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultiCharacterNonTerminalOnLeftSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("Start:S");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("Start"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultiCharacterNonTerminalOnRightSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:Start");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("Start"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerTokenizesSimpleSingleCharacterTerminalProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"a\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultiCharacterTerminalOnRightSideOfProduction() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerHandlesMultipleTerminalsOnRightSideWithoutSpaceBetweenThem() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\"\"terminal2\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
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

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\" \"terminal2\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
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

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizerIgnoresSpaceBetweenNonTerminalTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:nonterminal1 nonterminal2");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("nonterminal1"));
        expectedResult.add(new NonTerminalToken("nonterminal2"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testTokenizerThrowsExceptionForFewerThanTwoTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("");
    }

    @Test
    public void testTokenizerThrowsExceptionWhenFirstTokenIsNotANonterminal() throws ContextFreeGrammarSyntaxTokenizerException
    {
        assertEquals(true, false);
    }

    @Test
    public void testTokenizerThrowsExceptionWhenSecondTokenIsNotASeparator() throws ContextFreeGrammarSyntaxTokenizerException
    {
        assertEquals(true, false);
    }

    @Test
    public void testTokenizerThrowsExceptionForUnclosedQuoteAtEndOfString() throws ContextFreeGrammarSyntaxTokenizerException
    {
        assertEquals(true, false);
    }
}
