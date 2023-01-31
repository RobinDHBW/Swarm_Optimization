import java.util.List;

public class ElephantClan extends Swarm{

    public ElephantClan(Integer dimension, List<Double> upperLimits, List<Double> lowerLimits) {
        super(dimension, upperLimits, lowerLimits);
    }

    @Override
    public void visit(SwarmMember m, Enum c) {

    }

    @Override
    protected void rankMembers(Function f, Boolean sign) {

    }
}
