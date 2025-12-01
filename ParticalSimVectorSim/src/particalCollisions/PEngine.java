package particalCollisions;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PEngine implements Runnable {
	public static final String VERSION = "0.0.1";
	public static final String TITLE = "Partical Simulator " + VERSION;
	public static final Dimension SCREEN_SIZE = new Dimension(1150, 800);
	public static final Dimension PARTICAL_SCREEN_SIZE = new Dimension(1200, 1000);
	public static boolean isRuning;
	public static PRenderer rend;

	public static JFrame frame;
	public static JPanel ParticalScreen;
	public static InfoPanel infoPanel;
    public static int renderSpeed = 5;
	

	public static void main(String[] args) {
		PEngine ParticalEngine = new PEngine();
		new Thread(ParticalEngine).start();
	}

	public PEngine() {
		frame = new JFrame();
		frame.setSize(SCREEN_SIZE);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(null);

		rend = new PRenderer(frame);

		ParticalScreen = new JPanel(null);
		ParticalScreen.setBackground(Color.white);
		ParticalScreen.setBounds(313, 45, 800, 600);

		ParticalScrolPanel ParticalPanel = new ParticalScrolPanel();
		ParticalPanel.setBounds(0, 0, 270, 580);
		
		infoPanel = new InfoPanel();
		
		
		frame.add(ParticalPanel);
		frame.add(ParticalScreen);
		frame.add(infoPanel);

		frame.setVisible(true);
		isRuning = true;

	}

	//Left off here todo- implement double buffering
	public void run() {
		while (true) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
			}
			while (isRuning) {
				rend.render();
				try {
					Thread.sleep(renderSpeed);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
