import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The ship class represents a ship in the game .
 * This class is defined by :
 * <ul>
 * <li>point : a Point from  Point class </li>
 * <li>planetChosen: the ship's planet </li>
 * <li>planetTarget: Planet goal of the ship  </li>
 * <li>rogue: an integer which detect its squadron </li>
 * <li>vector : a double which adapt the same speed of the ship</li>
 * <li>speed : a double which influence the speed of the ship</li>
 * </ul>
 * Main function : render display a ship, detection of a planet, validate a position , and a selection 
 * of the ship
 * 
 * @author Watteau Paul && Combalbert Th�o
 * @version 1
 */

public class Ship implements Serializable{
	
	/** 
     * To save this version of the class
     */
	private static final long serialVersionUID = 1L;
	/** 
     * A point to represent our ship 
     */
	private Point point;
	/** 
     * The ship's planet
     */
	private Planet planetChosen;
	/** 
     * The goal of the ship
     */
	private Planet planetTarget;
	/** 
     * Its squadron identity
     */
	private int rogue;
	/** 
     * Adapt the speed 
     */
	private double vector;
	/** 
     * Influence the speed of the ship
     */
	private double speed;
	

	public Planet getPlanetChosen() {
		return planetChosen;
	}

	public void setPlanetChosen(Planet planetChosen) {
		this.planetChosen = planetChosen;
	}

	public Planet getPlanetTarget() {
		return planetTarget;
	}

	public void setPlanetTarget(Planet planetTarget) {
		this.planetTarget = planetTarget;
	}
	
	public int getRogue() {
		return rogue;
	}

	public void setRogue(int rogue) {
		this.rogue = rogue;
	}
	
	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	public void setPoint(double a , double b) {
		this.point.setPointX(a);
		this.point.setPointY(b);
	}
	

	public void setPosition(double x, double y) {
		this.point.setPoint(x, y); 
	}

    /** 
     * <b>Constructor of Ship</b> 
     * Build a ship with a Point , a Planet chosen and a Planet target
     * and an integer  
     * 
     * @param p  
     *     Point that Represent our ship 
     * @param planetChosen
     *     The ship's planet
     * @param planetTarget
     *     The goal of the ship
     * @param rogue
     * 		its squadron identity
     */ 
	Ship(Point p, Planet planetChosen , Planet planetTarget, int rogue){
		this.point = p;
		this.planetChosen=planetChosen;
		this.planetTarget=planetTarget;
		this.rogue=rogue;	
		Vector(); // adapt speed 
		this.speed= 1;
	}
	
