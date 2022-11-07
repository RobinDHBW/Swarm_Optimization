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
     * @param classifier
     */
    public void setClassifier(WolfClassifier classifier){
        this.classifier = classifier;
    }

    @Override
    public void accept(ISwarmVisitor visitor, Enum c) {
        visitor.resetClassifier(this);
        visitor.visit(this, (WolfClassifier) c);
    }
}
