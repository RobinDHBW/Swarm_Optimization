public class RatSwarm extends Swarm{

    private void rankMembers(){

    }

    private void moveMemberToNextPosition(){

    }

    private void catchLostMembers(){

    }

    @Override
    public SwarmSolution findMinimum(Function f, Integer iterationCount) {
        return null;
    }

    @Override
    public SwarmSolution findMaximum(Function f, Integer iterationCount) {
        return null;
    }

    /**
     * Override method from visitor interface
     * @param r
     * @param c
     */
    @Override
    public void visit(SwarmMember r, Enum c) {
        r.setClassifier(c);
    }
}
