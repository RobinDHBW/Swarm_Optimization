public abstract class SwarmMember implements ISwarmMember{
    protected Enum classifier;

    public void setClassifier(Enum classifier){
        this.classifier = classifier;
    }
    public Enum getClassifier() {
        return classifier;
    }
}
