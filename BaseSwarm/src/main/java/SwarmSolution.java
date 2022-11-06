public class SwarmSolution {
    private SwarmMember bestSwarmMember;
    private Integer iteration;
    private Double solution;

    public SwarmSolution(SwarmMember bestSwarmMember, Integer iteration, Double solution){
        this.bestSwarmMember = bestSwarmMember;
        this.iteration = iteration;
        this.solution = solution;
    }

    public Double getSolution() {
        return solution;
    }

    public Integer getIteration() {
        return iteration;
    }

    public SwarmMember getBestSwarmMember() {
        return bestSwarmMember;
    }
}
