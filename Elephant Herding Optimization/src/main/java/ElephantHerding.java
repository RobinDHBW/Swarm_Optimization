import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ElephantHerding extends Swarm implements ISwarmSolve {
    List<ElephantClan> clans;


    public ElephantHerding(Integer herdSize, Integer clanSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits){
        super(dimension, upperLimits, lowerLimits);

        clans = new ArrayList<ElephantClan>();

        for(int i=0; i<herdSize/clanSize; i++){
            clans.add(new ElephantClan(clanSize, dimension, upperLimits, lowerLimits));
        }
    }

    private Elephant findBestMatriarch(List<SwarmMember> matriarchs, Function f, Boolean sign){
        try{
            Double highestVal = resetHighestValue(sign);
            Elephant best = null;
                for(SwarmMember m : matriarchs){
                    Double val = f.evaluate(m.getPosition());
                   if( this.compare(val, highestVal, sign)){
                       highestVal = val;
                       best = (Elephant) m;
                   }
                }
            return best;
        }catch (Exception ex) {
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
        for (ElephantClan c : clans){
            c.rankMembers(f, sign);
        }
    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        try{
            Random random = new Random();
            List<Double> solution = new ArrayList<Double>();

            //Calc the parameter A, C, R
//        Double r = random.nextDouble(5 - 1) + 1;
//        Double c = random.nextDouble(2 - 0) + 0;
//        Double a = r - 0 * (r / iterationCount);

            //Initially rank elephants and get leader
            rankMembers(f, false);
            Elephant leader = this.findBestMatriarch(this.getMemberByClassifier(ElephantClassifier.MATRIARCH), f, false);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

            }
        }catch (Exception ex) {
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
