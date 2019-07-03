import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Selector extends KeyAdapter {
	private Pieces currentPiece;
	private Squares currentSquare;
	private Board board;
	private boolean pieceSelected;
	
	Selector(Board board){
		this.board = board;
		currentSquare = board.getSquare(0, 0);
		currentPiece = null;
	}
	
	
	public void move(int key) {
		if(key == KeyEvent.VK_S) {
			if(currentSquare.getRow()!=7) {
				currentSquare = board.getSquare(currentSquare.getRow()+1, currentSquare.getColumn());
				currentPiece = currentSquare.getCurrentPiece();
			}
		}
		if(key == KeyEvent.VK_D) {
			if(currentSquare.getColumn()!=7){
				currentSquare = board.getSquare(currentSquare.getRow(), currentSquare.getColumn()+1);
				currentPiece = currentSquare.getCurrentPiece();
			}
		}
		if(key == KeyEvent.VK_W) {
			if(currentSquare.getRow()!=0) {
				currentSquare = board.getSquare(currentSquare.getRow()-1, currentSquare.getColumn());
				currentPiece = currentSquare.getCurrentPiece();
			}
		}
		if(key == KeyEvent.VK_A) {
			if(currentSquare.getColumn()!=0) {
				currentSquare = board.getSquare(currentSquare.getRow(), currentSquare.getColumn()-1);
				currentPiece = currentSquare.getCurrentPiece();
			}
		}
		if(key == KeyEvent.VK_ENTER) {
			if(currentPiece != null && currentPiece.isPlayable() == true) {
				board.select(currentPiece);
				pieceSelected = true;
			}
			else if(currentSquare.isBeingDisplayed()) {
				board.move(currentSquare);
			}
		}
			
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fillRect(currentSquare.getX(), currentSquare.getY(), currentSquare.getWidth(), currentSquare.getHeight());
	}
	
	public void tick() {
		
	}
}
