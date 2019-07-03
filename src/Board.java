import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Board {
	private static final int ROWS = 8, COLUMNS = 8;
	private int whitePiecesRemaining , redPiecesRemaining;
	private ID currentPlayer;
	private Pieces selectedPiece;
	private boolean inProgress, gameOver, jumpAvailable;
	LinkedList<Pieces> pieces  = new LinkedList<Pieces>();
	LinkedList<Pieces> playablePieces  = new LinkedList<Pieces>();
	private Move previousValidMoves[] = new Move [4];
	
	private Squares board[][] = new Squares[ROWS][COLUMNS];
	

	
	Board(){
		whitePiecesRemaining = 12;
		redPiecesRemaining = 12;
		gameOver = false;
		generateSquares();
		generatePieces();
		for(int i = 0;i<=3;i++) {
			previousValidMoves[i] = new Move();
		}
	}
	
	private void generateSquares() {
		for(int row = 0;row<ROWS;row++) {
			for(int column = 0;column <COLUMNS;column++) {
				if (row%2 == 0 || row == 0) {
					if (column%2 == 0 || column == 0) {
						board[row][column]= new Squares(row, column, false, this);
					}
					else board[row][column]= new Squares(row, column, true, this);
				}
				else {
					if (column%2 == 0 || column == 0) {
						board[row][column] = new Squares(row, column, true, this);
					}
					else board[row][column] = new Squares(row, column, false, this);
				}
			}
		}
	}
	
	private void generatePieces() {
		for(int row = 0;row<ROWS;row++) {
			for(int column = 0;column <COLUMNS;column++) {
				board[row][column].setDiagonals();
				if(row != 3 && row != 4) {
					if(board[row][column].isPlayable()) {
						pieces.add(new Pieces(board[row][column]));
						board[row][column].setOccupied(true);
					}
				}
			}
		}
	}
	
	private void findPlayablePieces(ID currentPlayer) {
		for(int i = 0; i < pieces.size(); i++) {
			if(pieces.get(i).getId() == currentPlayer) {
				pieces.get(i).findValidMoves();	
				if(pieces.get(i).jumpAvailable()) {
					jumpAvailable = true;
				}
				playablePieces.add(pieces.get(i));
			}
		}
		if(jumpAvailable) {
			for(int i = 0; i < playablePieces.size(); i++) {
				if(!playablePieces.get(i).jumpAvailable()) {
					playablePieces.remove(pieces.get(i));
				}
			}
		}
	}
	
	public void jumpAvailable() {
		jumpAvailable = true;
	}
	
	public Squares getSquare(int row, int column) {
		return board[row][column];
	}
	
	public void startTurn(ID currentPlayer) {
		setInProgress(true);
		this.currentPlayer=currentPlayer;
		if(currentPlayer == ID.White) {
			findPlayablePieces(ID.White);
		}
		else if(currentPlayer == ID.Red) {
			findPlayablePieces(ID.Red);
		}
	}
	
	public void endTurn() {
		//playablePieces.clear();
	}
	
	public void display(Move [] validMoves) {
		for(int i = 0;i<=3;i++) {
			if(validMoves[i].getSquare()!=null) {
				validMoves[i].getSquare().setDisplay(true);
			}
		}
		previousValidMoves = validMoves;
	}
	
	public void render(Graphics g){
		for(int row = 0;row<ROWS;row++) {
			for(int column = 0;column <COLUMNS; column++) {
				board[row][column].render(g);
			}
		}
	}

	public void select(Pieces currentPiece) {
		selectedPiece = currentPiece;
		currentPiece.setSelected(true);
		currentPiece.setDisplay(true);
		display(currentPiece.getPotentialMoves());
		for(int i = 0; i < playablePieces.size(); i++) {
			if(playablePieces.get(i) != currentPiece) {
				playablePieces.get(i).setDisplay(false);		
			}
		}
		
	}

	public void move(Squares currentSquare) {
		selectedPiece.move(currentSquare);
		for(int i = 0;i<=3;i++) {
			if(previousValidMoves[i].getSquare()!=null) {
				previousValidMoves[i].clear();
			}
		}
		
		for(int i = 0; i < playablePieces.size(); i++) {
			playablePieces.get(i).clearMoves();
		}
		playablePieces.clear();
		inProgress = false;
		
	}

	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	
}
