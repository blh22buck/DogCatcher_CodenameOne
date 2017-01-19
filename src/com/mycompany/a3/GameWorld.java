package com.mycompany.a3;
/*
 *Bryce Hairabedian
 * CSC 133 Sec 1
 * Assignment 3
 */

import java.io.InputStream;
import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

public class GameWorld extends Observable {
	// ****************Attributes
	GameObjectCollection goCollection;
	private int mapHeight, mapWidth;
	private int dogsCaptured, catsCaptured, dogPopulation, catPopulation;

	private int currentScore, maxPossibleScore;

	private final int INITIALNETS = 1, INITIALCATS = 3, INITIALDOGS = 3; // amount
	private int count = 0;																		// of
																			// dogs
																			// and
																			// cats
																			// in
																			// the
																			// game
																			// to
																			// start
	int gwWidth, gwHeight, mvAbsY, mvAbsX, scoreY;
	private Net net;
	private Dog dog;
	private Cat cat;
	private int bottomY;
	private Sound catSound, dogSound, netSound;
	private BackSound bgSound;

	private final int INITIAL_NETSIZE = 100;
	Random random = new Random();

	// **************Constructors
	GameWorld() {
		goCollection = new GameObjectCollection();
		dog = new Dog();
		net = new Net();
		cat = new Cat();
		maxPossibleScore = INITIALDOGS * 10;
		catSound = new Sound("catmeow.wav");
		//dogSound = new Sound("dogRuff.wav");
		netSound = new Sound("netBloop.wav");
		
	}
	// **************Operations/Methods

	public void initLayout() {

		// code here to create the
		// initial game objects/layout

		catPopulation = INITIALCATS;
		dogPopulation = INITIALDOGS;
		buildDogs(INITIALDOGS);
		buildCats(INITIALCATS);
		buildNet(INITIALNETS);
		//bgSound = new BackSound("danceDance");
		//printMap();
	}

	// Initialize Gameworld size
	// initializes MapView bounds
	public void initGWSize(int mvAbsX, int mvAbsY, int mvWidth, int mvHeight, int scoreY, int bottomY) {
		this.mvAbsY = mvAbsY;
		this.mvAbsX = mvAbsX;
		gwWidth = mvWidth;
		gwHeight = mvHeight;
		this.scoreY = scoreY;
		this.bottomY = bottomY;
		
		setChanged();
		notifyObservers();
	}

	public int getBottomY() {
		return this.bottomY;
	}

	private void buildNet(int amountToBuild) {
		if (amountToBuild > 0) {
			for (int i = 0; i < amountToBuild; i++) {
				net = new Net(this, INITIAL_NETSIZE);
				goCollection.add(net);
			}
		}

	}// END buildNet()

	private void buildDogs(int amountToBuild) {
		if (amountToBuild > 0) {
			for (int i = 0; i < amountToBuild; i++) {
				dog = new Dog(this, getRandIntBetween(20, 50));
				// dog = new Dog(this);
				goCollection.add(dog);
			}
		}
	}// END buildDog

	private void buildCats(int amountToBuild) {
		if (amountToBuild > 0) {
			for (int i = 0; i < amountToBuild; i++) {
				cat = new Cat(this, getRandIntBetween(20, 50));
				// cat = new Cat(this);
				goCollection.add(cat);
			}
		}
	}// END buildCat

	public void createKitten(Cat inCat) {
		cat = new Cat(inCat, this);
		// cat.setTargetWorld(this);
		// cat.setKittenTimer();
		goCollection.add(cat);
		catPopulation++;

	}

	public void createKitten(Cat inCat, GameWorld gw) {
		cat = new Cat(inCat, gw);
		// cat.setTargetWorld(this);
		// cat.setKittenTimer();
		goCollection.add(cat);
		catPopulation++;
	}

	void createKitten() {
		cat = new Cat((Cat) goCollection.getRandomGameObject("CAT"), this);
		// cat.setKittenTimer();
		goCollection.add(cat);
		catPopulation++;

	}

	public int getAbsY() {
		return this.mvAbsY;
	}

	public int getAbsX() {
		return this.mvAbsX;
	}

	public int getScoreY() {
		return this.scoreY;
	}

	public int getmvAbsY() {
		return this.mvAbsY;
	}

	public int getmvAbsX() {
		return this.mvAbsX;
	}

	public int getMapHeight() {
		return gwHeight;
	}

	public int getMapWidth() {
		return gwWidth;
	}

	public int getgwHeight() {
		return gwHeight;
	}

	public int getgwWidth() {
		return gwWidth;
	}

