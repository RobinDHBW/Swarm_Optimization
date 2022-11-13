import java.util.ArrayList;

public class Wolf extends SwarmMember{

    //One position in space, with multiple dimensions
    private ArrayList<Double> position;


    /**
     *
     * @param pos
     */
    public Wolf(ArrayList<Double> pos) {
        super();
        this.position = pos;
    }

    /**
     * Return a certain coordinate from position
     * @param i (Integer)
     * @return
     */
    public Double getPositionFromIndex(Integer i){
        return position.get(i);
    }

    /**
     * Get the full position
     * @return
     */
    public ArrayList<Double> getPosition() {
        return position;
    }

    /**
     * Set a certain coordinate in position
     * @param index (Integer)
     * @param val (Double)
     */
    public void setPositionAtIndex(Integer index, Double val){
        position.set(index, val);
    }

    /**
     * Accepting method from Visitor interface
     * @param visitor (ISwarmVisitor)
     * @param c (Enum)
     */
    @Override
    public void accept(ISwarmVisitor visitor, Enum c) {
        visitor.visit(this, c);
    }
}
