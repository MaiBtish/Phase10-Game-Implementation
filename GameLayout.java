//Done by Mai Btish and Nada Abughazalah

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameLayout {

	/* welcome & rules */
	//panes
	private StackPane welcomeStackPane = new StackPane();
	private HBox buttonsHPane  = new HBox(10);
	private VBox rulesVPane = new VBox(10);
	private ScrollPane rulesScrollPane = new ScrollPane();
	//images
	private Image gameWallpaper = new Image("Cards/wallpaper.jpg");
	private ImageView wallpaper = new ImageView(gameWallpaper);
	//buttons
	Button exitButton = new Button("Exit");
	Button gameRulesButton = new Button("How To Play");
	Button playButton = new Button("Start Game");
	Button returnButton = new Button("Return");
	//labels & text
	private Label gameRulesText = new Label("HOW TO PLAY PHASE 10 \n" +
			"CARDS:\n"+
			"A deck of 108 cards: 24 each of red, blue, yellow, and green cards " + 
			"numbered “1” through “12,” four “Skip” cards, and eight “Wild” cards " +
 			"\n\nOBJECTIVE:\n" +
			"Be the first player to complete their phase!" +
			"\n\nPLAY:\n" + 
			"Each player gets 10 cards and they must complete the phase (written in rectangles) to win the game. "+
			"On your turn, draw one card, either the top card from the draw pile or the top " + 
			"card from the discard pile, and add it to your hand. End your turn by discarding any " + 
			"one of your cards onto the top of the discard pile. During the play of the first hand, " + 
			"each player tries to complete Phase 1. A Phase is a combination of cards." +
			"\n\nDEFENITIONS:\n" +
			"SET OF 3: In this phase, you must submit two sets of three cards that have the same number.\n"+
			"WILD CARDS: A “Wild” card may be used in place of a number.\n"+
			"SKIP CARDS: Skip cards have only one purpose: to cause another player to lose a turn."); 

	/* GAME */
	//panes
	private HBox p1hb = new HBox(); //player 1's cards
	private HBox p2hb = new HBox(); //player 2's cards
	private GridPane rightGridPane = new GridPane(); //draw and discard piles  
	private VBox leftVbox = new VBox(30); //control buttons
	private BorderPane playingPane = new BorderPane(); //main pane

	//nodes
	Button submitButton = new Button("Submit");
	Button cancelButton = new Button("Cancel");
	Button exitButton2 = new Button("Exit");
	private Image cardStack = new Image("Cards/cardstack.png");
	private ImageView drawStack = new ImageView(cardStack);
	private Rectangle discardRectangle = new Rectangle(82,121);
	
	private Label drawLabel = new Label("Draw");
	private Label discardLabel = new Label("Discard"); 
	
	StackPane discardStack = new StackPane(discardRectangle);
	
	public GameLayout() {
		setStyles();
		setRulesScene();
		setWelcomeScene();
		
		//game scene
		playingPane.setCenter(playersPhases);
		playingPane.setBottom(p1hb);
		playingPane.setTop(p2hb);
		
		//draw & discard pane
		rightGridPane.add(drawStack,0,0); rightGridPane.add(drawLabel,0,1);
		rightGridPane.add(discardStack,0,2); rightGridPane.add(discardLabel,0,3);
		leftVbox.getChildren().addAll(exitButton2,submitButton,cancelButton);
		playingPane.setRight(rightGridPane);
		playingPane.setLeft(leftVbox);
	}
	
	/* phase panes */
	public HBox p1Set = new HBox();
	public HBox p1Set_1 = new HBox();
	public HBox p2Set = new HBox();
	public HBox p2Set_1 = new HBox();
	private StackPane p1SetMain = new StackPane();
	private StackPane p1SetMain_1 = new StackPane();
	private StackPane p2SetMain = new StackPane();
	private StackPane p2SetMain_1 = new StackPane();
	private GridPane playersPhases = new GridPane();
	
	public void phase1layout() {
		Rectangle[] rect = new Rectangle[4];
		for (int i=0; i<rect.length; i++) {
			rect[i] = new Rectangle();
			rect[i].setWidth(400);
			rect[i].setHeight(120);
			rect[i].setArcHeight(15);
			rect[i].setArcWidth(15);
			if (i==0 || i==1)
				rect[i].setFill(Color.rgb(243,217,151));
			else
				rect[i].setFill(Color.rgb(186,230,175));
		}
		p1Set.setAlignment(Pos.BASELINE_CENTER);
		p1Set_1.setAlignment(Pos.BASELINE_CENTER);
		p2Set.setAlignment(Pos.BASELINE_CENTER);
		p2Set_1.setAlignment(Pos.BASELINE_CENTER);
		
		p2SetMain.getChildren().add(rect[0]);
		p2SetMain.getChildren().add(new Label("Set of 3"));
		p2SetMain.getChildren().add(p2Set);
		
		p2SetMain_1.getChildren().add(rect[1]);
		p2SetMain_1.getChildren().add(new Label("Set of 3"));
		p2SetMain_1.getChildren().add(p2Set_1);
		
		p1SetMain.getChildren().add(rect[2]);
		p1SetMain.getChildren().add(new Label("Set of 3"));
		p1SetMain.getChildren().add(p1Set);
		
		p1SetMain_1.getChildren().add(rect[3]);
		p1SetMain_1.getChildren().add(new Label("Set of 3"));
		p1SetMain_1.getChildren().add(p1Set_1);
		
		playersPhases.add(p2SetMain, 0, 0);
		playersPhases.add(p2SetMain_1, 1, 0);
    playersPhases.add(p1SetMain, 0, 1);
    playersPhases.add(p1SetMain_1, 1, 1);
	}

	//styles for GAME SCENE
	public void setStyles() {
		//images
		drawStack.setFitWidth(88);
		drawStack.setPreserveRatio(true);
		
		//buttons
		exitButton2.setPrefWidth(60);
		submitButton.setPrefWidth(60);
		cancelButton.setPrefWidth(60);

		//label
		GridPane.setHalignment(drawLabel, HPos.CENTER);
		GridPane.setHalignment(discardLabel, HPos.CENTER);
		
		//rectangles
		discardRectangle.setArcWidth(10);
		discardRectangle.setArcHeight(10);
		discardRectangle.setFill(Color.WHITE);
		discardRectangle.setOpacity(0);

		//panes
		rightGridPane.setVgap(5);
		rightGridPane.setPadding(new Insets(10,5,10,5));
		rightGridPane.setBackground(new Background(new BackgroundFill(
				Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
		rightGridPane.setAlignment(Pos.CENTER);
		
		leftVbox.setPadding(new Insets(5,5,5,5));
		leftVbox.setBackground(new Background(new BackgroundFill(
				Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
		leftVbox.setAlignment(Pos.CENTER);
		playersPhases.setAlignment(Pos.CENTER);
		playersPhases.setVgap(15);
		playersPhases.setHgap(5);
		playersPhases.setBackground(new Background(new BackgroundFill(
				Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
		p2hb.setPadding(new Insets(3,0,3,0));
		p1hb.setPadding(new Insets(3,0,3,0));
		p2hb.setAlignment(Pos.BASELINE_CENTER);
		p1hb.setAlignment(Pos.BASELINE_CENTER);
		p2hb.setBackground(new Background(new BackgroundFill(
				Color.rgb(243,217,151), CornerRadii.EMPTY, Insets.EMPTY)));
		p1hb.setBackground(new Background(new BackgroundFill(
				Color.rgb(186,230,175), CornerRadii.EMPTY, Insets.EMPTY)));
		playingPane.setBorder(Border.EMPTY);
	}
	
	public void setRulesScene() {
		//styles
		gameRulesText.setWrapText(true);
		gameRulesText.setMaxWidth(280);
		gameRulesText.setPadding(new Insets(15,15,15,15));
		returnButton.setPrefWidth(90);
		rulesScrollPane.setBackground(Background.EMPTY);;
		rulesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		rulesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		rulesVPane.setAlignment(Pos.CENTER);

		//adding nodes in panes
		rulesVPane.getChildren().addAll(gameRulesText,returnButton);
		rulesScrollPane.setContent(rulesVPane);
	}

	public void setWelcomeScene() {
		//styles
		wallpaper.setFitWidth(550);
		wallpaper.setPreserveRatio(true);
		gameRulesButton.setPrefWidth(90);
		playButton.setPrefWidth(90);
		exitButton.setPrefWidth(90);
		buttonsHPane.setAlignment(Pos.BOTTOM_CENTER); 
		buttonsHPane.setPadding(new Insets(0,0,30,0));

		//adding nodes to panes
		welcomeStackPane.getChildren().addAll(wallpaper,buttonsHPane);
		buttonsHPane.getChildren().addAll(gameRulesButton,playButton,exitButton);
	}

	/* get methods */
	public ImageView getDrawStack() {
		return drawStack;
	}
	
	public StackPane getDiscardStack() {
		return discardStack;
	}
	
	public HBox getp1hb() {
		return p1hb;
	}
	
	public HBox getp2hb() {
		return p2hb;
	}
	
	public StackPane getWelcomeStackPane() {
		return welcomeStackPane;
	}
	
	public ScrollPane getRulesScrollPane() {
		return rulesScrollPane;
	}
	
	public BorderPane getPlayingPane() {
		return playingPane;
  }
    
	public void removeStackPane() {
		playersPhases.getChildren().removeAll();
	}
}
