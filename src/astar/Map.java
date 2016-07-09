package astar;

import static astar.Type.*;
import static astar.Util.*;

import java.util.LinkedList;
import java.util.List;

public class Map extends ArrayFacade<Type> implements Cloneable {
	/*
	 * Test main()
	 */
	public static void main(String[] args) {
		//	@formatter:off
		Map map = Map.create()
				.row(FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , END     , FREE    , FREE    )
				.row(FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    )
				.row(FREE    , FREE    , FREE    , OBSTACLE, OBSTACLE, OBSTACLE, OBSTACLE, OBSTACLE, OBSTACLE, FREE    )
				.row(FREE    , FREE    , FREE    , OBSTACLE, OBSTACLE, FREE    , FREE    , OBSTACLE, OBSTACLE, FREE    )
				.row(FREE    , FREE    , FREE    , OBSTACLE, FREE    , FREE    , FREE    , FREE    , OBSTACLE, FREE    )
				.row(FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    )
				.row(FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    )
				.row(FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    )
				.row(FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    )
				.row(START   , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    , FREE    )
				.get();
		System.out.println(map);
	//	@formatter:on

		int[] coords = Algorithm.search(map, Type.START);
		System.out.println("Start point at: " + Util.string(coords));
		coords = Algorithm.search(map, Type.END);
		System.out.println("  End point at: " + Util.string(coords));

		{
			Map copy = map.clone();
			List<int[]> line = Algorithm.line(copy);
			copy.set(line, Type.WAY);
			System.out.println("Heuristic:");
			System.out.println(copy);
		}

		{
			Map copy = map.clone();
			List<int[]> path = Algorithm.findPath(copy);
			copy.set(path, Type.OPTIMAL);
			System.out.println("A Star result:");
			System.out.println(copy);
			System.out.println("Waypoints: " + path.size());
		}
	}

	/*
	 * MapBuilder
	 */
	static class MapBuilder {
		int colCount;
		List<Type[]> rows = new LinkedList<>();

		MapBuilder row(Type... colValues) {
			if (rows.isEmpty()) {
				colCount = colValues.length;
			} else {
				if (colValues.length > colCount) {
					throw new IllegalArgumentException(String.format(
							"The rows must all have the same number of columns wich is %d for this Map.", colCount));
				}
			}
			rows.add(colValues);
			return this;
		}

		Map get() {
			Map map = new Map(colCount, rows.size());
			for (int y = 0; y < rows.size(); y++) {
				Type[] row = rows.get(y);
				for (int x = 0; x < colCount; x++) {
					map.set(x, y, row[x]);
				}
			}
			return map;
		}
	}

	/*
	 * Map
	 */

	public Map(int width, int height) {
		super(width, height, Type.class, FREE);
	}

	public static MapBuilder create() {
		return new MapBuilder();
	}

	@Override
	public Map clone() {
		Map clone = new Map(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				clone.set(x, y, this.t[y][x]);
			}
		}
		return clone;
	}

	public boolean isFree(int[] coords) {
		return isFree(coords[0], coords[1]);
	}

	public boolean isFree(int x, int y) {
		if (x < 0 || y < 0)
			return false;
		if (y >= t.length)
			return false;
		if (x >= t[0].length)
			return false;

		return this.t[y][x] == FREE || this.t[y][x] == END;
	}

	public List<int[]> getFreeNeighbours(int[] coords) {
		Util.checkCoords(coords);
		return getFreeNeighbours(coords[0], coords[1]);
	}

	public List<int[]> getFreeNeighbours(int x, int y) {
		List<int[]> freeNeighbours = new LinkedList<>();

		_addIfFree(freeNeighbours, new int[] { x - 1, y });
		_addIfFree(freeNeighbours, new int[] { x, y - 1 });
		_addIfFree(freeNeighbours, new int[] { x + 1, y });
		_addIfFree(freeNeighbours, new int[] { x, y + 1 });
		_addIfFree(freeNeighbours, new int[] { x + 1, y + 1 });
		_addIfFree(freeNeighbours, new int[] { x - 1, y + 1 });
		_addIfFree(freeNeighbours, new int[] { x + 1, y - 1 });
		_addIfFree(freeNeighbours, new int[] { x - 1, y - 1 });

		return freeNeighbours;
	}

	private void _addIfFree(List<int[]> freeNeighbours, int[] neighbour) {
		if (isFree(neighbour)) {
			freeNeighbours.add(neighbour);
		}
	}

	/**
	 * Returns the maximum number of characters used by the defines labels.
	 */
	public int maxLength() {
		int maxDigits = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int digits = t[y][x].getValue().toString().length();
				if (maxDigits < digits) {
					maxDigits = digits;
				}
			}
		}

		return Math.max(maxDigits, 3);
	}

	@Override
	public String toString() {

		int maxDigits = maxLength();
		String contentFormat = "%" + maxDigits + "s";

		StringBuilder b = new StringBuilder();

		if (t.length == 0) {
			return "[EMPTY MAP]";
		} else {
			// Print scale
			b.append(" ");
			for (int x = 0; x < t[0].length; x++) {
				String scale = String.format(contentFormat, x);
				b.append(scale);
				b.append(" ");
			}
			b.append(" ");
			b.append(System.lineSeparator());

			// Paint the upper border
			b.append(utf8(0x2554));
			for (int x = 0; x < t[0].length; x++) {
				for (int i = 0; i < maxDigits; i++) {
					b.append(utf8(0x2550));
				}
				if (x != t.length - 1)
					b.append(utf8(0x2550));
			}
			b.append(utf8(0x2557));

			b.append(System.lineSeparator());

			// Paint rows
			for (int y = 0; y < t.length; y++) {
				// Content row
				b.append(utf8(0x2551));
				for (int x = 0; x < t[0].length; x++) {
					Type value = get(x, y);
					String cell = String.format(contentFormat, value.getValue());
					b.append(cell);
					if (x != t.length - 1)
						b.append(utf8(0x2502));
				}
				b.append(utf8(0x2551));

				// Print row scale
				b.append(String.valueOf(y));

				b.append(System.lineSeparator());

				// Separator
				if (y != t.length - 1) {
					b.append(utf8(0x2551));
					for (int x = 0; x < t[0].length; x++) {
						for (int i = 0; i < maxDigits; i++) {
							b.append(utf8(0x2500));
						}
						if (x != t.length - 1)
							b.append(utf8(0x253C));
					}
					b.append(utf8(0x2551));
					b.append(System.lineSeparator());
				}

			}

			// Paint the lower border
			b.append(utf8(0x255A));
			for (int x = 0; x < t[0].length; x++) {
				for (int i = 0; i < maxDigits; i++) {
					b.append(utf8(0x2550));
				}
				if (x != t.length - 1)
					b.append(utf8(0x2550));
			}
			b.append(utf8(0x255D));

			b.append(System.lineSeparator());

		}
		return b.toString();
	}

}
