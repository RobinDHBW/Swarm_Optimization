import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RatSwarm extends Swarm {

    /**
     * Construct Ratswarm according to given parameters
     *
     * @param swarmSize
     * @param dimension
     * @param upperLimits
     * @param lowerLimits
     */
    public RatSwarm(Integer swarmSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);
        members = new ArrayList<SwarmMember>();

        //Construct rats in requested quantity and add to swarm
        for (int i = 0; i < swarmSize; i++) {
            ArrayList<Double> position = new ArrayList<>();

            //Calculate a random ratposition between limits
            for (int z = 0; z < dimension; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                position.add(pos);
            }

            members.add(new Rat(position));

        }
    }

    private void rankMembers(Function f, Boolean sign) {
        try {
            this.setMembersRanking(members, RatClassifier.MEMBER);

            Double highestVal = this.resetHighestValue(sign);

            Rat leader = null;

            for(SwarmMember m : members){
                Rat r = (Rat) m;
                Double val = f.evaluate(r.getPosition());

                if(this.compare(val, highestVal, sign)){
                    highestVal = val;
                    leader = r;
                }
            }

            //classify all rats per visitor
            for (SwarmMember m : members) {
                RatClassifier c = m.equals(leader) ? RatClassifier.LEADER : RatClassifier.MEMBER;
                m.accept(this, c);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    private void moveMemberToNextPosition(Rat r) {

    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        try {
            List<Double> solution = new ArrayList<Double>();
            //TODO Calc R, A, C

            rankMembers(f, false);
            Rat leader = (Rat) this.getMemberByClassifier(RatClassifier.LEADER);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

                //Move each Rat to next position
                for (SwarmMember m : members) {
                    Rat r = (Rat) m;
                    this.moveMemberToNextPosition(r); //TODO Implement it
                }

                //TODO Recalc R, A, C

                //Trim Rats to limits in swarm
                this.catchLostMembers();

                //Rank Rats again by considering new positions and find leader
                this.rankMembers(f, false);
                leader = (Rat) this.getMemberByClassifier(RatClassifier.LEADER);

                solution.add(f.evaluate(leader.getPosition()));
            }

            //RSO finished for given iterations, return achieved solution
            return new SwarmSolution(leader, iterationCount, solution);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        return null;
    }

    /**
     * Override method from visitor interface
     *
     * @param r
     * @param c
     */
    @Override
    public void visit(SwarmMember r, Enum c) {
        r.setClassifier(c);
    }
}
