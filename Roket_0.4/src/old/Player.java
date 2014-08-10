package old;

import old.strategy.Position;

public class Player {
	private Player nextActive;
	private Player prevActive;
	private Player leftNeighbour;
	private Player rightNeighbour;
	private int tablePos;
	private int gamePos;
	private boolean ativeInGame;
	private Position position;
	
	private double post; // das was noch nicht im gesamtPot liegt, sondern
							// direkt vor ihm.
	private boolean itsMe;
	

	public Player() {

	}

	public Player getNext() {
		return nextActive;
	}

	public void setNext(Player next) {
		this.nextActive = next;
	}

	public Player getPrev() {
		return prevActive;
	}

	public void setPrev(Player prev) {
		this.prevActive = prev;
	}

	public int getTablePos() {
		return tablePos;
	}

	public void setTablePos(int tablePos) {
		this.tablePos = tablePos;
	}

	public int getGamePos() {
		return gamePos;
	}

	public void setGamePos(int gamePos) {
		this.gamePos = gamePos;
	}

	public boolean isAtiveInGame() {
		return ativeInGame;
	}

	public void setAtiveInGame(boolean ativeInGame) {
		this.ativeInGame = ativeInGame;
	}

	public double getPost() {
		return post;
	}

	public void setPost(double post) {
		this.post = post;
	}

	public boolean isItsMe() {
		return itsMe;
	}

	public void setItsMe(boolean itsMe) {
		this.itsMe = itsMe;
	}

	public Player getRightNeighbour() {
		return rightNeighbour;
	}

	public void setRightNeighbour(Player rightNeighbour) {
		this.rightNeighbour = rightNeighbour;
	}

	public Player getLeftNeighbour() {
		return leftNeighbour;
	}

	public void setLeftNeighbour(Player leftNeighbour) {
		this.leftNeighbour = leftNeighbour;
	}

	public void setPosition(Position pos) {
		position = pos;
	}

	public Position getPosition() {
		return position;
	}
}
