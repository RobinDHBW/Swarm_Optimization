public abstract class SwarmMember implements ISwarmMember{
    protected Enum classifier;

//    public SwarmMember(HierarchicalClassifier classifier){
//        this.classifier = classifier;
//    }

    /**
     *
     * @param classifier
     */
    public void setClassifier(Enum classifier){
        this.classifier = classifier;
    }

    public Enum getClassifier() {
        return classifier;
    }
}
