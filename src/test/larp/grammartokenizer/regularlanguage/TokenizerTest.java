/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseParenthesisToken;
import larp.token.regularlanguage.EpsilonToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenParenthesisToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.Token;

import java.util.ArrayList;
import java.util.List;

public class TokenizerTest
{
    @Test
    public void testTokenizeTokenizesString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("(ab|c)*");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CharacterToken('b'));
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('c'));
        expectedResult.add(new CloseParenthesisToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEpsilonExpression() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesExpressionContainingCharacterBeforeSpace() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("a ");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CharacterToken(' '));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesExpressionContainingCharacterAfterSpace() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize(" a");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken(' '));
        expectedResult.add(new CharacterToken('a'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesExpressionContainingTwoConsecutiveSpaces() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("  ");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken(' '));
        expectedResult.add(new CharacterToken(' '));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAppliedToLeadingEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("*");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAppliedToLeadingEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("|a");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('a'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAppliedToTrailingEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("a|");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesCloseParenthesisAfterOpenParenthesis() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("()");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAfterOpenParenthesis() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("(|a)");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAfterOpenParenthesis() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("(*)");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new KleeneClosureToken());
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesCloseParenthesisAfterOr() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("(a|)");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAfterOr() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("a||b");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('b'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAfterOr() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("a|*");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAppliedtoSpace() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize(" *");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken(' '));
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectExpressionNestingException.class)
    public void testTokenizeThrowsExceptionForNegativeParenthesisNesting() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize(")(");
    }

    @Test
    public void testTokenizeTokenizesEscapedSpecialCharacters() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("\\(\\)\\*\\|\\\\");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('('));
        expectedResult.add(new CharacterToken(')'));
        expectedResult.add(new CharacterToken('*'));
        expectedResult.add(new CharacterToken('|'));
        expectedResult.add(new CharacterToken('\\'));

        assertEquals(expectedResult, result);
    }

    @Test(expected = DanglingExpressionEscapeCharacterException.class)
    public void testTokenizeThrowsExceptionForDanglingEscapeCharacter() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("\\");
    }

    @Test(expected = IncorrectExpressionNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedParenthesisAtEndOfString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("(");
    }

    @Test
    public void testTokenizeDoesNotAdjustParenthesisNestingLevelOnEscapedOpenParenthesis() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("\\(");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('('));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeDoesNotAdjustParenthesisNestingLevelOnEscapedCloseParenthesis() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("\\)");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken(')'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeGeneratesStandardCharacterTokenFromCharacterAfterEscapeCharacter() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("\\a");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new CharacterToken('a'));

        assertEquals(expectedResult, result);
    }

    @Test(expected = DanglingExpressionEscapeCharacterException.class)
    public void testExceptionForDangingEscapeCharacterPrioritizedOverExceptionForUnclosedParenthesisAtEndOfString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("(\\");
    }
}
