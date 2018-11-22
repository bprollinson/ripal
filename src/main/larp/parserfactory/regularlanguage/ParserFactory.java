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
import larp.grammarparser.regularlanguage.GrammarParser;
import larp.grammartokenizer.regularlanguage.Tokenizer;
import larp.grammartokenizer.regularlanguage.TokenizerException;
import larp.parsercompiler.regularlanguage.EpsilonNFAToNFAConverter;
import larp.parsercompiler.regularlanguage.NFAToDFAConverter;
import larp.parsercompiler.regularlanguage.ParserCompiler;
import larp.parsetree.regularlanguage.Node;
import larp.token.regularlanguage.Token;

import java.util.List;

public class ParserFactory
{
    private Tokenizer tokenizer;
    private GrammarParser grammarParser;
    private ParserCompiler compiler;
    private EpsilonNFAToNFAConverter epsilonNFAToNFAConverter;
    private NFAToDFAConverter NFAToDFAConverter;

    public ParserFactory()
    {
        this.tokenizer = new Tokenizer();
        this.grammarParser = new GrammarParser();
        this.compiler = new ParserCompiler();
        this.epsilonNFAToNFAConverter = new EpsilonNFAToNFAConverter();
        this.NFAToDFAConverter = new NFAToDFAConverter();
    }

    public DFA factory(String regularExpression) throws TokenizerException
    {
        List<Token> tokenList = this.tokenizer.tokenize(regularExpression);
        Node rootNode = this.grammarParser.parse(tokenList);
        EpsilonNFA enfa = this.compiler.compile(rootNode);
        NFA nfa = this.epsilonNFAToNFAConverter.convert(enfa);

        return this.NFAToDFAConverter.convert(nfa);
    }
}