	// Dialog Show help commands
	public void showHelp() {
		final InteractionDialog dlg = new InteractionDialog("Help");
		dlg.setLayout(new BorderLayout());

		Button close = new Button("Close");

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dlg.dispose();
			}
		});
		Container helpContainer = new Container(new GridLayout(13, 1));

		helpContainer.add(new Label("Press 'e' to Expand Net"));
		helpContainer.add(new Label("Press 'c' to Contract Net"));
		helpContainer.add(new Label("Press 's' to Scoop"));
		helpContainer.add(new Label("Press 'r' to move net to right"));
		helpContainer.add(new Label("Press 'l' to move net to left"));
		helpContainer.add(new Label("Press 'u' to move net to up"));
		helpContainer.add(new Label("Press 'd' to move net to down"));
		helpContainer.add(new Label("Press 'o' Jump-to-Dog"));
		helpContainer.add(new Label("Press 'a' Jump-to-Cat"));
		helpContainer.add(new Label("Press 'k' to prduce Kitten"));
		helpContainer.add(new Label("Press 'f' for cat-dog fight"));
		helpContainer.add(new Label("Press 't' to tick"));
		helpContainer.add(new Label("Press 'x' to quit"));
		dlg.addComponent(BorderLayout.CENTER, helpContainer);
		dlg.addComponent(BorderLayout.SOUTH, close);
		dlg.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.GRAY));
		dlg.show(Display.getInstance().getDisplayHeight() / 4, Display.getInstance().getDisplayHeight() / 4, 200, 200);
		close.getAllStyles().setPadding(Component.LEFT, dlg.getX() / 2 + 15);
	}

	// Dialog About
	public void showAbout() {
		final InteractionDialog dlg = new InteractionDialog("About");
		dlg.setLayout(new BorderLayout());
		SpanLabel mysp = new SpanLabel("Bryce Hairabedian" + "  |  CSC 133" + "  |  Assignment 3");

		mysp.getAllStyles().setPadding(Component.LEFT, 0);
		dlg.getAllStyles().setBgColor(ColorUtil.LTGRAY);

		Button close = new Button("Close");

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dlg.dispose();
			}
		});
		dlg.addComponent(BorderLayout.CENTER, mysp);
		dlg.addComponent(BorderLayout.SOUTH, close);
		dlg.show(Display.getInstance().getDisplayHeight() / 4, Display.getInstance().getDisplayHeight() / 4, 200, 200);
		close.getAllStyles().setPadding(Component.LEFT, dlg.getX() / 2 + 15);
	}// showAbout

	private boolean soundBool = true;

	public boolean isSoundOn() {
		return this.soundBool;
	}
	
	
	public void toggleSound() {
		this.setSound(!this.soundBool);
	}

	public void setSound(boolean set) {
		this.soundBool = set;
		
		setChanged();
		notifyObservers();
	}

	public void exitApp() {
		final Dialog dlg = new Dialog("Exit App?");
		dlg.setLayout(new GridLayout(1, 2));
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		yesButton.getAllStyles().setBgTransparency(255);
		yesButton.getAllStyles().setBgColor(ColorUtil.GREEN);
		yesButton.getAllStyles().setMargin(120, 120, 50, 50);
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dlg.dispose();
				Display.getInstance().exitApplication();
			}
		});
		noButton.getAllStyles().setBgTransparency(255);
		noButton.getAllStyles().setBgColor(ColorHelper.getRed(4));
		noButton.getAllStyles().setMargin(120, 120, 50, 50);
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dlg.dispose();
			}
		});
		dlg.add(yesButton).add(noButton);
		dlg.setDisposeWhenPointerOutOfBounds(true);
		dlg.show();

	}

	public void expandNet() {
		if (net.getSize() < 100) {
			net.setSize(net.getSize() + 1);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("Cannot Expand Net, too large");
		}
	}

	public void downSizeNet() {
		if (net.getSize() > 50) {
			net.setSize(net.getSize() - 1);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("Cannot Contract Net, too small");
		}
	}

	public void netToRandomDog() {
		if (dogPopulation > 0) {
			net.jumpToDog((Dog) goCollection.getRandomGameObject("DOG"));
			setChanged();
			notifyObservers();
		} else if (dogPopulation < 1) {
			System.out.println("ERROR: There are no dogs to jump net to");
		}
	}

	public void netToRandomCat() {
		if (catPopulation > 0) {
			net.jumpToCat((Cat) goCollection.getRandomGameObject("CAT"));
			setChanged();
			notifyObservers();
		}
		if (catPopulation < 1) {
			System.out.println("ERROR: There are no cats to jump net to");
		}

	}

	public IIterator getGameObjIterator() {
		return goCollection.getIterator();
	}
	
	/*
	 * scoop up all the animals that are in the net. This causes all of the
	 * animals whose centers are within the boundaries of the bounding square of
	 * the net to be removed from the game world, and the score to be updated
	 * according to the rules of play described above
	 */
	public void scoop() {// **With iterator
		// TODO Sound scoopSound = new Sound()
		GameObject loc = null;
		IIterator scoopI = goCollection.getIterator();
		while (scoopI.hasNext()) {
			loc = scoopI.getNext();
			if (anythingToScoop(loc)) {
				if (loc instanceof Cat) {
					catCaptured((Cat) loc);// update score, remove cat,
					// update population and captured using catCaptured()
				} else if (loc instanceof Dog) {// dog has been scooped
					// update score, remove dog,
					// update population and captured using dogCaptured()
					dogCaptured((Dog) loc);
				}
				if(this.isSoundOn()){netSound.play();}
				
			}
		}
		setChanged();
		notifyObservers();
		//printMap();
	}// scoop()
		// anythingToScoop: checks to see
		// if the index points to an Animal and within the nets boundaries
		// Returns; True if so , false otherwise

	private boolean anythingToScoop(GameObject go) {
		boolean scoopBool = false;
		if ((go instanceof Animals)) {
			if ((go.getXLocation() >= net.getXLocation() - (net.getSize() / 2))
					&& (go.getXLocation() <= net.getXLocation() + (net.getSize() / 2))
					&& (go.getYLocation() <= net.getYLocation() + (net.getSize() / 2))
					&& (go.getYLocation() >= net.getYLocation() - (net.getSize() / 2))) {
				scoopBool = true;
			}
		}
		return scoopBool;
	}

	private void catCaptured(Cat i) {// but catching a cat deducts 10 points
		currentScore -= 10;
		goCollection.remove(i);
		catPopulation--;
		catsCaptured++;
		setChanged();
		notifyObservers();
	}

	private void dogCaptured(Dog i) {// Catching an unscratched dog is worth 10
										// points - scratches
		currentScore += 10 - i.getScratches();
		goCollection.remove(i);
		dogPopulation--;
		dogsCaptured++;
		if(dogPopulation <1){
			this.endTheGame();
		}
		setChanged();
		notifyObservers();
	}
	public void endTheGame(){
		
		final Dialog dlg = new Dialog("Final Score");
		dlg.setLayout(new GridLayout(2,1));
		Button quitButton = new Button("Quit");
		quitButton.getAllStyles().setBgTransparency(255);
		quitButton.getAllStyles().setBgColor(ColorUtil.GREEN);
		//quitButton.getAllStyles().setMargin(120, 120, 50, 50);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dlg.dispose();
				Display.getInstance().exitApplication();
			}
		});
		
		dlg.add(new SpanLabel(this.getScore(), "DialogBody"));
		dlg.add(quitButton);

		dlg.setDisposeWhenPointerOutOfBounds(true);
		dlg.show();
	}
	public void moveLeft() {
		net.moveLeft();
		setChanged();
		notifyObservers();
	}

	public void moveDown() {
		net.moveDown();
		setChanged();
		notifyObservers();
	}

	public void moveRight() {
		net.moveRight();
		setChanged();
		notifyObservers();
	}

	public void moveUp() {
		net.moveUp();
		setChanged();
		notifyObservers();
	}

	/*
	 * pretend that a fight occurred between a cat and a dog (a cat and a dog
	 * run into each other) 1. The program randomly picks a dog and scratches it
	 * once, changes its color, and reduces its speed by 1. If there are no
	 * cats, print an error message instead.
	 */
	public void catDogfight() {
		if (catPopulation < 1) {// are there cats?
			System.out.println("There are no cats in the game");
		} else if (dogPopulation > 0) {// are there dogs?
			((Dog) (goCollection.getRandomGameObject("DOG"))).scratchThisDog();// scratches
																				// dog,
																				// changes
																				// color
			setChanged();
			notifyObservers();
		} // reduces speed by 1
	}

	public void printMap() {
		GameObject loc = null;
		IIterator scoopI = goCollection.getIterator();

		System.out.println("        _______________|***| Map |***_______________ ");
		while (scoopI.hasNext()) {
			loc = scoopI.getNext();
			if ((loc instanceof Animals)) {
				if (loc instanceof Dog) {
					System.out.println(((Dog) (loc)).toString());
				} else if (loc instanceof Cat) {
					System.out.println(((Cat) (loc)).toString());
				}
			} else if ((loc instanceof Net)) {
				System.out.println(((Net) (loc)).toString());
			}
		} // while
	}// printMap

	/*
	 * pretend that a collision occurred between two cats. Later this semester,
	 * your program will determine this automatically 1. But for now, if the
	 * player specifies the command, the program first checks if there are at
	 * least two cats. If so, it randomly picks a cat and produces a kitten in a
	 * location that is close to the chosen cat. If the number of cats in the
	 * world is less than two, print an error message without producing a
	 * kitten.
	 */
	public void catFight() {
		if (catPopulation > 1) {// the program first checks if there are at
								// least two cats
			// randomly pick a cat
			createKitten();
			setChanged();
			notifyObservers();
		} else {
			System.out.println("ERROR: Cannot produce a kitten. Less than 2 Cats in the world. ");
		}
	}

	public int getRandIntBetween(int min, int max) {
		int r = random.nextInt(max - min) + min;
		return r;
	}

	
	
	/*
	 * All moving objects are told to update their positions according to their
	 * current direction and speed.
	 */
	public void tick() {
		GameObject loc = null;
		IIterator moveI = goCollection.getIterator();
		
		while (moveI.hasNext()) {
			loc = moveI.getNext();
			if (loc instanceof IMoving) {
				((Animals) loc).move(20);

				setChanged();
				notifyObservers();
			}
		} // while

		IIterator tickI = goCollection.getIterator();

		while (tickI.hasNext()) {
			ICollider obj1 = (ICollider) tickI.getNext();

			IIterator tickI2 = goCollection.getIterator();
			while (tickI2.hasNext()) {
				ICollider obj2 = (ICollider) tickI2.getNext();
				if (obj2 != obj1 && ((obj1 instanceof Animals)==true)
						&&((obj2 instanceof Animals)==true)) {
					if (obj1.collidesWith(obj2)) {
						if (obj1 instanceof Cat && obj2 instanceof Cat) {// the're
																			// both
																		// cats
							count++;
							if (count > 1) {
								count = 0;
								obj1.handleCollision(obj2);
								if(this.isSoundOn()){catSound.play();}
							
							}
							
							
						} else if (obj1 instanceof Dog && obj2 instanceof Cat) {
							obj1.handleCollision(obj2);
							if(this.isSoundOn()){
								//dogSound.play();
							}
						}
					}
				}
			}
		}
		setChanged();
		notifyObservers();

	}// tick()
	public void heal(){
		IIterator healI = goCollection.getIterator();
		while(healI.hasNext()){
			GameObject obj = healI.getNext();
			if(obj instanceof Dog && ((Dog)obj).isSelected()){
				Dog locDog = (Dog)obj;
				locDog.healThisDog();
				setChanged();
				notifyObservers();
			}
		}
	}
	public void quit() {
		System.exit(0);
	}

	/*
	 * print the points of game state values: (1) current score, (2) number of
	 * dogs/cats captured, and (3) number of dogs/cats left in the world. Output
	 * should be appropriately labeled in easily readable format.
	 */
	public void printPoints() {
		if (currentScore > maxPossibleScore) {
			System.out.println("Current Score: " + maxPossibleScore + "  Dogs Captured: " + dogsCaptured
					+ "  Cats Captured: " + catsCaptured + "  Dogs Remaining: " + dogPopulation + "  Cats Remaining: "
					+ catPopulation);
		} else {
			System.out.println("Current-Score: " + currentScore + "  Dogs Captured: " + dogsCaptured
					+ "  Cats Captured: " + catsCaptured + "  Dogs Remaining: " + dogPopulation + "  Cats Remaining: "
					+ catPopulation);
		}
	}// PrintPoints()

	public String getScore() {
		if (currentScore > maxPossibleScore) {
			return "Final Score: " + maxPossibleScore + "  Dogs Captured: " + dogsCaptured
					+ "  Cats Captured: " + catsCaptured + "  Dogs Remaining: " + dogPopulation + "  Cats Remaining: "
					+ catPopulation;
		} else {
			return "Final Score: " + currentScore + "  Dogs Captured: " + dogsCaptured
					+ "  Cats Captured: " + catsCaptured + "  Dogs Remaining: " + dogPopulation + "  Cats Remaining: "
					+ catPopulation;
		}
	}// PrintPoints()
	public int getCurrentScore() {
		return currentScore;
	}

	public int getDogsCaptured() {
		return dogsCaptured;
	}

	public int getCatsCaptured() {
		return catsCaptured;
	}

	public int getDogPopulation() {
		return dogPopulation;
	}

	public int getCatPopulation() {
		return catPopulation;
	}

}// END GAMEWORLD