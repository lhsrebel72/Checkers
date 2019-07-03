import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Squares {
	private int row, column, x, y;
	private int width = Game.WIDTH/11, height = Game.HEIGHT/11;
	private Color color;
	private boolean isPlayable, isOccupied, isValidMove, display;
	private Board board;
	private Squares downR, downL, upR, upL;
	private Pieces currentPiece;
	
	Squares(){
		
	}
	
	Squares(int row,int column ,boolean isPlayable,Board board){
		this.board = board;
		this.row = row;
		this.column = column;
		this.isPlayable = isPlayable;
		x = column * Game.WIDTH/11 + Game.WIDTH/7;
		y = row*Game.HEIGHT/11 + Game.HEIGHT/11;
		if(isPlayable == true) {
			color = Color.black;
		}
		else color = Color.red;
	}

	public void render(Graphics g) {
		if(display) g.setColor(Color.blue);
		else g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		g.drawRect(x, y, width, height);
		if(currentPiece!=null) currentPiece.render(g);
	}
	
	public boolean getIsPlayable() {
		return isPlayable();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public Pieces getCurrentPiece() {
		return currentPiece;
	}
	public void setCurrentPiece(Pieces currentPiece) {
		this.currentPiece = currentPiece;
		setOccupied(true);
	}
	public boolean isPlayable() {
		return isPlayable;
	}
	public void setPlayable(boolean isPlayable) {
		this.isPlayable = isPlayable;
	}
	public Squares getDownR() {
		return downR;
	}
	public void setDownR(Squares downR) {
		this.downR = downR;
	}
	public Squares getDownL() {
		return downL;
	}
	public void setDownL(Squares downL) {
		this.downL = downL;
	}
	public Squares getUpR() {
		return upR;
	}
	
	public Squares getDirection(ID id) {
		switch(id) {
		case DownR:
			return downR;
		case DownL:
			return downL;
		case UpR:
			return upR;
		case UpL:
			return upL;
		default:
			return null;
		}
	}
	public void setUpR(Squares upR) {
		this.upR = upR;
	}
	public Squares getUpL() {
		return upL;
	}
	public void setUpL(Squares upL) {
		this.upL = upL;
	}
	public boolean isOccupied() {
		if(currentPiece!=null) return true;
		else return false;
	}
	public void setOccupied(boolean bool) {
		if(bool) {
			isOccupied = true;
		}
		else if(!bool) {
			isOccupied = false;
		}
		if (!isOccupied) {
			System.out.println(column);
			System.out.println(row);
		}
	}
	public boolean isValidMove() {
		return isValidMove;
	}
	public void setValidMove(boolean isValidMove) {
		this.isValidMove = isValidMove;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setDiagonals() {
		if(row != 7) {
			if(getColumn() != 7) {
				setDownR(board.getSquare(row + 1, getColumn() + 1));
			}
			if(getColumn() != 0) {
				setDownL(board.getSquare(row + 1, getColumn() - 1));
			}
		}
		if(row != 0) {
			if(getColumn() != 7) {
				setUpR(board.getSquare(row - 1, getColumn() + 1));
			}
			if(getColumn() != 0) {
				setUpL(board.getSquare(row - 1, getColumn() - 1));
			}
		}
	}

	public void setDisplay(boolean display) {

		this.display = display;
	}
	public boolean isBeingDisplayed() {
		return display;
	}
}

