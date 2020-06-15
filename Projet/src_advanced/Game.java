import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * The Game class is the one which run the game
 * <ul>
 * <li>Width: width of the canvas</li>
 * <li>Height: height of the canvas</li>
 * <li>timer: timer of the game </li>
 * <li>planetChosen: keep the planet that we have chosen</li>
 * <li>sentShip: number of the squadron sent</li>
 * <li>planetTarget: keep the planet that we have target</li>
 * <li>shipChosen: keep the ship that we have chosen</li>
 * <li>squadron: to identify each squadron </li>
 * <li>nbrPlanet: number of planets in the game </li>
 * <li>action: to use options of the game </li>
 * <li>listPlanets: list of the planets</li>
 * </ul>
 * The game class make the game run and check the mouse
 * @author Watteau Paul && Combalbert Th�o
 * @version 1
 */

public class Game extends Application {
	
	 /** 
     * Width of the canvas
     */
	private final static int WIDTH = 800;
	 /** 
     * Height of the canvas
     */
	private final static int HEIGHT = 800;
	 /** 
     * timer of the game 
     */
	private double timer;
	 /** 
     * To keep the planet that we have chosen
     */
	private Planet planetChosen;
	 /** 
     * Define the number of ships sent
     */
	private int sentShip;
	 /** 
     * To keep the planet target
     */
	private Planet planetTarget; 
	 /** 
     * To keep a ship that we have chosen
     */
	private Ship shipChosen;
	 /** 
     * To define each squadron by a integer
     */
	private int squadron=1;
	 /** 
     * Number of planets
     */
	private int nbrPlanet = (int)( Math.random() * 5)+5; // Create between 5 and 10. 
	 /** 
     * To check what the player does in game ( the menu) 
     */
	private String action="break";
	 /** 
     * Collection to set up the game
     */
	public static Collection<Planet> listPlanets = new ArrayList<Planet>();
		
	public int getNbrPlanet() {
		return nbrPlanet;
	}
	
	   /** 
     * find resources on file
     * @param name 
     *     name of the file that we want
     * @return string of the file
     */ 
	public static String getRessourcePathByName(String name) {
		return Game.class.getResource('/' + name).toString();
	}

	 /** 
	  * Move the circle to the animation setup
	  * @param circlePlay
	  *     Circle of the button play
	  * @param circleBreak
	  *     Circle of the button break
	  * @param circleSave
	  *     Circle of the button save
	  * @param circleLeave
	  *     Circle of the button leave
	  * @param circleLoad
	  *     Circle of the button load
	  */ 
	private void moveToAnim(Circle circlePlay, Circle circleBreak, Circle circleSave, Circle circleLeave, Circle circleLoad) {
		circleBreak.putTo(695, 785);
		circleSave.putTo(740, 785);
		circleLeave.putTo(785, 785);
		circleLoad.putTo(-100, -100);
		circlePlay.putTo(-100,-100);
	}
	
	/** 
	  * Move the circle to the menu setup
	  * @param circlePlay
	  *     Circle of the button play
	  * @param circleBreak
	  *     Circle of the button break
	  * @param circleSave
	  *     Circle of the button save
	  * @param circleLeave
	  *     Circle of the button leave
	  * @param circleLoad
	  *     Circle of the button load
	  */ 
	private void moveToMenu(Circle circlePlay, Circle circleBreak, Circle circleSave, Circle circleLeave, Circle circleLoad) {
		circleBreak.putTo(-100, -100);
		circlePlay.putTo(350,415);
		circleSave.putTo(405, 415);
		circleLeave.putTo(445, 415);
		circleLoad.putTo(490,415);
	}
	
