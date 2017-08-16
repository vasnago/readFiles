package co.andrex.inc.readerAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
public class FrameReader {

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameReader window = new FrameReader();
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
	public FrameReader() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("FilerX Reader V.2.1");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 731, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 726, 711);
		frame.getContentPane().add(tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Search Files", null, panel_1, null);

		// Settear el alto y ancho del tab
		JLabel lab = new JLabel();
		lab.setText("Search Files");
		lab.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lab.setPreferredSize(new Dimension(150, 40));
		tabbedPane.setTabComponentAt(0, lab);
		panel_1.setLayout(null);

		txtAreaNameFiles = new TextArea();
		txtAreaNameFiles.setEditable(false);
		txtAreaNameFiles.setBounds(0, 165, 721, 495);
		panel_1.add(txtAreaNameFiles);

		Panel panel = new Panel();
		panel.setBounds(0, 0, 721, 166);
		panel_1.add(panel);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(null);

		JButton btnSelectFolder = new JButton("");
		btnSelectFolder.setIcon(new ImageIcon(FrameReader.class
				.getResource("/Image/selectFolder64.png")));
		btnSelectFolder.setBackground(SystemColor.inactiveCaption);
		btnSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSelectFolder.setForeground(Color.BLACK);
		btnSelectFolder.setBounds(34, 20, 65, 55);
		btnSelectFolder.setBorder(BorderFactory.createEmptyBorder());
		btnSelectFolder.setContentAreaFilled(false);
		btnSelectFolder.setFocusable(false);
		panel.add(btnSelectFolder);
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtAreaNameFiles.setText("");
				flagAction = false;
				selectPath();
				buttonRunSearchFile.setEnabled(directory != null);
			}
		});

		JLabel lblSelectFolder = new JLabel("Select Folder");
		lblSelectFolder.setBounds(5, 78, 137, 14);
		panel.add(lblSelectFolder);
		lblSelectFolder.setFont(new Font("Trebuchet MS", Font.BOLD, 20));

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(150, 20, 90, 24);
		panel.add(lblPath);
		lblPath.setFont(new Font("Trebuchet MS", Font.BOLD, 20));

		lblPathValue = new JLabel("");
		lblPathValue.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblPathValue.setBounds(150, 47, 529, 28);
		panel.add(lblPathValue);
		lblPathValue.setBackground(Color.WHITE);

		buttonRunSearchFile = new JButton("");
		buttonRunSearchFile.setBackground(SystemColor.inactiveCaption);
		buttonRunSearchFile.setIcon(new ImageIcon(FrameReader.class
				.getResource("/Image/runIconX72.png")));
		buttonRunSearchFile.setBounds(322, 86, 72, 72);
		buttonRunSearchFile.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonRunSearchFile.setForeground(Color.BLACK);
		buttonRunSearchFile.setBorder(BorderFactory.createEmptyBorder());
		buttonRunSearchFile.setContentAreaFilled(false);
		buttonRunSearchFile.setFocusable(false);
		buttonRunSearchFile.setEnabled(directory != null);
		buttonRunSearchFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readFilesPath();
			}
		});
		panel.add(buttonRunSearchFile);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Rename Files", null, panel_2, null);
		JLabel lab2 = new JLabel();
		lab2.setText("Rename Files");
		lab2.setPreferredSize(new Dimension(150, 40));
		lab2.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		tabbedPane.setTabComponentAt(1, lab2);
		panel_2.setLayout(null);

		Panel panel_3 = new Panel();
		panel_3.setLayout(null);
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_3.setBounds(0, 0, 720, 246);
		panel_2.add(panel_3);

		JButton btnRename = new JButton("");
		btnRename.setIcon(new ImageIcon(FrameReader.class
				.getResource("/Image/selectFolder64.png")));
		btnRename.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRename.setBackground(SystemColor.inactiveCaption);
		btnRename.setBounds(34, 20, 65, 55);
		btnRename.setContentAreaFilled(false);
		btnRename.setBorder(BorderFactory.createEmptyBorder());
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
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		label.setBounds(5, 78, 131, 14);
		panel_3.add(label);

		JLabel label_1 = new JLabel("Path:");
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		label_1.setBounds(150, 20, 83, 23);
		panel_3.add(label_1);

		lblPathRename = new JLabel("");
		lblPathRename.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblPathRename.setBackground(Color.WHITE);
		lblPathRename.setBounds(150, 45, 560, 30);
		panel_3.add(lblPathRename);

		chckAuto = new JCheckBox("AutoIncrement With Name");
		chckAuto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chckAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isIncrementWithoutName = false;
				isIncrement = chckAuto.isSelected();
				textPrefix.setEnabled(!isIncrement);
				chckbxAutoincrementWithoutName.setSelected(false);
			}
		});
		chckAuto.setBackground(SystemColor.inactiveCaption);
		chckAuto.setBounds(15, 102, 267, 23);
		panel_3.add(chckAuto);

		textPrefix = new JTextField();
		textPrefix.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPrefix.setBounds(94, 132, 616, 31);
		panel_3.add(textPrefix);
		textPrefix.setColumns(10);

		JLabel lblNewLabel = new JLabel("Prefix :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(16, 135, 73, 25);
		panel_3.add(lblNewLabel);

		chckbxAutoincrementWithoutName = new JCheckBox(
				"AutoIncrement Without Name");
		chckbxAutoincrementWithoutName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isIncrement = false;
				isIncrementWithoutName = chckbxAutoincrementWithoutName
						.isSelected();
				textPrefix.setEnabled(!isIncrementWithoutName);
				chckAuto.setSelected(false);
			}
		});
		chckbxAutoincrementWithoutName.setFont(new Font("Tahoma", Font.PLAIN,
				20));
		chckbxAutoincrementWithoutName
				.setBackground(SystemColor.inactiveCaption);
		chckbxAutoincrementWithoutName.setBounds(419, 102, 291, 23);
		panel_3.add(chckbxAutoincrementWithoutName);

		buttonRunRenameFiles = new JButton("");
		buttonRunRenameFiles.setIcon(new ImageIcon(FrameReader.class
				.getResource("/Image/runIconX72.png")));
		buttonRunRenameFiles.setBounds(322, 170, 72, 72);
		buttonRunRenameFiles.setBackground(SystemColor.inactiveCaption);
		buttonRunRenameFiles.setBorder(BorderFactory.createEmptyBorder());
		buttonRunRenameFiles.setContentAreaFilled(false);
		buttonRunRenameFiles.setFocusable(false);
		buttonRunRenameFiles.setEnabled(directory != null);
		buttonRunRenameFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readFilesPath();
			}
		});
		panel_3.add(buttonRunRenameFiles);

		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblStatus.setBounds(210, 252, 291, 22);
		panel_2.add(lblStatus);

		progressBar = new JProgressBar();
		progressBar.setBounds(10, 284, 701, 32);
		progressBar.setValue(0);
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		panel_2.add(progressBar);

		textAreaRenameFile = new TextArea();
		textAreaRenameFile.setEditable(false);
		textAreaRenameFile.setBounds(0, 322, 720, 338);
		panel_2.add(textAreaRenameFile);

		frame.setIconImage(new ImageIcon(getClass().getResource(
				"/Image/icon.png")).getImage());
	}

	/**
	 * This method allows the user select a folder to search files
	 */
	private void selectPath() {
		chooser.setPreferredSize(new Dimension(800, 600));
		Font font = new Font("Verdana", Font.PLAIN, 26);
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
			label.setFont(new Font("Arial", Font.BOLD, 20));
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
		// Quitamos la extension
		String ext = FilenameUtils.getExtension(name);
		name = name.replaceAll("." + ext, "");
		if (isIncrement) {
			value = name + "." + conn + "." + ext;
		} else if (isIncrementWithoutName) {
			value = conn + "." + ext;
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
		// 1. Primero quitamos la extension
		String name = FilenameUtils.getExtension(path);
		String value = path.replaceAll("." + name, "");

		// 2. Despues remplazamos los puntos (.) por espacios en blanco
		value = value.replaceAll("\\.", " ");
		value = value.replaceAll("_", " ");
		value = value.replaceAll("-", " ");

		// 3. Despues se llama el metodo que detecta la mayusculas y separa el
		// nombre.
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
}