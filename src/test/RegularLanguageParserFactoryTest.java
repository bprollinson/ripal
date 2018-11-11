/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.DFA;
import larp.automaton.DFAState;
import larp.automaton.StateTransition;
import larp.parserfactory.regularlanguage.RegularLanguageParserFactory;
import larp.syntaxtokenizer.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenizerException;

public class RegularLanguageParserFactoryTest
{
    @Test
    public void testFactoryCreatesDFAForRegularExpression() throws RegularExpressionSyntaxTokenizerException
    {
        RegularLanguageParserFactory factory = new RegularLanguageParserFactory();
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
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectRegularExpression() throws RegularExpressionSyntaxTokenizerException
    {
        RegularLanguageParserFactory factory = new RegularLanguageParserFactory();
        DFA dfa = factory.factory(")(");
    }
}
