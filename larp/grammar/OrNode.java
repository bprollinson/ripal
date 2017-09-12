package larp.grammar;

public class OrNode extends RegularExpressionSyntaxNode
{
    public void addChild(RegularExpressionSyntaxNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
