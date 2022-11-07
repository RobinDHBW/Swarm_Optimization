import java.util.List;

public class AckleyFunction extends Function{

    private Integer a;
    private Double b;
    private Double c;
    private Double d;

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public AckleyFunction(Integer a, Double b, Double c, Double d ){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

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
