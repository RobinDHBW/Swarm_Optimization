
public interface ISwarm {
    SwarmSolution findMinimum(Function f, Integer iterationCount);
    SwarmSolution findMaximum(Function f, Integer iterationCount);
}
