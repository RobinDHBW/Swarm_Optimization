
public interface ISwarmSolve {
    SwarmSolution findMinimum(Function f, Integer iterationCount);
    SwarmSolution findMaximum(Function f, Integer iterationCount);
}
