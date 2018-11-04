/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextFreeGrammarAugmentor
{
    public ContextFreeGrammar augment(ContextFreeGrammar grammar)
    {
        ContextFreeGrammar newGrammar = new ContextFreeGrammar();
        newGrammar.addProduction(this.calculateNewStartSymbol(grammar), grammar.getStartSymbol(), new EndOfStringNode());

        for (ContextFreeGrammarSyntaxNode production: grammar.getProductions())
        {
            newGrammar.addProduction(production);
        }

        return this.splitTerminalNodes(newGrammar);
    }

    private NonTerminalNode calculateNewStartSymbol(ContextFreeGrammar grammar)
    {
        Set<String> existingNames = this.calculateExistingNonTerminalNames(grammar);

        String newStartSymbolName = grammar.getStartSymbol().getName() + "'";
        while (existingNames.contains(newStartSymbolName))
        {
            newStartSymbolName += "'";
        }

        return new NonTerminalNode(newStartSymbolName);
    }

    private Set<String> calculateExistingNonTerminalNames(ContextFreeGrammar grammar)
    {
        Set<String> existingNames = new HashSet<String>();
        for (ContextFreeGrammarSyntaxNode production: grammar.getProductions())
        {
            NonTerminalNode leftHandSide = (NonTerminalNode)production.getChildNodes().get(0);
            existingNames.add(leftHandSide.getName());

            ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);
            for (ContextFreeGrammarSyntaxNode childNode: rightHandSide.getChildNodes())
            {
                if (childNode instanceof NonTerminalNode)
                {
                    existingNames.add(((NonTerminalNode)childNode).getName());
                }
            }
        }

        return existingNames;
    }

    private ContextFreeGrammar splitTerminalNodes(ContextFreeGrammar grammar)
    {
        ContextFreeGrammar newGrammar = new ContextFreeGrammar();
        for (ContextFreeGrammarSyntaxNode production: grammar.getProductions())
        {
            ContextFreeGrammarSyntaxNode concatenationNode = production.getChildNodes().get(1);
            ConcatenationNode newConcatenationNode = new ConcatenationNode();
            for (ContextFreeGrammarSyntaxNode childNode: concatenationNode.getChildNodes())
            {
                if (childNode instanceof TerminalNode)
                {
                    String terminalValue = ((TerminalNode)childNode).getValue();
                    for (int i = 0; i < terminalValue.length(); i++)
                    {
                        newConcatenationNode.addChild(new TerminalNode(terminalValue.substring(i, i + 1)));
                    }
                }
                else
                {
                    newConcatenationNode.addChild(childNode);
                }
            }

            ProductionNode newProduction = new ProductionNode();
            newProduction.addChild(production.getChildNodes().get(0));
            newProduction.addChild(newConcatenationNode);
            newGrammar.addProduction(newProduction);
        }

        return newGrammar;
    }
}
