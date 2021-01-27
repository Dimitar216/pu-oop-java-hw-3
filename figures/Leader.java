package figures;

import java.awt.*;

/**
 * This class creates an instance of a figure on the board and renders it.
 */
public class Leader extends Figures {

    private Color color;

    /**
     * Constructor for Leader
     * @param row row position
     * @param col col position
     * @param widthOfTile width of Leader
     * @param heightOfTile height of Leader
     * @param color color of figure
     */
    public Leader(int row, int col,int widthOfTile,int heightOfTile,Color color){
        this.row          = row;
        this.col          = col;
        this.widthOfTile  = widthOfTile;
        this.heightOfTile = heightOfTile;
        this.color        = color;
        this.idOfFigure = 0;
    }

    /**
     * renders figure on the board based on row/col and width/height.
     * @param g graphics component.
     */
    public void render(Graphics g){
        int tileX = (int) (this.col * this.widthOfTile);
        int tileY = (int) (this.row * this.heightOfTile);

        g.setColor(this.color);
        g.fillRect(tileX+22,tileY+20,this.widthOfTile-40, this.heightOfTile-40);

    }

    /**
     * Method which checks if the move of the figure is valid.
     * @param moveRow row it moves to.
     * @param moveCol col it moves to.
     * @param arr array of figures.
     * @return true if the move is valid and false if not.
     */
    @Override
    public boolean isMoveValid(int moveRow, int moveCol,Figures[][] arr) {
        int rowCoefficient = 0;
        int colCoefficient = 0;

        boolean isMovementToAbove = this.row-moveRow>0 && this.col-moveCol==0;
        boolean isMovementToBelow = this.row-moveRow<0 && this.col-moveCol==0;
        boolean isMovementToTheLeft = this.row-moveRow == 0 && this.col - moveCol<0;
        boolean isMovementToTheRight = this.row-moveRow == 0 && this.col - moveCol>0;

        rowCoefficient = getRowCoefficientForAbove(arr, rowCoefficient, isMovementToAbove);
        rowCoefficient = getRowCoefficientForBelow(arr, rowCoefficient, isMovementToBelow);
        colCoefficient = getColCoefficientForLeft(arr, colCoefficient, isMovementToTheLeft);
        colCoefficient = getColCoefficientForRight(arr, colCoefficient, isMovementToTheRight);

        return rowCoefficient == 0 && colCoefficient == moveCol || rowCoefficient == moveRow && colCoefficient == 0;
    }

    /**
     * Gets the lowest position available the leader can move to.
     * @param arr figure array.
     * @param rowCoefficient row that the leader will be able to be moved to.
     * @param isMovementToBelow checks if movement is going to be down.
     * @return rowCoefficient value that the leader will be able to be moved to.
     */
    private int getRowCoefficientForBelow(Figures[][] arr, int rowCoefficient, boolean isMovementToBelow) {
        if(isMovementToBelow){
            for(int i = row; i <=4; i++){
                if(i > row && arr[i][col] != null){
                    rowCoefficient = i - 1;
                    break;
                } else {
                    rowCoefficient = i;
                }
            }
        }
        return rowCoefficient;
    }

    /**
     * Gets the most right position the leader can move to.
     * @param arr figure array.
     * @param colCoefficient col that the leader will be able to be moved to.
     * @param isMovementToTheRight checks if movement is going to be to the right.
     * @return colCoefficient value that the leader will be able to be moved to.
     */
    private int getColCoefficientForRight(Figures[][] arr, int colCoefficient, boolean isMovementToTheRight) {
        if(isMovementToTheRight){
            for(int i = col;i >=0;i--){
                if(i<col && arr[row][i] !=null){
                    colCoefficient = i+1;
                    break;
                } else {
                    colCoefficient = i;
                }
            }
        }
        return colCoefficient;
    }

    /**
     * Gets the most left position the leader can move to.
     * @param arr figure array.
     * @param colCoefficient col that the leader will be able to be moved to.
     * @param isMovementToTheLeft checks if movement is going to be to the left.
     * @return colCoefficient value that the leader will be able to be moved to.
     */
    private int getColCoefficientForLeft(Figures[][] arr, int colCoefficient, boolean isMovementToTheLeft) {
        if(isMovementToTheLeft) {
            for (int i = col; i <= 4; i++) {
                if (i > col && arr[row][i] != null) {
                    colCoefficient = i-1;
                    break;
                } else {
                    colCoefficient = i;
                }
            }
        }
        return colCoefficient;
    }

    /**
     * Gets the highest position available the leader can move to.
     * @param arr figure array.
     * @param rowCoefficient row that the leader will be able to be moved to.
     * @param isMovementToAbove checks if movement is going to be above.
     * @return rowCoefficient value that the leader will be able to be moved to.
     */
    private int getRowCoefficientForAbove(Figures[][] arr, int rowCoefficient, boolean isMovementToAbove) {
        if(isMovementToAbove) {
            for (int i = row; i >= 0; i--) {
                if (i<row&& arr[i][col] != null) {
                    rowCoefficient = i+1;
                    break;
                } else{
                    rowCoefficient = i;
                }
            }
        }
        return rowCoefficient;
    }

    /**
     * color getter
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method which inputs string based on color.
     * @param color color.
     * @return name of the color.
     */
    public String getColorString(Color color){
        if(color.equals(Color.YELLOW)){
            return "Yellow";
        } else {
            return "Green";
        }
    }
}