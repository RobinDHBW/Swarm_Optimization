import java.util.ArrayList;

public class Rat extends SwarmMember{

    /**
     * Construct Rat by given parameters
     * @param pos (ArrayList<Double>)
     */
    public Rat(ArrayList<Double> pos){
        super();
        this.position=pos;
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
