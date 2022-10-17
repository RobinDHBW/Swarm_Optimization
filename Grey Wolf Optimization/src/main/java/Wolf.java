import java.util.ArrayList;

/**
 *
 */
public class Wolf {
    private ArrayList<Double> positions;


    /**
     *
     * @param pos
     */
    public Wolf(ArrayList<Double> pos) {
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
}
