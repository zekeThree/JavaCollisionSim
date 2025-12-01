package particalCollisions;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	/**
	 * to do : normalize the coordinates
	 */
	private static final long serialVersionUID = 1L;

	private JLabel ForceLable, angleLabel, cordsLable;

	public InfoPanel() {
		
		cordsLable = new JLabel("()");
		angleLabel = new JLabel("Angle:  Deg");
		ForceLable = new JLabel(0+" N");
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createVerticalStrut(5));
		add(ForceLable);
		add(Box.createVerticalStrut(5));
		add(angleLabel);
		add(Box.createVerticalStrut(5));
		add(cordsLable);
		add(Box.createVerticalStrut(5));
		setBorder(BorderFactory.createTitledBorder("PARTICAL INFO"));
		setBounds(700, 650, 280, 60);

	}

	@Override
	public void repaint() {
		if ((ParticalScrolPanel.selectedIndex) != -1 && ParticalScrolPanel.particalMap
				.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)) != null) {
				ForceLable .setText(Math.round(ParticalScrolPanel.particalMap
					.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getVOV().getForce()) + " N");
			angleLabel.setText("Angle: " + Math.round(ParticalScrolPanel.particalMap
					.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getVOV().getAngle())
					+ " Deg");
			cordsLable
					.setText("("
							+ ParticalScrolPanel.particalMap
									.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getX()
							+ ","
							+ ParticalScrolPanel.particalMap
									.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getY()
							+ ")");
		}
		super.repaint();
//		PEngine.rend.drawPartical(ParticalScrolPanel.particalMap
//									.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)), 130,-400);
	}

}
