import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestAckleyGWO {
    private final IFunction ackley = new AckleyFunction(20, 0.2, 2 * Math.PI, 0.6);
    private ISwarmSolve pack;

    @BeforeEach
    void init() {
        List<Double> uLimit = Arrays.asList(32.768, 32.768);
        List<Double> lLimit = Arrays.asList(-32.768, -32.768);
        this.pack = new Wolfpack(20, 2, uLimit, lLimit);
    }

    @Test
    void TestAckley10IterationsGWO() {
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 10);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double gwoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertNotEquals(should, gwoRes);
    }

    @Test
    void TestAckley25IterationsGWO() {
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 25);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double gwoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertNotEquals(should, gwoRes);
    }

    @Test
    void TestAckley50IterationsGWO() {
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 50);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double gwoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, gwoRes);
    }

    @Test
    void TestAckley100IterationsGWO() {
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 100);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double gwoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, gwoRes);
    }

    @Test
    void TestAckley1000IterationsGWO() {
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 1000);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double gwoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, gwoRes);
    }
}
