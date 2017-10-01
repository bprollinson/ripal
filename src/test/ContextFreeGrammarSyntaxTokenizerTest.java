import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;
import larp.grammar.contextfreelanguage.TerminalToken;

import java.util.Vector;

public class ContextFreeGrammarSyntaxTokenizerTest
{
    @Test
    public void testTokenizerTokenizesSimpleSingleCharacterNonTerminalProduction()
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
    public void testTokenizerHandlesMultiCharacterNonTerminalOnLeftSideOfProduction()
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
    public void testTokenizerHandlesMultiCharacterNonTerminalOnRightSideOfProduction()
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
    public void testTokenizerTokenizesSimpleSingleCharacterTerminalProduction()
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
    public void testTokenizerHandlesMultiCharacterTerminalOnRightSideOfProduction()
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }
}
