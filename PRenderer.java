package particalCollisions;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PRenderer {

	private JFrame frame;

	public static ArrayList<Partical> ParticalArray = new ArrayList<>();

	public PRenderer(JFrame frame) {
		this.frame = frame;

	}

	public void render() {
		Graphics g = frame.getGraphics();
		g.drawRect(320, 75, 800, 600);

		g.translate(720, 375);
		g.drawLine(0, -300, 0, 300);
		g.drawLine(-400, 0, 400, 0);

		clearAll(g);
		g.setColor(Color.gray);

		for (Partical p : ParticalArray) {
			p.move(PEngine.renderSpeed / 1000.0);
			checkWallcollision(p);
		}
		for (Partical p : ParticalArray) {
			cheekCollisions(ParticalArray, p, g);
			p.paintComponent(g);
		}

		g.setColor(Color.gray);

		// what do? PEngine.infoPanel.repaint();
		// what do? g.dispose();

	}

// works
	public void checkWallcollision(Partical p) {

		int xValPartical = p.getX();
		int yValPartical = p.getY();
		int radiusPartical = (int) p.getRadius();

		if ((400 - 2 * radiusPartical) <= xValPartical) {
			p.getVOV().setVector(p.getVOV().getVector() + -(180 + 2 * p.getVOV().getVector() % 360));
		}
		if (xValPartical <= (-400)) {
			p.getVOV().setVector(p.getVOV().getVector() + -(180 + 2 * p.getVOV().getVector() % 360));
		}
		if (yValPartical <= (-300)) {
			p.getVOV().setVector(Math.abs((p.getVOV().getVector() % 360) - 360));
		}
		if ((300 - 2 * radiusPartical) <= yValPartical) {
			p.getVOV().setVector(180 + Math.abs((p.getVOV().getVector() % 360) - 180));
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

					resolveCollision(p, listOfParticalsToCheek.get(i), 1);

					System.out.println("Collition!!");

				}
			}

		}

	}

	public void resolveCollision(Partical p1, Partical p2, double e) {

		int p1X = p1.getX();
		int p2X = p2.getX();
		int p1Y = p1.getY();
		int p2Y = p2.getY();
		double angleOfDeflection;
		VisableObjectVector tempVOV = p1.getVOV();
		p1.setVOV(p2.getVOV());
		p2.setVOV(tempVOV);

		int equationPart1 = (p1X - p2X);
		System.out.println("equationPart1" + equationPart1);
		double equationPart2 = (p1Y - p2Y);
		System.out.println("equationPart2" + equationPart2);
		double equationPart3 = -1 * (equationPart1 / equationPart2);
		System.out.println("equationPart3:" + equationPart3);
		if (equationPart2 == 0) {
			equationPart3 = 0;
		}
		angleOfDeflection = Math.atan(equationPart3);
		angleOfDeflection = Math.toDegrees(angleOfDeflection);

		System.out.println("angle of Deflection:" + angleOfDeflection);
		p1.getVOV().setVector(p1.getVOV().getVector() + angleOfDeflection);
		p2.getVOV().setVector(p2.getVOV().getVector() - angleOfDeflection);

		CollisionEvent collisionEvent = new CollisionEvent();
		Thread eventThread = new Thread(collisionEvent);
		eventThread.start();
//		p1.setVector( angleOfDeflection);
//		p2.setVector( angleOfDeflection);
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
				PEngine.isRuning = false;
				Thread.sleep(2000);
				PEngine.isRuning = true;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
