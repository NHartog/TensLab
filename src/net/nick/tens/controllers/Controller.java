package net.nick.tens.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.nick.tens.classes.Board;
import net.nick.tens.classes.Card;
import net.nick.tens.classes.TensBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    //instance variables

    public Text moveCnt;
    private boolean[] selections;
    private Board board;
    private ArrayList<ImageView> displayCards = new ArrayList<>();
    private ArrayList<Image> forPane = new ArrayList<>();
    private double totalGames = 1;
    private double totalWins = 0;
    private int totalLoses = 0;

    private int moves = 0;
    private int gridCnt = 0;
    private int zeroOrOne = 0;
    private String loss = "You Lost";
    private String win = "Congratulations, you actually won!";


    @FXML
    public Text numMoves;
    @FXML
    public Text left;
    @FXML
    public Text gameText;
    @FXML
    public Text gameLostText;
    @FXML
    public Text percentText;
    @FXML
    public Text wonText;
    @FXML
    public GridPane dealtCard;
    //instance variables for fx obhects
    @FXML
    private ImageView one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen;
    @FXML
    private Text statusMsg;

    //initializes the "game". Brings up pop up message with instuctions
    @FXML
    private void initialize() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("How To Play Tens");
        alert.setContentText("Welcome to Tens, where Mr.Rico was too lazy to create an original idea for a homework\n" +
                "To win you must clear all the cards in the deck. There will be 13 cards on the table at \n" +
                "all times. The unshuffled number of cards is at the bottom\n" +
                "To replace cards, you must select two card that add up to 10 (aces are worth one) \n" +
                "or you can choose four of the same acrds if they are Tens, Pipes, Trevors  or Nicholas'\n" +
                "I wish you luck because thats what this game is all about ");

        ButtonType buttonTypeOne = new ButtonType("I understand");
        alert.getButtonTypes().setAll(buttonTypeOne);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            // ... user chose "One"
            System.out.println("kept playing");
        }


        board = new TensBoard();
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

    // sets up the Board in the Gridpane once the game has been initiailzed
    public void setBoardDisplay(){
        for(int r = 0; r < board.size(); r++){
            String cardImageFileName = imageFileName(board.cardAt(r), selections[r]);
            Image imageURL = new Image(cardImageFileName);
            if (imageURL != null) {
                displayCards.get(r).setImage(imageURL);
                displayCards.get(r).setVisible(true);
            } else {
                throw new RuntimeException(
                        "Card image not found: \"" + cardImageFileName + "\"");
            }
        }
    }

    //if this method has been csalled, restarts game, increments variables for games played, and gives loss message
    private void signalLoss() {
        statusMsg.setVisible(true);
        statusMsg.setText(loss);
        totalLoses++;
        gameLostText.setText(String.valueOf(totalLoses));
        System.out.println(totalWins / totalGames);
        String percent = String.format("%d", (int) ((totalWins / totalGames) * 100));
        percentText.setText(percent + "%");
        totalGames++;
    }

    //if this method is called, retsarts game, increments total games variable, and gives win message
    private void signalWin() {
        statusMsg.setVisible(true);
        statusMsg.setText(win);
        statusMsg.setFill(Color.LIGHTGREEN);
        totalWins++;
        wonText.setText(String.valueOf((int)totalWins));
        System.out.println(totalWins/totalGames);
        String percent = String.format("%d", (int)((totalWins / totalGames) * 100));
        percentText.setText(percent + "%");
        totalGames++;
    }

    //Getter method for getting the card images
    private String imageFileName(Card c, boolean isSelected) {
        String str = "net/nick/tens/files/PNG/";
        if (c == null) {
            return "net/nick/tens/files/PNG/red_back.png";
        }
        String crd =c.suit().substring(0,1);
        str += c.rank() + c.suit().substring(0,1);
        System.out.println(str);
        if (isSelected) {
            str += " copy";
        }
        str += ".png";
        System.out.println(str);
        return str;
    }

    //method uses a mouse event (click) to swap a card
    public void replace(MouseEvent mouseEvent) {
        moves++;
        System.out.println("replacing...");
        List<Integer> selection = new ArrayList<>();
        for (int k = 0; k < board.size(); k++) {
            if (selections[k]) {
                selection.add(k);
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
        numMoves.setText(String.valueOf(moves));
        left.setText(String.valueOf(board.deckSize()));

        for(int i = forPane.size()-selection.size(); i< forPane.size(); i++) {
            ImageView img = new ImageView(forPane.get(i));
            img.setFitHeight(97.0);
            img.setFitWidth(73.0);
            System.out.println("adding..");
            dealtCard.add(img, zeroOrOne, gridCnt);
//            index++;
            if (zeroOrOne == 0) {
                zeroOrOne = 1;
            } else {
                zeroOrOne = 0;
                gridCnt++;
            }
        }

    }

    //retsarts game and variables based on mouse event (click)
    public void restart(MouseEvent mouseEvent) {
        moves = 0;
        gameText.setText(String.valueOf((int)totalGames));
        dealtCard.getChildren().clear();
        gridCnt = 0;
        zeroOrOne = 0;
        numMoves.setText("0");
        left.setText(String.valueOf(board.deckSize()));
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

    //called if a card object is clicked (mouse event)
    public void selectCard(MouseEvent e) {
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

    // if exit button clicked use this method
    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
