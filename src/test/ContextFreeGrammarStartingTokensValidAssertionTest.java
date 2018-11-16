/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarStartingTokensValidAssertion;
import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarTokenizerException;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.token.contextfreelanguage.ContextFreeGrammarToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarStartingTokensValidAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionForFewerThanThreeTokens() throws ContextFreeGrammarTokenizerException
    {
        List<ContextFreeGrammarToken> tokens = new ArrayList<ContextFreeGrammarToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenFirstTokenIsNotANonterminal() throws ContextFreeGrammarTokenizerException
    {
        List<ContextFreeGrammarToken> tokens = new ArrayList<ContextFreeGrammarToken>();
        tokens.add(new SeparatorToken());
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenSecondTokenIsNotASeparator() throws ContextFreeGrammarTokenizerException
    {
        List<ContextFreeGrammarToken> tokens = new ArrayList<ContextFreeGrammarToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws ContextFreeGrammarTokenizerException
    {
        List<ContextFreeGrammarToken> tokens = new ArrayList<ContextFreeGrammarToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }
}
