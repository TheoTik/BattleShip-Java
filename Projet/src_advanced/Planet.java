import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * The Planet class represents a planet in the game .
 * This class is defined by :
 * <ul>
 * <li>nbrShip: the number of ships </li>
 * <li>circle : a circle which represents the planet </li>
 * <li>color : to whom is this planet </li>
 * <li>production : production of the planet as a function of time </li>
 * <li>ship : collection of ship belonging to the planet</li>
 * <li>image : image of the planet</li>
 * </ul>
 * Main function : render draw the planet, check position of the planets (not too far), 
 * change a function that change the color, prod increase the quantity of nbrShip, 
 * AI is the artificial intelligence which control a color
 * 
 * @author Watteau Paul && Combalbert Th�o
 * @version 1
 */

public class Planet implements Serializable {
	
	/** 
     * To save this version of the class
     */
	private static final long serialVersionUID = 1941204086063526634L;
	
	/** 
     * Number of ships
     */
	private int nbrShip;
	
	/** 
     * Form of the planet
     */
	private Circle circle;
	
	/** 
     * To whom is this planet
     */
	private int color;
	
	/** 
     * Quantity of nbrShip that increase with time
     */
	private double production;
	
	/** 
     * Collection of ships sent
     */
	private Collection<Ship> ship ;
	
	/** 
     * Image of the planet
     */
	private  transient Image image;
	

	public Collection<Ship> getShip() {
		return ship;
	}

