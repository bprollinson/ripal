import larp.statemachine.State;
import larp.statemachine.StateMachine;
import larp.statemachine.StateTransition;

public class LARP
{
    public static void main(String args[])
    {
        System.out.println("LARP main");

        State state0 = new State("S0", false);
        State state1 = new State("S1", true);
        state0.addTransition(new StateTransition('a', state1));

        StateMachine machine = new StateMachine(state0);
        System.out.println(": " + machine.accepts(""));
        System.out.println("a: " + machine.accepts("a"));
        System.out.println("b: " + machine.accepts("b"));
    }
}
