import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SwarmGroup implements ISwarmVisitor {
    protected ArrayList<SwarmMember> members;
    protected Integer dimension = 0;
    protected List<Double> upperLimits, lowerLimits;

    /**
     * Construct SwarmGroup by given parameters
     *
     * @param dimension   (Integer)
     * @param upperLimits (List<Double>)
     * @param lowerLimits (List<Double>)
     */
    public SwarmGroup(Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        this.dimension = dimension;

        //Save list sizes to variables, because it's a very expensive operation
        Integer uSize = upperLimits.size();
        Integer lSize = lowerLimits.size();
        //Check if arguments for limits fit, set to default values if not
        if (uSize != dimension || uSize == 0 || lSize != dimension || lSize == 0 || uSize != lSize) {
            this.upperLimits = new ArrayList<Double>(Collections.nCopies(dimension, 1.0));
            this.lowerLimits = new ArrayList<Double>(Collections.nCopies(dimension, -1.0));
        } else {
            this.upperLimits = upperLimits;
            this.lowerLimits = lowerLimits;
        }
    }

    /**
     * abstract method to rank SwarmMembers
     *
     * @param f    - (IFunction)
     * @param sign - (Boolean)
     */
    protected abstract void rankMembers(IFunction f, Boolean sign);

    /**
     * Simple helper method to compare two values, considering a sign
     *
     * @param a    (Double)
     * @param b    (Double)
     * @param sign (Boolean)
     * @return
     */
    protected Boolean compare(Double a, Double b, Boolean sign) {
        return sign ? (a > b) : (a < b);
    }

    /**
     * Simple helper method to set a Double value to NEGATIVE_INFINITY or POSITIVE_INFINITY
     *
     * @param sign (Boolean)
     * @return
     */
    protected Double resetHighestValue(Boolean sign) {
        return sign ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    /**
     * Use visitor to reset the SwarmMember ranking
     *
     * @param list (ArrayList<SwarmMember>)
     */
    protected void setMembersRanking(ArrayList<SwarmMember> list, Enum c) {
        for (SwarmMember member : list) {
            member.accept(this, c);
        }
    }

    /**
     * Get the corresponding SwarmMembers for a given Classifier from SwarmGroup
     *
     * @param c (Enum)
     * @return
     */
    protected List<SwarmMember> getMemberByClassifier(Enum c) {
        List<SwarmMember> res = new ArrayList<>();
        for (SwarmMember m : members) {
            if ((m.getClassifier()).equals(c)) res.add(m);
        }
        return res;
    }
}