import java.util.ArrayList;

/**
 *
 */
public class Wolf extends SwarmMember{
    private ArrayList<Double> position;
//    private WolfClassifier classifier;


    /**
     *
     * @param pos
     */
    public Wolf(ArrayList<Double> pos) {
        super();
        this.position = pos;
    }

    /**
     *
     * @param pos
     */
    public void addPosition(Double pos){
        position.add(pos);
    }

    /**
     *
     * @param i
     * @return
     */
    public Double getPositionFromIndex(Integer i){
        return position.get(i);
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getPosition() {
        return position;
    }

    /**
     *
     * @param index
     * @param val
     */
    public void setPositionAtIndex(Integer index, Double val){
        position.set(index, val);
    }



    @Override
    public void accept(ISwarmVisitor visitor, Enum c) {
        visitor.visit(this, (WolfClassifier) c);
    }
}
