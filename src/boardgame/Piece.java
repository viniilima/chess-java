package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		// position = null
	}

	protected Board getBoard() {
		return board;
	}
	
	// verifica todos os movimentos possiveis de uma peca
	public abstract boolean[][] possibleMoves();
	
	// retorna se eh possivel mexer a peca para a posicao p
	public boolean possibleMove(Position p) {
		return possibleMoves()[p.getRow()][p.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] m = possibleMoves();
		for(int i = 0; i < m.length; i++) {
			for(int j = 0; j < m.length; j++) {
				if(m[i][j])
					return true;
			}
		}
		return false;
	}
}