	 /** 
     * Display a ship
     * @param  gc
     *    graphicsContext that Display on the scene
     * @param list
     *     a Collection of planets on the map 
     * @param img
     * 		Image of the ship
     */ 
	public void render(GraphicsContext gc, Collection<Planet> list, Image img) {
		
		Planet plan = detection(list);
				
		double a = equationA(this.point,this.planetTarget.getCircle().getCenter());
		double b = equationB(this.point, this.planetTarget.getCircle().getCenter());
		
		if (plan != this.planetTarget) {
		
			if( plan == null) {	
		
				if (this.point.getPointX() <= this.planetTarget.getCircle().getCenter().getPointX()) {
					gc.drawImage(img,point.getPointX()-15,(point.getPointY()-15));
					point.setPointX(point.getPointX() - this.vector);
					point.setPointY(a*(point.getPointX()) + b );
					}
				else {
					gc.drawImage(img,point.getPointX()-15, (point.getPointY()-15));
					point.setPointX(point.getPointX() - this.vector);
					point.setPointY(a*(point.getPointX()) + b );
				}	
			}
			
			else if (Position(plan)==1) {
				if(a*plan.getCircle().getCenterX()+b >= plan.getCircle().getCenterY() && a>0) {
					dashUnClockWise(gc, plan, img);
				}
				else if(a*plan.getCircle().getCenterX()+b <= plan.getCircle().getCenterY() && a>0) {
					dashClockWise(gc, plan, img);
				}
				else if (this.planetTarget.getCircle().getCenterY()>plan.getCircle().getCenterY() ) {
					dashUnClockWise(gc, plan, img);
				}
				else {
					dashClockWise(gc,plan,img);
				}
			}
			else if (Position(plan)==2) {
				if(a*plan.getCircle().getCenterX()+b <= plan.getCircle().getCenterY() && a<0) {
					dashUnClockWise(gc, plan, img);
				}
				else if(a*plan.getCircle().getCenterX()+b >= plan.getCircle().getCenterY() && a<0) {
					dashClockWise(gc, plan, img);
				}
				else if (this.planetTarget.getCircle().getCenterY()>plan.getCircle().getCenterY() ) {
					dashClockWise(gc, plan, img);
				}
				else {
					dashUnClockWise(gc,plan,img);
				}
			}
			else if (Position(plan)==3) {
				if(a*plan.getCircle().getCenterX()+b <= plan.getCircle().getCenterY() && a>0) {
					dashUnClockWise(gc, plan, img);
				}
				else if(a*plan.getCircle().getCenterX()+b >= plan.getCircle().getCenterY() && a>0) {
					dashClockWise(gc, plan, img);
				}
				else if (this.planetTarget.getCircle().getCenterY()>plan.getCircle().getCenterY() ) {
					dashClockWise(gc, plan, img);
				}
				else {
					dashUnClockWise(gc,plan,img);
				}
			}
			else if (Position(plan)==4) {
				if(a*plan.getCircle().getCenterX()+b >= plan.getCircle().getCenterY() && a<0) {
					dashUnClockWise(gc, plan, img);
				}
				else if(a*plan.getCircle().getCenterX()+b <= plan.getCircle().getCenterY() && a<0) {
					dashClockWise(gc, plan, img);
				}
				else if (this.planetTarget.getCircle().getCenterY()>plan.getCircle().getCenterY() ) {
					dashUnClockWise(gc, plan, img);
				}
				else {
					dashClockWise(gc,plan,img);
				}
			}
		Vector();
		}
	}
		
	/** 
     * Calculate the gradient of a straight line.
     * @param p1
     *     First Point 
     * @param p2
     *     Second Point 
     * @return a double which represents the gradient 
     */ 
	public double equationA(Point p1, Point p2) {
		double a = (p2.getPointY() - p1.getPointY()) / (p2.getPointX() - p1.getPointX());
		return a;
	}
	
	/** 
     * Calculate the second element of a straight line
     * @param p1
     *     First Point 
     * @param p2
     *     Second Point 
     * @return a double when abscissa is equal 0
     */ 
	public double equationB(Point p1, Point p2) {
		double a = equationA(p1,p2);
		double b = p1.getPointY() - a * p1.getPointX();
		return b;
	}

	/** 
     * Check if a ship is at a validate position 
     * Check if the ship is inside a planet and adapt the planet number ship in consequences
     * Check to whom is this planet 
     * Check if the ship capture the planet and use the function called change, if it does
     * @return true if the ship is not inside the planet target
     * and return false if the ship is inside the planet target
     */ 
	public boolean validatePosition() {	
		if (planetTarget.getCircle().isInside(this.point)) {

			if (this.planetTarget.getColor()== this.planetChosen.getColor() ) {
				this.planetTarget.setNbrShip(this.planetTarget.getNbrShip()+1);
			}
			else {
				if( this.planetTarget.getNbrShip()<=1) {
					this.planetTarget.change(this.planetChosen);
				}
				else {
					this.planetTarget.setNbrShip(this.planetTarget.getNbrShip()-1);
				}
			}
			this.planetChosen=null;
			this.planetTarget=null;			
			return false;
		}
		else return true;
	}
	
	/** 
     * Detect a planet on the ship's road
     * detect before the planet to don't crash into the planet
     * @param  list
     *     Collection of the planet of the game
     * @return the Planet on the ship's road if it is detected, otherwise null 
     */ 
	public Planet detection(Collection<Planet> list) {
		Iterator<Planet> it = list.iterator();
		while (it.hasNext()) {
			Planet plan = it.next();
			
			if(plan.getCircle().getRadius() +10 >= Point.distance(this.point, plan.getCircle().getCenter()) ) {
				return plan;
			}
		}
		return null;	
	}
	
