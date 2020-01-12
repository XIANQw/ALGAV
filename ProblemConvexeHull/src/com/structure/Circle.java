/*
This class define a Circle.
*/
package com.structure;

import java.awt.Point;

public class Circle {

	private Point center;
	private int radius;
	public Circle(Point c, int r) {
		this.center = c;
		this.radius = r;
	}
	public int getRadius() {
		return this.radius;
	}
	public Point getCenter() {
		return this.center;
	}
}
