import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The Deserialisable class is use to read a file and setup the game. 
 * This class has just a main function
 * @author Watteau Paul && Combalbert Th�o
 * @version 1
 */
public class Deserialisable  {
    
	public static void main1() {
	    
		Collection<Planet> listPlanet = new ArrayList<Planet>();		
		try {
			ObjectInputStream entry = new ObjectInputStream(new FileInputStream("Liste.dat"));
			int nbrPlanet = entry.readInt();
			for (int i=0; i<nbrPlanet; i++) {
				for (int j=0; j<1;j++) {
					Planet plan= new Planet();
					plan= (Planet) entry.readObject();
					System.out.println(plan.getShip());

					listPlanet.add(plan);
				}
			}		
			entry.close();
			Game.listPlanets.addAll(listPlanet);
		}
		catch (Exception e){
			System.out.println("probl�me fichier: " + e.getMessage());
		}
	}

}

