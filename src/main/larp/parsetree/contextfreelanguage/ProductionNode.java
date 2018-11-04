/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.contextfreelanguage;

public class ProductionNode extends ContextFreeGrammarSyntaxNode
{
    public void addChild(ContextFreeGrammarSyntaxNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
