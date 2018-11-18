/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.contextfreelanguage;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarParseTreeNode
{
    protected List<ContextFreeGrammarParseTreeNode> childNodes;

    public ContextFreeGrammarParseTreeNode()
    {
        this.childNodes = new ArrayList<ContextFreeGrammarParseTreeNode>();
    }

    public List<ContextFreeGrammarParseTreeNode> getChildNodes()
    {
        return this.childNodes;
    }

    public boolean equals(Object other)
    {
        if (!this.getClass().equals(other.getClass()))
        {
            return false;
        }

        return this.childNodes.equals(((ContextFreeGrammarParseTreeNode)other).getChildNodes());
    }

    public int hashCode()
    {
        int total = this.getClass().getName().hashCode() + this.childNodes.size();

        for (int i = 0; i < this.childNodes.size(); i++)
        {
            int multiplier = i + 1;

            total += multiplier * this.childNodes.get(i).hashCode();
        }

        return total;
    }
}