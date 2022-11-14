import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAckleyRSO {
    private Function ackley = new AckleyFunction(20, 0.2, 2*Math.PI, 0.6);
    private RatSwarm swarm;

    @BeforeEach
    void init(){
        List<Double> uLimit = Arrays.asList(32.768, 32.768);
        List<Double> lLimit = Arrays.asList(-32.768, -32.768);
        this.swarm = new RatSwarm(20, 2, uLimit, lLimit);
    }

    @Test
    void TestAckley10IterationsRSO(){
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 10);

        // Should be 0.6, tolerance for 10 iterations 0.05
        Double should = ackley.evaluate(Arrays.asList(0.0,0.0));
        Double gwoRes = solution.getSolution().get(solution.getSolutionSize()-1);

        assertTrue(should-gwoRes < 0.05);
    }

    @Test
    void TestAckley100IterationsRSO(){
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 100);

        // Should be 0.6, tolerance for 10 iterations 0.005
        Double should = ackley.evaluate(Arrays.asList(0.0,0.0));
        Double gwoRes = solution.getSolution().get(solution.getSolutionSize()-1);

        assertTrue(should-gwoRes < 0.005);
    }

    @Test
    void TestAckley1000IterationsRSO(){
        SwarmSolution solution = this.swarm.findMinimum(this.ackley, 1000);

        // Should be 0.6, tolerance for 10 iterations 0.0005
        Double should = ackley.evaluate(Arrays.asList(0.0,0.0));
        Double gwoRes = solution.getSolution().get(solution.getSolutionSize()-1);

        assertTrue(should-gwoRes < 0.0005);
    }
}
