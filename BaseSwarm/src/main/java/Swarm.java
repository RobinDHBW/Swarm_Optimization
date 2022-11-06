import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Swarm implements ISwarm{

    protected ArrayList<SwarmMember> members;


    @Override
    public SwarmSolution findMinimum(Function f) {
        return null;
    }

    @Override
    public SwarmSolution findMaximum(Function f) {
        return null;
    }
}
