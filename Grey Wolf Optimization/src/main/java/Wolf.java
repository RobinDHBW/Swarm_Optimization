import java.util.ArrayList;

public class Wolf extends SwarmMember{

    /**
     *
     * @param pos (ArrayList<Double>)
     */
    public Wolf(ArrayList<Double> pos) {
        super();
        this.position = pos;
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
