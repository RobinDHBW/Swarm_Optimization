import java.util.ArrayList;
import java.util.Random;

public class Wolfpack {

    private ArrayList<Wolf> members;

    public Wolfpack(Integer n, Integer d){
        members = new ArrayList<Wolf>();

        //For N = Quantity of wolves --> construct a new wolf
        for(int i =0; i<n; i++){
            ArrayList<Double> positions = new ArrayList<Double>();
            Random rand = new Random();

            //Für D = Quantity of dimensions --> Random wolfposition between limits
            for(int z=0; z<d; z++){
                Double pos = Math.random(); //limits?
                positions.add(pos);
            }

            members.add(new Wolf(positions));
        }

    }


}
