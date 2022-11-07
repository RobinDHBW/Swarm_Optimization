import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Wolfpack extends Swarm {
    private List<Double> upperLimits, lowerLimits;

//    private Wolf alphaWolf;
//    private Wolf betaWolf;
//    private Wolf deltaWolf;

    /**
     * @param n
     * @param d
     * @param upperLimits
     * @param lowerLimits
     */
    public Wolfpack(Integer n, Integer d, List<Double> upperLimits, List<Double> lowerLimits) {
        members = new ArrayList<SwarmMember>();

        this.upperLimits = upperLimits;
        this.lowerLimits = lowerLimits;

        //For N = Quantity of wolves --> construct a new wolf
        for (int i = 0; i < n; i++) {
            ArrayList<Double> positions = new ArrayList<Double>();
            Random rand = new Random();

            //Für D = Quantity of dimensions --> Random wolfposition between limits
            for (int z = 0; z < d; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                positions.add(pos);
            }

            members.add(new Wolf(positions));
        }

    }

    /**
     * @param a
     * @param b
     * @param sign (Boolean) - true to find maxima, false to find minima
     * @return
     */
    private Boolean compare(Double a, Double b, Boolean sign) {
        return sign ? a > b : a < b;
    }

    /**
     * @param list
     */
    private void resetWolvesRanking(ArrayList<SwarmMember> list) {
        for (SwarmMember member : list) {
            member.accept(this, WolfClassifier.OMEGA);
        }
    }

    private Double resetHighestValue(Boolean sign) {
        return sign ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    }

    /**
     * @param f
     */
    public void rankWolves(Function f, Boolean sign) {
        try {
            this.resetWolvesRanking(members);
            Double highestVal = resetHighestValue(sign);

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
