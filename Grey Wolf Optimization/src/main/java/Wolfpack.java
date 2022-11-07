import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Wolfpack extends Swarm {
    private List<Double> upperLimits, lowerLimits;

    /**
     * Construct Wolfpack according to given parameters
     * @param packSize
     * @param dimension
     * @param upperLimits
     * @param lowerLimits
     */
    public Wolfpack(Integer packSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        members = new ArrayList<SwarmMember>();

        //Save list sizes to variables, because it's a very expensive operation
        Integer uSize = upperLimits.size();
        Integer lSize = lowerLimits.size();

        //Check if arguments for limits fit, set to default values if not
        if(uSize != dimension || uSize == 0 || lSize != dimension || lSize == 0 || uSize != lSize){
            this.upperLimits = new ArrayList<Double>(Collections.nCopies(dimension,1.0));
            this.lowerLimits = new ArrayList<Double>(Collections.nCopies(dimension,0.0));
        }else{
            this.upperLimits = upperLimits;
            this.lowerLimits = lowerLimits;
        }

        //Construct wolves in requested quantity and add to pack
        for (int i = 0; i < packSize; i++) {
            ArrayList<Double> positions = new ArrayList<Double>();
            Random rand = new Random();

            //Calculate a random wolfposition between limits
            for (int z = 0; z < dimension; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                positions.add(pos);
            }

            members.add(new Wolf(positions));
        }

    }

    /**
     * Simple helper method to compare two values, considering a sign
     * @param a
     * @param b
     * @param sign (Boolean) - true to find maxima, false to find minima
     * @return
     */
    private Boolean compare(Double a, Double b, Boolean sign) {
        return sign ? a > b : a < b;
    }

    /**
     * Use visitor to reset the wolf ranking
     * @param list
     */
    private void resetWolvesRanking(ArrayList<SwarmMember> list) {
        for (SwarmMember member : list) {
            member.accept(this, WolfClassifier.OMEGA);
        }
    }

    /**
     * Simple helper method to set a Double value to NEGATIVE_INFINITY or POSITIVE_INFINITY
     * @param sign
     * @return
     */
    private Double resetHighestValue(Boolean sign) {
        return sign ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    /**
     * Part of 'hunting the prey'
     * Rank the wolves in the pack by comparing their positions near the prey, considering a sign
     * @param f
     * @param sign (Boolean) - true to find maxima, false to find minima
     */
    public void rankWolves(Function f, Boolean sign) {
        try {
            //initially reset the wolves ranking
            this.resetWolvesRanking(members);

            //NEGATIVE_INFINITY for finding maximum, POSITIVE_INFINITY for finding minimum
            Double highestVal = resetHighestValue(sign);

            //Hold the best wolves - representing the best solutions
            Wolf alphaWolf = null;
            Wolf betaWolf = null;
            Wolf deltaWolf = null;

            //find Alpha
            for (SwarmMember m : members) {
                Wolf w = (Wolf) m;
                Double val = f.evaluate(w.getPositions());

                if (this.compare(val, highestVal, sign)) {
                    highestVal = val;
                    alphaWolf = w;
                }
            }

            //find Beta
            highestVal = resetHighestValue(sign);
            for (SwarmMember m : members) {
                Wolf w = (Wolf) m;
                if (w.equals(alphaWolf)) continue;

                Double val = f.evaluate(w.getPositions());
                if (this.compare(val, highestVal, sign)) {
                    highestVal = val;
                    betaWolf = w;
                }
            }

            //find Delta
            highestVal = resetHighestValue(sign);
            for (SwarmMember m : members) {
                Wolf w = (Wolf) m;
                if (w.equals(alphaWolf) || w.equals((betaWolf))) continue;

                Double val = f.evaluate(w.getPositions());
                if (this.compare(val, highestVal, sign)) {
                    highestVal = val;
                    deltaWolf = w;
                }
            }

            //classify all wolves per visitor
            for (SwarmMember m : members) {
                Wolf w = (Wolf) m;
                WolfClassifier c = w.equals(alphaWolf) ? WolfClassifier.ALPHA : w.equals(betaWolf) ? WolfClassifier.BETA : w.equals(deltaWolf) ? WolfClassifier.DELTA : WolfClassifier.OMEGA;

                w.accept(this, c);

            }
        } catch (Exception ex) {
            throw ex;
        }
    }


    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        try {
            rankWolves(f, false);
            List<Double> solution = new ArrayList<Double>();
            Double max = 2.0;
            Wolf alpha = this.getAlpha();

            for (int i = 0; i < iterationCount; i++) {
                Double a = max - i * max / (double) i;

                for (SwarmMember m : members) {
                    Wolf w = (Wolf) m;
                    //@TODO: move the wolf to a
                    //@TODO: Do I have to verwurschtel the limits here?
                }

                rankWolves(f, false);
                alpha = this.getAlpha();
                solution.add(f.evaluate(alpha.getPositions()));
            }
            return new SwarmSolution(alpha, iterationCount, solution);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        try {
            rankWolves(f, true);
            List<Double> solution = new ArrayList<Double>();
            Double max = 2.0;
            Wolf alpha = this.getAlpha();

            for (int i = 0; i < iterationCount; i++) {
                Double a = max - i * max / (double) i;

                for (SwarmMember m : members) {
                    Wolf w = (Wolf) m;
                    //@TODO: move the wolf to a
                    //@TODO: Do I have to verwurschtel the limits here?

                }

                rankWolves(f, true);
                alpha = this.getAlpha();
                solution.add(f.evaluate(alpha.getPositions()));
            }
            return new SwarmSolution(alpha, iterationCount, solution);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void visit(SwarmMember w, Enum c) {
        w.setClassifier((WolfClassifier) c);
    }

    /**
     * @return
     */
    public Wolf getAlpha() {
        for (SwarmMember m : members) {
            if (m.getClassifier().equals(WolfClassifier.ALPHA)) return (Wolf) m;
        }
        return null;
    }

}
