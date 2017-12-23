package larp.parserfactory.contextfreelanguage;

import larp.parser.contextfreelanguage.LL1Parser;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;

import java.util.List;

public class ContextFreeLanguageParserFactory
{
    private ContextFreeGrammarSyntaxTokenizer tokenizer;

    public ContextFreeLanguageParserFactory()
    {
        this.tokenizer = new ContextFreeGrammarSyntaxTokenizer();
    }

    public LL1Parser factory(List<String> input) throws ContextFreeGrammarSyntaxTokenizerException
    {
        for (String inputString: input)
        {
            List<ContextFreeGrammarSyntaxToken> tokenList = this.tokenizer.tokenize(inputString);
        }

        return null;
    }
}
