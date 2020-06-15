import java.io.Serializable;


/**
 * The point class represents a point.
 * This class is defined by two doubles.
 * <ul>
 * <li>x : the coordinate on abscissa </li>
 * <li>y : the coordinate on ordinate</li>
 * </ul>
 * Main function :
 * <ul>
 * <li> Distance
 * </ul> 
 * @author Watteau Paul && Combalbert Th�o
 * @version 1
 */
class Point implements Serializable {

    /** 
     * To save this version of the class
     */
	private static final long serialVersionUID = 1L;

    /** 
     * Coordinate on abscissa
     */
	private double x;

    /** 
     * Coordinate on abscissa
     */
	private double y;

	/** 
     * <b>Constructor of Point</b> 
     * Create a Point with two doubles.
     * 
     * @param x 
     *     Coordinate on abscissa
     * @param y
     *     Coordinate on abscissa
     */ 
    Point(double x , double y ){
      this.x=x;
      this.y=y;
    }
	/** 
     * <b>Constructor of Point</b> 
     * Create a null Point :( 0 ; 0 )
     */ 
    Point(){
      this.x=0;
      this.y=0;
    }
    
    public void setPoint (Point p) {
    	this.x= p.getPointX();
    	this.y= p.getPointY();
    }
    public void setPoint(double x, double y){
    	this.x = x;
    	this.y = y;
    }

    public void setPointX(double x){
    	this.x = x;
    }
  
    public void setPointY(double y){
    	this.y=y;
    }

    public double getPointX(){
    	return x;
    }
  
    public double getPointY(){
    	return y;
    } 
    
    /** 
     * Calculate the distance between two points
     * @param p
     *     First Point
     * @param q
     *     Second Point  
     * @return Return the distance as a double thanks to Pythagore.
     */ 
    public static double distance(Point p , Point q){
    	double d = Math.sqrt(  (p.x-q.x)*(p.x-q.x)  +  (p.y-q.y)*(p.y-q.y)  );
    	return d;
    }
    
    /** 
     * To String : create a string of this class
     * @return A string to describe a Point with its abscissa and ordinate
     */ 

    public String toString(){
	    return "Point (" + x + ", "  + y + ")";
	  }
 
}

