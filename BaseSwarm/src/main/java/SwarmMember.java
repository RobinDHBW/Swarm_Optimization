import java.util.ArrayList;

public abstract class SwarmMember implements ISwarmMember{

    //One position in space, with multiple dimensions
    protected ArrayList<Double> position;
    protected Enum classifier;

    /**
     * Setter for Classifier Enum
     * @param classifier (Enum)
     */
    public void setClassifier(Enum classifier){
        this.classifier = classifier;
    }

    /**
     * Getter for Classifier Enum
     * @return
     */
    public Enum getClassifier() {
        return classifier;
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


}
