/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.regularlanguage;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    protected List<Node> childNodes;

    public Node()
    {
        this.childNodes = new ArrayList<Node>();
    }

    public List<Node> getChildNodes()
    {
        return this.childNodes;
    }

    public boolean equals(Object other)
    {
        if (!this.getClass().equals(other.getClass()))
        {
            return false;
        }

        return this.childNodes.equals(((Node)other).getChildNodes());
    }
}
