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
import larp.parsercompiler.regularlanguage.EpsilonNFAToNFAConverter;
import larp.parsercompiler.regularlanguage.NFAToDFAConverter;
import larp.parsercompiler.regularlanguage.RegularExpressionParserCompiler;
import larp.parsetree.regularlanguage.RegularExpressionParseTreeNode;
import larp.token.regularlanguage.RegularExpressionGrammarToken;

import java.util.List;

public class ParserFactory
{
    private RegularExpressionGrammarTokenizer tokenizer;
    private RegularExpressionGrammarParser parser;
    private RegularExpressionParserCompiler compiler;
    private EpsilonNFAToNFAConverter epsilonNFAToNFAConverter;
    private NFAToDFAConverter NFAToDFAConverter;

    public ParserFactory()
    {
        this.tokenizer = new RegularExpressionGrammarTokenizer();
        this.parser = new RegularExpressionGrammarParser();
        this.compiler = new RegularExpressionParserCompiler();
        this.epsilonNFAToNFAConverter = new EpsilonNFAToNFAConverter();
        this.NFAToDFAConverter = new NFAToDFAConverter();
    }

    public DFA factory(String regularExpression) throws RegularExpressionGrammarTokenizerException
    {
        List<RegularExpressionGrammarToken> tokenList = this.tokenizer.tokenize(regularExpression);
        RegularExpressionParseTreeNode rootNode = this.parser.parse(tokenList);
        EpsilonNFA enfa = this.compiler.compile(rootNode);
        NFA nfa = this.epsilonNFAToNFAConverter.convert(enfa);

        return this.NFAToDFAConverter.convert(nfa);
    }
}
