/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import java.util.List;

public interface Parser
{
    public boolean accepts(String inputString);
    public List<Integer> getAppliedRules();
}
