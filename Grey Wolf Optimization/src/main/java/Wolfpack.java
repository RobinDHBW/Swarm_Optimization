import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Wolfpack extends Swarm implements ISwarmSolve {


    /**
     * Construct Wolfpack according to given parameters
     * @param packSize (Integer)
     * @param dimension (Integer)
     * @param upperLimits (List<Double>)
     * @param lowerLimits (List<Double>)
     */
    public Wolfpack(Integer packSize, Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);
        members = new ArrayList<SwarmMember>();

        //Construct wolves in requested quantity and add to pack
        for (int i = 0; i < packSize; i++) {
            ArrayList<Double> position = new ArrayList<Double>();

            //Calculate a random wolfposition between limits
            for (int z = 0; z < dimension; z++) {
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                position.add(pos);
            }

            members.add(new Wolf(position));
        }

    }





    /**
     * Move Wolf to next position
     * Hunting
     * @param a (Double)
     * @param w (Wolf)
     */
    private void moveMemberToNextPosition(Double a, Wolf w){
        try{
            Random rand = new Random();

            //Calc new position-value for each dimension
            for(int i =0; i<this.dimension; i++) {

                Double alphaPos = this.getMemberByClassifier(WolfClassifier.ALPHA).get(0).getPositionFromIndex(i);
                Double betaPos = this.getMemberByClassifier(WolfClassifier.BETA).get(0).getPositionFromIndex(i);
                Double deltaPos = this.getMemberByClassifier(WolfClassifier.DELTA).get(0).getPositionFromIndex(i);
                Double currentWolfPos = w.getPositionFromIndex(i);

                /*-----Calculate X1-----*/
                //Generate two random numbers
                Double rand1 = rand.nextDouble();
                Double rand2 = rand.nextDouble();

                //Calc A and C for Alpha
                Double a1 = 2 * a * rand1 - a;
                Double c1 = 2 * rand2;

                //Calc D for Alpha
                Double dAlpha =Math.abs(c1 * alphaPos - currentWolfPos);
                //Calc x1
                Double x1 = alphaPos - a1 * dAlpha;

                /*-----Calculate X2-----*/
                //Generate two random numbers
                rand1 = rand.nextDouble();
                rand2 = rand.nextDouble();

                //Calc A and C for Beta
                Double a2 = 2 * a * rand1 - a;
                Double c2 = 2 * rand2;

                //Calc D for Beta
                Double dBeta = Math.abs(c2 * betaPos - currentWolfPos);
                //Calc x2
                Double x2 = betaPos - a2 * dBeta;

                /*-----Calculate X2-----*/
                //Generate two random numbers
                rand1 = rand.nextDouble();
                rand2 = rand.nextDouble();

                //Calc A and C for Delta
                Double a3 = 2 * a * rand1 - a;
                Double c3 = 2 * rand2;

                //Calc D for Delta
                Double dDelta = Math.abs(c3 * deltaPos - currentWolfPos);
                //Calc x3
                Double x3 = deltaPos - a3 * dDelta;

                //Set the wolf to next position X_(t+1) at index i
                w.setPositionAtIndex(i, ((x1+x2+x3)/3));
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * Rank the wolves in the pack by comparing their positions near the prey, considering a sign
     * @param f (Function)
     * @param sign (Boolean) - true to find maxima, false to find minima
     */
    protected void rankMembers(Function f, Boolean sign) {
        try {
            //initially reset the wolves ranking
            this.setMembersRanking(members, WolfClassifier.OMEGA);

            //NEGATIVE_INFINITY for finding maximum, POSITIVE_INFINITY for finding minimum
            Double highestVal = resetHighestValue(sign);

            //Hold the best wolves - representing the best solutions
            Wolf alphaWolf = null;
            Wolf betaWolf = null;
            Wolf deltaWolf = null;

            //find Alpha
            for (SwarmMember m : members) {
                Wolf w = (Wolf) m;
                //Calc this Wolf's fitness
                Double val = f.evaluate(w.getPosition());

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

                //Calc this Wolf's fitness
                Double val = f.evaluate(w.getPosition());
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

                //Calc this Wolf's fitness
                Double val = f.evaluate(w.getPosition());
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
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }


    /**
     * Override method from Swarm interface
     * Find global minimum by approximating the solution
     * @param f (Function)
     * @param iterationCount (Integer)
     * @return SwarmSolution
     */
    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        try {
            List<Double> solution = new ArrayList<Double>();
            Double max = 2.0;

            //Initially rank Wolves and get alpha
            rankMembers(f, false);
            Wolf alpha = (Wolf) this.getMemberByClassifier(WolfClassifier.ALPHA).get(0);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

                //Linear decreasing a from 2.0 to 0 by each iteration
                Double a = max - i * max / (double) iterationCount;

                //Move each wolf to next position
                for (SwarmMember m : members) {
                    Wolf w = (Wolf) m;
                    this.moveMemberToNextPosition(a, w);
                }

                //Trim Wolves to limits
                catchLostMembers();

                //Rank Wolves again by considering new positions and find alpha
                rankMembers(f, false);
                alpha = (Wolf) this.getMemberByClassifier(WolfClassifier.ALPHA).get(0);

                //Add alpha solution for each iteration to solution list
                solution.add(f.evaluate(alpha.getPosition()));
            }

            //GWO finished for given iterations, return achieved solution
            return new SwarmSolution(solution);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    /**
     * Override method from Swarm interface
     * Find global maximum by approximating the solution
     * @param f (Function)
     * @param iterationCount (Integer)
     * @return
     */
    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        try {
            List<Double> solution = new ArrayList<Double>();
            Double max = 2.0;

            //Initially rank Wolves and get alpha
            rankMembers(f, true);
            Wolf alpha = (Wolf) this.getMemberByClassifier(WolfClassifier.ALPHA);

            //Approximate the solution using the pack for given iterations
            for (int i = 0; i < iterationCount; i++) {

                //Linear decreasing a from 2.0 to 0 by each iteration
                Double a = max - i * max / (double) iterationCount;

                //Move each wolf to next position
                for (SwarmMember m : members) {
                    Wolf w = (Wolf) m;
                    this.moveMemberToNextPosition(a, w);
                }

                //Trim Wolves to limits
                catchLostMembers();

                //Rank Wolves again by considering new positions and find alpha
                rankMembers(f, true);
                alpha = (Wolf) this.getMemberByClassifier(WolfClassifier.ALPHA);

                //Add alpha solution for each iteration to solution list
                solution.add(f.evaluate(alpha.getPosition()));
            }

            //GWO finished for given iterations, return achieved solution
            return new SwarmSolution(solution);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    /**
     * Override method from visitor interface
     * @param w
     * @param c
     */
    @Override
    public void visit(SwarmMember w, Enum c) {
        w.setClassifier(c);
    }


}
