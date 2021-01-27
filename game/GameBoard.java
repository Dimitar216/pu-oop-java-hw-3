package game;

import figures.*;
import ui.Modal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Gameboard class where everything is initialized and rendered
 */
public class GameBoard extends JFrame implements MouseListener {
    private int turtleOneCol;
    private int turtleTwoCol;
    private int turtleOneCounter = 0;
    private int turtleTwoCounter = 0;
    private static final int TILE_SIZE = 100;
    private static final int rowOfCollection = 5;
    private static final int colOfCollection = 5;
    private final Figures[][] figureCollection;
    private Figures selectedFigure;


    GameTile redTileOne = new GameTile(0,0,TILE_SIZE,TILE_SIZE,Color.RED);
    GameTile brownTileOne = new GameTile(0,1,TILE_SIZE,TILE_SIZE,BROWN);
    GameTile whiteTileOne = new GameTile(0,2,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile brownTileTwo = new GameTile(0,3,TILE_SIZE,TILE_SIZE,BROWN);
    GameTile redTileTwo = new GameTile(0,4,TILE_SIZE,TILE_SIZE,Color.RED);

    GameTile grayTileOne = new GameTile(1,0,TILE_SIZE,TILE_SIZE,Color.GRAY);
    GameTile grayTileTwo = new GameTile(1,1,TILE_SIZE,TILE_SIZE,Color.GRAY);
    GameTile whiteTileTwo = new GameTile(1,2,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile grayTileThree = new GameTile(1,3,TILE_SIZE,TILE_SIZE,Color.GRAY);
    GameTile grayTileFour = new GameTile(1,4,TILE_SIZE,TILE_SIZE,Color.GRAY);

    GameTile whiteTileThree = new GameTile(2,0,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile whiteTileFour = new GameTile(2,1,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTileDot whiteCenter = new GameTileDot(3,2,2.25,2.245,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile whiteTileSix = new GameTile(2,3,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile whiteTileSeven = new GameTile(2,4,TILE_SIZE,TILE_SIZE,Color.WHITE);

    GameTile grayTileFive = new GameTile(3,0,TILE_SIZE,TILE_SIZE,Color.GRAY);
    GameTile grayTileSix = new GameTile(3,1,TILE_SIZE,TILE_SIZE,Color.GRAY);
    GameTile whiteTileEight = new GameTile(3,2,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile grayTileSeven = new GameTile(3,3,TILE_SIZE,TILE_SIZE,Color.GRAY);
    GameTile grayTileEight = new GameTile(3,4,TILE_SIZE,TILE_SIZE,Color.GRAY);

    GameTile brownTileThree = new GameTile(4,0,TILE_SIZE,TILE_SIZE,BROWN);
    GameTile redTileThree = new GameTile(4,1,TILE_SIZE,TILE_SIZE,Color.RED);
    GameTile whiteTileNine = new GameTile(4,2,TILE_SIZE,TILE_SIZE,Color.WHITE);
    GameTile redTileFour = new GameTile(4,3,TILE_SIZE,TILE_SIZE,Color.RED);
    GameTile brownTileFour = new GameTile(4,4,TILE_SIZE,TILE_SIZE,BROWN);

    Leader yellowLeader = new Leader(0,4,TILE_SIZE,TILE_SIZE,Color.YELLOW);
    Leader greenLeader = new Leader(4,0,TILE_SIZE,TILE_SIZE,Color.GREEN);

    Guard yellowGuardOne = new Guard(0,0,TILE_SIZE,TILE_SIZE,Color.YELLOW,Color.GREEN);
    Guard yellowGuardTwo = new Guard(0,1,TILE_SIZE,TILE_SIZE,Color.YELLOW,Color.GREEN);
    Guard yellowGuardThree = new Guard(0,2,TILE_SIZE,TILE_SIZE,Color.YELLOW,Color.GREEN);
    Guard yellowGuardFour = new Guard(0,3,TILE_SIZE,TILE_SIZE,Color.YELLOW,Color.GREEN);

    Guard greenGuardOne = new Guard(4,1,TILE_SIZE,TILE_SIZE,Color.GREEN,Color.YELLOW);
    Guard greenGuardTwo = new Guard(4,2,TILE_SIZE,TILE_SIZE,Color.GREEN,Color.YELLOW);
    Guard greenGuardThree = new Guard(4,3,TILE_SIZE,TILE_SIZE,Color.GREEN,Color.YELLOW);
    Guard greenGuardFour = new Guard(4,4,TILE_SIZE,TILE_SIZE,Color.GREEN,Color.YELLOW);

    private static final Color BROWN = new Color(51,0,0);

    /**
     * constructor
      */
    public GameBoard(){
        this.figureCollection = new Figures[rowOfCollection][colOfCollection];

        //Yellow
        this.figureCollection[0][0] = yellowGuardOne;
        this.figureCollection[0][1] = yellowGuardTwo;
        this.figureCollection[0][2] = yellowGuardThree;
        this.figureCollection[0][3] = yellowGuardFour;
        this.figureCollection[0][4] = yellowLeader;

        //Green
        this.figureCollection[4][0] = greenLeader;
        this.figureCollection[4][1] = greenGuardOne;
        this.figureCollection[4][2] = greenGuardTwo;
        this.figureCollection[4][3] = greenGuardThree;
        this.figureCollection[4][4] = greenGuardFour;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
        this.addMouseListener(this);
        randomNumberGenerator();
    }

    /**
     * Method where movement of the figures is realized through mouse input.
     * @param e mouse event listener.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardCoordinates(e.getY());
        int col = this.getBoardCoordinates(e.getX());

        if(row<=4&&col<=4) {
            if (this.selectedFigure != null) {

                Figures figure = this.selectedFigure;

                if (figure.idOfFigure == 1) {
                    Guard guard = (Guard) this.selectedFigure;
                    isGuardMoveValid(row,col,guard);
                    return;
                } else if (figure.idOfFigure == 0) {
                    Leader leader = (Leader) this.selectedFigure;

                    isLeaderMoveValid(row,col,leader);
                    return;
                }
            }

            if (this.hasBoardFigure(row, col)) {
                this.selectedFigure = this.getBoardFigure(row, col);
            }
        } else {
            Modal.render(this,"Warning!","Out of bounds");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Method which calls the methods where all the figures/lines/tiles are rendered.
     * @param g graphics component
     */
    @Override
    public void paint(Graphics g){

        tilesRender(g);

        figuresRender(g);

        turtleSetUp(g);

        turtleCollisionChecker(g);

        g.setColor(Color.BLACK);
        linesInitializeAndRender(g);
    }

    /**
     * Method where all the lines are initialized and rendered.
     * @param g graphics component
     */
    private static void linesInitializeAndRender(Graphics g){
        Graphics2D lineDrawer = (Graphics2D) g;
        Line2D line0 = new Line2D.Float(8,100,8,2500);
        Line2D line1 = new Line2D.Float(100,100,100,2500);
        Line2D line2 = new Line2D.Float(200,100,200,2500);
        Line2D line3 = new Line2D.Float(300,100,300,2500);
        Line2D line4 = new Line2D.Float(400,100,400,2500);
        Line2D line5 = new Line2D.Float(500,100,500,2500);
        Line2D line6 = new Line2D.Float(0,100,1300,100);
        Line2D line7 = new Line2D.Float(0,200,1300,200);
        Line2D line8 = new Line2D.Float(0,300,1300,300);
        Line2D line9 = new Line2D.Float(0,400,1300,400);
        Line2D line10 = new Line2D.Float(0,500,1300,500);
        Line2D line11 = new Line2D.Float(0,600,1300,600);
        lineDrawer.draw(line0);
        lineDrawer.draw(line1);
        lineDrawer.draw(line2);
        lineDrawer.draw(line3);
        lineDrawer.draw(line4);
        lineDrawer.draw(line5);
        lineDrawer.draw(line6);
        lineDrawer.draw(line7);
        lineDrawer.draw(line8);
        lineDrawer.draw(line9);
        lineDrawer.draw(line10);
        lineDrawer.draw(line11);
    }

    /**
     * Method where all the tiles are rendered.
     * @param g graphics component
     */
    private void tilesRender(Graphics g){
        brownTileThree.render(g);
        redTileThree.render(g);
        whiteTileNine.render(g);
        redTileFour.render(g);
        brownTileFour.render(g);
        grayTileFive.render(g);
        grayTileSix.render(g);
        whiteTileEight.render(g);
        grayTileSeven.render(g);
        grayTileEight.render(g);
        whiteTileThree.render(g);
        whiteTileFour.render(g);
        whiteCenter.render(g);
        whiteTileSix.render(g);
        whiteTileSeven.render(g);
        grayTileOne.render(g);
        grayTileTwo.render(g);
        whiteTileTwo.render(g);
        grayTileThree.render(g);
        grayTileFour.render(g);
        redTileOne.render(g);
        brownTileOne.render(g);
        whiteTileOne.render(g);
        brownTileTwo.render(g);
        redTileTwo.render(g);
    }

    /**
     * Method where all the figures are rendered.
     * @param g graphics component
     */
    private void figuresRender(Graphics g){
        yellowLeader.render(g);
        greenLeader.render(g);

        yellowGuardOne.render(g);
        yellowGuardTwo.render(g);
        yellowGuardThree.render(g);
        yellowGuardFour.render(g);

        greenGuardOne.render(g);
        greenGuardTwo.render(g);
        greenGuardThree.render(g);
        greenGuardFour.render(g);
    }

    /**
     * Method which gets coordinates based on inputted X/Y and returns row/col
     * @param coordinates X or Y coordinate.
     * @return row or col coordinate.
     */
    private int getBoardCoordinates(int coordinates){
        return  coordinates/TILE_SIZE;
    }

    /**
     * Gets figure from array with inputted coordinates
     * @param row row of figure.
     * @param col col of figure.
     * @return
     */
    private Figures getBoardFigure(int row, int col){
        return this.figureCollection[row][col];
    }

    /**
     * Method which checks if the figure exists.
     * @param row row of the figure.
     * @param col col of the figure.
     * @return true if it exists,false if not.
     */
    private boolean hasBoardFigure(int row,int col){
        return this.getBoardFigure(row,col) !=null;
    }

    /**
     * Method where the movement of the guard is realized.
     * @param row row of the move.
     * @param col col of the move
     * @param guard guard object.
     */
    private void isGuardMoveValid(int row,int col,Figures guard){
        if(guard.isMoveValid(row,col,this.figureCollection)){
            if(this.figureCollection[row][col] == null) {
                int initialRow = guard.getRow();
                int initialCol = guard.getCol();

                guard.move(row, col);
                this.figureCollection[guard.getRow()][guard.getCol()] = this.selectedFigure;
                this.figureCollection[initialRow][initialCol] = null;
                this.selectedFigure = null;
                this.repaint();
            } else{
                Modal.render(this,"Warning!","Invalid move");
            }
        } else {
            Modal.render(this,"Warning!","Invalid move");
        }
    }

    /**
     * Method where the movement of the leader is realized.
     * @param row row of the move.
     * @param col col of the move
     * @param leader leader object.
     */
    private void isLeaderMoveValid(int row,int col,Leader leader){
        if(leader.isMoveValid(row,col,this.figureCollection)){
            if(this.figureCollection[row][col] == null) {
                int initialRow = leader.getRow();
                int initialCol = leader.getCol();

                leader.move(row, col);
                this.figureCollection[leader.getRow()][leader.getCol()] = this.selectedFigure;
                this.figureCollection[initialRow][initialCol] = null;
                this.selectedFigure = null;
                this.repaint();
            } else {
                Modal.render(this,"Warning!","Invalid move");
            }
        } else {
            Modal.render(this,"Warning!","Invalid move");
        }
        if(hasBoardFigure(2,2)) {
            if (isGameWon(leader)) {
                Modal.render(this, "Winner", leader.getColorString(leader.getColor()) + " has won");
                System.exit(1);
            }
        }
    }

    /**
     * Method that sets up and renders 2 turtles on the gameboard
     * @param g graphics component
     */
    private void turtleSetUp(Graphics g){
                Turtle turtle1 = new Turtle(2, turtleOneCol,TILE_SIZE,TILE_SIZE);
                if(turtleOneCounter==0) {
                    turtle1.render(g);
                }
                Turtle turtle2 = new Turtle(2, turtleTwoCol,TILE_SIZE,TILE_SIZE);
                if(turtleTwoCounter==0) {
                    turtle2.render(g);
                }

        }

    /**
     * Method which assigns random values to turtleCol One and Two
      */
    private void randomNumberGenerator(){
        boolean setUpIsGoing=true;
        while(setUpIsGoing){
            turtleOneCol = ThreadLocalRandom.current().nextInt(0,5);
            turtleTwoCol = ThreadLocalRandom.current().nextInt(0,5);
            if(turtleOneCol !=2&& turtleTwoCol !=2&& turtleOneCol != turtleTwoCol){
                setUpIsGoing = false;
            }
        }
    }

    /**
     * boolean method that checks if a leader figure is on a particular coordinate.
     * @param leader leader figure
     * @return true if the leader is there false if not.
     */
    private boolean isGameWon(Leader leader){
        return this.figureCollection[2][2].equals(leader);
    }

    /**
     * Method checks if turtle's coordinate is populated by an object and renders a white tile on top of it.
     * @param g graphics component.
     */
    private void turtleCollisionChecker(Graphics g) {
        if(this.figureCollection[2][turtleOneCol] != null && turtleOneCounter==0){
            turtleOneCounter++;
            this.figureCollection[2][turtleOneCol] = null;
            g.setColor(Color.white);
            g.fillRect(turtleOneCol*TILE_SIZE,2*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        }
        if(this.figureCollection[2][turtleTwoCol] != null && turtleTwoCounter==0){
            turtleTwoCounter++;
            this.figureCollection[2][turtleTwoCol] = null;
            g.setColor(Color.white);
            g.fillRect(turtleTwoCol*TILE_SIZE,2*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        }
        if(turtleOneCounter !=0 && this.figureCollection[2][turtleOneCol] == null){
            g.setColor(Color.white);
            g.fillRect(turtleOneCol*TILE_SIZE,2*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        }
        if(turtleTwoCounter !=0&& this.figureCollection[2][turtleTwoCol] == null){
            g.setColor(Color.white);
            g.fillRect(turtleTwoCol*TILE_SIZE,2*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        }
    }
}