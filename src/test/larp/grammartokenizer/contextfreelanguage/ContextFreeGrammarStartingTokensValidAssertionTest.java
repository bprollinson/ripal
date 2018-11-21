/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import org.junit.Test;

import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.Token;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarStartingTokensValidAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionForFewerThanThreeTokens() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenFirstTokenIsNotANonterminal() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new SeparatorToken());
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenSecondTokenIsNotASeparator() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }
}
