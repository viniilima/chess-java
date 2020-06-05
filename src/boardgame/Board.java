package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows <= 0 || columns <= 0)
			throw new BoardException("Error creating board: must have at least 1 row and 1 column.");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	// retorna a peca na posicao desejada, se nao tiver uma peca, retorna null
	public Piece getPiece(int row, int column) {
		if(!positionExists(row, column))
			throw new BoardException("This position doesn't exist.");
		return pieces[row][column];
	}
	
	public Piece getPiece(Position position) {
		if(!positionExists(position.getRow(), position.getColumn()))
			throw new BoardException("This position doesn't exist.");
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position))
			throw new BoardException("Position already taken: " + position);
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	// remove a peca do tabuleiro e retorna a mesma
	public Piece removePiece(Position position) {
		if(!positionExists(position))
			throw new BoardException("This position doesn't exist.");
		if(getPiece(position) == null)
			return null;
		Piece aux = getPiece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	// checa se tem uma peca na posicao
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position))
			throw new BoardException("This position doesn't exist.");
		return getPiece(position) != null;
	}
}
