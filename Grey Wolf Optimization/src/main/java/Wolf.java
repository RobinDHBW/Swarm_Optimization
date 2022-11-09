import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 */
public class Wolf extends SwarmMember{
    private ArrayList<Double> positions;
//    private WolfClassifier classifier;


    /**
     *
     * @param pos
     */
    public Wolf(ArrayList<Double> pos) {
        super();
        this.positions = pos;
    }

    /**
     * Calc wolf movements
     * @param a
     * @param alphaPos
     * @param betaPos
     * @param deltaPos
     * @return
     */
    private Double calcMovement(Double a, Double alphaPos, Double betaPos, Double deltaPos){
//        TODO move calculation to wolfpack
        try{
            Random rand = new Random();
//            IntStream.range(0, this.positions.size()).forEach(index -> {
                Double rand1 = rand.nextDouble();
                Double rand2 = rand.nextDouble();

                Double a1 = 2*a*rand1-a;
                Double c1 = 2*rand2;

                Double x1 = alphaPos - a1 * Math.abs(c1 * alphaPos - alphaPos);

                 rand1 = rand.nextDouble();
                 rand2 = rand.nextDouble();

                Double a2 = 2*a*rand1-a;
                Double c2 = 2*rand2;

                Double x2 = betaPos - a2 * Math.abs(c2 * betaPos - betaPos);

                rand1 = rand.nextDouble();
                rand2 = rand.nextDouble();

                Double a3 = 2*a*rand1-a;
                Double c3 = 2*rand2;

                Double x3 = deltaPos - a3 * Math.abs(c3 * deltaPos - deltaPos);

                return ((x1+x2+x3)/3);
//            });
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     *
     * @param pos
     */
    public void addPosition(Double pos){
        positions.add(pos);
    }

    /**
     *
     * @param i
     * @return
     */
    public Double getPositionFromIndex(Integer i){
        return positions.get(i);
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getPositions() {
        return positions;
    }

    /**
     *
     * @param index
     * @param val
     */
    public void setPositionAtIndex(Integer index, Double val){
        positions.set(index, val);
    }



    @Override
    public void accept(ISwarmVisitor visitor, Enum c) {
        visitor.visit(this, (WolfClassifier) c);
    }
}
