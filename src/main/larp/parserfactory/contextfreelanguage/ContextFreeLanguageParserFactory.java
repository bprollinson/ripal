/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.grammarparser.contextfreelanguage.ContextFreeGrammarParser;
import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarTokenizer;
import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarTokenizerException;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.ContextFreeLanguageParser;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parsercompiler.contextfreelanguage.ContextFreeGrammarLL1ParserCompiler;
import larp.parsercompiler.contextfreelanguage.ContextFreeGrammarLR0ParserCompiler;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;

import java.util.List;

public class ContextFreeLanguageParserFactory
{
    private ContextFreeGrammarTokenizer tokenizer;
    private ContextFreeGrammarParser parser;
    private ContextFreeGrammarLL1ParserCompiler ll1compiler;
    private ContextFreeGrammarLR0ParserCompiler lr0compiler;

    public ContextFreeLanguageParserFactory()
    {
        this.tokenizer = new ContextFreeGrammarTokenizer();
        this.parser = new ContextFreeGrammarParser();
        this.ll1compiler = new ContextFreeGrammarLL1ParserCompiler();
        this.lr0compiler = new ContextFreeGrammarLR0ParserCompiler();
    }

    public ContextFreeLanguageParser factory(List<String> input) throws ContextFreeGrammarTokenizerException, AmbiguousParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        for (String inputString: input)
        {
            List<ContextFreeGrammarSyntaxToken> tokenList = this.tokenizer.tokenize(inputString);
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
