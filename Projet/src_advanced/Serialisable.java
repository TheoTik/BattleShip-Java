import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

public class Serialisable {
	
	public static void main1(int nbrPlanet) {
		try {
			ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("Liste.dat"));
			save.writeInt(nbrPlanet);
			Iterator<Planet> it = Game.listPlanets.iterator();
			while (it.hasNext()) {
				Planet c1 = it.next();
				save.writeObject(c1);
			}	
			save.close();
		}
		catch (Exception e) {
			System.out.println("problème fichier: " + e.getMessage());
		}
	}
}