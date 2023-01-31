import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ElephantClan extends SwarmGroup {

    public ElephantClan(Integer clanSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        members = new ArrayList<>();

        //Construct elephants in requested quantity and add to clan
        for (int i = 0; i < clanSize; i++) {
            ArrayList<Double> position = new ArrayList<Double>();

            //Calculate a random elephantposition between limits
            for (int z = 0; z < dimension; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(lowerLimits.get(z), upperLimits.get(z));
                position.add(pos);
            }

            members.add(new Elephant(position));
        }
    }

    @Override
    public void visit(SwarmMember e, Enum c) {
        e.setClassifier(c);
    }

    /**
     * Rank the elephants in the clan by comparing their fitness, considering a sign
     * @param f - (Function)
     * @param sign - (Boolean)
     */
    @Override
    protected void rankMembers(Function f, Boolean sign) {
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
