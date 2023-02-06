import java.util.ArrayList;

public abstract class SwarmMember implements ISwarmMember {

    //One position in space, with multiple dimensions
    protected ArrayList<Double> position;
    protected Enum classifier;

    public SwarmMember(ArrayList<Double> pos){
        this.position = pos;
    }

    /**
     * Getter for Classifier Enum
     *
     * @return
     */
    public Enum getClassifier() {
        return classifier;
    }

    /**
     * Setter for Classifier Enum
     *
     * @param classifier (Enum)
     */
    public void setClassifier(Enum classifier) {
        this.classifier = classifier;
    }

    /**
     * Return a certain coordinate from position
     *
     * @param i (Integer)
     * @return
     */
    public Double getPositionFromIndex(Integer i) {
        return position.get(i);
    }

    /**
     * Get the full position
     *
     * @return
     */
    public ArrayList<Double> getPosition() {
        return position;
    }

    /**
     * Set a certain coordinate in position
     *
     * @param i (Integer)
     * @param val   (Double)
     */
    public void setPositionAtIndex(Integer i, Double val) {
        position.set(i, val);
    }


}
