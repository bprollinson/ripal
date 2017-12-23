package larp.parserfactory.contextfreelanguage;

import larp.parser.contextfreelanguage.LL1Parser;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.syntaxparser.contextfreelanguage.ContextFreeGrammarSyntaxParser;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;

import java.util.List;

public class ContextFreeLanguageParserFactory
{
    private ContextFreeGrammarSyntaxTokenizer tokenizer;
    private ContextFreeGrammarSyntaxParser parser;

    public ContextFreeLanguageParserFactory()
    {
        this.tokenizer = new ContextFreeGrammarSyntaxTokenizer();
        this.parser = new ContextFreeGrammarSyntaxParser();
    }

    public LL1Parser factory(List<String> input) throws ContextFreeGrammarSyntaxTokenizerException
    {
        for (String inputString: input)
        {
            List<ContextFreeGrammarSyntaxToken> tokenList = this.tokenizer.tokenize(inputString);
            ContextFreeGrammarSyntaxNode rootNode = this.parser.parse(tokenList);
        }

        return null;
    }
}
