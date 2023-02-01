import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Swarm extends SwarmGroup{

    public Swarm(Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension,upperLimits, lowerLimits);
    }

    /**
     * If a SwarmMember is outside the limit, set the position to the corresponding limit
     * Trim swarm to search around best solutions
     */
    protected void catchLostMembers() {
        try {
            for (SwarmMember m : members) {
                List<Double> positions = m.getPosition();

                //Limitcheck for all positions
                for (int i = 0; i < positions.size(); i++) {

                    Double lLimit = lowerLimits.get(i);
                    Double uLimit = upperLimits.get(i);

                    //Check if position is below lower limit
                    if (positions.get(i) < lLimit) {
                        m.setPositionAtIndex(i, lLimit);
                    }

                    //Check if position is above upper limit
                    if (positions.get(i) > uLimit) {
                        m.setPositionAtIndex(i, uLimit);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
