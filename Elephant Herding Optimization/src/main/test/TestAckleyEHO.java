import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAckleyEHO {
    private final Function ackley = new AckleyFunction(20, 0.2, 2 * Math.PI, 0.6);
    private ElephantHerding herd;

    @BeforeEach
    void init() {
        List<Double> uLimit = Arrays.asList(32.768, 32.768);
        List<Double> lLimit = Arrays.asList(-32.768, -32.768);
        this.herd = new ElephantHerding(20, 5, 2, uLimit, lLimit);
    }

    @Test
    void TestAckley10IterationsGWO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 50);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }

    @Test
    void TestAckley100IterationsGWO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 100);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }

    @Test
    void TestAckley1000IterationsGWO() {
        SwarmSolution solution = this.herd.findMinimum(this.ackley, 1000);

        // Should be 0.6
        Double should = Math.round(ackley.evaluate(Arrays.asList(0.0, 0.0)) * 10000.0) / 10000.0;
        Double ehoRes = Math.round(solution.getSolution().get(solution.getSolutionSize() - 1) * 10000.0) / 10000.0;

        assertEquals(should, ehoRes);
    }
}