package astar;

import java.lang.reflect.Array;
import java.util.List;

public class ArrayFacade<T> {

	protected int width, height;

	protected T initialValue;

	protected T t[][];

	private Class<T> contentType;

	@SuppressWarnings("unchecked")
	public ArrayFacade(int width, int height, Class<T> contentType, T initialValue) {
		super();
		this.width = width;
		this.height = height;
		this.initialValue = initialValue;
		this.contentType = contentType;
		this.t = (T[][]) Array.newInstance(contentType, height, width);
		reset();
	}

	@Override
	public ArrayFacade<T> clone() {
		ArrayFacade<T> clone = new ArrayFacade<T>(width, height, contentType, initialValue);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				clone.set(x, y, this.t[y][x]);
			}
		}
		return clone;
	}

	public void reset() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				this.t[y][x] = initialValue;
			}
		}
	}

	public void set(int[] coords, T value) {
		set(coords[0], coords[1], value);

	}

	public void set(List<int[]> coords, T value) {
		for (int[] point : coords) {
			set(point[0], point[1], value);
		}
	}

	public void set(int x, int y, T value) {
		this.t[y][x] = value;
	}

	public T get(int x, int y) {
		return this.t[y][x];
	}

	public boolean hasValue(int x, int y) {
		return this.t[y][x] != null;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}

}
