import java.util.ArrayList;
import java.util.List;

public abstract class Swarm implements ISwarm, ISwarmVisitor{

    protected ArrayList<SwarmMember> members;

    /**
     * Simple helper method to compare two values, considering a sign
     * @param a (Double) - value to be compared
     * @param b (Double) - value to compare against
     * @param sign (Boolean) - true to find maxima, false to find minima
     * @return
     */
    protected Boolean compare(Double a, Double b, Boolean sign) {
        return sign ? (a > b) : (a < b);
    }

    /**
     * Simple helper method to set a Double value to NEGATIVE_INFINITY or POSITIVE_INFINITY
     * @param sign (Boolean)
     * @return
     */
    protected Double resetHighestValue(Boolean sign) {
        return sign ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }
}
