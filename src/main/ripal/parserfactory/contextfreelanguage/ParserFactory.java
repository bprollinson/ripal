/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parserfactory.contextfreelanguage;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.grammarparser.contextfreelanguage.GrammarParser;
import ripal.grammartokenizer.contextfreelanguage.Tokenizer;
import ripal.grammartokenizer.contextfreelanguage.TokenizerException;
import ripal.parser.contextfreelanguage.AmbiguousParseTableException;
import ripal.parser.contextfreelanguage.LL1Parser;
import ripal.parser.contextfreelanguage.LL1ParseTable;
import ripal.parser.contextfreelanguage.LR0Parser;
import ripal.parser.contextfreelanguage.LR0ParseTable;
import ripal.parser.contextfreelanguage.LR1Parser;
import ripal.parser.contextfreelanguage.Parser;
import ripal.parser.contextfreelanguage.SLR1Parser;
import ripal.parsercompiler.contextfreelanguage.LL1ParserCompiler;
import ripal.parsercompiler.contextfreelanguage.LR0ParserCompiler;
import ripal.parsercompiler.contextfreelanguage.LR1ParserCompiler;
import ripal.parsercompiler.contextfreelanguage.SLR1ParserCompiler;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.token.contextfreelanguage.Token;

import java.util.List;

public class ParserFactory
{
    private Tokenizer tokenizer;
    private GrammarParser grammarParser;
    private LL1ParserCompiler ll1compiler;
    private LR0ParserCompiler lr0compiler;
    private SLR1ParserCompiler slr1compiler;
    private LR1ParserCompiler lr1compiler;

    public ParserFactory()
    {
        this.tokenizer = new Tokenizer();
        this.grammarParser = new GrammarParser();
        this.ll1compiler = new LL1ParserCompiler();
        this.lr0compiler = new LR0ParserCompiler();
        this.slr1compiler = new SLR1ParserCompiler();
        this.lr1compiler = new LR1ParserCompiler();
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

        try
        {
            return this.buildLR0Parser(grammar);
        }
        catch (AmbiguousParseTableException apte)
        {
        }

        try
        {
            return this.buildSLR1Parser(grammar);
        }
        catch (AmbiguousParseTableException apte)
        {
        }

        return this.buildLR1Parser(grammar);
    }

    private Parser buildLL1Parser(Grammar grammar) throws AmbiguousParseTableException
    {
        LL1ParseTable parseTable = this.ll1compiler.compile(grammar);

        return new LL1Parser(parseTable);
    }

    private Parser buildLR0Parser(Grammar grammar) throws AmbiguousParseTableException
    {
        LR0ParseTable parseTable = this.lr0compiler.compile(grammar);

        return new LR0Parser(parseTable);
    }

    private Parser buildSLR1Parser(Grammar grammar) throws AmbiguousParseTableException
    {
        LR0ParseTable parseTable = this.slr1compiler.compile(grammar);

        return new SLR1Parser(parseTable);
    }

    private Parser buildLR1Parser(Grammar grammar) throws AmbiguousParseTableException
    {
        LR0ParseTable parseTable = this.lr1compiler.compile(grammar);

        return new LR1Parser(parseTable);
    }
}
