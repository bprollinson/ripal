import larp.statemachine.State;
import larp.statemachine.StateTransition;

public class LARP
{
    public static void main(String args[])
    {
        System.out.println("LARP main");

        State state0 = new State("S0");
        State state1 = new State("S1");
        state0.addTransition(new StateTransition('a', state1));
    }
}
