/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.regularlanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.DFA;
import larp.automaton.DFAState;
import larp.automaton.StateTransition;
import larp.grammartokenizer.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.grammartokenizer.regularlanguage.RegularExpressionGrammarTokenizerException;

public class ParserFactoryTest
{
    @Test
    public void testFactoryCreatesDFAForRegularExpression() throws RegularExpressionGrammarTokenizerException
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

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectRegularExpression() throws RegularExpressionGrammarTokenizerException
    {
        ParserFactory factory = new ParserFactory();
        DFA dfa = factory.factory(")(");
    }
}
