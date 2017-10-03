package larp.grammar.contextfreelanguage;

import java.util.Vector;

public class ContextFreeGrammarStartingTokenAssertion
{
    private Vector<ContextFreeGrammarSyntaxToken> tokens;

    public ContextFreeGrammarStartingTokenAssertion(Vector<ContextFreeGrammarSyntaxToken> tokens)
    {
        this.tokens = tokens;
    }

    public void validate() throws ContextFreeGrammarSyntaxTokenizerException
    {
        if (tokens.size() < 2)
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }
        if (!(tokens.get(0) instanceof NonTerminalToken))
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }
        if (!(tokens.get(1) instanceof SeparatorToken))
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }
    }
}
