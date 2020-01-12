/*
This class define a point
*/
package com.structure;

import java.awt.Point;

public class Line {
	private Point p;
	private Point q;
	public Line(Point p, Point q) {
		this.p = p;
		this.q = q;
	}
	public Point getP() {
		return this.p;
	}
	public Point getQ() {
		return this.q;
	}
}
