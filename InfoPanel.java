package particalCollisions;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel XcompLable, YcompLable, angleLabel;

	public InfoPanel() {
		XcompLable = new JLabel("Xcomp:  N");
		YcompLable = new JLabel("Ycomp:  N");
		angleLabel = new JLabel("Angle:  Deg");
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(XcompLable);
		add(Box.createVerticalStrut(5));
		add(YcompLable);
		add(Box.createVerticalStrut(5));
		add(angleLabel);
		setBorder(BorderFactory.createTitledBorder("PARTICAL INFO"));
		setBounds(700, 650, 250, 60);

	}

	@Override
	public void repaint() {
		if ((ParticalScrolPanel.selectedIndex) != -1 && ParticalScrolPanel.particalMap
				.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)) != null) {
			XcompLable.setText("Xcomp: " + Math.round(ParticalScrolPanel.particalMap
					.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getVOV().getCompForceX().getForce())
					+ " N");
			YcompLable.setText("Ycomp: " + Math.round(ParticalScrolPanel.particalMap
					.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getVOV().getCompForceY().getForce())
					+ " N");
			angleLabel.setText("Angle: "
					+ Math.round(ParticalScrolPanel.particalMap
							.get(ParticalScrolPanel.listModel.get(ParticalScrolPanel.selectedIndex)).getVOV().getVector())
					+ " Deg");
		}
		super.repaint();

	}

}
