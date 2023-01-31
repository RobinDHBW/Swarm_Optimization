import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ElephantHerding extends Swarm implements ISwarmSolve {


    public ElephantHerding(Integer herdSize, Integer groupSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits){
        super(dimension, upperLimits, lowerLimits);


    }

    @Override
    public void visit(SwarmMember m, Enum c) {

    }

    @Override
    protected void rankMembers(Function f, Boolean sign) {

    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        return null;
    }

    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        return null;
    }
}
