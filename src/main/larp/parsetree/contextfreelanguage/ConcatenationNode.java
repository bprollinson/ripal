/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.contextfreelanguage;

public class ConcatenationNode extends ContextFreeGrammarParseTreeNode
{
    public void addChild(ContextFreeGrammarParseTreeNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
