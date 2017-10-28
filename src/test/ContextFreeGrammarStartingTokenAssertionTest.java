import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammarStartingTokenAssertion;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.grammar.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;

import java.util.Vector;

public class ContextFreeGrammarStartingTokenAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionForFewerThanThreeTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenFirstTokenIsNotANonterminal() throws ContextFreeGrammarSyntaxTokenizerException
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();
        tokens.add(new SeparatorToken());
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenSecondTokenIsNotASeparator() throws ContextFreeGrammarSyntaxTokenizerException
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws ContextFreeGrammarSyntaxTokenizerException
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }
}
