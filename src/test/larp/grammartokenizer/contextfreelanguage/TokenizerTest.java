/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.token.contextfreelanguage.ContextFreeGrammarToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class TokenizerTest
{
    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterNonTerminalProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:S");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnLeftSideOfProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("Start:S");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("Start"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnRightSideOfProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:Start");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("Start"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEpsilonProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterTerminalProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"a\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterTerminalOnRightSideOfProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultipleTerminalsOnRightSideWithoutSpaceBetweenThem() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal1\"\"terminal2\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenTerminalTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal1\" \"terminal2\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesSpaceWithinTerminalToken() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal 1\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenNonTerminalTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:nonterminal1 nonterminal2");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("nonterminal1"));
        expectedResult.add(new NonTerminalToken("nonterminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesTerminalFollowedDirectlyByNonterminal() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal\"S");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenBeforeEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenBeforeEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:S\"\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenAfterEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"\"\"terminal 1\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenAfterEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"\"S");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingMultipleEpsilonTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"\"\"\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingTerminalAndMultipleEpsilonTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"\"\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingNonTerminalAndMultipleEpsilonTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:S\"\"\"\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testTokenizeThrowsExceptionForIncorrecTokenSequence() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("");
    }

    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("S:\"a");
    }

    @Test(expected = IncorrectContextFreeGrammarSeparatorException.class)
    public void testTokenizeThrowsExceptionForIncorrectNumberofSeparators() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("S::");
    }

    @Test
    public void testTokenizeTokenizesColonWithinTerminalString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\":\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken(":"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEscapedSpecialCharactersWithinTerminalString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"\\\"\\\\\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("\"\\"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeGeneratesStandardCharacterTokenFromCharacterAfterEscapeCharacter() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<ContextFreeGrammarToken> result = tokenizer.tokenize("S:\"\\a\"");
        List<ContextFreeGrammarToken> expectedResult = new ArrayList<ContextFreeGrammarToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfStringContainingEscapedQuote() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("S:\"\\\"");
    }

    @Test(expected = IncorrectContextFreeGrammarEscapeCharacterPositionException.class)
    public void testTokenizeThrowsExceptionForEscapeCharacterOutsideTerminalString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("S:\\\\");
    }
}
