import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RatSwarm extends Swarm{

    /**
     * Construct Ratswarm according to given parameters
     * @param swarmSize
     * @param dimension
     * @param upperLimits
     * @param lowerLimits
     */
    public RatSwarm(Integer swarmSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits){
        super(dimension, upperLimits, lowerLimits);
        members = new ArrayList<SwarmMember>();

        //Construct rats in requested quantity and add to swarm
        for(int i = 0; i < swarmSize; i++){
            ArrayList<Double> position = new ArrayList<>();

            //Calculate a random ratposition between limits
            for (int z = 0; z < dimension; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                position.add(pos);
            }

            members.add(new Wolf(position));

        }
    }


    private void rankMembers(){

    }

    private void moveMemberToNextPosition(){

    }

    private void catchLostMembers(){

    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        return null;
    }

    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        return null;
    }

    /**
     * Override method from visitor interface
     * @param r
     * @param c
     */
    @Override
    public void visit(SwarmMember r, Enum c) {
        r.setClassifier(c);
    }
}
