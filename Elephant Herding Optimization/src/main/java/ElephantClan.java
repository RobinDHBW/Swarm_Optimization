import java.util.List;

public class ElephantClan extends Swarm{

    public ElephantClan(Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);
    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        return null;
    }

    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        return null;
    }

    @Override
    public void visit(SwarmMember m, Enum c) {

    }
}
