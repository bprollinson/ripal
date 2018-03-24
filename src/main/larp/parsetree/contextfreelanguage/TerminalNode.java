package larp.parsetree.contextfreelanguage;

public class TerminalNode extends ContextFreeGrammarSyntaxNode
{
    private String value;

    public TerminalNode(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.value.equals(((TerminalNode)other).getValue());
    }

    public int hashCode()
    {
        return super.hashCode() + this.value.hashCode();
    }
}
