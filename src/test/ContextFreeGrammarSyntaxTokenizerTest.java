import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;

public class ContextFreeGrammarSyntaxTokenizerTest
{
    @Test
    public void testTokenizerTokenizesString()
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S: S \"+\" S");
    }
}
