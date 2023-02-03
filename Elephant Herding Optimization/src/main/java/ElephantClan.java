import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ElephantClan extends SwarmGroup {
    private Integer separateCount;

    /**
     * Construct elephant clan according to given parameters
     *
     * @param clanSize      (Integer)
     * @param separateCount (Integer)
     * @param dimension     (Integer)
     * @param upperLimits   (List<Double>)
     * @param lowerLimits   (List<Double>)
     */
    public ElephantClan(Integer clanSize, Integer separateCount, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);
        this.members = new ArrayList<>();
        this.dimension = dimension;
        this.separateCount = separateCount;

        //Construct elephants in requested quantity and add to clan
        for (int i = 0; i < clanSize; i++) {
            ArrayList<Double> position = new ArrayList<Double>();

            //Calculate a random elephantposition between limits
            for (int z = 0; z < this.dimension; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(lowerLimits.get(z), upperLimits.get(z));
                position.add(pos);
            }

            this.members.add(new Elephant(position));
        }
    }

    /**
     * Separate worst elephant by substitution through new position
     *
     * @param f (IFunction)
     */
    public void separateWorst(IFunction f) {
        try {

            //List of tuples from elephant and fitness value
            List<AbstractMap.SimpleEntry<Elephant, Double>> valTuples = new ArrayList<>();
            //calc fitness for all elephants in clan
            for (SwarmMember m : members) {
                Elephant r = (Elephant) m;
                //Calc the elephants fitness
                Double val = f.evaluate(r.getPosition());
                valTuples.add(new AbstractMap.SimpleEntry<Elephant, Double>(r, val));
            }

            //Sort elephants by fitness
            valTuples.sort(Comparator.comparing(AbstractMap.SimpleEntry::getValue));

            //Separate worst elephants by replacing positions
            Integer l = valTuples.size();
            for (AbstractMap.SimpleEntry<Elephant, Double> entry : valTuples.subList(l-separateCount-1, l-1)){
                Elephant e = entry.getKey();
                for (int i = 0; i < e.position.size(); i++) {
                    e.setPositionAtIndex(i, lowerLimits.get(i) + (upperLimits.get(i) - lowerLimits.get(i) + 1) * Math.random());
                }
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * Move Elephant to next position
     * Clan updating operator
     *
     * @param e (Elephant)
     * @param a (Double)
     * @param b (Double)
     * @param r (Double)
     */
    public void moveMemberToNextPosition(Elephant e, Double a, Double b, Double r) {
        try {

            //Calc new position-value for each dimension
            for (int i = 0; i < this.dimension; i++) {
                Double leaderPos = this.getMemberByClassifier(ElephantClassifier.MATRIARCH).get(0).getPositionFromIndex(i);
                Double elephantPos = e.getPositionFromIndex(i);

                Double pNext;
                if (e.getClassifier().equals(ElephantClassifier.MATRIARCH)) {
                    Double sum = 0.0;
                    for (int z = 0; z < this.members.size(); z++) {
                        sum += members.get(z).getPositionFromIndex(i);
                    }

                    Double centerPos = (1 / this.members.size()) * sum;
                    pNext = b * centerPos;
                } else {
                    pNext = elephantPos + a * (leaderPos - elephantPos) * r;
                }

                //Move the elephant to calculated position
                e.setPositionAtIndex(i, pNext);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * Override method from Visitor interface
     *
     * @param e (SwarmMember)
     * @param c (Enum)
     */
    @Override
    public void visit(SwarmMember e, Enum c) {
        e.setClassifier(c);
    }

    /**
     * Rank the elephants in the clan by comparing their fitness, considering a sign
     *
     * @param f    - (IFunction)
     * @param sign - (Boolean)
     */
    @Override
    protected void rankMembers(IFunction f, Boolean sign) {
        try {
            //initially reset the elephants ranking
            this.setMembersRanking(members, ElephantClassifier.MEMBER);

            //NEGATIVE_INFINITY for finding maximum, POSITIVE_INFINITY for finding minimum
            Double highestVal = this.resetHighestValue(sign);

            //Hold the best elephant - representing the best solution
            Elephant leader = null;

            //find matriarch (best solution)
            for (SwarmMember m : members) {
                Elephant r = (Elephant) m;
                //Calc the rats fitness
                Double val = f.evaluate(r.getPosition());

                if (this.compare(val, highestVal, sign)) {
                    highestVal = val;
                    leader = r;
                }
            }

            //classify all elephants per visitor
            for (SwarmMember m : members) {
                ElephantClassifier c = m.equals(leader) ? ElephantClassifier.MATRIARCH : ElephantClassifier.MEMBER;
                m.accept(this, c);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }


}
