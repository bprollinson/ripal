package larp.grammar;

import java.util.Vector;

public class RegularExpressionSyntaxNode
{
    protected Vector<RegularExpressionSyntaxNode> childNodes;

    public RegularExpressionSyntaxNode()
    {
        this.childNodes = new Vector<RegularExpressionSyntaxNode>();
    }
}
