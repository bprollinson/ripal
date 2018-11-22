/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.regularlanguage;

public class KleeneClosureNode extends Node
{
    public void addChild(Node childNode)
    {
        this.childNodes.add(childNode);
    }
}
