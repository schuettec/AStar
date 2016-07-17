package de.schuette.math;

public class Point {

	public double x;
	public double y;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this(p.x, p.y);
	}

	public int getRoundX() {
		return (int) Math.round(x);
	}

	public int getRoundY() {
		return (int) Math.round(y);
	}

	public void translate(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void translate(Point p) {
		this.x += p.x;
		this.y += p.y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	@Override
	public String toString() {
		return "[x" + x + ", " + y + "]";
	}

	public java.awt.Point getPoint() {
		return new java.awt.Point(getRoundX(), getRoundY());
	}

}
