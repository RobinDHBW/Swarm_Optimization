import com.sun.source.tree.TryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Wolfpack extends Swarm {
    private List<Double> upperLimits, lowerLimits;

//    private Wolf alphaWolf;
//    private Wolf betaWolf;
//    private Wolf deltaWolf;

    /**
     *
     * @param n
     * @param d
     * @param upperLimits
     * @param lowerLimits
     */
    public Wolfpack(Integer n, Integer d, List<Double> upperLimits, List<Double> lowerLimits){
        members = new ArrayList<SwarmMember>();

        this.upperLimits = upperLimits;
        this.lowerLimits = lowerLimits;

        //For N = Quantity of wolves --> construct a new wolf
        for(int i =0; i<n; i++){
            ArrayList<Double> positions = new ArrayList<Double>();
            Random rand = new Random();

            //Für D = Quantity of dimensions --> Random wolfposition between limits
            for(int z=0; z<d; z++){
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                positions.add(pos);
            }

            members.add(new Wolf(positions));
        }

    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    private Boolean compare(Double a, Double b){return a < b;}

    /**
     *
     * @param list
     */
    private void resetWolvesRanking(ArrayList<SwarmMember> list){
        for(SwarmMember member : list){
            member.accept(this, WolfClassifier.OMEGA);
        }
    }

    /**
     *
     * @param f
     */
    public void rankWolves(Function f){
        try {
            this.resetWolvesRanking(members);
            Double highestVal = 0.0;

            Wolf alphaWolf = null;
            Wolf betaWolf = null;
            Wolf deltaWolf = null;

            //find Alpha
            for(SwarmMember m : members){
                Wolf w = (Wolf) m;
                Double val = f.evaluate(w.getPositions());

                if(this.compare(val, highestVal)){
                    highestVal = val;
                    alphaWolf = w;
                }
            }

            //find Beta
            highestVal = 0.0;
            for(SwarmMember m : members){
                Wolf w = (Wolf) m;
                if(w.equals(alphaWolf)) continue;

                Double val = f.evaluate(w.getPositions());
                if(this.compare(val, highestVal)){
                    highestVal = val;
                    betaWolf = w;
                }
            }

            //find Delta
            highestVal = 0.0;
            for(SwarmMember m : members){
                Wolf w = (Wolf) m;
                if(w.equals(alphaWolf) || w.equals((betaWolf))) continue;

                Double val = f.evaluate(w.getPositions());
                if(this.compare(val, highestVal)){
                    highestVal = val;
                    deltaWolf = w;
                }
            }

            //classify all wolves per visitor
            for(SwarmMember m : members){
                Wolf w = (Wolf) m;
                WolfClassifier c = w.equals(alphaWolf) ? WolfClassifier.ALPHA : w.equals(betaWolf) ? WolfClassifier.BETA : w.equals(deltaWolf) ? WolfClassifier.DELTA : WolfClassifier.OMEGA;

                w.accept(this, c);

            }
        }catch (Exception ex){
            throw ex;
        }
    }


    @Override
    public SwarmSolution findMinimum(Function f) {
        return null;
    }

    @Override
    public SwarmSolution findMaximum(Function f) {
        return null;
    }

    @Override
    public void visit(Wolf w, Enum c) {
        w.setClassifier((WolfClassifier) c);
    }
}
