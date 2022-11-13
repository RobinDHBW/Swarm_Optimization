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

//        @TODO Which are the right solutions for the ackley function with my parameters?
        assertEquals(5, solution.getSolution());
    }

    @Test
    void TestAckley100Iterations(){
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 100);

        assertEquals(5, solution.getSolution());
    }

    @Test
    void TestAckley1000Iterations(){
        SwarmSolution solution = this.pack.findMinimum(this.ackley, 1000);

        assertEquals(5, solution.getSolution());
    }
}
