import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestAckleyRSO {
    private final IFunction ackley = new AckleyFunction(20, 0.2, 2 * Math.PI, 0.6);
    private ISwarmSolve swarm;

    @BeforeEach
    void init() {
        List<Double> uLimit = Arrays.asList(32.768, 32.768);
        List<Double> lLimit = Arrays.asList(-32.768, -32.768);
        this.swarm = new RatSwarm(20, 2, uLimit, lLimit);
    }

    @Test
    void TestAckley10IterationsRSO() {
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 10);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double rsoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertNotEquals(should, rsoRes);
    }

     @Test
    void TestAckley50IterationsRSO() {
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 50);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double rsoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, rsoRes);
    }

    @Test
    void TestAckley100IterationsRSO() {
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 100);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double rsoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, rsoRes);
    }

    @Test
    void TestAckley1000IterationsRSO() {
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 1000);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double rsoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, rsoRes);
    }
}
