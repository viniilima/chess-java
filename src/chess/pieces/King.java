package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch match;

	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().getPiece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position pos) {
		ChessPiece p = (ChessPiece)getBoard().getPiece(pos);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] m = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		
		// up
		p.setValues(position.getRow() - 1, position.getColumn()); // contador - acessa a posicao acima da peca. "position" vem da classe Piece
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// down
		p.setValues(position.getRow() + 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// #specialmove castling
		if(getMoveCount() == 0 && !match.getCheck()) {
			// kingside
			Position t1 = new Position(position.getRow(), position.getColumn() + 3); // torre 1
			if(testRookCastling(t1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().getPiece(p1) == null && getBoard().getPiece(p2) == null)
					m[position.getRow()][position.getColumn() + 2] = true;
			}
			// queenside
			Position t2 = new Position(position.getRow(), position.getColumn() - 4); // torre 2
			if(testRookCastling(t2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().getPiece(p1) == null && getBoard().getPiece(p2) == null && getBoard().getPiece(p3) == null)
					m[position.getRow()][position.getColumn() - 2] = true;
			}
		}
		return m;
	}
}
