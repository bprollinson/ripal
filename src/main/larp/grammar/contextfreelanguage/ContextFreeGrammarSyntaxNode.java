package larp.grammar.contextfreelanguage;

import java.util.List;
import java.util.Vector;

public class ContextFreeGrammarSyntaxNode
{
    protected List<ContextFreeGrammarSyntaxNode> childNodes;

    public ContextFreeGrammarSyntaxNode()
    {
        this.childNodes = new Vector<ContextFreeGrammarSyntaxNode>();
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
