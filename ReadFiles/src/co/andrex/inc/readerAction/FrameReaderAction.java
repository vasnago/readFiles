package co.andrex.inc.readerAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.io.FilenameUtils;

/**
 * This Class have the logic to the read and write the files of a path selected
 * by a user
 * 
 * @author Andrex.Gomez
 * 
 */
@SuppressWarnings("serial")
public class FrameReaderAction implements Serializable {

	private JFrame frame;

	private JLabel lblPathValue;
	private JLabel lblPathRename;
	private JLabel lblStatus;
	private JFileChooser chooser = new JFileChooser();
	private JProgressBar progressBar;
	private JTabbedPane tabbedPane;
	private JTextField textPrefix;
	private JCheckBox chckAuto;
	private JCheckBox chckbxAutoincrementWithoutName;
	private TextArea txtAreaNameFiles;
	private TextArea textAreaRenameFile;
	private JButton buttonRunSearchFile;
	private JButton buttonRunRenameFiles;
	private File directory;
	private boolean flagAction = false;
	private boolean isIncrement;
	private boolean isIncrementWithoutName;
	private int conn = 0;
	private JButton btnClearRaname;
	private boolean dis = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameReaderAction window = new FrameReaderAction();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameReaderAction() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Detected the resolution of display
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double width = screenSize.getWidth();
		dis = false;
		if (width > 2000) {
			dis = true;
		}
		// Create the principal frame
		frame = new JFrame();
		frame.setTitle("FilerX Reader V.2.1");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);

		frame.setBounds(100, 100, (dis ? 1462 : 731), (dis ? 1495 : 740));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, (dis ? 1452 : 726), (dis ? 1422 : 711));
		frame.getContentPane().add(tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Search Files", null, panel_1, null);

		// Set the width and height of tab
		JLabel lab = new JLabel();
		lab.setText("Search Files");
		lab.setFont(new Font("Trebuchet MS", Font.BOLD, dis ? 40 : 20));
		lab.setPreferredSize(new Dimension(dis ? 300 : 150, dis ? 80 : 40));
		tabbedPane.setTabComponentAt(0, lab);
		panel_1.setLayout(null);

		txtAreaNameFiles = new TextArea();
		txtAreaNameFiles.setFont(new Font("Tahoma", Font.PLAIN, dis ? 40 : 20));
		txtAreaNameFiles.setEditable(false);
		txtAreaNameFiles.setBounds(0, dis ? 330 : 165, dis ? 1442 : 721,
				dis ? 990 : 495);
		panel_1.add(txtAreaNameFiles);

		Panel panel = new Panel();
		panel.setBounds(0, 0, dis ? 1442 : 721, dis ? 332 : 166);
		panel_1.add(panel);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(null);

		// Create Button of Select Folder Search Files
		JButton btnSelectFolder = new JButton("");
		String pathImage = dis ? "/Image/selectFolder128.png"
				: "/Image/selectFolder64.png";
		setButtonOption(btnSelectFolder, pathImage, false, 34, 20, 65, 55);
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtAreaNameFiles.setText("");
				flagAction = false;
				selectPath();
				buttonRunSearchFile.setEnabled(directory != null);
			}
		});
		panel.add(btnSelectFolder);

		JLabel lblSelectFolder = new JLabel("Select Folder");
		lblSelectFolder.setBounds(5, dis ? 156 : 78, dis ? 274 : 137, dis ? 28
				: 14);
		panel.add(lblSelectFolder);
		lblSelectFolder.setFont(new Font("Trebuchet MS", Font.BOLD, dis ? 40
				: 20));

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(dis ? 300 : 150, 20, dis ? 180 : 90, dis ? 48 : 24);
		panel.add(lblPath);
		lblPath.setFont(new Font("Trebuchet MS", Font.BOLD, dis ? 40 : 20));

		lblPathValue = new JLabel("");
		lblPathValue.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC,
				dis ? 40 : 20));
		lblPathValue.setBounds(dis ? 300 : 150, 47, dis ? 1058 : 529, dis ? 56
				: 28);
		panel.add(lblPathValue);
		lblPathValue.setBackground(Color.WHITE);

		// Create Button to run the search files
		buttonRunSearchFile = new JButton("");
		String pathRunIcon = dis ? "/Image/runIcon_128.png"
				: "/Image/runIconX72.png";
		setButtonOption(buttonRunSearchFile, pathRunIcon, true,
				dis ? 644 : 322, dis ? 172 : 86, 72, 72);
		buttonRunSearchFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readFilesPath();
			}
		});
		panel.add(buttonRunSearchFile);

		// Create Button to clean the textArea in Search tab
		JButton btnClearSearch = new JButton("");
		String pathClear = dis ? "/Image/Clear_64.png" : "/Image/Clear_32.png";
		setButtonOption(btnClearSearch, pathClear, false, 10, dis ? 252 : 126,
				32, 32);
		btnClearSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAreaNameFiles.setText("");
			}
		});
		panel.add(btnClearSearch);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Rename Files", null, panel_2, null);
		JLabel lab2 = new JLabel();
		lab2.setText("Rename Files");
		lab2.setPreferredSize(new Dimension(dis ? 300 : 150, dis ? 80 : 40));
		lab2.setFont(new Font("Trebuchet MS", Font.BOLD, dis ? 40 : 20));
		tabbedPane.setTabComponentAt(1, lab2);
		panel_2.setLayout(null);

		Panel panel_3 = new Panel();
		panel_3.setLayout(null);
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_3.setBounds(0, 0, dis ? 1440 : 720, dis ? 492 : 246);
		panel_2.add(panel_3);

		// Create Button to select folder to rename tab
		JButton btnRename = new JButton("");
		setButtonOption(btnRename, pathImage, false, 34, 20, 65, 55);
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaRenameFile.setText("");
				flagAction = true;
				selectPath();
				buttonRunRenameFiles.setEnabled(directory != null);
			}
		});
		panel_3.add(btnRename);

		JLabel label = new JLabel("Select Folder");
		label.setFont(new Font("Trebuchet MS", Font.BOLD, dis ? 40 : 20));
		label.setBounds(5, dis ? 156 : 78, dis ? 262 : 131, dis ? 28 : 14);
		panel_3.add(label);

		JLabel label_1 = new JLabel("Path:");
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, dis ? 40 : 20));
		label_1.setBounds(dis ? 300 : 150, 20, dis ? 166 : 83, dis ? 46 : 23);
		panel_3.add(label_1);

		lblPathRename = new JLabel("");
		lblPathRename.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC,
				dis ? 40 : 20));
		lblPathRename.setBackground(Color.WHITE);
		lblPathRename.setBounds(dis ? 300 : 150, 45, dis ? 1120 : 560, dis ? 60
				: 30);
		panel_3.add(lblPathRename);

		chckAuto = new JCheckBox("AutoIncrement With Name");
		chckAuto.setFont(new Font("Tahoma", Font.PLAIN, dis ? 40 : 20));
		chckAuto.setBackground(SystemColor.inactiveCaption);
		chckAuto.setBounds(15, dis ? 204 : 102, dis ? 534 : 267, dis ? 46 : 23);
		chckAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isIncrementWithoutName = false;
				isIncrement = chckAuto.isSelected();
				textPrefix.setEnabled(!isIncrement);
				chckbxAutoincrementWithoutName.setSelected(false);
			}
		});
		panel_3.add(chckAuto);

		chckbxAutoincrementWithoutName = new JCheckBox(
				"AutoIncrement Without Name");
		chckbxAutoincrementWithoutName.setFont(new Font("Tahoma", Font.PLAIN,
				dis ? 40 : 20));
		chckbxAutoincrementWithoutName
				.setBackground(SystemColor.inactiveCaption);
		chckbxAutoincrementWithoutName.setBounds(dis ? 838 : 419, dis ? 204
				: 102, dis ? 582 : 291, dis ? 46 : 23);
		chckbxAutoincrementWithoutName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isIncrement = false;
				isIncrementWithoutName = chckbxAutoincrementWithoutName
						.isSelected();
				textPrefix.setEnabled(true);
				chckAuto.setSelected(false);
			}
		});
		panel_3.add(chckbxAutoincrementWithoutName);

		textPrefix = new JTextField();
		textPrefix.setFont(new Font("Tahoma", Font.PLAIN, dis ? 40 : 20));
		textPrefix.setBounds(dis ? 188 : 94, dis ? 264 : 132, dis ? 1232 : 616,
				dis ? 62 : 31);
		panel_3.add(textPrefix);
		textPrefix.setColumns(10);

		JLabel lblNewLabel = new JLabel("Prefix :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, dis ? 40 : 20));
		lblNewLabel.setBounds(16, dis ? 270 : 135, dis ? 146 : 73, dis ? 50
				: 25);
		panel_3.add(lblNewLabel);

		buttonRunRenameFiles = new JButton("");
		setButtonOption(buttonRunRenameFiles, pathRunIcon, true, dis ? 644
				: 322, dis ? 340 : 170, 72, 72);
		buttonRunRenameFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readFilesPath();
			}
		});
		panel_3.add(buttonRunRenameFiles);

		btnClearRaname = new JButton("");
		setButtonOption(btnClearRaname, pathClear, false, 10, dis ? 410 : 205,
				32, 32);
		btnClearRaname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaRenameFile.setText("");
			}
		});
		panel_3.add(btnClearRaname);

		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, dis ? 36
				: 18));
		lblStatus.setBounds(dis ? 420 : 210, dis ? 504 : 252, dis ? 582 : 291,
				dis ? 44 : 22);
		panel_2.add(lblStatus);

		progressBar = new JProgressBar();
		progressBar.setBounds(10, dis ? 568 : 284, dis ? 1402 : 701, dis ? 64
				: 32);
		progressBar.setValue(0);
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		panel_2.add(progressBar);

		textAreaRenameFile = new TextArea();
		textAreaRenameFile
				.setFont(new Font("Tahoma", Font.PLAIN, dis ? 40 : 20));
		textAreaRenameFile.setEditable(false);
		textAreaRenameFile.setBounds(0, dis ? 644 : 322, dis ? 1440 : 720,
				dis ? 676 : 338);
		panel_2.add(textAreaRenameFile);

		frame.setIconImage(new ImageIcon(getClass().getResource(
				"/Image/icon.png")).getImage());
	}

	/**
	 * This method allows the user select a folder to search files
	 */
	private void selectPath() {
		chooser.setPreferredSize(new Dimension(dis ? 1000 : 600, dis ? 800
				: 400));
		Font font = new Font("Verdana", Font.PLAIN, dis ? 52 : 26);
		UIManager.put("FileChooser.listFont",
				new javax.swing.plaf.FontUIResource(font));
		SwingUtilities.updateComponentTreeUI(chooser);
		if (directory != null) {
			chooser.setCurrentDirectory(directory);
		} else {
			String user = System.getProperty("user.name");
			String path = "C:/Users/" + user + "/Downloads";
			chooser.setCurrentDirectory(new java.io.File(path));
		}
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();
			JLabel label = new JLabel(
					"<html>Are Your Sure of the Path?<br><br>" + path
							+ "<br></html> ");
			label.setFont(new Font("Arial", Font.BOLD, dis ? 40 : 20));
			int resp = JOptionPane.showConfirmDialog(frame, label,
					"Confirmation!", JOptionPane.YES_NO_OPTION);
			if (resp == 0) {
				directory = chooser.getCurrentDirectory();
				if (flagAction) {
					lblPathRename.setText(path);
				} else {
					lblPathValue.setText(path);
				}
			}
		}
	}

	/**
	 * This method allows show the content of the path selected by the user
	 */
	private void readFilesPath() {
		File folder = new File(chooser.getSelectedFile().getPath());
		File[] listOfFiles = folder.listFiles();
		conn = 0;
		int v = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				v++;
			}
		}
		int cont = 1;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (flagAction) {
					renameFile(listOfFiles[i]);
					int percent = (int) ((cont * 100.0f) / v);
					progressBar.setValue(percent);
					cont++;
				} else {
					lblPathValue.setText(chooser.getSelectedFile().getPath());
					String value = changeFileName(listOfFiles[i].getName());
					txtAreaNameFiles.append(value + "\n");
				}
			}
		}
		if (flagAction) {
			lblStatus.setText("Completed Successful!");
		}
	}

	/**
	 * This method allows rename the file
	 * 
	 * @param file
	 *            : to rename
	 */
	private void renameFile(File file) {
		String path = chooser.getSelectedFile().getPath();
		String name = changeName(file.getName());
		textAreaRenameFile.append(name + "\n");
		File newfile = new File(path + "/" + name);
		file.renameTo(newfile);
	}

	/**
	 * This method allows change the name according the selected of the user
	 * 
	 * @param name
	 *            : name of the file
	 */
	private String changeName(String name) {
		String value = "";
		conn++;
		// Removed the extension
		String ext = FilenameUtils.getExtension(name);
		name = name.replaceAll("." + ext, "");

		if (isIncrement) {
			value = name + "." + conn + "." + ext;
		} else if (isIncrementWithoutName) {
			value = textPrefix.getText() + conn + "." + ext;
		} else {
			String x = name;
			String number = "";
			int z = x.length();
			for (int y = z; y > 0; y--) {
				int index = y - 1;
				if (Character.isDigit(x.charAt(index))) {
					char c = x.charAt(index);
					number += c;
				} else {
					break;
				}
			}
			String prefix = textPrefix.getText();
			int w = number.length();
			String number1 = "";
			for (int y = w - 1; y >= 0; y--) {
				number1 += number.charAt(y);
			}
			value = prefix + "." + number1 + "." + ext;
		}
		return value;
	}

	/**
	 * This method allow replace and fix the name of the files
	 * 
	 * @param path
	 *            : path directory of the file
	 * @return String: value fixed of name of the
	 */
	private String changeFileName(String path) {
		// 1. Removed the extension
		String name = FilenameUtils.getExtension(path);
		String value = path.replaceAll("." + name, "");

		// 2. Then replace the dots (.) With spaces
		value = value.replaceAll("\\.", " ");
		value = value.replaceAll("_", " ");
		value = value.replaceAll("-", " ");

		// 3. Then it is called the method that detects the capital letters and
		// separates the name
		String x = value;
		String filename = "";
		String newvalue = x;
		int z = x.length();
		int count = 0;
		int sum = 0;
		for (int y = 0; y < z; y++) {
			int numberChart = count - 1;
			if (Character.isUpperCase(x.charAt(y))) {
				if (numberChart >= 0) {
					char c = x.charAt(numberChart);
					if (Character.isLowerCase(c)) {
						filename = newvalue.substring(0, y + sum) + ' '
								+ newvalue.substring(y + sum);
						newvalue = filename;
						sum++;
					}
				}
			}
			count++;
		}
		return newvalue;
	}

	/**
	 * This Method allows set the option to custom the button
	 * 
	 * @param jButton
	 *            :Button object to set the parameters
	 * @param pathIcon
	 *            :Path to set the icon of the button
	 * @param eneable
	 *            :Set a default condition
	 * @param x
	 *            :x-coordinate of this component
	 * @param y
	 *            :y-coordinate of this component
	 * @param width
	 *            :width of this component
	 * @param height
	 *            :height of this component
	 */
	public void setButtonOption(JButton jButton, String pathIcon,
			boolean eneable, int x, int y, int width, int height) {
		if (jButton != null) {
			if (pathIcon != null) {
				jButton.setIcon(new ImageIcon(FrameReaderAction.class
						.getResource(pathIcon)));
			}
			if (dis) {
				jButton.setBounds(x, y, width * 2, height * 2);
			} else {
				jButton.setBounds(x, y, width, height);
			}
			jButton.setBackground(SystemColor.inactiveCaption);
			jButton.setBorder(BorderFactory.createEmptyBorder());
			jButton.setContentAreaFilled(false);
			jButton.setFocusable(false);
			if (eneable) {
				jButton.setEnabled(directory != null);
			}
		}
	}

}