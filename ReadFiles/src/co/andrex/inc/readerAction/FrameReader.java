package co.andrex.inc.readerAction;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

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

import org.apache.commons.io.FilenameUtils;

/**
 * This Class have the logic to the read and write the files of a path selected
 * by a user
 * 
 * @author Andrex.Gomez
 * 
 */
@SuppressWarnings("serial")
public class FrameReader extends JFrame implements ComponentListener {

	private JFrame frame;

	private JLabel lblPathValue;
	private JLabel lblPathRename;
	private JLabel lblStatus;
	private JFileChooser chooser = new JFileChooser();
	private JProgressBar progressBar;
	private JTabbedPane tabbedPane;
	private JTextField textPrefix;
	private JCheckBox chckAuto;
	private TextArea txtAreaNameFiles;
	private TextArea textAreaRenameFile;
	private File directory;
	private boolean flagAction = false;
	private boolean flag;
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
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 700, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 694, 752);
		frame.getContentPane().add(tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Search Files", null, panel_1, null);
		panel_1.setLayout(null);

		txtAreaNameFiles = new TextArea();
		txtAreaNameFiles.setEditable(false);
		txtAreaNameFiles.setBounds(0, 97, 689, 627);
		panel_1.add(txtAreaNameFiles);

		Panel panel = new Panel();
		panel.setBounds(0, 0, 689, 97);
		panel_1.add(panel);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(null);

		JButton btnSelectFolder = new JButton("");
		btnSelectFolder.setIcon(new ImageIcon(FrameReader.class
				.getResource("/Image/selectFolder64.png")));
		btnSelectFolder.setBackground(SystemColor.inactiveCaption);
		btnSelectFolder.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSelectFolder.setForeground(Color.BLACK);
		btnSelectFolder.setBounds(20, 20, 65, 55);
		panel.add(btnSelectFolder);
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtAreaNameFiles.setText("");
				flagAction = false;
				selectPath();
			}
		});

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(150, 20, 39, 14);
		panel.add(lblPath);
		lblPath.setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		lblPathValue = new JLabel("");
		lblPathValue.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblPathValue.setBounds(150, 39, 426, 14);
		panel.add(lblPathValue);
		lblPathValue.setBackground(Color.WHITE);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Rename Files", null, panel_2, null);
		panel_2.setLayout(null);

		Panel panel_3 = new Panel();
		panel_3.setLayout(null);
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_3.setBounds(0, 0, 689, 174);
		panel_2.add(panel_3);

		JButton btnRename = new JButton("");
		btnRename.setIcon(new ImageIcon(FrameReader.class
				.getResource("/Image/selectFolder64.png")));
		btnRename.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRename.setBackground(SystemColor.inactiveCaption);
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaRenameFile.setText("");
				flagAction = true;
				selectPath();
			}
		});

		btnRename.setBounds(20, 20, 65, 55);
		panel_3.add(btnRename);

		JLabel label_1 = new JLabel("Path:");
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		label_1.setBounds(150, 20, 39, 14);
		panel_3.add(label_1);

		lblPathRename = new JLabel("");
		lblPathRename.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblPathRename.setBackground(Color.WHITE);
		lblPathRename.setBounds(150, 45, 389, 14);
		panel_3.add(lblPathRename);

		chckAuto = new JCheckBox("Auto-Increment");
		chckAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				flag = chckAuto.isSelected();
				textPrefix.setEnabled(!flag);
			}
		});
		chckAuto.setBackground(SystemColor.inactiveCaption);
		chckAuto.setBounds(16, 98, 117, 23);
		panel_3.add(chckAuto);

		textPrefix = new JTextField();
		textPrefix.setBounds(89, 130, 590, 31);
		panel_3.add(textPrefix);
		textPrefix.setColumns(10);

		JLabel lblNewLabel = new JLabel("Prefix :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(16, 136, 65, 14);
		panel_3.add(lblNewLabel);

		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblStatus.setBounds(209, 180, 246, 14);
		panel_2.add(lblStatus);

		progressBar = new JProgressBar();
		progressBar.setBounds(10, 203, 669, 32);
		progressBar.setValue(0);
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		panel_2.add(progressBar);

		textAreaRenameFile = new TextArea();
		textAreaRenameFile.setBounds(0, 241, 689, 483);
		panel_2.add(textAreaRenameFile);

		frame.setIconImage(new ImageIcon(getClass().getResource(
				"/Image/icon.png")).getImage());
	}

	/**
	 * This method allows the user select a folder to search files
	 */
	private void selectPath() {
		if (directory != null) {
			chooser.setCurrentDirectory(directory);
		} else {
			chooser.setCurrentDirectory(new java.io.File("."));
		}
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();
			int resp = JOptionPane.showConfirmDialog(null,
					"¿Are Your Sure of the Path?\n" + path, "Confirmation!",
					JOptionPane.YES_NO_OPTION);
			if (resp == 0) {
				readFilesPath();
				directory = chooser.getCurrentDirectory();
			}
		}
	}

	/**
	 * This method allows show the content of the path selected by the user
	 */
	private void readFilesPath() {
		File folder = new File(chooser.getSelectedFile().getPath());
		File[] listOfFiles = folder.listFiles();
		String path = chooser.getSelectedFile().getPath();
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
			lblPathRename.setText(path);
		} else {
			lblPathValue.setText(path);
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
		if (flag) {
			value = name + "." + conn + "." + ext;
		} else {
			String x = name;
			String number = "";
			int z = x.length();
			for (int y = z - 1; y > 0; y--) {
				if (Character.isDigit(x.charAt(y))) {
					char c = x.charAt(y);
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

	@Override
	public void componentResized(ComponentEvent paramComponentEvent) {

	}

	@Override
	public void componentMoved(ComponentEvent paramComponentEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent paramComponentEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent paramComponentEvent) {
		// TODO Auto-generated method stub

	}
}