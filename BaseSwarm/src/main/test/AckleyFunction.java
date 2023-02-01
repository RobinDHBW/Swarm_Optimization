import java.util.List;

public class AckleyFunction implements IFunction {

    private final Integer a;
    private final Double b;
    private final Double c;
    private final Double d;

    /**
     * Implementation of Ackley extending IFunction
     *
     * @param a (Integer)
     * @param b (Double)
     * @param c (Double)
     * @param d (Double)
     */
    public AckleyFunction(Integer a, Double b, Double c, Double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Override method implementing the mathematical logic of the Ackley function
     *
     * @param args (List<Double>)
     * @return
     */
    @Override
    public Double evaluate(List<Double> args) {
        Double sum1 = 0.0;
        Double sum2 = 0.0;

        Integer dimension = args.size();

        for (Double val : args) {
            sum1 += Math.pow(val, 2);
            sum2 += Math.cos(this.c * val);
        }

        return this.a + Math.exp(1) + this.d - this.a * Math.exp(-this.b * Math.sqrt(sum1 / dimension)) - Math.exp(sum2 / dimension);
    }
}
