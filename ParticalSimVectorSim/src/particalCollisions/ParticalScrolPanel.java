package particalCollisions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ParticalScrolPanel extends JPanel implements ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private static final String addString = "ADD", removeString = "REMOVE", pauseString = "PAUSE";

	public static HashMap<String, Partical> particalMap = new HashMap<>();
	public static JList<String> list;
	public static int selectedIndex;

	private static JTextField particalField;
	private static JButton removeButton;

	public static DefaultListModel<String> listModel;

	// for testing only
	public static int count;

	public ParticalScrolPanel() {
		super(null);

		listModel = new DefaultListModel<>();
		listModel.addElement("Particals");
		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(JList.VERTICAL);
		list.setFont(new Font("Arial", Font.BOLD, 22));
		JScrollPane listScrollPane = new JScrollPane(list);

		JButton addButton = new JButton(addString);
		AddListener addListener = new AddListener(addButton);
		addButton.addActionListener(addListener);

		JButton pauseButton = new JButton(pauseString);
		PauseListener pauseListener = new PauseListener();
		pauseButton.addActionListener(pauseListener);

		removeButton = new JButton(removeString);
		RemoveListener removeListener = new RemoveListener();
		removeButton.addActionListener(removeListener);

		particalField = new JTextField();
		particalField.addActionListener(addListener);
		particalField.getDocument().addDocumentListener(addListener);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(addButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(removeButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(pauseButton);
		buttonPane.add(Box.createGlue());

		JPanel fieldPane = new JPanel();
		fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
		fieldPane.setBounds(0, 500, 300, 80);
		fieldPane.add(buttonPane);
		fieldPane.add(Box.createVerticalStrut(5));
		fieldPane.add(particalField);
		fieldPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		listScrollPane.setBounds(0, 0, 300, 500);
		fieldPane.setBounds(0, 500, 270, 80);
		add(listScrollPane);
		add(fieldPane);

	}

	class PauseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (PEngine.isRuning) {
				PEngine.isRuning = false;
			} else {
				PEngine.isRuning = true;
			}

		}

	}

	class RemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			if (index == 0) {
				return;
			}

			String name = listModel.get(index);
			PEngine.rend.clearPartical(particalMap.get(name));
			PRenderer.ParticalArray.remove(index - 1);
			particalMap.remove(name);
			listModel.remove(index);
			count--;
			index--;
			if (index == 0) {
				removeButton.setEnabled(false);
			}
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);

		}

	}

	class AddListener implements ActionListener, DocumentListener {

		private JButton button;
		private boolean isEnabled = false;

		public AddListener(JButton button) {
			this.button = button;

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			enableButton();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			handleEmptyField(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if (!handleEmptyField(e)) {
				enableButton();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = particalField.getText();
			if (alreadyInList(name) || name.startsWith(" ")) {
				Toolkit.getDefaultToolkit().beep();
				particalField.requestFocusInWindow();
				particalField.selectAll();
				return;
			}
			int index = list.getSelectedIndex();
			if (index == -1) {
				index = 0;
			} else {
				index++;
			}
			int x = (int) (Math.random() * 201) - 100;
			int y = (int) (Math.random() * 201) - 100;
			int angle;
			int speed;
			Color color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
					(int) (Math.random() * 256));
			y = 0;
			x = 0;
			angle = (int) (Math.random() * 361);
			speed = (int) (Math.random() * 201);
			if (name.equals("")) {
				name = "P" + listModel.size();
			}
			if (count == 0) {
				speed = 70;
				angle = 270;
				y = 178;
				x = 0;
				color =  new Color(50, 150, 250);
			} else if (count == 1) {
				speed = 170;
				angle = 65;
				y = -130;
				x = -100;
				color =  new Color(250, 150, 50);
			} else {
				speed = 180;
			}
			
			particalMap.put(name, new Partical(name, 10.5, 15, speed, angle, color, x, y));
			particalMap.put(name, new Partical(name, 10.5, 15, speed, angle, color, x, y+30));
			listModel.insertElementAt(name, index);
			PRenderer.ParticalArray
					.add(ParticalScrolPanel.particalMap.get(ParticalScrolPanel.listModel.elementAt(index)));
			particalField.requestFocusInWindow();
			particalField.setText("");
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);
			removeButton.setEnabled(true);
			count++;
		}

		protected boolean alreadyInList(String name) {
			return listModel.contains(name);
		}

		private void enableButton() {
			if (!isEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyField(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0) {
				return true;
			}
			return false;
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int index = list.getSelectedIndex();
		selectedIndex = index;
		if (selectedIndex != 0) {
			removeButton.setEnabled(true);
		}

		PEngine.infoPanel.repaint();

	}

}
