import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RatSwarm extends Swarm {

    /**
     * Construct Ratswarm according to given parameters
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

    /**
     * Rank the rats in the swarm by comparing their positions near the prey, considering a sign
     * @param f
     * @param sign
     */
    private void rankMembers(Function f, Boolean sign) {
        try {
            //initially reset the rats ranking
            this.setMembersRanking(members, RatClassifier.MEMBER);

            //NEGATIVE_INFINITY for finding maximum, POSITIVE_INFINITY for finding minimum
            Double highestVal = this.resetHighestValue(sign);

            //Hold the best rat - representing the best solution
            Rat leader = null;

            //find leader (best solution)
            for (SwarmMember m : members) {
                Rat r = (Rat) m;
                //Calc the rats fitness
                Double val = f.evaluate(r.getPosition());

                if (this.compare(val, highestVal, sign)) {
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

    /**
     * Move Rat to next position
     * Fighting the prey
     * @param rm
     * @param a
     * @param r
     * @param c
     */
    private void moveMemberToNextPosition(Rat rm, Double a, Double r, Double c) {
        try {
            //Calc new position-value for each dimension
            for(int i=0; i<this.dimension; i++){
                Double leaderPos = this.getMemberByClassifier(RatClassifier.LEADER).getPositionFromIndex(i);
                Double ratPos = rm.getPositionFromIndex(i);

                //Position P respecting the leader
                Double p = a * ratPos + c * (leaderPos - ratPos);

                //Next position P_(x+1)
                Double pNext = Math.abs(leaderPos - p);

                //Move the rat to calculated position
                rm.setPositionAtIndex(i, pNext);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     *
     * @param f
     * @param iterationCount
     * @return
     */
    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        try {
            Random random = new Random();
            List<Double> solution = new ArrayList<Double>();

            //Calc the parameter A, C, R
            Double r = random.nextDouble(5 - 1) + 1;
            Double c = random.nextDouble(2 - 0) + 0;
            Double a = r - 0 * (r / iterationCount);

            //Initially rank rats and get leader
            rankMembers(f, false);
            Rat leader = (Rat) this.getMemberByClassifier(RatClassifier.LEADER);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

                //Move each Rat to next position
                for (SwarmMember m : members) {
                    Rat rm = (Rat) m;
                    this.moveMemberToNextPosition(rm, a, r, c);
                }

                //Calc the parameter A, C, R
                r = random.nextDouble(5 - 1) + 1;
                c = random.nextDouble(2 - 0) + 0;
                a = r - i * (r / iterationCount);

                //Trim Rats to limits in swarm
                this.catchLostMembers();

                //Rank Rats again by considering new positions and find leader
                this.rankMembers(f, false);
                leader = (Rat) this.getMemberByClassifier(RatClassifier.LEADER);

                ///Add leader solution for each iteration to solution list
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
        try {
            Random random = new Random();
            List<Double> solution = new ArrayList<Double>();

            //Calc the parameter A, C, R
            Double r = random.nextDouble(5 - 1) + 1;
            Double c = random.nextDouble(2 - 0) + 0;
            Double a = r - 0 * (r / iterationCount);

            //Initially rank rats and get leader
            rankMembers(f, true);
            Rat leader = (Rat) this.getMemberByClassifier(RatClassifier.LEADER);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

                //Move each Rat to next position
                for (SwarmMember m : members) {
                    Rat rm = (Rat) m;
                    this.moveMemberToNextPosition(rm, a, r, c);
                }

                //Calc the parameter A, C, R
                r = random.nextDouble(5 - 1) + 1;
                c = random.nextDouble(2 - 0) + 0;
                a = r - i * (r / iterationCount);

                //Trim Rats to limits in swarm
                this.catchLostMembers();

                //Rank Rats again by considering new positions and find leader
                this.rankMembers(f, true);
                leader = (Rat) this.getMemberByClassifier(RatClassifier.LEADER);

                ///Add leader solution for each iteration to solution list
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
