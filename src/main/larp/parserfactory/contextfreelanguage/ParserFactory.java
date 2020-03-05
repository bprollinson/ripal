/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.contextfreelanguage;

import larp.grammar.contextfreelanguage.Grammar;
import larp.grammarparser.contextfreelanguage.GrammarParser;
import larp.grammartokenizer.contextfreelanguage.Tokenizer;
import larp.grammartokenizer.contextfreelanguage.TokenizerException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.Parser;
import larp.parsercompiler.contextfreelanguage.LL1ParserCompiler;
import larp.parsercompiler.contextfreelanguage.SLR1ParserCompiler;
import larp.parsetree.contextfreelanguage.Node;
import larp.token.contextfreelanguage.Token;

import java.util.List;

public class ParserFactory
{
    private Tokenizer tokenizer;
    private GrammarParser grammarParser;
    private LL1ParserCompiler ll1compiler;
    private SLR1ParserCompiler slr1compiler;

    public ParserFactory()
    {
        this.tokenizer = new Tokenizer();
        this.grammarParser = new GrammarParser();
        this.ll1compiler = new LL1ParserCompiler();
        this.slr1compiler = new SLR1ParserCompiler();
    }

    public Parser factory(List<String> input) throws TokenizerException, AmbiguousParseTableException
    {
        Grammar grammar = this.buildGrammar(input);

        return this.buildParser(grammar);
    }

    private Grammar buildGrammar(List<String> input) throws TokenizerException
    {
        Grammar grammar = new Grammar();
        for (String inputString: input)
        {
            List<Token> tokenList = this.tokenizer.tokenize(inputString);
            Node rootNode = this.grammarParser.parse(tokenList);
            grammar.addProduction(rootNode);
        }

        return grammar;
    }

    private Parser buildParser(Grammar grammar) throws AmbiguousParseTableException
    {
        try
        {
            return this.buildLL1Parser(grammar);
        }
        catch (AmbiguousParseTableException apte)
        {
        }

        return this.buildSLR1Parser(grammar);
    }

    private Parser buildLL1Parser(Grammar grammar) throws AmbiguousParseTableException
    {
        LL1ParseTable parseTable = this.ll1compiler.compile(grammar);

        return new LL1Parser(parseTable);
    }

    private Parser buildSLR1Parser(Grammar grammar) throws AmbiguousParseTableException
    {
        LR0ParseTable parseTable = this.slr1compiler.compile(grammar);

        return new LR0Parser(parseTable);
    }
}
