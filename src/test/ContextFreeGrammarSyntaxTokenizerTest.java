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
    public void testTokenizerTokenizesSimpleTerminalProduction()
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        Vector<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"a\"");
        Vector<ContextFreeGrammarSyntaxToken> expectedResult = new Vector();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }
}
