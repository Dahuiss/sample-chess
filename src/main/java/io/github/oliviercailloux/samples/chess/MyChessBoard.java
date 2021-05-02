package io.github.oliviercailloux.samples.chess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class MyChessBoard implements ChessBoard {
	
	public static final ImmutableList<String> COLUMNS = ImmutableList.of("a", "b", "c", "d", "e", "f", "g", "h");
	
	private Map<String, Piece> map;
	
	MyChessBoard(){
		map.put("e1",Piece.king("W"));
		map.put("e8",Piece.king("B"));
	}
	/**
	 * <p>
	 * This method, with the given declaration, <b>must</b> be present.
	 * </p>
	 * <p>
	 * Initially (for simplicity), a board has just two pieces: a White King on
	 * square e1 and a Black King on square e8.
	 * </p>
	 *
	 */
	public static MyChessBoard newInstance() {
		return new MyChessBoard();
	}

	@Override
	public boolean setBoardByString(List<String> inputBoard) {
		String WKingPosition = inputBoard.get(5);
		String BKingPosition = inputBoard.get(61);
		return Piece.king("W").equals(map.get(WKingPosition)) && Piece.king("B").equals(map.get(BKingPosition));
	}

	@Override
	public boolean setBoardByPieces(List<Optional<Piece>> inputBoard) {
		if (inputBoard.get(5).isEmpty() || inputBoard.get(61).isPresent()) {
			return this.map.get(inputBoard.get(5)).equals(Piece.king("W")) && this.map.get(inputBoard.get(61)).equals(Piece.king("B"));
		}
		return false;
	}

	@Override
	public ImmutableMap<String, String> getStringPiecesByPosition() {
		TODO();
		return null;
	}

	@Override
	public ImmutableMap<String, Piece> getPiecesByPosition() {
		TODO();
		return null;
	}

	@Override
	public Optional<Piece> getPieceByPosition(String position) {
		TODO();
		return null;
	}

	@Override
	public ImmutableSet<Piece> getPieces(String color) {
		TODO();
		return null;
	}

	@Override
	public ImmutableList<Piece> getOrderedPieces(String color) {
		TODO();
		return null;
	}

	@Override
	public void movePiece(String oldPosition, String newPosition) {
		TODO();
		
	}

}
