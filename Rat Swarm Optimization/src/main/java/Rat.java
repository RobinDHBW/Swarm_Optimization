import java.util.ArrayList;

public class Rat extends SwarmMember{

    /**
     *
     * @param pos (ArrayList<Double>)
     */
    public Rat(ArrayList<Double> pos){
        super();
        this.position=pos;
    }

    /**
     * Accepting method from Visitor interface
     * @param visitor
     * @param c
     */
    @Override
    public void accept(ISwarmVisitor visitor, Enum c) {
        visitor.visit(this, c);
    }
}
