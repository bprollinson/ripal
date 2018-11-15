/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.regularlanguage;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionParseTreeNode
{
    protected List<RegularExpressionParseTreeNode> childNodes;

    public RegularExpressionParseTreeNode()
    {
        this.childNodes = new ArrayList<RegularExpressionParseTreeNode>();
    }

    public List<RegularExpressionParseTreeNode> getChildNodes()
    {
        return this.childNodes;
    }

    public boolean equals(Object other)
    {
        if (!this.getClass().equals(other.getClass()))
        {
            return false;
        }

        return this.childNodes.equals(((RegularExpressionParseTreeNode)other).getChildNodes());
    }
}