	/** 
     * To create a hitbox with the size of the ship's image
     * @param p
     *     Point that represents the ship
     * @return true if click detect a ship else return false
     */ 
	public boolean ClickShip(Point p) {
		if (p.getPointX()>=this.point.getPointX()-15 && p.getPointX()<= this.point.getPointX()+15
				&& p.getPointY()>= this.point.getPointY()-15 && p.getPointY() <= this.point.getPointY()+15) {
			return true;
		}
		return false;
	}

	
	 /** 
     * Display a ship when the ship want to avoid a planet and turn around clockwise
     * @param gc
     *     GraphicsContext Display on the scene
     * @param plan
     *     The planet to avoid
     * @param image
     * 		Image of the ship
     */ 
	public void dashClockWise(GraphicsContext gc, Planet plan, Image image) {	
		int posShip = Position(plan);
		// case up left
		if (posShip==1) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() +2);
			point.setPointY(point.getPointY() -8);
		}
		// case up right
		if (posShip==2) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() +8);
			point.setPointY(point.getPointY() -2);
		}
		//case down right
		if (posShip==3) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() -2);
			point.setPointY(point.getPointY() +8);
		}
		// case down left
		if (posShip==4) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() -8);
			point.setPointY(point.getPointY() -2);
		}
	}
	 /** 
     * Display a ship when the ship want to avoid a planet and turn around unclockwise
     * @param gc
     *    GraphicsContext that display on the scene
     * @param plan
     *    The planet to avoid
     * @param image
     * 		Image of the ship
     */ 
	public void dashUnClockWise(GraphicsContext gc, Planet plan, Image image) {
		int posShip = Position(plan);
	
		// case up left
		if (posShip==1) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() -8);
			point.setPointY(point.getPointY() +2);
		}
		// case up right
		if (posShip==2) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() -2);
			point.setPointY(point.getPointY() -8);
		}
		//case down right
		if (posShip==3) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() +8);
			point.setPointY(point.getPointY() -2);
		}
		// case down left
		if (posShip==4) {
			gc.drawImage(image,point.getPointX()-15,(point.getPointY()-15));
			point.setPointX(point.getPointX() -2);
			point.setPointY(point.getPointY() +8);
		}
	}
	 /** 
     * Check where the ship is relative to the planet 
     * @param plan
     *      the planet we want to avoid
     * @return return 1 if the ship is at the top left of the planet
     * return 2 if the ship is at the top right of the planet
     * return 3 if the ship is at the down right of the planet
     * return 4 if the ship is at the down left of the planet
     */ 
	public int Position(Planet plan) {
		// case top left
		if ( point.getPointX()+15<= plan.getCircle().getCenterX() && point.getPointY()+15<=plan.getCircle().getCenterY()) {
			return 1;
		}
		// case top right
		if ( point.getPointX()+15> plan.getCircle().getCenterX() && point.getPointY()+15< plan.getCircle().getCenterY()) {
			return 2;
		}
		//case down right
		if ( point.getPointX()+15>= plan.getCircle().getCenterX() && point.getPointY()+15>= plan.getCircle().getCenterY()) {
			return 3;
		}
		// case down left
			return 4;
	}	
	
	 /** 
     * Adapt the speed of the ship  and put into the parameter vector
     * the movement of the ship on abscissa using cosinus
     */ 
	public void Vector() {
		double diffX = this.getPoint().getPointX() - this.planetTarget.getCircle().getCenterX();		
		double distance = Point.distance(this.getPoint(), this.planetTarget.getCircle().getCenter());	
		double cos= diffX/distance;
		
		this.vector= this.speed*cos;
		
	}
	
    /** 
     * To String : create a string of this class
     * @return A string to describe a Ship with its parameters
     */ 
	  public String toString(){
		    return "Ship : "+ this.point.toString() +" , "+  this.planetChosen.toString() + " , " + this.planetTarget.toString() + " , " + this.getRogue() ;
		  }
}


