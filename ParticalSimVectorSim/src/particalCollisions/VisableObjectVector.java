package particalCollisions;

import javax.swing.JComponent;

public class VisableObjectVector extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double force, angle;

	public VisableObjectVector() {
		this.force = 0;
		this.angle = 0;
	}

	public VisableObjectVector(double newtons, double angle) {
		this.force = newtons;
		this.angle = angle;
	}
	public void addAngle(double angle) {
		this.angle = this.angle + angle;
	}

	public void setForce(double par1) {
		this.force = par1;
	}

	public void setAngle(double par1) {
		this.angle = par1;
	}

	public double getForce() {
		return this.force;
	}

	public double getAngle() {
		return this.angle;
	}

	public String toString() {
		return this.force + "@" + this.angle;
	}

	public VisableObjectVector clone() {
		return new VisableObjectVector(this.force, this.angle);
	}

	public boolean equals(Object obj) {
		if (obj instanceof VisableObjectVector) {
			VisableObjectVector objForce = (VisableObjectVector) ((VisableObjectVector) obj).clone();
			return objForce.force == this.force && objForce.angle == this.angle ? true : false;
		}
		return false;
	}

	public VisableObjectVector getCompForceX() {
		double newVector = this.angle;
		double x = (Math.cos(Math.toRadians(newVector)) * getForce());
		return new VisableObjectVector(x, 0);
	}
	

	public VisableObjectVector getCompForceY() {
		double newVector = this.angle;
		double y = (Math.sin(Math.toRadians(newVector)) * getForce());
		return new VisableObjectVector(y, 90);
	}

}
