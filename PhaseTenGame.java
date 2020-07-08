//Done by Mai Btish and Nada Abughazalah

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PhaseTenGame extends Application {
	/* classes */
	private Cards cards = new Cards();
	private GameLayout layout = new GameLayout();

	/* scenes */
	private Scene gameScene = new Scene(layout.getPlayingPane(),1000,600);
	private Scene welcomeScene = new Scene(layout.getWelcomeStackPane(),500,281);
	private Scene rulesScene = new Scene(layout.getRulesScrollPane(),281,281);

	/* cards */
	private ArrayList<ImageView> p1Cards = new ArrayList<ImageView>();
	private ArrayList<ImageView> p2Cards = new ArrayList<ImageView>();

	/* other */
	private Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	private Stage stage;

	private boolean P1TURN = false, P2TURN = false;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage; //link the stages in both classes
		layout.phase1layout();

		//stage
		stage.setResizable(false);
		stage.setScene(welcomeScene);
		stage.setTitle("Phase 10");
		stage.show();

		/* event handling */
		//welcome screen
		layout.submitButton.setOnAction(new Handling());
		layout.cancelButton.setOnAction(new Handling());
		layout.playButton.setOnAction(new Handling());
		layout.gameRulesButton.setOnAction(new Handling());
		layout.returnButton.setOnAction(new Handling());
		layout.exitButton.setOnAction(new Handling());
		layout.exitButton2.setOnAction(new Handling());

		drawFromDeck(); //draw a card from the deck OR
		drawFromDiscard(); //from the discard pile
		addToPhase1P1(p1Cards, layout.p1Set, layout.p1Set_1); //adding cards to p1's phases
		addToPhase1P2(p2Cards, layout.p2Set, layout.p2Set_1); //adding cards to p2's phases
		moveToDiscard(); //player discards card
	}

	public void drawFromDeck() {
		for (int i = 0; i < p1Cards.size(); i++) 
			p1Cards.get(i).setDisable(true);

		for (int i = 0; i < p2Cards.size(); i++) 
			p2Cards.get(i).setDisable(true);

		layout.getDrawStack().setOnMouseClicked(e ->{
			layout.getDiscardStack().setDisable(true);
			if (P1TURN == true && P2TURN == false) {
				p1Cards.add(cards.getDeck().get(0));
				cards.getDeck().remove(0);
				layout.getp1hb().getChildren().clear();
				for (int i=0; i<p1Cards.size(); i++) 
					layout.getp1hb().getChildren().add(p1Cards.get(i));
				layout.getDrawStack().setDisable(true);

				for (int i = 0; i < p1Cards.size(); i++) 
					p1Cards.get(i).setDisable(false);

				addToPhase1P1(p1Cards, layout.p1Set, layout.p1Set_1);
				moveToDiscard();
			}
			else if (P1TURN == false && P2TURN == true) {
				p2Cards.add(cards.getDeck().get(0));
				cards.getDeck().remove(0);
				layout.getp2hb().getChildren().clear();
				for (int i=0; i<p2Cards.size(); i++) 
					layout.getp2hb().getChildren().add(p2Cards.get(i));
				layout.getDrawStack().setDisable(true);

				for (int i = 0; i < p1Cards.size(); i++) 
					p2Cards.get(i).setDisable(false);

				addToPhase1P2(p2Cards, layout.p2Set, layout.p2Set_1);
				moveToDiscard();
			}
		}); 
	}

	public void drawFromDiscard() { // drawing cards from discard deck
		layout.getDiscardStack().setOnMouseClicked(e -> {
			if(P1TURN == true && P2TURN == false) { //start p1 turn
				//don't draw a card if it's empty, if deck is disabled, or if the top card is skip
				if (cards.getDiscardDeck().isEmpty() || layout.getDrawStack().isDisabled()
						|| cards.getDiscardDeck().get(cards.getDiscardDeck().size()-1).getId().equals("14") )
					e.consume();
				else {
					p1Cards.add(cards.getDiscardDeck().get(cards.getDiscardDeck().size()-1)); //add topmost discarded card to player's cards
					cards.getDiscardDeck().remove(cards.getDiscardDeck().size()-1); //remove it from the discard deck
					layout.getp1hb().getChildren().clear(); //clear player's deck
					for (int i=0; i<p1Cards.size(); i++) //and add the cards again
						layout.getp1hb().getChildren().add(p1Cards.get(i));
					//enable p1's cards, draw and discard stacks
					for (int i = 0; i < p1Cards.size(); i++) 
						p1Cards.get(i).setDisable(false);
					layout.getDrawStack().setDisable(true);
					layout.getDiscardStack().setDisable(true);
				}
				addToPhase1P1(p1Cards, layout.p1Set, layout.p1Set_1);
				moveToDiscard();
			}

			if(P1TURN == false && P2TURN == true) {
				//don't draw a card if it's empty, if deck is disabled, or if the top card is skip
				if (cards.getDiscardDeck().isEmpty() || layout.getDrawStack().isDisabled()
						|| cards.getDiscardDeck().get(cards.getDiscardDeck().size()-1).getId().equals("14") )
					e.consume();
				else {
					p2Cards.add(cards.getDiscardDeck().get(cards.getDiscardDeck().size()-1));
					cards.getDiscardDeck().remove(cards.getDiscardDeck().size()-1);
					layout.getp2hb().getChildren().clear();
					for (int i=0; i<p2Cards.size(); i++) 
						layout.getp2hb().getChildren().add(p2Cards.get(i));
					for (int i = 0; i < p1Cards.size(); i++) 
						p2Cards.get(i).setDisable(false);
					layout.getDrawStack().setDisable(true);
					layout.getDiscardStack().setDisable(true);
				}
				addToPhase1P2(p2Cards, layout.p2Set, layout.p2Set_1);
				moveToDiscard();
			}
		});
	}

	public void moveToDiscard() {
		if(P1TURN == true && P2TURN == false) { //player 1's turn
			for (int i=0; i<p1Cards.size(); i++) {//start player 1 turn
				/*drag event handling*/
				p1Cards.get(i).setOnDragDetected(event1 -> { //p1 drags a card
					//if p1's phase 1 sets are NOT empty
					if (layout.p1Set.getChildren().isEmpty() == false | layout.p1Set_1.getChildren().isEmpty() == false) {
						layout.getDiscardStack().setDisable(true); //disable discard stack
					}
					else { //if phase 1 sets are empty
						layout.getDiscardStack().setDisable(false); //enable discard stack
					}

					ImageView card = (ImageView) event1.getSource();
					Dragboard db = card.startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putImage(card.getImage());
					db.setContent(content); 
					event1.consume();

					layout.discardStack.setOnDragOver(event2 -> { //discard stack receives new card
						event2.acceptTransferModes(TransferMode.MOVE);
						event2.consume();
					});

					layout.discardStack.setOnDragEntered(event3 -> {
						if (event3.getGestureSource() != layout.discardStack &&
								event3.getDragboard().hasImage()) {
							layout.discardStack.setBackground(new Background(new BackgroundFill(
									Color.LAWNGREEN, new CornerRadii(10,10,10,10,false), null)));
						}
						event3.consume();
					});

					layout.discardStack.setOnDragExited(event4 -> {
						layout.discardStack.setBackground(new Background(new BackgroundFill(
								Color.rgb(255,255,255), new CornerRadii(10,10,10,10,false), null)));
						event4.consume();
					});

					layout.discardStack.setOnDragDropped(event5 -> {
						Dragboard db1 = event5.getDragboard();
						boolean success = false;
						if (db1.hasImage()) {
							cards.getDiscardDeck().add(card);
							p1Cards.remove( p1Cards.indexOf(card) ); //remove the card from the player's ArrayList
							layout.discardStack.getChildren().add(
									cards.getDiscardDeck().get(cards.getDiscardDeck().size()-1));
							success = true;
						}
						event5.setDropCompleted(success);
						event5.consume();
						/*drag event handling*/

						//check whose turn is it
						if (card.getId().equals("14")) { //p1 discarded SKIP, p1 plays again
							P1TURN = true;
							P2TURN = false;
							for(int m=0;m<p2Cards.size();m++) 
								p2Cards.get(m).setDisable(true);
							layout.getDrawStack().setDisable(false);
							drawFromDeck(); //p1 can only draw from deck
							addToPhase1P1(p1Cards, layout.p1Set, layout.p1Set_1);
						}
						else { //p1's turn is over, now p2 plays
							P1TURN = false;
							P2TURN = true;
							for(int m=0;m<p1Cards.size();m++)
								p1Cards.get(m).setDisable(true);
							layout.getDrawStack().setDisable(false);
						}
					});
				});
			}//end p1 for loop
		}//end p1 turn


		//same as previous block but for p2
		if (P1TURN == false && P2TURN == true) {
			for (int i=0; i<p2Cards.size(); i++) {//start player two turn
				p2Cards.get(i).setOnDragDetected(event1 -> {

					if (layout.p2Set.getChildren().isEmpty() == false | layout.p2Set_1.getChildren().isEmpty() == false) {
						layout.getDiscardStack().setDisable(true);
					}
					else {
						layout.getDiscardStack().setDisable(false);
					}

					ImageView card = (ImageView) event1.getSource();
					Dragboard db = card.startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putImage(card.getImage());
					db.setContent(content);
					event1.consume();
					layout.discardStack.setOnDragOver(event2 -> {
						event2.acceptTransferModes(TransferMode.MOVE);
						event2.consume();
					});

					layout.discardStack.setOnDragEntered(event3 -> {
						if (event3.getGestureSource() != layout.discardStack &&
								event3.getDragboard().hasImage()) {
							layout.discardStack.setBackground(new Background(new BackgroundFill(
									Color.LAWNGREEN, new CornerRadii(10,10,10,10,false), null)));
						}
						event3.consume();
					});

					layout.discardStack.setOnDragExited(event4 -> {
						layout.discardStack.setBackground(new Background(new BackgroundFill(
								Color.rgb(255,255,255), new CornerRadii(10,10,10,10,false), null)));
						event4.consume();
					});

					layout.discardStack.setOnDragDropped(event5 -> {
						Dragboard db1 = event5.getDragboard();
						boolean success = false;
						if (db1.hasImage()) {
							cards.getDiscardDeck().add(card);
							p2Cards.remove( p2Cards.indexOf(card) ); //remove the card from the player's ArrayList
							layout.discardStack.getChildren().add(
									cards.getDiscardDeck().get(cards.getDiscardDeck().size()-1));
							success = true;
						}

						event5.setDropCompleted(success);
						event5.consume();

						if (card.getId().equals("14")) {
							P1TURN = false;
							P2TURN = true;

							for(int m=0;m<p1Cards.size();m++)
								p1Cards.get(m).setDisable(true);
							layout.getDrawStack().setDisable(false); 
							drawFromDeck(); //p2 can only draw from deck
							addToPhase1P2(p2Cards, layout.p2Set, layout.p2Set_1);
						}
						else {
							P1TURN = true;
							P2TURN = false;
							for(int m=0;m<p2Cards.size();m++)
								p2Cards.get(m).setDisable(true);
							layout.getDrawStack().setDisable(false);
							addToPhase1P1(p1Cards, layout.p1Set, layout.p1Set_1);
						}
					});
				});
			} //end p2 for loop
		}//end p2 turn
	}

	/*Phase One Methods Start*/
	public void addToPhase1P1(ArrayList<ImageView> p1Cards,HBox p1Set_1, HBox p1Set) {
		if(P1TURN == true && P2TURN == false ) {
			for(int i=0 ; i<p1Cards.size() ; i++) {
				p1Cards.get(i).setOnMouseClicked(e -> {
					MouseButton button = e.getButton();
					ImageView card = (ImageView)e.getSource();
					if (p1Set.getChildren().size() < 3) {
						if (button == MouseButton.SECONDARY) { //add cards to set of 3
							if (card.getId().equals("14")) //skip card
								e.consume();
							else {
								try {
									p1Set.getChildren().add(card);
								}
								catch(IllegalArgumentException ex) {
									e.consume();
								}
							}
						}
					}
					if (p1Set_1.getChildren().size() < 3) {
						if (button == MouseButton.PRIMARY) {
							if (card.getId().equals("14")) //skip card
								e.consume();
							else {
								try {
									p1Set_1.getChildren().add(card);
								}
								catch(IllegalArgumentException ex) {
									e.consume();
								}
							}
						}
					}
				});
			}
		}
	}

	public void addToPhase1P2(ArrayList<ImageView> p2Cards,HBox p2Set_1, HBox p2Set) {
		if(P2TURN == true && P1TURN == false) { 
			for(int i=0 ; i<p2Cards.size() ; i++) {
				p2Cards.get(i).setOnMouseClicked(e -> {
					MouseButton button = e.getButton();
					ImageView card = (ImageView)e.getSource();

					if (p2Set.getChildren().size() < 3) {
						if (button == MouseButton.SECONDARY) { //add cards to set of 3
							if (card.getId().equals("14")) //skip card
								e.consume();
							else {
								try {
									p2Set.getChildren().add(card);
								}
								catch(IllegalArgumentException ex) {
									e.consume();
								}
							}
						}
					}

					if (p2Set_1.getChildren().size() < 3) {
						if (button == MouseButton.PRIMARY) {
							if (card.getId().equals("14")) //skip card
								e.consume();
							else {
								try {
									p2Set_1.getChildren().add(card);
								}
								catch(IllegalArgumentException ex) {
									e.consume();
								}
						}
					}
				});
			}
		}
	}

	public boolean checkPlayerOneSetPhaseOne(){
		if ((layout.p1Set.getChildren().get(0).getId().equals("1")  || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("1") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("1") || layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("2")	|| layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("2") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("2")||layout.p1Set.getChildren().get(2).getId().equals("13") ))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("3") || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("3") ||layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("3") ||layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("4") || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("4") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("4") || layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ( (layout.p1Set.getChildren().get(0).getId().equals("5") || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("5") ||layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("5") ||layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("6") ||  layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("6") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("6") || layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("7") ||layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("7") ||layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("7") ||layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("8") || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("8") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("8") || layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("9") |layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("9") |layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("9") |layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("10") ||layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("10") ||layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("10") ||layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("11") || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("11") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("11") || layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set.getChildren().get(0).getId().equals("12") || layout.p1Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(1).getId().equals("12") || layout.p1Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set.getChildren().get(2).getId().equals("12") || layout.p1Set.getChildren().get(2).getId().equals("13")))
			return true;

		else
			return false;
	}

	public boolean checkPlayerOneSet1PhaseOne(){
		if ((layout.p1Set_1.getChildren().get(0).getId().equals("1")  || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("1") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("1") || layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("2")	|| layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("2") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("2")||layout.p1Set_1.getChildren().get(2).getId().equals("13") ))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("3") || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("3") ||layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("3") ||layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("4") || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("4") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("4") || layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ( (layout.p1Set_1.getChildren().get(0).getId().equals("5") || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("5") ||layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("5") ||layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("6") ||  layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("6") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("6") || layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("7") ||layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("7") ||layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("7") ||layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("8") || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("8") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("8") || layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("9") |layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("9") |layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("9") |layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("10") ||layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("10") ||layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("10") ||layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("11") || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("11") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("11") || layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p1Set_1.getChildren().get(0).getId().equals("12") || layout.p1Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(1).getId().equals("12") || layout.p1Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p1Set_1.getChildren().get(2).getId().equals("12") || layout.p1Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else
			return false;
	}

	public boolean checkPlayerTwoSetPhaseOne(){
		if ((layout.p2Set.getChildren().get(0).getId().equals("1")  || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("1") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("1") || layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("2")	|| layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("2") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("2")||layout.p2Set.getChildren().get(2).getId().equals("13") ))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("3") || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("3") ||layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("3") ||layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("4") || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("4") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("4") || layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ( (layout.p2Set.getChildren().get(0).getId().equals("5") || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("5") ||layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("5") ||layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("6") ||  layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("6") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("6") || layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("7") ||layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("7") ||layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("7") ||layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("8") || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("8") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("8") || layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("9") |layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("9") |layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("9") |layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("10") ||layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("10") ||layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("10") ||layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("11") || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("11") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("11") || layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set.getChildren().get(0).getId().equals("12") || layout.p2Set.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(1).getId().equals("12") || layout.p2Set.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set.getChildren().get(2).getId().equals("12") || layout.p2Set.getChildren().get(2).getId().equals("13")))
			return true;

		else
			return false;
	}

	public boolean checkPlayerTwoSet1PhaseOne() {
		if ((layout.p2Set_1.getChildren().get(0).getId().equals("1")  || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("1") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("1") || layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("2")	|| layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("2") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("2")||layout.p2Set_1.getChildren().get(2).getId().equals("13") ))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("3") || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("3") ||layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("3") ||layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("4") || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("4") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("4") || layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ( (layout.p2Set_1.getChildren().get(0).getId().equals("5") || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("5") ||layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("5") ||layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("6") ||  layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("6") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("6") || layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("7") ||layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("7") ||layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("7") ||layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("8") || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("8") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("8") || layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("9") |layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("9") |layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("9") |layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("10") ||layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("10") ||layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("10") ||layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("11") || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("11") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("11") || layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else if ((layout.p2Set_1.getChildren().get(0).getId().equals("12") || layout.p2Set_1.getChildren().get(0).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(1).getId().equals("12") || layout.p2Set_1.getChildren().get(1).getId().equals("13"))
				&&
				(layout.p2Set_1.getChildren().get(2).getId().equals("12") || layout.p2Set_1.getChildren().get(2).getId().equals("13")))
			return true;

		else
			return false;
	}
	/*Phase One Methods End*/

	public class Handling implements EventHandler<ActionEvent>{
		@Override //handling buttons
		public void handle(ActionEvent e) {
			Button button = (Button) e.getSource();
			String pressed = button.getText();
			if (pressed == "Return") {
				stage.setScene(welcomeScene);
				stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
				stage.setResizable(false);
			}
			if (pressed == "Start Game") {
				p1Cards.clear();
				p2Cards.clear();
				layout.getp1hb().getChildren().clear();
				layout.getp2hb().getChildren().clear();
				layout.p1Set.getChildren().clear();
				layout.p2Set.getChildren().clear();
				layout.p1Set_1.getChildren().clear();
				layout.p1Set_1.getChildren().clear();

				//game: distribute cards to players
				cards.shuffleDistrCards(p1Cards,layout.getp1hb());
				cards.shuffleDistrCards(p2Cards,layout.getp2hb());
				stage.setScene(gameScene);
				stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
				P1TURN = true; P2TURN = false;
				stage.setResizable(false);
			}
			if (pressed == "How To Play") {
				stage.setScene(rulesScene);
				stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
				stage.setResizable(false);
			}
			if (pressed == "Exit")
				System.exit(0);

			if (pressed =="Submit") {
				if(P1TURN == true && P2TURN == false) {
					if (layout.p1Set.getChildren().size()==3 && layout.p1Set_1.getChildren().size()==3) {

						if(checkPlayerOneSetPhaseOne()==true && checkPlayerOneSet1PhaseOne()==true) { //the sets are correct
							correctPhase(P1TURN, P2TURN);
						}

						else { 
							incorrectPhase(P1TURN, P2TURN);
						}
					}
					}

				if(P1TURN == false && P2TURN == true) {
					if (layout.p2Set.getChildren().size()==3 && layout.p2Set_1.getChildren().size()==3) {

						if(checkPlayerTwoSetPhaseOne()==true && checkPlayerTwoSet1PhaseOne()==true) {
							correctPhase(P1TURN, P2TURN);
						}
						else { 
							incorrectPhase(P1TURN, P2TURN);
						}
					}
				}
			}

			if(pressed == "Cancel")	{
				if(P1TURN == true && P2TURN == false) {
					while(layout.p1Set.getChildren().isEmpty()==false) 
						layout.getp1hb().getChildren().add(layout.p1Set.getChildren().get(0));
					while(layout.p1Set_1.getChildren().isEmpty()==false) 
						layout.getp1hb().getChildren().add(layout.p1Set_1.getChildren().get(0));
				}
				else if(P1TURN == false && P2TURN == true) {
					while(layout.p2Set.getChildren().isEmpty()==false) 
						layout.getp2hb().getChildren().add(layout.p2Set.getChildren().get(0));
					while(layout.p2Set_1.getChildren().isEmpty()==false)
						layout.getp2hb().getChildren().add(layout.p2Set_1.getChildren().get(0));
				}
				else
					e.consume();
			}
		}
	}//Handling class END

	public void correctPhase(boolean p1, boolean p2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("CONGRATULATIONS!");
		if (p1==true && p2==false)
			alert.setContentText("Player 1 has won the game!");

		else if (p1==false && p2==true)
			alert.setContentText("Player 2 has won the game!");
		alert.showAndWait();
	}

	public void incorrectPhase(boolean p1,boolean p2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		if (p1==true && p2==false)
			alert.setHeaderText("Player 1 Phase Incorrect");
		else if (p1==false && p2==true)
			alert.setHeaderText("Player 2 Phase Incorrect");
		alert.setContentText("Make sure you satisfy the phase's conditions!");
		alert.showAndWait();
	}

	public static void main(String[] args){
		launch(args);
	}
}
