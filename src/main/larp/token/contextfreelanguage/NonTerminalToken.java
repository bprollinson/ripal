package larp.token.contextfreelanguage;

public class NonTerminalToken extends ContextFreeGrammarSyntaxToken
{
    private String name;

    public NonTerminalToken(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.name.equals(((NonTerminalToken)other).getName());
    }
}
