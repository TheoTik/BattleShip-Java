import java.io.Serializable;

/**
 * The Circle class represents a circle.
 * This class is defined by a double and a Point.
 * <ul>
 * <li>radius : a double </li>
 * <li>center : a Point from the class Point </li>
 * </ul>
 * Main function : Move the circle and check if a Point is inside the circle
 * 
 * @author Watteau Paul && Combalbert Th�o
 * @version 1
 */
class Circle implements Serializable{
	
  /**
	 * To save this version of the class
	 */
	private static final long serialVersionUID = 1L;
	
	  /**
		 * Radius of the circle 
		 */
	private double radius;
	
	  /**
		 * Center of the circle
		 */
	private Point center;
	
	/** 
     * <b>Constructor of Circle</b> 
     * Create a circle with a double and a Point.
     * 
     * @param radius
     *     Radius of the circle
     * @param center
     *     Center of the circle 
     */ 
	public Circle(double radius, Point center ){
		this.radius = radius;
		this.center = center;
	}

 	public Point getCenter() {
		return center;
	}
 	
 	public double getRadius(){
 		return this.radius;
 	}

 	public double getCenterX(){
 		return center.getPointX();
 	}
  
 	public double getCenterY(){
 		return center.getPointY();
 	}
  
 	public void setRadius(double radius){
	    this.radius = radius;
	}

 	public void setCenter(Point center){
	    this.center = center;
	}
  
 	public void setCenterX(double a){
	    this.center.setPointX(a);
	}
  
 	public void setCenterY(double a){
 		this.center.setPointY(a);
	}
  
 	/** 
     * Translate a circle
     * @param  x
     *     Coordinate on abscissa
     * @param  y
     *     Coordinate on ordinate
     */ 
 	public void putTo(double x, double y) {
 		this.setCenterX(x);
	  	this.setCenterY(y);
 	}
 	
 	/** 
     * Check if a point is inside a circle
     * @param p
     * 		A point with abscissa and ordinate
     * @return a boolean: true if the Point is inside a circle ( radius +15 ) 
     */
 	boolean isInside(Point p){
 		return (Point.distance(p , center) <= radius+15 );
 	}
  
 	/** 
     * Check if a circle is far enough a circle
     * @param c1
     *     a circle that we want to get far from it 
     * @return true if c1 is too far from an other circle
     */
 	boolean isFar(Circle c1) {
 		return (Point.distance(this.center, c1.center)<75+(this.radius + c1.radius));
 	}
  
    /** 
     * To String : create a string of this class
     * @return A string to describe a Circle with its radius and center.
     */ 
 	public String toString(){
	    return "Circle : "+ center.toString()+" , "+  radius ;
	}
  
  
}
