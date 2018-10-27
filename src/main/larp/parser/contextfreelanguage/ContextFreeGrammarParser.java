package larp.parser.contextfreelanguage;

import java.util.List;

public interface ContextFreeGrammarParser
{
    public boolean accepts(String inputString);
    public List<Integer> getAppliedRules();
}
