import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SchedulerGUI extends JFrame implements ActionListener {

	private JButton loadFileButton, createNewScheduleButton;
	private JTextArea newSchedule;
	private JScrollPane scrollPane;
	private JTextField fileNameTextField;
	private final JFileChooser fileChooser = new JFileChooser();
	private static final int WIDTH = 1000; // width of GUI
	private static final int HEIGHT = 1000; // height of GUI

	public SchedulerGUI() {

		JFrame frame = new JFrame("Class Scheduler");
		frame.setLayout(null);

		// Create JTextField object
		fileNameTextField = new JTextField(6);
		fileNameTextField.setBounds(400, 70, 200, 30);
		fileNameTextField.setEnabled(false);

		// Create JButton objects
		loadFileButton = new JButton("Load File");
		loadFileButton.addActionListener(this);
		loadFileButton.setBounds(400, 30, 200, 30);

		createNewScheduleButton = new JButton("Create New Schedule");
		createNewScheduleButton.addActionListener(this);
		createNewScheduleButton.setBounds(400, 150, 200, 30);

		// I had problems getting the jar file for JDatePicker
		// https://stackoverflow.com/questions/11736878/display-calendar-to-pick-a-date-in-java
		// JXDatePicker picker = new JXDatePicker();
		// picker.setDate(Calendar.getInstance().getTime());
		// picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

		// Create text area with scroll
		newSchedule = new JTextArea();
		newSchedule.setEnabled(false);
		newSchedule.setText("New Class Schedule will be displayed here.");
		newSchedule.setDisabledTextColor(Color.BLACK);
		scrollPane = new JScrollPane(newSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(100, 200, 800, 500);

		// Adds the components to the GUI
		frame.add(loadFileButton);
		frame.add(fileNameTextField);
		frame.add(scrollPane);
		frame.add(createNewScheduleButton);

		// Set the title of the window
		setTitle("Class Scheduler");

		// Set the size of the window and display it
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void printSchedule(File filename, JTextArea jTextArea) {
		jTextArea.setText("");
		Scanner myReader = null;
		try {
			myReader = new Scanner(filename);
			// Loops through the file line by line
			while (myReader.hasNextLine()) {
				String entryLine = myReader.nextLine();
				jTextArea.append(entryLine + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (myReader != null)
				myReader.close();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton clickedButton = (JButton) e.getSource();
			if (clickedButton == loadFileButton) {
				File openDirectory = new File(System.getProperty("user.dir"));
				fileChooser.setCurrentDirectory(openDirectory);
				int value = fileChooser.showOpenDialog(SchedulerGUI.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					File fileToTest = fileChooser.getSelectedFile();
					fileNameTextField.setText(fileToTest.getName());
				}
			}
			if (clickedButton == createNewScheduleButton) {
				File file = new File(fileNameTextField.getText());
				printSchedule(file, newSchedule);
			}
		}
	}

	public static void main(String[] args) {

		SchedulerGUI scheduler = new SchedulerGUI();
	}
}
