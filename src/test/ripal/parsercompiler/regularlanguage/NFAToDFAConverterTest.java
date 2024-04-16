/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.regularlanguage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.automaton.DFA;
import ripal.automaton.DFAState;
import ripal.automaton.NFA;
import ripal.automaton.NFAState;
import ripal.automaton.StateTransition;

public class NFAToDFAConverterTest
{
    @Test
    public void testSingleStateDFARemainsUnchanged()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testDFARemainsUnchanged()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S1", false);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testMultipleTransitionsForSameCharacterCombinedFromStartState()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S0", false);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        NFAState state3 = new NFAState("S2", false);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        state1.addTransition(new StateTransition<Character, NFAState>('a', state3));
        state1.addTransition(new StateTransition<Character, NFAState>('a', state3));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testStateFinalStatusObtainedFromStateInSet()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S1", true);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        NFAState state3 = new NFAState("S2", true);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        state1.addTransition(new StateTransition<Character, NFAState>('a', state3));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testSubsequentTransitionCalculatedFromMultipleStates()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S1", false);
        DFAState expectedState3 = new DFAState("S2", false);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        expectedState2.addTransition(new StateTransition<Character, DFAState>('a', expectedState3));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        NFAState state3 = new NFAState("S2", false);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        state1.addTransition(new StateTransition<Character, NFAState>('a', state3));
        state2.addTransition(new StateTransition<Character, NFAState>('a', state3));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testCycleNavigatedForNextTransitionCalculated()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S1", false);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        expectedState2.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state1));
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testDuplicateSubsequentStateRemovedFromNextStateSetCalculation()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S1", false);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        expectedState2.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state1));
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        state2.addTransition(new StateTransition<Character, NFAState>('a', state2));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }

    @Test
    public void testSetConstructionRecognizesSameCompoundStateWithStatesInDifferentOrder()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S1", false);
        DFAState expectedState3 = new DFAState("S2", false);
        DFAState expectedState4 = new DFAState("S2", false);
        expectedState1.addTransition(new StateTransition<Character, DFAState>('a', expectedState2));
        expectedState1.addTransition(new StateTransition<Character, DFAState>('b', expectedState3));
        expectedState2.addTransition(new StateTransition<Character, DFAState>('a', expectedState4));
        expectedState3.addTransition(new StateTransition<Character, DFAState>('a', expectedState4));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        NFAState state3 = new NFAState("S2", false);
        NFAState state4 = new NFAState("S3", false);
        NFAState state5 = new NFAState("S4", false);
        state1.addTransition(new StateTransition<Character, NFAState>('a', state2));
        state1.addTransition(new StateTransition<Character, NFAState>('b', state3));
        state2.addTransition(new StateTransition<Character, NFAState>('a', state4));
        state2.addTransition(new StateTransition<Character, NFAState>('a', state5));
        state3.addTransition(new StateTransition<Character, NFAState>('a', state5));
        state3.addTransition(new StateTransition<Character, NFAState>('a', state4));
        NFA NFA = new NFA(state1);

        assertTrue(expectedDFA.structureEquals(converter.convert(NFA)));
    }
}
