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
		g.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 13));
		g.drawChars(message.toCharArray(), 0, message.length(), 30, 60);
		drawDiameter(g, Color.CYAN);
		drawHull(g, Color.red);
		drawRectangle(g, Color.BLACK);
		drawCircle(g, Color.ORANGE);
	}
	
	
	private void drawPoint(Point p, Graphics g, Color color) {
		g.setColor(color);
		g.fillOval(p.x, p.y, 3, 3);
	}
	
	private void drawDiameter(Graphics g, Color color) {
		if(diameter == null) return ;
		g.drawLine(diameter.getP().x, diameter.getP().y, diameter.getQ().x, diameter.getQ().y);
	}
	
	private void drawHull(Graphics g, Color color) {
		if (enveloppe.size() == 0) return;
		int n = enveloppe.size();
		g.setColor(color);
		for (int i = 1; i < enveloppe.size(); i++) {
			g.drawLine(enveloppe.get(i - 1).x, enveloppe.get(i - 1).y,
					enveloppe.get(i).x, enveloppe.get(i).y);
		}
		g.drawLine(enveloppe.get(n - 1).x, enveloppe.get(n - 1).y,
				enveloppe.get(0).x, enveloppe.get(0).y);
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
		g.drawLine(rectangle.get(3).x, rectangle.get(3).y,
				rectangle.get(0).x, rectangle.get(0).y);
	}
	
	private void drawCircle(Graphics g, Color color) {
		if (circle == null) return;
		g.setColor(color);
		g.drawOval(circle.getCenter().x - circle.getRadius(), circle.getCenter().y
				- circle.getRadius(), (2 * circle.getRadius()), (2 * circle.getRadius()));
	}
	
	
	public void init() {
		points = FileToData.nextFile();
		enveloppe.clear();
		rectangle.clear();
		circle = null;
		diameter = null;
		repaint();
	}
	
	public void diameter() {
		if(diameter == null) diameter = Toussaint.rotatingCalipers(points);
		repaint();
	}
	
	public void enveloppe() {
		if(enveloppe.size() == 0) enveloppe = Calcule.jarvis(points);
		repaint();
	}
	
	public void rectangle() {
		if(enveloppe.size() == 0) enveloppe = Calcule.jarvis(points);
		rectangle = Toussaint.toussaint(enveloppe);
		repaint();
	}
	
	public void circle() {
		circle = Ritter.ritter(points);
		repaint();
	}
	
}
