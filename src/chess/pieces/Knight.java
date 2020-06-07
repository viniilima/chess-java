package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().getPiece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] m = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		
		// left-up
		p.setValues(position.getRow() - 1, position.getColumn() - 2); // contador - acessa a posicao acima da peca. "position" vem da classe Piece
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// left-down
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// right-up
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// right-down
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// up-right
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// up-left
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// down-right
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// down-left
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		return m;
	}
}
