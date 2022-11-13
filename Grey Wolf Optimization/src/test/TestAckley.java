import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAckley {
    private Function ackley = new AckleyFunction(20, 0.2, 2*Math.PI, 0.6);
    private Wolfpack pack;

    @BeforeEach
    void init(){
        List<Double> uLimit = Arrays.asList(1.0, 2.0);
        List<Double> lLimit = Arrays.asList(-1.0, -2.0);
        this.pack = new Wolfpack(20, 2, uLimit, lLimit);
    }

    @Test
    void TestAckley10Iterations(){
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 10);

        // Should be 0.6, tolerance for 10 iterations 0.05
        Double should = solution.getSolution().get(solution.getSolutionSize()-1);
        assertTrue(.06-should < 0.05);
    }

    @Test
    void TestAckley100Iterations(){
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 100);

        // Should be 0.6, tolerance for 10 iterations 0.005
        Double should = solution.getSolution().get(solution.getSolutionSize()-1);
        assertTrue(.06-should < 0.005);
    }

    @Test
    void TestAckley1000Iterations(){
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 1000);

        // Should be 0.6, tolerance for 10 iterations 0.0005
        Double should = solution.getSolution().get(solution.getSolutionSize()-1);
        assertTrue(.06-should < 0.0005);
    }
}
