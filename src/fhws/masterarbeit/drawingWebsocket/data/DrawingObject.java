package fhws.masterarbeit.drawingWebsocket.data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import fhws.masterarbeit.drawingWebsocket.data.Shape;

public class DrawingObject 
{
	public static String MESSAGE_NAME = "DrawingObject";
	private Shape shape;
	private Point center;
	private int radius = 0;
	private Color color;
	
	public DrawingObject(Shape shape, Point center, int radius, Color color)
	{
		this.shape = shape;
		this.center = center;
		this.radius = radius;
		this.color = color;
	}
	
	public Shape getShape()
	{
		return this.shape;
	}
	
	public Point getCenter()
	{
		return this.center;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(this.color);
		switch (shape)
		{
			case CIRCLE:
				g.fillOval(center.x - radius, center.y - radius, 2*radius, 2*radius);
				break;
			case TRIANGLE:
				Polygon triangle = new Polygon();
				triangle.addPoint(center.x, center.y - radius);
				triangle.addPoint(center.x + radius, center.y + radius);
				triangle.addPoint(center.x - radius, center.y + radius);
				g.fillPolygon(triangle);
				break;
			case SQUARE:
				Polygon square = new Polygon();
				square.addPoint(center.x - radius, center.y - radius);
				square.addPoint(center.x + radius, center.y - radius);
				square.addPoint(center.x + radius, center.y + radius);
				square.addPoint(center.x - radius, center.y + radius);
				g.fillPolygon(square);
				break;
			case PENTAGON:
				Polygon pentagon = new Polygon();
				pentagon.addPoint(center.x,  center.y - radius);
				pentagon.addPoint(center.x + radius,  center.y - (radius/5));
				pentagon.addPoint(center.x + (3*radius/5), center.y + radius);
				pentagon.addPoint(center.x - (3*radius/5), center.y + radius);
				pentagon.addPoint(center.x - radius, center.y - (radius/5));
				g.fillPolygon(pentagon);
				break;
			default:
				g.fillOval(center.x - 5, center.y - 5, 10, 10);
				break;
		}
	}
}
