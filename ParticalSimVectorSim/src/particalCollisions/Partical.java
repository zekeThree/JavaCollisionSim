package particalCollisions;

import java.awt.*;

public class Partical {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VisableObjectVector vov;
	private double mass;
	private int radius;
	private int x, y;
	private double xHolder, yHolder;
	public String name;
	private Color color;
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	private int collided;

	public Partical(String name, double mass, int radius, float force, float direction, Color color, int x, int y) {
		vov = new VisableObjectVector(force, direction);
		this.radius = radius;
		this.name = name;
		this.mass = mass;
		this.color = color;
		xHolder = x;
		yHolder = y;
		this.setX(x);
		this.setY(y);
		collided = 10;
	}
	/**
	 * 
	 * @param mass
	 * @param radius
	 * @param force
	 * @param direction
	 * @param color
	 * @param x
	 * @param y
	 */
	public Partical(double mass, int radius, float force, float direction, Color color, int x, int y) {
		vov = new VisableObjectVector(force, direction);
		this.radius = radius;
		this.mass = mass;
		this.color = color;
		xHolder = x;
		yHolder = y;
		this.setX(x);
		this.setY(y);
		collided = 10;
	}
	/**
	 * 
	 * @param g Graphics
	 */
	
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, radius * 2, radius * 2);
	}
	/**
	 * 
	 * @param movement
	 */
	public void move(double movement) {
		xHolder = xHolder + (movement * vov.getCompForceX().getForce());
		yHolder = yHolder + (movement * vov.getCompForceY().getForce());

		this.x = (int) (xHolder + .5);
		this.y = (int) (yHolder + .5);
		if (collided > 0) {
			collided--;
		}
	}
	/*
	 * @pram collided
	 */
	public void setCollided(boolean Collided) {
		this.collided = 25;
	}
	
	public int getCollided() {
		return collided;
	}

	public int getX() {
		return x;
	}

	public void setX(double x) {
		this.xHolder = x;
		this.x = (int) xHolder;
	}

	public VisableObjectVector getVOV() {
		return vov;
	}

	public void setVOV(VisableObjectVector vov) {
		this.vov = vov;
	}

	public int getY() {
		return y;
	}

	public void setY(double y) {
		this.yHolder = y;
		this.y = (int) yHolder;
	}

	public int getRadius() {
		return radius;
	}
	

}
