/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

public class GrammarAugmentorTest
{
    @Test
    public void testAugmentDoesNotChangeEmptyGrammar()
    {
        GrammarAugmentor augmentor = new GrammarAugmentor();

        Grammar grammar = new Grammar();
        Grammar expectedGrammar = new Grammar();

        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentAddsNewStartStateToValidGrammar()
    {
        GrammarAugmentor augmentor = new GrammarAugmentor();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentCalculatesNewStartSymbolNameFromExistingStartSymbol()
    {
        GrammarAugmentor augmentor = new GrammarAugmentor();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("Q"), new NonTerminalNode("R"));

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("Q'"), new NonTerminalNode("Q"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("Q"), new NonTerminalNode("R"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentResolvesNamingConflictRelatedtoNewStartSymbolName()
    {
        GrammarAugmentor augmentor = new GrammarAugmentor();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S'"));
        grammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S''"));
        grammar.addProduction(new NonTerminalNode("S''"), new NonTerminalNode("S'''"));

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S''''"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S'"));
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S''"));
        expectedGrammar.addProduction(new NonTerminalNode("S''"), new NonTerminalNode("S'''"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentSplitsMultiCharacterTerminalNodeIntoMultipleNodes()
    {
        GrammarAugmentor augmentor = new GrammarAugmentor();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("abc"));

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }
}
