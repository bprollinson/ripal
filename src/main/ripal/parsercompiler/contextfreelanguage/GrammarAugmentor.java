/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrammarAugmentor
{
    public Grammar augment(Grammar grammar)
    {
        Grammar newGrammar = new Grammar();
        if (grammar.getStartSymbol() == null)
        {
            return newGrammar;
        }
        newGrammar.addProduction(this.calculateNewStartSymbol(grammar), grammar.getStartSymbol(), new EndOfStringNode());

        for (Node production: grammar.getProductions())
        {
            newGrammar.addProduction(production);
        }

        return this.splitTerminalNodes(newGrammar);
    }

    private NonTerminalNode calculateNewStartSymbol(Grammar grammar)
    {
        Set<String> existingNames = this.calculateExistingNonTerminalNames(grammar);

        String newStartSymbolName = grammar.getStartSymbol().getName() + "'";
        while (existingNames.contains(newStartSymbolName))
        {
            newStartSymbolName += "'";
        }

        return new NonTerminalNode(newStartSymbolName);
    }

    private Set<String> calculateExistingNonTerminalNames(Grammar grammar)
    {
        Set<String> existingNames = new HashSet<String>();
        for (Node production: grammar.getProductions())
        {
            NonTerminalNode leftHandSide = (NonTerminalNode)production.getChildNodes().get(0);
            existingNames.add(leftHandSide.getName());

            Node rightHandSide = production.getChildNodes().get(1);
            for (Node childNode: rightHandSide.getChildNodes())
            {
                if (childNode instanceof NonTerminalNode)
                {
                    existingNames.add(((NonTerminalNode)childNode).getName());
                }
            }
        }

        return existingNames;
    }

    private Grammar splitTerminalNodes(Grammar grammar)
    {
        Grammar newGrammar = new Grammar();
        for (Node production: grammar.getProductions())
        {
            Node concatenationNode = production.getChildNodes().get(1);
            ConcatenationNode newConcatenationNode = new ConcatenationNode();
            for (Node childNode: concatenationNode.getChildNodes())
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
