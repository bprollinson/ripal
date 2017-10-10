package larp.grammar.contextfreelanguage;

public class NonTerminalNode extends ContextFreeGrammarSyntaxNode
{
    private String name;

    public NonTerminalNode(String name)
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

        return this.name == ((NonTerminalNode)other).getName();
    }
}
