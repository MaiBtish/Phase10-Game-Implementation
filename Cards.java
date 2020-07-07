//Done by by Mai Btish and Nada Abughazalah

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Cards {

	private ArrayList<ImageView> deck = new ArrayList<ImageView>();
	private ArrayList<ImageView> discardDeck = new ArrayList<ImageView>();

	ImageView[] cardsID = new ImageView[108];

	public Cards() { //adds all cards to deck
		for (int i=1; i<=108; i++) {
			//first is blue
			if (i>=1 && i<=12) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*0)) + "b"+ ".png"));
				cardsID[i-1].setId( (i-(12*0))+"" );
			}
			else if (i>=13 && i<=24) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*1)) + "b"+ ".png"));
				cardsID[i-1].setId( (i-(12*1)) + "" );
			}
			else if (i>=25 && i<=36) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*2)) + "r"+ ".png"));
				cardsID[i-1].setId( (i-(12*2)) + "" );
			}
			else if (i>=37 && i<=48) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*3)) + "r"+ ".png"));
				cardsID[i-1].setId( (i-(12*3)) + "" );
			}
			else if (i>=49 && i<=60) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*4)) + "g"+ ".png"));
				cardsID[i-1].setId( (i-(12*4)) + "" );
			}
			else if (i>=61 && i<=72) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*5)) + "g.png"));
				cardsID[i-1].setId( (i-(12*5)) + "" );
			}
			else if (i>=73 && i<=84) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*6)) + "y.png"));
				cardsID[i-1].setId( (i-(12*6)) + "" );
			}
			else if (i>=85 && i<=96) {
				cardsID[i-1] = new ImageView(new Image("Cards/" + (i-(12*7)) + "y.png"));
				cardsID[i-1].setId( (i-(12*7)) + "" );
			}
			else if (i>=97 && i<=104) {
				int k=13;
				cardsID[i-1] = new ImageView(new Image("Cards/wild.png"));
				cardsID[i-1].setId( k+"" );
			}
			else /*if (i>=105 && i<=108)*/ {
				cardsID[i-1] = new ImageView(new Image("Cards/skip.png"));
				cardsID[i-1].setId( "14" );
			}
			cardsID[i-1].setFitWidth(85);
			cardsID[i-1].setPreserveRatio(true);
			cardsID[i-1].setSmooth(true);

			deck.add(i-1,cardsID[i-1]); //adding cards to deck
		}
		Collections.shuffle(this.getDeck()); //shuffle deck
	}

	public void shuffleDistrCards(ArrayList<ImageView>playerCards, HBox playerPane) {
		for (int i=0; i<10; i++) {
			playerCards.add(i, getDeck().get(i) );
			getDeck().remove(i);
		}
		//adding cards to pane
		for (int i=0; i<10; i++) {
			playerPane.getChildren().add(playerCards.get(i));
		}
	}

	public ArrayList<ImageView> getDiscardDeck() {
		return discardDeck;
	}

	public ArrayList<ImageView> getDeck() {
		return deck;
	}

}
