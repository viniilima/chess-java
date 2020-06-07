package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
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

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check!");
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		if(testCheckMate(opponent(currentPlayer)))
			checkMate = true;
		else
			nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	// move uma peca e retorna uma possivel peca capturada
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece cp = board.removePiece(target);
		board.placePiece(p, target); // placePiece() recebe uma Piece, entao eh feito um upcasting de 'p' naturalmente
		if(cp != null) {
			piecesOnBoard.remove(cp);
			capturedPieces.add(cp);
		}
		return cp;
	}
	
	private void undoMove(Position source, Position target, Piece cp) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		if(cp != null) {
			board.placePiece(cp, target);
			capturedPieces.remove(cp);
			piecesOnBoard.add(cp);
		}
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	// retorna o rei da cor desejada
	private ChessPiece getKing(Color color) {
		List<Piece> pieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // add pecas da cor color a lista
		for(Piece p : pieces) {
			if(p instanceof King)
				return (ChessPiece)p;
		}
		throw new IllegalStateException("There's no " + color + " king on the board");
	}
	
	// checa se o rei esta em xeque
	private boolean testCheck(Color color) {
		Position kingPos = getKing(color).getChessPosition().chessToPosition();
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] m = p.possibleMoves(); // pega todos os movimentos possiveis para as posicoes
			if(m[kingPos.getRow()][kingPos.getColumn()])
				return true;
		}
		return false;
	}
	
	// checa se o rei esta em xeque-mate
	private boolean testCheckMate(Color color) {
		if(!testCheck(color))
			return false;
		List<Piece> pieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : pieces) {
			boolean[][] m = p.possibleMoves(); // pega os movimentos possiveis de 'p' e os coloca numa matriz
			for(int i = 0; i < board.getRows(); i++) {
				for(int j = 0; j < board.getColumns(); j++) {
					if(m[i][j]) {
						Position src = ((ChessPiece)p).getChessPosition().chessToPosition();
						Position target = new Position(i, j);
						Piece cp = makeMove(src, target); // move 'p'
						boolean testCheck = testCheck(color); // checa se o rei ainda esta em xeque depois de mover 'p'
						undoMove(src, target, cp); // volta 'p' para o lugar
						if(!testCheck) return false;
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).chessToPosition());
		piecesOnBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
	
}
