package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] m = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0); // contador
		
		// up
		p.setValues(position.getRow() - 1, position.getColumn()); // contador - acessa a posicao acima da peca. "position" vem da classe Piece
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posicao p existir e nao houver uma peca na posicao atual
			m[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1); // "aumenta" o contador
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// down
		p.setValues(position.getRow() + 1, position.getColumn()); // contador - acessa a posicao acima da peca. "position" vem da classe Piece
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posicao p existir e nao houver uma peca na posicao atual
			m[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1); // "aumenta" o contador
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			m[p.getRow()][p.getColumn()] = true;
		
		// left
		p.setValues(position.getRow(), position.getColumn() - 1); // contador - acessa a posicao acima da peca. "position" vem da classe Piece
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posicao p existir e nao houver uma peca na posicao atual
			m[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1); // "aumenta" o contador
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			m[p.getRow()][p.getColumn()] = true;

		// right
		p.setValues(position.getRow(), position.getColumn() + 1); // contador - acessa a posicao acima da peca. "position" vem da classe Piece
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posicao p existir e nao houver uma peca na posicao atual
			m[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1); // "aumenta" o contador
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			m[p.getRow()][p.getColumn()] = true;
		
		return m;
	}
}
