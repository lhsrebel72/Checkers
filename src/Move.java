import java.awt.Color;

public class Move {
	private Squares square;
	private ID direction;
	private boolean isJump;
	private boolean isValid;
	
	Move(){
		square = null;
		direction = null;
		isJump = false;
		isValid = false;
	}
	
	
	public void clear() {
		if(square!=null) {
		square.setDisplay(false);
		square = null;
		}
		direction = null;
		isJump = false;
		isValid = false;
	}
	public ID getDirection() {
		return direction;
	}
	public void setDirection(ID direction) {
		this.direction = direction;
	}
	public boolean isJump() {
		return isJump;
	}
	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Squares getSquare() {
		return square;
	}
	public void setSquare(Squares square) {
		this.square = square;
	}
}
