import java.util.ArrayList;

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
