/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.grammarparser.contextfreelanguage.ContextFreeGrammarParser;
import larp.grammartokenizer.contextfreelanguage.Tokenizer;
import larp.grammartokenizer.contextfreelanguage.TokenizerException;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.Parser;
import larp.parsercompiler.contextfreelanguage.LL1ParserCompiler;
import larp.parsercompiler.contextfreelanguage.LR0ParserCompiler;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.token.contextfreelanguage.ContextFreeGrammarToken;

import java.util.List;

public class ParserFactory
{
    private Tokenizer tokenizer;
    private ContextFreeGrammarParser parser;
    private LL1ParserCompiler ll1compiler;
    private LR0ParserCompiler lr0compiler;

    public ParserFactory()
    {
        this.tokenizer = new Tokenizer();
        this.parser = new ContextFreeGrammarParser();
        this.ll1compiler = new LL1ParserCompiler();
        this.lr0compiler = new LR0ParserCompiler();
    }

    public Parser factory(List<String> input) throws TokenizerException, AmbiguousParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        for (String inputString: input)
        {
            List<ContextFreeGrammarToken> tokenList = this.tokenizer.tokenize(inputString);
            ContextFreeGrammarParseTreeNode rootNode = this.parser.parse(tokenList);
            grammar.addProduction(rootNode);
        }

        try
        {
            LL1ParseTable parseTable = this.ll1compiler.compile(grammar);

            return new LL1Parser(parseTable);
        }
        catch (AmbiguousLL1ParseTableException apte)
        {
            LR0ParseTable parseTable = this.lr0compiler.compile(grammar);

            return new LR0Parser(parseTable);
        }
    }
}
