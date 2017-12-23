package larp.parserfactory.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarLL1SyntaxCompiler;
import larp.syntaxparser.contextfreelanguage.ContextFreeGrammarSyntaxParser;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;

import java.util.List;

public class ContextFreeLanguageParserFactory
{
    private ContextFreeGrammarSyntaxTokenizer tokenizer;
    private ContextFreeGrammarSyntaxParser parser;
    private ContextFreeGrammarLL1SyntaxCompiler compiler;

    public ContextFreeLanguageParserFactory()
    {
        this.tokenizer = new ContextFreeGrammarSyntaxTokenizer();
        this.parser = new ContextFreeGrammarSyntaxParser();
        this.compiler = new ContextFreeGrammarLL1SyntaxCompiler();
    }

    public LL1Parser factory(List<String> input) throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        for (String inputString: input)
        {
            List<ContextFreeGrammarSyntaxToken> tokenList = this.tokenizer.tokenize(inputString);
            ContextFreeGrammarSyntaxNode rootNode = this.parser.parse(tokenList);
            cfg.addProduction(rootNode);
        }

        LL1ParseTable parseTable = this.compiler.compile(cfg);

        return null;
    }
}
