package figures;

import java.awt.*;

/**
 * This class creates an instance of a figure on the board and renders it.
 */
public class Turtle extends Figures {

    /**
     * Constructor for Guard
     * @param row row position
     * @param col col position
     * @param widthOfTile width of Guard
     * @param heightOfTile height of Guard
     */
    public Turtle(int row, int col,int widthOfTile,int heightOfTile){
        this.row          = row;
        this.col          = col;
        this.widthOfTile  = widthOfTile;
        this.heightOfTile = heightOfTile;
        this.idOfFigure = 1;
    }

    /**
     * renders figure on the board based on row/col and width/height.
     * @param g graphics component.
     */
    public void render(Graphics g){
        int tileX =  this.col * this.widthOfTile;
        int tileY =  this.row * this.heightOfTile;

        g.setColor(Color.RED);
        g.fillOval(tileX+25,tileY+25,this.widthOfTile/2, this.heightOfTile/2);

        g.setColor(Color.WHITE);
        g.fillOval(tileX+30,tileY+31,this.widthOfTile-60, this.heightOfTile-60);
    }

    /**
     * Required class in order to inherit Figures
     * @param moveRow row it moves to.
     * @param moveCol col it moves to.
     * @param figures array of figures.
     * @return always flase since the turtle isn't supposed to move.
     */
    @Override
    public boolean isMoveValid(int moveRow, int moveCol, Figures[][] figures) {
        return false;
    }
}