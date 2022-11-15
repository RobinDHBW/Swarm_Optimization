import java.util.ArrayList;

public class Elephant extends SwarmMember{

    /**
     *
     * @param pos (ArrayList<Double>)
     */
    public Elephant(ArrayList<Double> pos) {
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
