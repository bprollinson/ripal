/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarTokenizer;
import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarTokenizerException;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.ContextFreeGrammarParser;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarLL1SyntaxCompiler;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarLR0SyntaxCompiler;
import larp.syntaxparser.contextfreelanguage.ContextFreeGrammarSyntaxParser;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;

import java.util.List;

public class ContextFreeLanguageParserFactory
{
    private ContextFreeGrammarTokenizer tokenizer;
    private ContextFreeGrammarSyntaxParser parser;
    private ContextFreeGrammarLL1SyntaxCompiler ll1compiler;
    private ContextFreeGrammarLR0SyntaxCompiler lr0compiler;

    public ContextFreeLanguageParserFactory()
    {
        this.tokenizer = new ContextFreeGrammarTokenizer();
        this.parser = new ContextFreeGrammarSyntaxParser();
        this.ll1compiler = new ContextFreeGrammarLL1SyntaxCompiler();
        this.lr0compiler = new ContextFreeGrammarLR0SyntaxCompiler();
    }

    public ContextFreeGrammarParser factory(List<String> input) throws ContextFreeGrammarTokenizerException, AmbiguousParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        for (String inputString: input)
        {
            List<ContextFreeGrammarSyntaxToken> tokenList = this.tokenizer.tokenize(inputString);
            ContextFreeGrammarSyntaxNode rootNode = this.parser.parse(tokenList);
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
