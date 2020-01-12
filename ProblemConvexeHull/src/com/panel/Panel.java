/*
This class realize panel and add some method for drawing shapes, like draw a rectangle inputed, draw a polygon inputed, etc.
*/
package com.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.algorithms.Calcule;
import com.algorithms.Ritter;
import com.algorithms.Toussaint;
import com.structure.Circle;
import com.structure.Line;
import com.test.FileToData;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	ArrayList<Point> points;
	ArrayList<Point> enveloppe;
	ArrayList<Point> rectangle;
	Circle circle;
	Line diameter;
	int timeExecute;

	public Panel() {
		points = FileToData.nextFile();
		enveloppe = new ArrayList<Point>();
		rectangle = new ArrayList<Point>();
		requestFocus();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		setBackground(Color.WHITE);
	}

	private void draw(Graphics g) {
		for(Point p : points) {
			drawPoint(p, g, Color.BLUE);
		}
		String message = "n: next point set | d: diameter | e: polygon | r: rectangle | c: circle";
		g.setColor(Color.BLACK);
		g.setFont(new Font("font", Font.PLAIN, 14));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 60);
		drawDiameter(g, Color.CYAN);
		drawPolygon(g, Color.red);
		drawRectangle(g, Color.BLACK);
		drawCircle(g, Color.ORANGE);
		drawTime(g);
	}


	private void drawPoint(Point p, Graphics g, Color color) {
		g.setColor(color);
		g.fillOval(p.x, p.y, 3, 3);
	}

	private void drawTime(Graphics g) {
		String message = "Execute time: " + timeExecute + " ms.";
		g.setColor(Color.red);
		g.setFont(new Font("font", Font.PLAIN, 14));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 90);
	}

	private void drawPolygon(Graphics g, Color color) {
		if (enveloppe.size() == 0) return;
		int n = enveloppe.size();
		g.setColor(color);
		for (int i = 1; i < enveloppe.size(); i++) {
			g.drawLine(enveloppe.get(i - 1).x, enveloppe.get(i - 1).y,
					enveloppe.get(i).x, enveloppe.get(i).y);
		}
		String message = "Polygon area: " + Calcule.areaConvexPolygon(enveloppe);
		g.setColor(Color.BLACK);
		g.setFont(new Font("font", Font.PLAIN, 14));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 120);
	}

	private void drawRectangle(Graphics g, Color color) {
		if (rectangle.size() == 0) return;
		g.setColor(color);
		drawPoint(rectangle.get(0), g, color);
		for (int i = 1; i < rectangle.size(); i++) {
			g.drawLine(rectangle.get(i - 1).x, rectangle.get(i - 1).y,
					rectangle.get(i).x,(int) rectangle.get(i).y);
			drawPoint(rectangle.get(i), g, color);
		}
		String message = "Rectangle area: " + Calcule.areaConvexPolygon(rectangle);
		g.setColor(Color.BLACK);
		g.setFont(new Font("font", Font.PLAIN, 14));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 150);
	}

	private void drawCircle(Graphics g, Color color) {
		if (circle == null) return;
		g.setColor(color);
		g.drawOval(circle.getCenter().x - circle.getRadius(), circle.getCenter().y
				- circle.getRadius(), (2 * circle.getRadius()), (2 * circle.getRadius()));
		double areaCircle = Math.PI * circle.getRadius() * circle.getRadius();
		String message = "Circle area: " + (int) areaCircle;
		g.setColor(Color.BLACK);
		g.setFont(new Font("font", Font.PLAIN, 14));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 180);
	}

	private void drawDiameter(Graphics g, Color color) {
		if(diameter == null) return ;
		g.drawLine(diameter.getP().x, diameter.getP().y, diameter.getQ().x, diameter.getQ().y);
		String message = "Diameter length: " + (int)Math.sqrt(Calcule.calculDistance(diameter.getP(), diameter.getQ()));
		g.setColor(Color.BLACK);
		g.setFont(new Font("font", Font.PLAIN, 14));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 210);
	}

	/*Initialiser le panel, vider le dernier resultat*/
	public void init() {
		points = FileToData.nextFile();
		enveloppe.clear();
		rectangle.clear();
		circle = null;
		diameter = null;
		repaint();
	}
	/*Calculer la diametre en appelant l'algorithme Toussaint*/
	public void diameter() {
		long time = System.currentTimeMillis();
		if(diameter == null) diameter = Toussaint.rotatingCalipers(points);
		timeExecute = (int) (System.currentTimeMillis() - time);
		repaint();
	}
	/*Calculer l'enveloppe convexe minimum en appelant l'algorithme Jarvis*/
	public void enveloppe() {
		long time = System.currentTimeMillis();
		enveloppe = Calcule.jarvis(points);
		timeExecute = (int) (System.currentTimeMillis() - time);
		repaint();
	}
	/*Calculer le rectangle convexe minimum en appelant l'algorithme Toussaint*/
	public void rectangle() {
		long time = System.currentTimeMillis();
		enveloppe = Calcule.jarvis(points);
		rectangle = Toussaint.toussaint(enveloppe);
		rectangle.add(rectangle.get(0));
		timeExecute = (int) (System.currentTimeMillis() - time);
		repaint();
	}
	/*Calculer la cercle convexe minimum en appelant l'algorithme Ritter*/
	public void circle() {
		long time = System.currentTimeMillis();
		circle = Ritter.ritter(points);
		timeExecute = (int) (System.currentTimeMillis() - time);
		repaint();
	}

}
