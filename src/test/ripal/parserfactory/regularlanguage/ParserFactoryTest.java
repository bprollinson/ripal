/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parserfactory.regularlanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import ripal.automaton.DFA;
import ripal.automaton.DFAState;
import ripal.automaton.StateTransition;
import ripal.grammartokenizer.regularlanguage.IncorrectExpressionNestingException;
import ripal.grammartokenizer.regularlanguage.TokenizerException;

public class ParserFactoryTest
{
    @Test
    public void testFactoryCreatesDFAForExpression() throws TokenizerException
    {
        ParserFactory factory = new ParserFactory();
        DFA dfa = factory.factory("ab*");

        DFAState state1 = new DFAState("0", false);
        DFAState state2 = new DFAState("0", true);
        DFAState state3 = new DFAState("0", true);
        state1.addTransition(new StateTransition<Character, DFAState>('a', state2));
        state2.addTransition(new StateTransition<Character, DFAState>('b', state3));
        state3.addTransition(new StateTransition<Character, DFAState>('b', state3));
        DFA expectedDFA = new DFA(state1);

        assertTrue(expectedDFA.structureEquals(dfa));
    }

    @Test
    public void testFactoryCreatesDFAForEmptyExpression() throws TokenizerException
    {
        ParserFactory factory = new ParserFactory();
        DFA dfa = factory.factory("");

        DFAState state1 = new DFAState("0", true);
        DFA expectedDFA = new DFA(state1);

        assertTrue(expectedDFA.structureEquals(dfa));
    }

    @Test(expected = IncorrectExpressionNestingException.class)
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectExpression() throws TokenizerException
    {
        ParserFactory factory = new ParserFactory();
        DFA dfa = factory.factory(")(");
    }
}
