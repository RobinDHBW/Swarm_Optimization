import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Wolfpack extends Swarm {
    private List<Double> upperLimits, lowerLimits;

    /**
     *
     * @param n
     * @param d
     * @param upperLimits
     * @param lowerLimits
     */
    public Wolfpack(Integer n, Integer d, List<Double> upperLimits, List<Double> lowerLimits){
        members = new ArrayList<SwarmMember>();

        this.upperLimits = upperLimits;
        this.lowerLimits = lowerLimits;

        //For N = Quantity of wolves --> construct a new wolf
        for(int i =0; i<n; i++){
            ArrayList<Double> positions = new ArrayList<Double>();
            Random rand = new Random();

            //Für D = Quantity of dimensions --> Random wolfposition between limits
            for(int z=0; z<d; z++){
                Double pos = ThreadLocalRandom.current().nextDouble(this.lowerLimits.get(z), this.upperLimits.get(z));
                positions.add(pos);
            }

            members.add(new Wolf(positions));
        }

    }


}