	public void setShip(Collection<Ship> ship) {
		this.ship = ship;
	}
	public void setShip(Ship ship) {
		this.ship.add(ship);
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	public double getProduction() {
		return production;
	}

	public void setProduction(double production) {
		this.production = production;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getNbrShip() {
		return nbrShip;
	}

	public void setNbrShip(int nbrShip) {
		this.nbrShip = nbrShip;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	 /** 
     * <b>Constructor of Planet</b> 
     * Build a Planet with a circle, a production and a color
     * @param c
     *     Circle that Form of the planet
     * @param production
     *    double that increase the number of ship
     * @param color
     * 		To whom is this planet
     */ 
	public Planet(Circle c, double production, int color) {
		this.circle = c;
		this.color=color;
		if( color == 2) {
			this.image= new Image(Game.getRessourcePathByName("images/PlanetJ2.png"),  this.getCircle().getRadius()*2,
					this.getCircle().getRadius()*2, false, false);
		}
		 if ( color == 1) {
			this.image=new Image(Game.getRessourcePathByName("images/PlanetJ1.png"),  this.getCircle().getRadius()*2,
					this.getCircle().getRadius()*2, false, false);
		}
		if( color == 0 ) {
			this.image= new Image(Game.getRessourcePathByName("images/mars.png"),  this.getCircle().getRadius()*2,
					this.getCircle().getRadius()*2, false, false);
		}
		

		this.production=production; // plus petite plan�te avec le moins de production 
		
		this.nbrShip = (int) (c.getRadius()); // r�duire le nombre de ship.
		this.ship = new ArrayList<Ship>();
	}
	
	 /** 
     * <b>Constructor null of Planet</b> 
     * Use in AI in order to find which Planet to attack
     * Use to save the Planets in Serialisable
     * nbrShip = 1000 to find the poorest Planet in ship
     * Color is natrual
     */ 

	public Planet() {
		this.nbrShip = 1000;
		this.ship = new ArrayList<Ship>();
	}

	 /** 
     * Change the color of the planet and its image
     * @param planetChosen
     *     the planet which capture the planet
     */ 
	public void change(Planet planetChosen) {
		if(planetChosen.getColor()==2) {
			this.color=planetChosen.getColor();
			this.image = new Image (Game.getRessourcePathByName("images/PlanetJ2.png"),
					2*this.circle.getRadius(),2*this.circle.getRadius() , false, false) ;
		}
		if (planetChosen.getColor()==1) {
			this.color=planetChosen.getColor();
			this.image= new Image (Game.getRessourcePathByName("images/PlanetJ1.png"),
					2*this.circle.getRadius(),2*this.circle.getRadius() , false, false) ;
		}
		this.getShip().clear(); // destroy the ship outside the planet if the planet get captured
		this.nbrShip = 0;
		this.production = ((int) 150-this.circle.getRadius());
	}

	 /** 
     * Display a planet , its ship's number in the center of the planet
     * @param gc
     *     GraphicsContext that Display on the scene
     */ 
	public void render(GraphicsContext gc) {
		
		gc.drawImage(this.image, circle.getCenterX()-circle.getRadius(), circle.getCenterY()-circle.getRadius());
		
		
		if (nbrShip<=9) {

			gc.fillText(""+this.nbrShip , circle.getCenterX() - 0.19*circle.getRadius(),circle.getCenterY());
			gc.setFill(Color.WHITE);
			
		}
		if ((nbrShip>9 )  && (nbrShip<=99) ){

		gc.fillText(""+this.getNbrShip() , circle.getCenterX() - 0.22*circle.getRadius() ,getCircle().getCenterY());
		gc.setFill(Color.WHITE);
		}
	
		if (nbrShip>99) {

			gc.fillText(""+this.getNbrShip() , circle.getCenterX() - 0.25*circle.getRadius(), circle.getCenterY());
			gc.setFill(Color.WHITE);
		}
	}
	 /** 
     * Check if planet is far enough from others
     * @param col
     * 		A collection of planets of the game
     * @return false if the planet is too close from others  else true.
     */ 	
	public boolean check(Collection<Planet> col) {
		Iterator<Planet> it = col.iterator();
		while (it.hasNext()) {
			Planet plan = it.next();
			if( ((this.circle.isFar(plan.circle)))) {
				return false;
			}
			
		}
		return true;		
	}
	
	 /** 
     * Increase the ship's number and limit at 999 the number of ship.
     */ 
	public void prod() {
		if ( this.nbrShip== 999) {
			this.nbrShip= 999;
		}
		 this.nbrShip=this.nbrShip +1 ;
	}

	 /** 
     * The first player ( J1 ) an AI. 
     * Seek which planet to attack and choose the one with the least number of ships
     * or check if it's own planet gets less than 25 ships and helps it in this case
     * and send ten ships around it per wave.
     * @param listPlanet
     * 		A collection of planets of the game
     */ 
	public void AI(Collection<Planet> listPlanet) {
		Planet temp= new Planet();
		Iterator<Planet> it = listPlanet.iterator();
		while (it.hasNext()) {
			Planet plan = it.next();
			if ( temp.getNbrShip() > plan.getNbrShip() && plan.getColor() != 1 ) {
				temp=plan;
			}
			if (plan.getColor()==1 && plan.getNbrShip() <25) {
				temp=plan;
			}
		}
			if ( this.getNbrShip()> 30) {
				for (double i=0 ; i<10; i++) {
					
					double r = this.getCircle().getRadius()+10;
					double x = r * Math.cos(i) + this.getCircle().getCenterX();
					double y = r * Math.sin(i) + this.getCircle().getCenterY();
					
				Point a = new Point(x,y);
				this.getShip().add(new Ship(a,this, temp, 0));
				this.setNbrShip(this.getNbrShip()-1);
				}
			}
		
	}
	
    /** 
     * Check if someone win the game, J1 or J2 has no more planets.
     * @param listPlanet
     * 		Collection of the planets
     * @return return "You loose" if J1 win and capture all J2 planet,return "VICTORY" if J2
     * capture J1's planets and return "play" if it's not over.
     */ 
	public static String end(Collection<Planet> listPlanet) {
		Iterator<Planet> it = listPlanet.iterator();
		boolean j1=false ;
		boolean j2=false ;
		while (it.hasNext()) {
			Planet plan = it.next();
			if (plan.getColor() == 1 ) {
				j1=true;
			}
			if (plan.getColor()== 2) {
				j2=true;
			}		
		}
		if (j2 == false) {
			Game.listPlanets.clear();
			return "You lose";
		}
		if(j1== false) {
			Game.listPlanets.clear();
			return "VICTORY";
		}
	return "play";
	}
	
    /** 
     * To String : create a string of this class
     * @return A string to describe a Planet with its parameters
     */ 
	public String toString() {
		return "Planet : " + 
		this.nbrShip + " " +
		this.circle + " " +
		this.color + " " +
		this.production;
	}
}

