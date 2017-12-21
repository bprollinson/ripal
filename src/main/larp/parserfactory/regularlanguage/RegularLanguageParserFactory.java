package larp.parserfactory.regularlanguage;

import larp.parser.regularlanguage.DFA;
import larp.syntaxcompiler.regularlanguage.RegularExpressionSyntaxCompiler;
import larp.syntaxparser.regularlanguage.RegularExpressionSyntaxParser;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenizer;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenizerException;
import larp.token.regularlanguage.RegularExpressionSyntaxToken;

import java.util.List;

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

    public DFA factory(String regularExpression) throws RegularExpressionSyntaxTokenizerException
    {
        List<RegularExpressionSyntaxToken> tokenList = this.tokenizer.tokenize(regularExpression);

        return null;
    }
}
