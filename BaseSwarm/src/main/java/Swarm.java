import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Swarm implements ISwarm, ISwarmVisitor{

    protected ArrayList<SwarmMember> members;
    protected List<Double> upperLimits, lowerLimits;
    protected Integer dimension = 0;

    public Swarm(Integer dimension, List<Double> upperLimits, List<Double> lowerLimits){
        this.dimension = dimension;

        //Save list sizes to variables, because it's a very expensive operation
        Integer uSize = upperLimits.size();
        Integer lSize = lowerLimits.size();

        //Check if arguments for limits fit, set to default values if not
        if(uSize != dimension || uSize == 0 || lSize != dimension || lSize == 0 || uSize != lSize){
            this.upperLimits = new ArrayList<Double>(Collections.nCopies(dimension,1.0));
            this.lowerLimits = new ArrayList<Double>(Collections.nCopies(dimension,-1.0));
        }else{
            this.upperLimits = upperLimits;
            this.lowerLimits = lowerLimits;
        }
    }

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
