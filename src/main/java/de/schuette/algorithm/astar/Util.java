package de.schuette.algorithm.astar;

import java.util.Arrays;

public class Util {

	public static void checkCoords(int[] coords) {
		if (coords == null || coords.length != 2) {
			throw new IllegalArgumentException("Coords must be an array with length of 2.");
		}
	}

	public static String string(int[] coords) {
		return Arrays.toString(coords);
	}

	public static char utf8(int codePoint) {
		return Character.toChars(codePoint)[0];
	}

	public static String utf8(int... codePoints) {
		StringBuilder b = new StringBuilder();
		for (int codePoint : codePoints) {
			b.append(utf8(codePoint));
		}
		return b.toString();
	}
}