	 /** 
     * Create a list of Planet in listPlanets
     */ 
	private void CreateMap() {
		listPlanets.clear();
		nbrPlanet=(int)( Math.random() * 5)+5;
		// to create our planet 
				int cpt=0;
				
				while (cpt<nbrPlanet) {
					if (cpt==4 ) {
						// create the player planet 
						Circle c = new Circle(Math.random() * 50+50, 
								new Point(100 + Math.abs(Math.random()*WIDTH-200), 
										100 + Math.abs(Math.random()*HEIGHT-200)));	
						Planet p = new Planet(c, ((int) 150-c.getRadius()), 2);
						
						// check if the planet is inside an other.
						if  (p.check(listPlanets)) { 
							cpt++;
							listPlanets.add(p); // Add planet to collection
						}
					}
					
					else if (cpt==2) {
						// create the AI planet 
						Circle c = new Circle(Math.random() * 50+50, 
								new Point(100 + Math.abs(Math.random()*WIDTH-200), 
										100 + Math.abs(Math.random()*HEIGHT-200)));
						Planet p = new Planet(c,( (int) 150-c.getRadius()) , 1);
						
						if  (p.check(listPlanets)) { 
							cpt++;
							listPlanets.add(p);
						}
					}
					
					else {
						// create neutral planet 
						Circle c = new Circle(Math.random() * 50+30, 
							new Point(100 + Math.abs(Math.random()*WIDTH-200), 
									100 + Math.abs(Math.random()*HEIGHT-200)));
						Planet p = new Planet(c, 0 , 0);
						
						if  (p.check(listPlanets)) { 
							cpt++;
							listPlanets.add(p);
						}
					}	
				}
	}
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
	public void start(Stage stage) {
    	
        stage.setTitle("BATTLESHIP FOR OUR GOD");
        stage.setResizable(false); // can't change size
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
    	Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		
		// to use gcdraw 
		Image space = new Image(getRessourcePathByName("images/space.jpeg"), WIDTH, HEIGHT, false, false);
		Image victory = new Image(getRessourcePathByName("images/victory.jpg"), WIDTH, HEIGHT, false, false);
		Image loose = new Image(getRessourcePathByName("images/lose.jpg"), WIDTH, HEIGHT, false, false);
		Image imgBreak = new Image(getRessourcePathByName("images/pause.png"), 30, 30, false, false);
		Image imgSave = new Image(getRessourcePathByName("images/save.png"), 30, 30, false, false);
		Image imgLeave = new Image(getRessourcePathByName("images/leave.png"), 30, 30, false, false);
		Image imgPlay = new Image(getRessourcePathByName("images/play.png"), 30, 30, false, false);	
		Image imgLoad = new Image(getRessourcePathByName("images/load.png"), 30, 30, false, false);
		Image imgShipJ1 = new Image(Game.getRessourcePathByName("images/ship.png"),30,30 , false, false);
	    Image imgShipJ2 = new Image(Game.getRessourcePathByName("images/shipJ2.png"),30,30 , false, false);

	    // create Circle to use our menu
		Circle circlePlay = new Circle (2, new Point(355,400));
		Circle circleBreak = new Circle(2, new Point(-100, -100));
		Circle circleSave = new Circle(2, new Point(400, 400));
		Circle circleLeave = new Circle(2, new Point(445, 400));
		Circle circleLoad= new Circle (2, new Point(490, 400));
		
	
	    CreateMap();
		stage.setScene(scene);
		stage.show();
		
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				// get the position of the click
				Point p = new Point(e.getX(), e.getY());
				
				// check the menu button 
				if (circleBreak.isInside(p)) {
					// move the circle to play 
					moveToMenu(circlePlay, circleBreak, circleSave, circleLeave, circleLoad);
					action = "break";
					System.out.println(action);
				}
				if (circlePlay.isInside(p) ) {
				    if (listPlanets.size()==0) {
						CreateMap(); 
					}
					moveToAnim(circlePlay, circleBreak, circleSave, circleLeave, circleLoad);
					action = "play";
					System.out.println(action);
				}
				if (circleSave.isInside(p)) {
					moveToMenu(circlePlay, circleBreak, circleSave, circleLeave, circleLoad);
					Iterator<Planet> it = listPlanets.iterator();
					while (it.hasNext()) {
						Planet plan = it.next();
						System.out.println(plan.getShip());
					}
					// save the game 
					Serialisable.main1(nbrPlanet);
					action = "save";
					System.out.println(action);
				}
				if (circleLeave.isInside(p)) {
					action = "leave";
					System.out.println(action);
				}
				// load a old game 
				if(circleLoad.isInside(p)) {
					listPlanets.clear(); 
					Deserialisable.main1();
					
					Iterator<Planet> it = listPlanets.iterator();
					// put image ( that we can't save ) to the planet 
					while (it.hasNext()) {
						Planet plan = it.next();
						if(plan.getColor()==0) {
							plan.setImage(new Image(Game.getRessourcePathByName("images/mars.png"), 2*plan.getCircle().getRadius(),
									2*plan.getCircle().getRadius(), false, false));
						}
						if(plan.getColor()==1) {
							plan.setImage(new Image(Game.getRessourcePathByName("images/PlanetJ1.png"), 2*plan.getCircle().getRadius(),
										2*plan.getCircle().getRadius(), false, false));
						}
						if(plan.getColor()==2) {
							plan.setImage(new Image(Game.getRessourcePathByName("images/PlanetJ2.png"), 2*plan.getCircle().getRadius(),
									2*plan.getCircle().getRadius(), false, false));
						}
					}
					action="load";	
					System.out.println(action);
				}
				
				if (action=="play"){
				    
				    // check if we click on a planet 
				    Iterator<Planet> it = listPlanets.iterator();
				    while (it.hasNext()) {
				    	Planet plan = it.next();
				    	// first click to get the first planet 
				    	if(planetChosen==null && plan.getColor()==2 && shipChosen==null) {
					    	if (plan.getCircle().isInside(p)) {
					    		planetChosen = plan;
					    		sentShip=planetChosen.getNbrShip()/2 ;
					    	}
					    	// to scroll how many ship we want to send 
					    	scene.setOnScroll(new EventHandler<ScrollEvent>(){
						    	public void handle( ScrollEvent event ) {
						    		if ( sentShip < 1 ) {
						    			sentShip=1;
						    		}
							    	else if ( sentShip >= planetChosen.getNbrShip()-1) {
							    		sentShip= planetChosen.getNbrShip() -1 ;
							    	}
							    	else {
								    	sentShip=(int) (sentShip+ event.getDeltaY()/40);
							    	}
							    }
						    });
				    	}
					
				    	// to cancel planetChosen if we click twice in a row on it
					    else if(plan.getCircle().isInside(p)) {
					    	if( planetChosen==plan) {
					    		planetChosen=null;
					    	}
					    	// to send our ship 
					    	else if(shipChosen==null) {
						    	planetTarget=plan;
							
							    // don't send ship that the planet can't have 
					             /* if ( sentShip>planetChosen.getNbrShip()) {
						    	    sentShip = planetChosen.getNbrShip();
							     } */
							
							    // to get the position of ship's spawn 
							    for (double i=0 ; i<sentShip; i++) {
										
							    	double r = planetChosen.getCircle().getRadius()+10;
							    	double x = r * Math.cos(i) + planetChosen.getCircle().getCenterX();
							    	double y = r * Math.sin(i) + planetChosen.getCircle().getCenterY();
								
						           	Point a = new Point(x,y);
							        planetChosen.getShip().add(new Ship(a,planetChosen, planetTarget, squadron));
							        planetChosen.setNbrShip(planetChosen.getNbrShip()-1);
							    }
							    sentShip=0; // to avoid a NullPointerException
						    	squadron++ ; // to change ID of each squadron sent 
							
						    	// to use click another time 
						    	planetChosen=null; 
						    	planetTarget=null;
						    }
						
						    // to change direction of a squadron 
						    else {
						    	Iterator<Ship> it2 =shipChosen.getPlanetChosen().getShip().iterator();
						    	while( it2.hasNext()) {
							    	Ship ship= it2.next();
								    	if( shipChosen.getRogue()== ship.getRogue()) {
								    		ship.setPlanetTarget(plan);
								    		ship.Vector();
								    	}
						    	}	
							    shipChosen=null;
							    planetChosen=null;
						    }
					    }
					
				    	// to click on a ship 
					    Iterator<Ship> it2 =plan.getShip().iterator();
					    while( it2.hasNext()) {
					    	Ship ship= it2.next();
					    	// first click on a ship
					    	if( ship.ClickShip(p) && plan.getColor()== 2 && shipChosen==null) {
					    		shipChosen=ship;
					    	}
					    	// second click in a row to cancel the ship selection
						    else if(shipChosen== ship && ship.ClickShip(p)) {
						    	shipChosen=null;
						    }
					    }
				    }
				}
			}
		};

		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);
					
		AnimationTimer animation = new AnimationTimer() {
			
			public void handle(long arg0) {
				// draw when the game is live 
				
				System.out.println(action);
				if(action=="play") {
				    
				    // draw background
					gc.drawImage(space, 0, 0);
					
					//draw button 
					gc.drawImage(imgBreak, 680, 770);
					gc.drawImage(imgSave, 725, 770);
					gc.drawImage(imgLeave, 770, 770);
					
					
					// draw the number of ship that we want to send, right or left depending of the planet position
					if ( planetChosen != null) {
						if( planetChosen.getCircle().getCenterX() <400) {
						gc.fillText(""+sentShip, planetChosen.getCircle().getCenterX()
								+planetChosen.getCircle().getRadius() + 10,
								planetChosen.getCircle().getCenterY());
						}
						else
							gc.fillText(""+sentShip, planetChosen.getCircle().getCenterX()
									-planetChosen.getCircle().getRadius() - 10 ,
									planetChosen.getCircle().getCenterY());
					}
			
					// loop all the planet  				
					Iterator<Planet> it = listPlanets.iterator();
					while (it.hasNext()) {
						Planet plan = it.next();
						//increase production depending on planet production
						if (timer%((int)plan.getProduction()) == 0) {
							plan.prod();
						}
						plan.render(gc); // draw planet 
						
						// to call the AI 
						if (plan.getColor() == 1) {
							if( timer%30 ==0 ) {
							plan.AI(listPlanets);
							}
						}
						
						// loop all the ship 
						Iterator<Ship> it2 = plan.getShip().iterator();
						while( it2.hasNext()) {
							Ship ship= it2.next();
							// draw ship 
							if(ship.getPlanetTarget()!=null && ship.validatePosition() && (ship.getPlanetChosen()==plan) ) {
								
								if (plan.getColor() == 1){
								    ship.render(gc, listPlanets, imgShipJ1);
								}
								if (plan.getColor() == 2){
								    ship.render(gc, listPlanets, imgShipJ2);
								}
								// remove ship from the collection
								if (ship.getPlanetTarget().getCircle().isInside(ship.getPoint())){
									if(ship==shipChosen ) {
										shipChosen=null;
									}
									ship.validatePosition();
									it2.remove();
								}
							}
							// to draw square around the ship we select
							if (shipChosen != null) {
								if ( shipChosen == ship) {
									gc.strokeRect(ship.getPoint().getPointX()-15,
											ship.getPoint().getPointY()-15, 30, 30);
									gc.setStroke(Color.RED);
									
								}
								if( shipChosen.getRogue() == ship.getRogue()) {
									gc.strokeRect(ship.getPoint().getPointX()-15,
											ship.getPoint().getPointY()-15, 30, 30);
									gc.setStroke(Color.YELLOW);
									
								}
							}
						}
					}
					action= Planet.end(listPlanets);
				    timer=timer+1;	
				}
				// draw the menu 
				else {	
					gc.drawImage(space, 0, 0);
					gc.drawImage(imgPlay, 340,400);
					gc.drawImage(imgSave, 385, 400);
					gc.drawImage(imgLeave, 430, 400);
					gc.drawImage(imgLoad, 475 , 400);
					
					if (action=="break") {
					    gc.setFill(Color.WHITE);
						gc.fillText("MENU", 380, 380);
					}
					
					if (action=="save") {
						gc.fillText("SAVED", 375, 380);
					}
					
					if (action=="leave") {
						stage.close();
					}
					
					if(action=="load") {
					    gc.fillText("LOAD", 380, 380);
					}
					if(action=="You lose") {
						gc.drawImage(loose, 0, 0);
						gc.fillText(action, 100, 200);
						gc.setFill(Color.RED);
						//this.stop();
						gc.drawImage(imgBreak, 680, 770);
						gc.drawImage(imgSave, 725, 770);
						gc.drawImage(imgLeave, 770, 770);	
					}
					if(action== "VICTORY") {
						gc.drawImage(victory, 0, 0);
						gc.fillText(action + "!!!", 345, 200);
						gc.setFill(Color.YELLOW);
						//this.stop();
						gc.drawImage(imgBreak, 680, 770);
						gc.drawImage(imgSave, 725, 770);
						gc.drawImage(imgLeave, 770, 770);
						
					}
				}
			}
		};

		animation.start();
        
    }

}
