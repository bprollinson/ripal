/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.regularlanguage;

import larp.parser.regularlanguage.DFA;
import larp.parser.regularlanguage.EpsilonNFA;
import larp.parser.regularlanguage.EpsilonNFAToNFAConverter;
import larp.parser.regularlanguage.NFA;
import larp.parser.regularlanguage.NFAToDFAConverter;
import larp.parsetree.regularlanguage.RegularExpressionSyntaxNode;
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
    private EpsilonNFAToNFAConverter epsilonNFAToNFAConverter;
    private NFAToDFAConverter NFAToDFAConverter;

    public RegularLanguageParserFactory()
    {
        this.tokenizer = new RegularExpressionSyntaxTokenizer();
        this.parser = new RegularExpressionSyntaxParser();
        this.compiler = new RegularExpressionSyntaxCompiler();
        this.epsilonNFAToNFAConverter = new EpsilonNFAToNFAConverter();
        this.NFAToDFAConverter = new NFAToDFAConverter();
    }

    public DFA factory(String regularExpression) throws RegularExpressionSyntaxTokenizerException
    {
        List<RegularExpressionSyntaxToken> tokenList = this.tokenizer.tokenize(regularExpression);
        RegularExpressionSyntaxNode rootNode = this.parser.parse(tokenList);
        EpsilonNFA enfa = this.compiler.compile(rootNode);
        NFA nfa = this.epsilonNFAToNFAConverter.convert(enfa);

        return this.NFAToDFAConverter.convert(nfa);
    }
}
