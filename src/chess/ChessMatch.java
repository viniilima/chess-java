package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	// retorna uma matriz com todas as pecas do tabuleiro
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.getPiece(i, j); // faz um downcasting do tipo Piece para ChessPiece
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition source) {
		Position p = source.chessToPosition();
		validateSourcePosition(p);
		return board.getPiece(p).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.chessToPosition();
		Position target = targetPosition.chessToPosition();
		validateSourcePosition(source);
		validadeTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	// move uma peca e retorna uma possivel peca capturada
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece cp = board.removePiece(target);
		board.placePiece(p, target);
		if(cp != null) {
			piecesOnBoard.remove(cp);
			capturedPieces.add(cp);
		}
		return cp;
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position))
			throw new ChessException("There isn't a piece on source position.");
		
		if(currentPlayer != ((ChessPiece)board.getPiece(position)).getColor()) // downcasting do tipo Piece para ChessPiece
			throw new ChessException("This piece isn't yours.");
		
		if(!board.getPiece(position).isThereAnyPossibleMove())
			throw new ChessException("There aren't possible moves for this piece.");
	}
	
	private void validadeTargetPosition(Position source, Position target) {
		if(!board.getPiece(source).possibleMove(target)) // verifica se eh possivel mover a peca de source a target
			throw new ChessException("This move isn't possible.");
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).chessToPosition());
		piecesOnBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
}
