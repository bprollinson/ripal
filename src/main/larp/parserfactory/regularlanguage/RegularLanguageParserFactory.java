package larp.parserfactory.regularlanguage;

import larp.parser.regularlanguage.DFA;
import larp.syntaxcompiler.regularlanguage.RegularExpressionSyntaxCompiler;
import larp.syntaxparser.regularlanguage.RegularExpressionSyntaxParser;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenizer;

public class RegularLanguageParserFactory
{
    private RegularExpressionSyntaxTokenizer tokenizer;
    private RegularExpressionSyntaxParser parser;
    private RegularExpressionSyntaxCompiler compiler;

    public RegularLanguageParserFactory()
    {
        this.tokenizer = new RegularExpressionSyntaxTokenizer();
        this.parser = new RegularExpressionSyntaxParser();
        this.compiler = new RegularExpressionSyntaxCompiler();
    }

    public DFA factory(String regularExpression)
    {
        return null;
    }
}
