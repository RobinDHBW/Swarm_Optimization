import java.util.List;

public class SwarmSolution {
    private final List<Double> solution;

    /**
     * Construct solution by given parameters
     *
     * @param solution (List<Double>)
     */
    public SwarmSolution(List<Double> solution) {
        this.solution = solution;
    }

    public List<Double> getSolution() {
        return solution;
    }

    public Integer getSolutionSize() {
        return solution.size();
    }
}
