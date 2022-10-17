import java.util.ArrayList;
import java.util.Random;

public class Wolfpack {

    private ArrayList<Wolf> members;

    public Wolfpack(Integer n, Integer d){
        members = new ArrayList<Wolf>();

        //Für N = Anzahl Wölfe --> kreire neuen Wolf
        for(int i =0; i<n; i++){
            ArrayList<Double> positions = new ArrayList<Double>();
            Random rand = new Random();

            //Für D = Anzahl Dimensionen --> Zufällige Wolfposition innerhalb der Grenznen
            for(int z=0; z<d; z++){
                Double pos = Math.random(); //Grenzen?
                positions.add(pos);
            }

            members.add(new Wolf(positions));
        }

    }


}
