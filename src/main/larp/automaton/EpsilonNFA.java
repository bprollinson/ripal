/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.automaton;

public class EpsilonNFA extends FiniteAutomata<EpsilonNFAState>
{
    public EpsilonNFA(EpsilonNFAState startState)
    {
        super(startState);
    }
}
