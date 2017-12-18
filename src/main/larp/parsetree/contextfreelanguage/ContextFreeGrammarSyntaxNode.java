package larp.parsetree.contextfreelanguage;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarSyntaxNode
{
    protected List<ContextFreeGrammarSyntaxNode> childNodes;

    public ContextFreeGrammarSyntaxNode()
    {
        this.childNodes = new ArrayList<ContextFreeGrammarSyntaxNode>();
    }

    public List<ContextFreeGrammarSyntaxNode> getChildNodes()
    {
        return this.childNodes;
    }

    public boolean equals(Object other)
    {
        if (!this.getClass().equals(other.getClass()))
        {
            return false;
        }

        return this.childNodes.equals(((ContextFreeGrammarSyntaxNode)other).getChildNodes());
    }
}
