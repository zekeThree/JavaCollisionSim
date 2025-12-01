package particalCollisions;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PRenderer {

	private JFrame frame;
	private boolean isRendering = true;

	public static ArrayList<Partical> ParticalArray = new ArrayList<>();

	public PRenderer(JFrame frame) {
		this.frame = frame;

	}

	public void render() {
		Graphics g = frame.getGraphics();
		g.setColor(Color.gray);
		g.drawRect(320, 75, 800, 600);

		g.translate(720, 375);
		g.drawLine(0, -300, 0, 300);
		g.drawLine(-400, 0, 400, 0);

		clearAll(g);
		g.setColor(Color.gray);

		for (Partical p : ParticalArray) {
			if (isRendering) {
				p.move(PEngine.renderSpeed / 1000.0);
			}
			checkWallcollision(p);
		}
		for (Partical p : ParticalArray) {
			if (isRendering) {
				cheekCollisions(ParticalArray, p, g);
			}
			p.paintComponent(g);
		}

		g.setColor(Color.gray);

		PEngine.infoPanel.repaint();
		// what do? g.dispose();

	}

// works
	public void checkWallcollision(Partical p) {

		int xValPartical = p.getX();
		int yValPartical = p.getY();
		int radiusPartical = (int) p.getRadius();

		if ((400 - 2 * radiusPartical) <= xValPartical) {
			p.getVOV().setAngle(p.getVOV().getAngle() + -(180 + 2 * p.getVOV().getAngle() % 360));
		}
		if (xValPartical <= (-400)) {
			p.getVOV().setAngle(p.getVOV().getAngle() + -(180 + 2 * p.getVOV().getAngle() % 360));
		}
		if (yValPartical <= (-300)) {
			p.getVOV().setAngle(Math.abs((p.getVOV().getAngle() % 360) - 360));
		}
		if ((300 - 2 * radiusPartical) <= yValPartical) {
			p.getVOV().setAngle(180 + Math.abs((p.getVOV().getAngle() % 360) - 180));
		}

	}

	public void clearPartical(Partical p) {
		Graphics g = frame.getGraphics();
		g.translate(720, 375);
		g.setColor(Color.WHITE);
		g.fillOval(p.getX(), p.getY(), (int) (p.getRadius() * 2), (int) (p.getRadius() * 2));

	}

	public void cheekCollisions(ArrayList<Partical> listOfParticalsToCheek, Partical p, Graphics g) {
		int x1 = p.getX();
		int y1 = p.getY();
		int radius1 = p.getRadius();

		for (int i = 0; i < listOfParticalsToCheek.size(); i++) {
			if (!listOfParticalsToCheek.get(i).equals(p) && !(listOfParticalsToCheek.get(i).getCollided() > 0)) {
				int x2 = listOfParticalsToCheek.get(i).getX();
				int y2 = listOfParticalsToCheek.get(i).getY();
				int radius2 = listOfParticalsToCheek.get(i).getRadius();
				int distance = (int) Math.sqrt(Math.pow((y1 - y2), 2) + Math.pow((x1 - x2), 2));
				if (distance <= (radius1 + radius2)) {

					resolveCollision(p, listOfParticalsToCheek.get(i), .5);

					System.out.println("Collition!!");

				}
			}

		}

	}

	public VisableObjectVector addVectors(VisableObjectVector v1, VisableObjectVector v2) {

		double YcompV1 = Math.sin(Math.toRadians(v1.getAngle())) * v1.getForce();
		double YcompV2 = Math.sin(Math.toRadians(v2.getAngle())) * v2.getForce();
		double XcompV1 = Math.cos(Math.toRadians(v1.getAngle())) * v1.getForce();
		double XcompV2 = Math.cos(Math.toRadians(v2.getAngle())) * v2.getForce();

		double YcompV = YcompV1 + YcompV2;
		double XcompV = XcompV1 + XcompV2;
		double forceV = Math.pow((Math.pow(XcompV, 2) + Math.pow(YcompV, 2)), .5);
		double angleV;
		System.out.println(" "+YcompV+" \n "+XcompV);
		if (YcompV > 0) {
			if (XcompV > 0) {
					 angleV = 90-Math.toDegrees(Math.asin(XcompV/forceV));
					 if(forceV==0) {
						 angleV = 90-Math.toDegrees(Math.asin(0));
					 }
			} else {
				 angleV = 180-Math.toDegrees(Math.asin(YcompV/forceV));
				 if(forceV==0) {
					 angleV  = 180-Math.toDegrees(Math.asin(0));
				 }
			}
		} else {
			if (XcompV > 0) {
				 angleV = 360-Math.toDegrees(Math.asin(Math.abs(YcompV)/forceV));
				 if(forceV==0) {
					 angleV = 360-Math.toDegrees(Math.asin(0));
				 }
			} else {
				 angleV = 270- Math.toDegrees(Math.asin(Math.abs(XcompV)/forceV));
				 if(forceV==0) {
					 angleV = 270- Math.toDegrees(Math.asin(0));
				 }
			}
		}
		VisableObjectVector v = new VisableObjectVector(forceV, angleV);
		return v;
	}

	public void resolveCollision(Partical p1, Partical p2, double e) {

		int p1X = p1.getX();
		int p2X = p2.getX();
		int p1Y = p1.getY();
		int p2Y = p2.getY();
		double MidpointY = (p1Y + p2Y) / 2;
		double MidpointX = (p1X + p2X) / 2;
		double Rp1X = p1.getX() - MidpointX;
		double Rp2X = p2.getX() - MidpointX;
		double Rp1Y = p1.getY() - MidpointY;
		double Rp2Y = p2.getY() - MidpointY;
		Graphics g = frame.getGraphics();
		g.translate(720, 375);

		double CollisionAngle = Math.toDegrees(Math.atan((Rp1Y - Rp2Y) / (Rp1X - Rp2X)));

		double pcompP1 = Math.sin(Math.toRadians(p1.getVOV().getAngle() - CollisionAngle)) * p1.getVOV().getForce();
		double pcompP2 = Math.sin(Math.toRadians(p2.getVOV().getAngle() - CollisionAngle)) * p2.getVOV().getForce();
		double ncompP1 = Math.cos(Math.toRadians(p1.getVOV().getAngle() - CollisionAngle)) * p1.getVOV().getForce();
		double ncompP2 = Math.cos(Math.toRadians(p2.getVOV().getAngle() - CollisionAngle)) * p2.getVOV().getForce();
		System.out.println(CollisionAngle+" Deg");
		VisableObjectVector pcompP1V = new VisableObjectVector(pcompP1*e, CollisionAngle + 90);
		VisableObjectVector pcompP2V = new VisableObjectVector(pcompP2*e, CollisionAngle + 90);
		VisableObjectVector ncompP1V = new VisableObjectVector(ncompP1, CollisionAngle );
		VisableObjectVector ncompP2V = new VisableObjectVector(ncompP2, CollisionAngle );

		VisableObjectVector p1vectorF = addVectors(pcompP1V, ncompP2V);
		System.out.println("pcompP1V "+pcompP1V);
		System.out.println("ncompP2V "+ncompP2V);
		System.out.println("p1vectorF "+p1vectorF);
		System.out.println();
		VisableObjectVector p2vectorF = addVectors(pcompP2V, ncompP1V);
		System.out.println("pcompP2V "+pcompP2V);
		System.out.println("ncompP1V "+ncompP1V);
		System.out.println("p2vectorF "+p2vectorF);
		System.out.println();
		
		System.out.println(pcompP2V);
		System.out.println(ncompP1V);
		System.out.println(p2vectorF);

		
		
		p1.setVOV(p1vectorF);
		p2.setVOV(p2vectorF);

		CollisionEvent collisionEvent = new CollisionEvent();
		Thread eventThread = new Thread(collisionEvent);
		eventThread.start();
		p1.setCollided(true);
		p2.setCollided(true);

	}

	public void clearAll(Graphics g) {
		g.setColor(Color.WHITE);
		for (int i = 0; i < ParticalArray.size(); i++) {
			clearPartical(ParticalArray.get(i));
		}
	}

	public class CollisionEvent implements Runnable {

		public CollisionEvent() {

		}

		@Override
		public void run() {

			try {
				isRendering = false;
				Thread.sleep(20);
				isRendering = true;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
