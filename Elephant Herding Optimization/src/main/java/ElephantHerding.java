import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ElephantHerding extends Swarm implements ISwarmSolve {
    List<ElephantClan> clans;


    public ElephantHerding(Integer herdSize, Integer clanSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);

        clans = new ArrayList<ElephantClan>();

        for (int i = 0; i < (herdSize / clanSize); i++) {
            clans.add(new ElephantClan(clanSize, dimension, upperLimits, lowerLimits));
        }
    }

    private Elephant findBestMatriarch(List<SwarmMember> matriarchs, Function f, Boolean sign) {
        try {
            Double highestVal = resetHighestValue(sign);
            Elephant best = null;
            for (SwarmMember m : matriarchs) {
                Double val = f.evaluate(m.getPosition());
                if (this.compare(val, highestVal, sign)) {
                    highestVal = val;
                    best = (Elephant) m;
                }
            }
            return best;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    /**
     * Move Elephant to next position
     * Clan updating operator
     */
    private void moveClanToNextPosition(ElephantClan ec) {
        try {
            Random random = new Random();

            for (SwarmMember m : ec.members) {
                //Calc the parameter alpha, beta, r - all of which are in [0,1]
                Double a = random.nextDouble();
                Double b = random.nextDouble();
                Double r = random.nextDouble();

                Elephant e = (Elephant) m;
                ec.moveMemberToNextPosition(e, a, b, r);
            }

            //Calc new position-value for each dimension
//            for(int i=0; i<this.dimension; i++){
//                Double leaderPos = this.getMemberByClassifier(RatClassifier.LEADER).get(0).getPositionFromIndex(i);
//                Double ratPos = rm.getPositionFromIndex(i);
//
//                //Position P respecting the leader
//                Double p = a * ratPos + c * (leaderPos - ratPos);
//
//                //Next position P_(x+1)
//                Double pNext = Math.abs(leaderPos - p);
//
//                //Move the rat to calculated position
//                rm.setPositionAtIndex(i, pNext);
//            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    @Override
    protected List<SwarmMember> getMemberByClassifier(Enum c) {
        try {
            List<SwarmMember> matriarchs = new ArrayList<>();

            for(ElephantClan ec : this.clans){
                matriarchs.add(ec.getMemberByClassifier(ElephantClassifier.MATRIARCH).get(0));
            }
            return  matriarchs;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }


    @Override
    public void visit(SwarmMember e, Enum c) {
        e.setClassifier(c);
    }

    @Override
    protected void rankMembers(Function f, Boolean sign) {
        for (ElephantClan c : clans) {
            c.rankMembers(f, sign);
        }
    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        try {
            Random random = new Random();
            List<Double> solution = new ArrayList<Double>();


            //Initially rank elephants and get leader
            rankMembers(f, false);
            Elephant leader = this.findBestMatriarch(this.getMemberByClassifier(ElephantClassifier.MATRIARCH), f, false);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

                // Move each clan
                for (ElephantClan ec : clans) {
                    this.moveClanToNextPosition(ec);
                }

                rankMembers(f, false);

                // Separate worst member
                for (ElephantClan ec : clans) {
                    ec.separateWorst(f);
                }

                //rank elephants again by considering new positions and find new leader
                rankMembers(f, false);
                leader = this.findBestMatriarch(this.getMemberByClassifier(ElephantClassifier.MATRIARCH), f, false);

                ///Add leader solution for each iteration to solution list
                solution.add(f.evaluate(leader.getPosition()));
            }
            return new SwarmSolution(solution);
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
}
