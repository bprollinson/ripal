package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

public interface LR0ParseTableAction
{
    public boolean supportsTransition();
    public State getNextState();
}
