import java.util.ArrayList;

public abstract class SwarmGroup implements ISwarmVisitor{
    protected ArrayList<SwarmMember> members;

    /**
     * abstract method to rank SwarmMembers
     * @param f - (Function)
     * @param sign - (Boolean)
     */
    protected abstract void rankMembers(Function f, Boolean sign);

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

    /**
     * Use visitor to reset the SwarmMember ranking
     * @param list (ArrayList<SwarmMember>)
     */
    protected void setMembersRanking(ArrayList<SwarmMember> list, Enum c) {
        for (SwarmMember member : list) {
            member.accept(this, c);
        }
    }

    /**
     * Get the corresponding SwarmMember for a given Classifier from Swarm
     * @param c (Enum)
     * @return
     */
    protected SwarmMember getMemberByClassifier(Enum c) {
        for (SwarmMember m : members) {
            if ((m.getClassifier()).equals(c)) return m;
        }
        return null;
    }
}
