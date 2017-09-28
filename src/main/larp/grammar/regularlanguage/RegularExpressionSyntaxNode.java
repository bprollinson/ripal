package larp.grammar.regularlanguage;

import java.util.Vector;

public class RegularExpressionSyntaxNode
{
    protected Vector<RegularExpressionSyntaxNode> childNodes;

    public RegularExpressionSyntaxNode()
    {
        this.childNodes = new Vector<RegularExpressionSyntaxNode>();
    }

    public Vector<RegularExpressionSyntaxNode> getChildNodes()
    {
        return this.childNodes;
    }

    public boolean equals(Object other)
    {
        if (!this.getClass().equals(other.getClass()))
        {
            return false;
        }

        return this.childNodes.equals(((RegularExpressionSyntaxNode)other).getChildNodes());
    }
}
