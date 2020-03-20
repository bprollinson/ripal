/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parserfactory.regularlanguage;

import ripal.automaton.DFA;
import ripal.automaton.EpsilonNFA;
import ripal.automaton.NFA;
import ripal.grammarparser.regularlanguage.GrammarParser;
import ripal.grammartokenizer.regularlanguage.Tokenizer;
import ripal.grammartokenizer.regularlanguage.TokenizerException;
import ripal.parsercompiler.regularlanguage.EpsilonNFAToNFAConverter;
import ripal.parsercompiler.regularlanguage.NFAToDFAConverter;
import ripal.parsercompiler.regularlanguage.ParserCompiler;
import ripal.parsetree.regularlanguage.Node;
import ripal.token.regularlanguage.Token;

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
