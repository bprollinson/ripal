/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.regularlanguage;

public class ConcatenationNode extends RegularExpressionParseTreeNode
{
    public void addChild(RegularExpressionParseTreeNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
