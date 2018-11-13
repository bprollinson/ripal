/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizer;
import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarTokenizerException;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarEscapeCharacterPositionException;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarQuoteNestingException;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarSeparatorException;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarSyntaxTokenizerTest
{
    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterNonTerminalProduction() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnLeftSideOfProduction() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("Start:S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("Start"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnRightSideOfProduction() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:Start");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("Start"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEpsilonProduction() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterTerminalProduction() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"a\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterTerminalOnRightSideOfProduction() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultipleTerminalsOnRightSideWithoutSpaceBetweenThem() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\"\"terminal2\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenTerminalTokens() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal1\" \"terminal2\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesSpaceWithinTerminalToken() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenNonTerminalTokens() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:nonterminal1 nonterminal2");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("nonterminal1"));
        expectedResult.add(new NonTerminalToken("nonterminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesTerminalFollowedDirectlyByNonterminal() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal\"S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenBeforeEpsilon() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenBeforeEpsilon() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenAfterEpsilon() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"\"terminal 1\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenAfterEpsilon() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"S");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingMultipleEpsilonTokens() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingTerminalAndMultipleEpsilonTokens() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"terminal 1\"\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingNonTerminalAndMultipleEpsilonTokens() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:S\"\"\"\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testTokenizeThrowsExceptionForIncorrecTokenSequence() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("");
    }

    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfString() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S:\"a");
    }

    @Test(expected = IncorrectContextFreeGrammarSeparatorException.class)
    public void testTokenizeThrowsExceptionForIncorrectNumberofSeparators() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S::");
    }

    @Test
    public void testTokenizeTokenizesColonWithinTerminalString() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\":\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken(":"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEscapedSpecialCharactersWithinTerminalString() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\\\"\\\\\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("\"\\"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeGeneratesStandardCharacterTokenFromCharacterAfterEscapeCharacter() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        List<ContextFreeGrammarSyntaxToken> result = tokenizer.tokenize("S:\"\\a\"");
        List<ContextFreeGrammarSyntaxToken> expectedResult = new ArrayList<ContextFreeGrammarSyntaxToken>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectContextFreeGrammarQuoteNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfStringContainingEscapedQuote() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S:\"\\\"");
    }

    @Test(expected = IncorrectContextFreeGrammarEscapeCharacterPositionException.class)
    public void testTokenizeThrowsExceptionForEscapeCharacterOutsideTerminalString() throws ContextFreeGrammarTokenizerException
    {
        ContextFreeGrammarSyntaxTokenizer tokenizer = new ContextFreeGrammarSyntaxTokenizer();

        tokenizer.tokenize("S:\\\\");
    }
}
