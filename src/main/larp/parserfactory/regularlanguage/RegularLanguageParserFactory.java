/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.regularlanguage;

import larp.automaton.DFA;
import larp.automaton.EpsilonNFA;
import larp.automaton.NFA;
import larp.grammarparser.regularlanguage.RegularExpressionGrammarParser;
import larp.grammartokenizer.regularlanguage.RegularExpressionGrammarTokenizer;
import larp.grammartokenizer.regularlanguage.RegularExpressionGrammarTokenizerException;
import larp.parser.regularlanguage.EpsilonNFAToNFAConverter;
import larp.parser.regularlanguage.NFAToDFAConverter;
import larp.parsercompiler.regularlanguage.RegularExpressionSyntaxCompiler;
import larp.parsetree.regularlanguage.RegularExpressionSyntaxNode;
import larp.token.regularlanguage.RegularExpressionSyntaxToken;

import java.util.List;

public class RegularLanguageParserFactory
{
    private RegularExpressionGrammarTokenizer tokenizer;
    private RegularExpressionGrammarParser parser;
    private RegularExpressionSyntaxCompiler compiler;
    private EpsilonNFAToNFAConverter epsilonNFAToNFAConverter;
    private NFAToDFAConverter NFAToDFAConverter;

    public RegularLanguageParserFactory()
    {
        this.tokenizer = new RegularExpressionGrammarTokenizer();
        this.parser = new RegularExpressionGrammarParser();
        this.compiler = new RegularExpressionSyntaxCompiler();
        this.epsilonNFAToNFAConverter = new EpsilonNFAToNFAConverter();
        this.NFAToDFAConverter = new NFAToDFAConverter();
    }

    public DFA factory(String regularExpression) throws RegularExpressionGrammarTokenizerException
    {
        List<RegularExpressionSyntaxToken> tokenList = this.tokenizer.tokenize(regularExpression);
        RegularExpressionSyntaxNode rootNode = this.parser.parse(tokenList);
        EpsilonNFA enfa = this.compiler.compile(rootNode);
        NFA nfa = this.epsilonNFAToNFAConverter.convert(enfa);

        return this.NFAToDFAConverter.convert(nfa);
    }
}
