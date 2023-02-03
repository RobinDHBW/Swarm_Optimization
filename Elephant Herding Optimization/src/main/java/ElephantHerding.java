import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ElephantHerding extends Swarm implements ISwarmSolve {
    private List<ElephantClan> clans;
    private Double a, b, r;

    /**
     * Construct elephant herd according to given parameters
     *
     * @param herdSize      (Integer)
     * @param clanSize      (Integer)
     * @param separateCount (Integer)
     * @param a             (Double)
     * @param b             (Double)
     * @param r             (Double)
     * @param dimension     (Integer)
     * @param upperLimits   (List<Double>)
     * @param lowerLimits   (List<Double>)
     */
    public ElephantHerding(Integer herdSize, Integer clanSize, Integer separateCount, Double a, Double b, Double r, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);

        this.a = a;
        this.b = b;
        this.r = r;

        // Construct clans in herd
        clans = new ArrayList<ElephantClan>();
        for (int i = 0; i < (herdSize / clanSize); i++) {
            clans.add(new ElephantClan(clanSize, separateCount, dimension, upperLimits, lowerLimits));
        }
    }

    /**
     * Find fittest matriarch of all clans
     *
     * @param matriarchs
     * @param f
     * @param sign
     * @return
     */
    private Elephant findBestMatriarch(List<SwarmMember> matriarchs, IFunction f, Boolean sign) {
        try {

            Double highestVal = resetHighestValue(sign);
            Elephant best = null;

            // Compare all matriarchs, to find best one
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
     *
     * @param ec (ElephantClan)
     */
    private void moveClanToNextPosition(ElephantClan ec) {
        try {
            Random random = new Random();

            for (SwarmMember m : ec.members) {
                //Calc the parameter alpha, beta, r - all of which are in [0,1]
//                Double a = random.nextDouble();
//                Double b = random.nextDouble();
//                Double r = random.nextDouble();

                Elephant e = (Elephant) m;
                ec.moveMemberToNextPosition(e, this.a, this.b, this.r);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * Override method to get the corresponding SwarmMembers for a given Classifier for all clans
     *
     * @param c (Enum)
     * @return
     */
    @Override
    protected List<SwarmMember> getMemberByClassifier(Enum c) {
        try {
            List<SwarmMember> matriarchs = new ArrayList<>();

            for (ElephantClan ec : this.clans) {
                matriarchs.add(ec.getMemberByClassifier(ElephantClassifier.MATRIARCH).get(0));
            }
            return matriarchs;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }


    /**
     * Override method from visitor interface
     *
     * @param e (SwarmMember)
     * @param c (Enum)
     */
    @Override
    public void visit(SwarmMember e, Enum c) {
        e.setClassifier(c);
    }

    /**
     * Override method to rank members for all clans
     *
     * @param f    - (IFunction)
     * @param sign - (Boolean)
     */
    @Override
    protected void rankMembers(IFunction f, Boolean sign) {
        for (ElephantClan c : clans) {
            c.rankMembers(f, sign);
        }
    }

    /**
     * Override method from SwarmSolve interface
     * Find global minimum by approximating the solution
     *
     * @param f              (IFunction)
     * @param iterationCount (Integer)
     * @return
     */
    @Override
    public SwarmSolution findMinimum(IFunction f, Integer iterationCount) {
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
}
