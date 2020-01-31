package net.nick.tens.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import net.nick.tens.classes.Board;
import net.nick.tens.classes.Card;
import net.nick.tens.classes.ElevensBoard;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller {



    private boolean[] selections;
    private Board board;
    private ArrayList<ImageView> displayCards = new ArrayList<>();
    private ArrayList<Image> forPane = new ArrayList<>();
    private int totalGames = 0;
    private int totalWins = 0;
    private int moves = 0;
    private String loss = "You Lost, so now they don't get the hotel :(";
    private String win = "Now they get to watch their Disney movies in the hotels!";

    @FXML private ImageView one;
    @FXML private ImageView two;
    @FXML private ImageView three;
    @FXML private ImageView four;
    @FXML private ImageView five;
    @FXML private ImageView six;
    @FXML private ImageView seven;
    @FXML private ImageView eight;
    @FXML private ImageView nine;
    @FXML private ImageView ten;
    @FXML private ImageView eleven;
    @FXML private ImageView twelve;
    @FXML private ImageView thirteen;

    @FXML private Text statusMsg;

    @FXML private ScrollPane plays;

    @FXML public Text numMoves;

    @FXML public Text left;

    @FXML
    private void initialize() {
        Board gameBoard = new ElevensBoard();
        board = gameBoard;
        System.out.println("here?");
        selections = new boolean[board.size()];

        displayCards.add(one);
        displayCards.add(two);
        displayCards.add(three);
        displayCards.add(four);
        displayCards.add(five);
        displayCards.add(six);
        displayCards.add(seven);
        displayCards.add(eight);
        displayCards.add(nine);
        displayCards.add(ten);
        displayCards.add(eleven);
        displayCards.add(twelve);
        displayCards.add(thirteen);

        setBoardDisplay();
    }

    public void setBoardDisplay(){
        for(int r = 0; r < board.size(); r++){
            String cardImageFileName = imageFileName(board.cardAt(r), selections[r]);
            System.out.println(cardImageFileName);
            Image imageURL = new Image(cardImageFileName);
            if (imageURL != null ) {
                displayCards.get(r).setImage(imageURL);
                displayCards.get(r).setVisible(true);
            } else {
                throw new RuntimeException(
                        "Card image not found: \"" + cardImageFileName + "\"");
            }
        }
    }
//
    private void signalLoss() {
        statusMsg.setVisible(true);
        statusMsg.setText(loss);
        totalGames++;
    }

    private void signalWin() {
        statusMsg.setVisible(true);
        statusMsg.setText(win);
        totalWins++;
        totalGames++;
    }


    private String imageFileName(Card c, boolean isSelected) {
        String str = "net/nick/tens/files/cards/";
        if (c == null) {
            return "cards/back1.GIF";
        }
        str += c.rank() + c.suit();
        if (isSelected) {
            str += "S";
        }
        str += ".GIF";
        return str;
    }

    public void replace(MouseEvent mouseEvent) {
        moves++;
        System.out.println("replacing...");
        List<Integer> selection = new ArrayList<Integer>();
        for (int k = 0; k < board.size(); k++) {
            if (selections[k]) {
                selection.add(new Integer(k));
            }
        }
        // Make sure that the selected cards represent a legal replacement.
        if (!board.isLegal(selection)) {
            return;
        }
        for (int k = 0; k < board.size(); k++) {
            selections[k] = false;
        }
        // Do the replace.
        board.replaceSelectedCards(selection);
        if (board.isEmpty()) {
            signalWin();
        } else if (!board.anotherPlayIsPossible()) {
            signalLoss();
        }
        setBoardDisplay();
        for(int i = 0; i< forPane.size(); i++){
            plays.setContent(new ImageView(forPane.get(i)));

        }
        numMoves.setText(String.valueOf(moves));
        left.setText(String.valueOf(board.deckSize()));
    }

    public void restart(MouseEvent mouseEvent) {
        statusMsg.setText("");
        board.newGame();
        statusMsg.setVisible(false);
        if (!board.anotherPlayIsPossible()) {
            signalLoss();
        }
        for (int i = 0; i < selections.length; i++) {
            selections[i] = false;
        }
        setBoardDisplay();
    }

    public void selectCard(MouseEvent e) {
        Image img = new Image("net/nick/tens/files/cards/2clubs.GIF");
        for (int k = 0; k < board.size(); k++) {
            if (e.getSource().equals(displayCards.get(k))
                    && board.cardAt(k) != null) {
                if(!selections[k]){
                    forPane.add(displayCards.get(k).getImage());
                }
                selections[k] = !selections[k];
                System.out.println(displayCards.get(k).toString() + "this is the one");
                setBoardDisplay();
                return;
            }
        }
    }
}
