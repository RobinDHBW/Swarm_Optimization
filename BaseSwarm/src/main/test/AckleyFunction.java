import java.util.List;

public class AckleyFunction extends Function{

    private Integer a;
    private Double b;
    private Double c;
    private Double d;

    /**
     * Implementation of Ackley extending Function
     * @param a (Integer) - Parameter for Ackleyfunction
     * @param b (Double) - Parameter for Ackleyfunction
     * @param c (Double) - Parameter for Ackleyfunction
     * @param d (Double) - Parameter for Ackleyfunction
     */
    public AckleyFunction(Integer a, Double b, Double c, Double d ){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Override method implementing the mathematical logic of the Ackley function
     * @param args
     * @return
     */
    @Override
    public Double evaluate(List<Double> args) {
        Double sum1 = 0.0;
        Double sum2 = 0.0;

        Integer dimension = args.size();

        for(Double val : args){
            sum1 += Math.pow(val, 2);
            sum2 += Math.cos(this.c *val);
        }

        return this.a + Math.exp(1) + this.d - this.a * Math.exp(-this.b * Math.sqrt(sum1/dimension)) - Math.exp(sum2/dimension);
    }
}
