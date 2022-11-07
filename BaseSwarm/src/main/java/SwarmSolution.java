import java.util.List;

public class SwarmSolution {
    private SwarmMember bestSwarmMember;
    private Integer iteration;
    private List<Double> solution;

    public SwarmSolution(SwarmMember bestSwarmMember, Integer iteration, List<Double> solution){
        this.bestSwarmMember = bestSwarmMember;
        this.iteration = iteration;
        this.solution = solution;
    }

    public List<Double> getSolution() {
        return solution;
    }

    public Integer getIteration() {
        return iteration;
    }

    public SwarmMember getBestSwarmMember() {
        return bestSwarmMember;
    }
}
