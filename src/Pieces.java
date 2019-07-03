import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Pieces {
	private int x,y,diameter,radius;
	private Color color;
	private ID id;
	private boolean jumpAvailable; 
	private Squares currentSquare;
	private boolean isKing, isPlayable, isSelected, display;
	private Move potentialMove[] = new Move [4];
	
	Pieces(Squares square){
			jumpAvailable = false;
			this.x = ((square.getX()+square.getWidth())+square.getX())/2;
			this.y = ((square.getY()+square.getHeight())+square.getY())/2;
			this.diameter = (int) (square.getHeight()/1.2);
			this.radius = diameter/2;
			x -= radius;
			y -= radius;
			if(square.getRow() < 4) {
				color = Color.white;
				setId(ID.White);
			}
			else if (square.getRow()>4){
				color = Color.red;
				setId(ID.Red);
			}
			square.setCurrentPiece(this);
			this.setCurrentSquare(square);
			for(int i = 0;i<=3;i++) {
				potentialMove[i] = new Move();
			}
		}
	
	public void findValidMoves() {
		int i = 0;
		
		if(id == ID.White) {
			if(currentSquare.getRow() != 7) {
				if(currentSquare.getColumn() != 7) {
					if(currentSquare.getDownR().isOccupied() != true || jumpCheck(ID.DownR,ID.White,i)) {
							potentialMove[i].setDirection(ID.DownR);
							potentialMove[i].setValid(true);
							potentialMove[i].setSquare(currentSquare.getDirection(potentialMove[i].getDirection()));
							i++;
						
					}
				}
				if(currentSquare.getColumn() != 0) {
					if(currentSquare.getDownL().isOccupied() != true || jumpCheck(ID.DownL,ID.White,i)) {
						potentialMove[i].setDirection(ID.DownL);
						potentialMove[i].setValid(true);
						potentialMove[i].setSquare(currentSquare.getDirection(potentialMove[i].getDirection()));
						i++;
					}
				}
			}
		}
		
		if(id == ID.Red) {
			if(currentSquare.getRow() != 0) {
				if(currentSquare.getColumn() != 7) {
					if(currentSquare.getUpR().isOccupied() != true || jumpCheck(ID.UpR,ID.Red,i)) {
						potentialMove[i].setDirection(ID.UpR);
						potentialMove[i].setValid(true);
						potentialMove[i].setSquare(currentSquare.getDirection(potentialMove[i].getDirection()));
						i++;
					}
				}
				if(currentSquare.getColumn() != 0) {
					if(currentSquare.getUpL().isOccupied() != true || jumpCheck(ID.UpL,ID.Red,i)) {
						potentialMove[i].setDirection(ID.UpL);
						potentialMove[i].setValid(true);
						potentialMove[i].setSquare(currentSquare.getDirection(potentialMove[i].getDirection()));
						i++;
					}
				}
			}
		}
		
		if(potentialMove[0].getSquare()!=null) {
			if(jumpAvailable) {
				Move jumpMove [] = new Move [4]; 
				for(int j = 0; j<=3; j++) {
					jumpMove [j] = new Move();
					if(!potentialMove[j].isJump()) {
						potentialMove[j].clear();
					}
					else {
						jumpMove[j] = potentialMove[j]; 
					}
				}
				potentialMove = jumpMove;
			}
			display = true;
			isPlayable = true;
		}
		else {
			isPlayable = false;
		}
	}
	
	public void move(Squares square) {
		currentSquare.setOccupied(false);
		currentSquare.setCurrentPiece(null);
		if(square.isOccupied()) {
			square.setCurrentPiece(null);
			square.setOccupied(false);
			for(int i = 0; i <= 3; i++) {
				if(square == potentialMove[i].getSquare()) {
					switch(potentialMove[i].getDirection()) {
					case DownR:
						square = square.getDownR();
						break;
					case DownL:
						square = square.getDownL();
						break;
					case UpR:
						square = square.getUpR();
						break;
					case UpL:
						square = square.getUpL();
						break;
				}
			}
		}
		
		}
		this.x = ((square.getX()+square.getWidth())+square.getX())/2;
		this.y = ((square.getY()+square.getHeight())+square.getY())/2;
		x -= radius;
		y -= radius;
		findValidMoves();
		square.setCurrentPiece(this);
		this.setCurrentSquare(square);
		jumpAvailable = false;
		
	}
	
	private boolean jumpCheck(ID direction, ID id,int i) {
		Squares tempSquare = currentSquare.getDirection(direction);
		if(id != tempSquare.getCurrentPiece().getId()) {
			if(id == ID.White) {
				switch(direction) {
				case DownR:
					if(tempSquare.getRow() != 7) {
						if(tempSquare.getColumn() != 7) {
							if(!tempSquare.getDirection(direction).isOccupied()) {
								potentialMove[i].setJump(true);
								jumpAvailable = true;
								return true;
							}
							else return false;
						}
						else return false;
					}
					else return false;
				case DownL:
					if(tempSquare.getRow() != 7) {
						if(tempSquare.getColumn() != 0) {
							if(!tempSquare.getDirection(direction).isOccupied()) {
								potentialMove[i].setJump(true);
								jumpAvailable = true;
								return true;
							}
							else return false;
						}
						else return false;
					}
					else return false;
				default: 
					return false;
				}	
			}
				
			else if(id == ID.Red) {
				switch(direction) {
				case UpR:
					if(tempSquare.getRow() != 0) {
						if(tempSquare.getColumn() != 7) {
							if(!tempSquare.getDirection(direction).isOccupied()) {
								potentialMove[i].setJump(true);
								jumpAvailable = true;
								return true;
							}
							else return false;
						}
						else return false;
					}
					else return false;
				case UpL:
					if(tempSquare.getRow() != 0) {
						if(tempSquare.getColumn() != 0) {
							if(!tempSquare.getDirection(direction).isOccupied()) {
								potentialMove[i].setJump(true);
								jumpAvailable = true;
								return true;
							}
							else return false;
						}
						else return false;
					}
					else return false;
				default: 
					return false;
			   }
			}
			else return false;
		}
		else return false;
	}
	
	public void render(Graphics g) {
		if (display) g.setColor(Color.green);
		else g.setColor(color);
		g.fillOval(x, y, diameter, diameter);
		g.setColor(Color.black);
		g.drawOval(x, y, diameter, diameter);
		
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public boolean isPlayable() {
		return isPlayable;
	}

	public void setPlayable(boolean isPlayable) {
		this.isPlayable = isPlayable;
	}

	public Squares getCurrentSquare() {
		return currentSquare;
	}

	public void setCurrentSquare(Squares currentSquare) {
		this.currentSquare = currentSquare;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public boolean jumpAvailable() {
		if(jumpAvailable == true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public Move[] getPotentialMoves() {
		return potentialMove;
	}

	public void clearMoves() {
		isPlayable = false;
		display = false;
		for(int i = 0;i<=3;i++) {
			if(potentialMove[i]!=null) {
				potentialMove[i].clear();
			}
		}
		
	}	
	
}
