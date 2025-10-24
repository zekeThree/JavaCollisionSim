package particalCollisions;

import javax.swing.JComponent;

public class VisableObjectVector extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double force, vector;

	public VisableObjectVector() {
		this.force = 0;
		this.vector = 0;
	}

	public VisableObjectVector(double newtons, double vector) {
		this.force = newtons;
		this.vector = vector;
	}

	public void setForce(double par1) {
		this.force = par1;
	}

	public void setVector(double par1) {
		this.vector = par1;
	}

	public double getForce() {
		return this.force;
	}

	public double getVector() {
		return this.vector;
	}

	public String toString() {
		return this.force + "@" + this.vector;
	}

	public VisableObjectVector clone() {
		return new VisableObjectVector(this.force, this.vector);
	}

	public boolean equals(Object obj) {
		if (obj instanceof VisableObjectVector) {
			VisableObjectVector objForce = (VisableObjectVector) ((VisableObjectVector) obj).clone();
			return objForce.force == this.force && objForce.vector == this.vector ? true : false;
		}
		return false;
	}

	public VisableObjectVector getCompForceX() {
		double newVector = this.vector;
		double x = (Math.cos(Math.toRadians(newVector)) * getForce());
		return new VisableObjectVector(x, 0);
	}
	public void setCompForceX(VisableObjectVector vov) {
		double newVector = vov.vector;
		
		
	}

	public VisableObjectVector getCompForceY() {
		double newVector = this.vector;
		double y = (Math.sin(Math.toRadians(newVector)) * getForce());
		return new VisableObjectVector(y, 90);
	}

}
