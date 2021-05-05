package io.github.oliviercailloux.samples.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class MyChessBoard implements ChessBoard {

	public static final ImmutableList<String> COLUMNS = ImmutableList.of("a", "b", "c", "d", "e", "f", "g", "h");

	private Map<String, Piece> map;

	MyChessBoard() {
		this.map = new HashMap<>();
		this.map.put("e1", Piece.king("W"));
		this.map.put("e8", Piece.king("B"));
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
		boolean changed = false;
		Builder<Optional<Piece>> listImu = ImmutableList.builder();
		for (String str : inputBoard) {
			if (str.length() == 2) {
				listImu.add(Optional.of(Piece.given(str.substring(0, 1), str.substring(1, 2))));
			}
		}
		final List<Optional<Piece>> PieceToBoard = listImu.build();
		changed = this.setBoardByPieces(PieceToBoard);
		return changed;
	}

	@Override
	public boolean setBoardByPieces(List<Optional<Piece>> inputBoard) {
		boolean whiteKing = false;
		boolean blackKing = false;
		boolean changed = false;
		Map<String, Piece> board = new HashMap<>();
		Iterator<Optional<Piece>> iteratorPiece = inputBoard.iterator();
		if (inputBoard.size() == 64) {
			for (int row = 1; row <= 8; row++) {
				for (String col : COLUMNS) {
					String position = col + String.valueOf(row);
					Optional<Piece> OptPiece = iteratorPiece.next();
					if (OptPiece.isPresent()) {
						Piece piece = OptPiece.get();
						board.put(position, piece);
						if (piece.isWhite() && piece.getIdentifyingLetter().equals("K")) {
							whiteKing = true;
						}
						if (piece.isBlack() && piece.getIdentifyingLetter().equals("B")) {
							blackKing = true;
						}
					}

					final Optional<Piece> previous = this.getPieceByPosition(position);
					
					if (!OptPiece.equals(previous)) {
						changed = true;
					}
				}
			}
		}
		
		if ( whiteKing && blackKing) {
			this.map = board;
		}	
		return changed;
	}

	@Override
	public ImmutableMap<String, String> getStringPiecesByPosition() {
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		for (String position : map.keySet()) {
			Piece piece = map.get(position);
			builder.put(position, piece.getColor() + piece.getIdentifyingLetter());
		}
		return builder.build();
	}

	@Override
	public ImmutableMap<String, Piece> getPiecesByPosition() {
		return ImmutableMap.copyOf(map);
	}

	@Override
	public Optional<Piece> getPieceByPosition(String position) {
		String column = position.substring(0, 1);
		String row = position.substring(1, 2);
		int valRow = Integer.parseInt(row);
		if (! position.isEmpty() && position.length() == 2 ) {
			if (COLUMNS.contains(column) && valRow>= 1 && valRow<=8) {
				 Optional.ofNullable(map.get(position));
			}
			
		}
		return  Optional.ofNullable(map.get(position));
	}

	@Override
	public ImmutableSet<Piece> getPieces(String color) {
		
		ImmutableSet.Builder<Piece> piecebuild = ImmutableSet.builder();
		if (!color.isEmpty() && color.equals("w") || color.equals("B")) {
			for (Piece piece : map.values()) {
				if (piece.getColor().equals(color)) {
					piecebuild.add(piece);
				}
			}
		}
		return piecebuild.build();
	}

	@Override
	public ImmutableList<Piece> getOrderedPieces(String color) {
		ImmutableSet<Piece> piece = getPieces(color);
		List<Piece> list = new ArrayList<>(piece);
		Collections.sort(list, Piece.getComparator());
		return ImmutableList.copyOf(list);
	}

	@Override
	public void movePiece(String oldPosition, String newPosition) {
		Piece piece = map.get(oldPosition);
		if (piece != null) {
			map.remove(oldPosition);
			map.put(newPosition, piece);
		}
	}

}
