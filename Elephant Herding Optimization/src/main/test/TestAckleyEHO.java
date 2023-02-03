import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAckleyEHO {
    private final IFunction ackley = new AckleyFunction(20, 0.2, 2 * Math.PI, 0.6);
    private ISwarmSolve herd;

    @BeforeEach
    void init() {
        List<Double> uLimit = Arrays.asList(32.768, 32.768);
        List<Double> lLimit = Arrays.asList(-32.768, -32.768);
        this.herd = new ElephantHerding(20, 5, 3, 0.2, 0.3, 0.1,  2, uLimit, lLimit);
    }

    @Test
    void TestAckley10IterationsEHO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 10);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }

    @Test
    void TestAckley25IterationsEHO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 25);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }

    @Test
    void TestAckley50IterationsEHO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 50);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }

    @Test
    void TestAckley100IterationsEHO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 100);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }

    @Test
    void TestAckley1000IterationsEHO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 1000);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }
}