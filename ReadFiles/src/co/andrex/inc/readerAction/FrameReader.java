package co.andrex.inc.readerAction;

import java.awt.Color;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

/**
 * This Class have the logic to the read and write the files of a path selected
 * by a user
 * 
 * @author Andrex.Gomez
 * 
 */
@SuppressWarnings("serial")
public class FrameReader extends JPanel {

	private JFrame frame;

	private JLabel lblPathValue;
	private JFileChooser chooser;
	private TextArea txtAreaNameFiles;

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
		frame.setBounds(100, 100, 431, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		String iconfilePath = this.getClass().getResource("selectFolder64.png")
				.getFile();

		Panel panel = new Panel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 415, 87);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnSelectFolder = new JButton("");
		btnSelectFolder.setBounds(10, 11, 65, 53);
		panel.add(btnSelectFolder);
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectPath();
			}
		});
		btnSelectFolder.setIcon(new ImageIcon(iconfilePath));
		btnSelectFolder.setBorder(BorderFactory.createEmptyBorder());
		btnSelectFolder.setContentAreaFilled(false);
		btnSelectFolder.setFocusable(false);

		JLabel lblSelectFolder = new JLabel("Select Folder");
		lblSelectFolder.setBounds(10, 64, 89, 14);
		panel.add(lblSelectFolder);
		lblSelectFolder.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(99, 11, 39, 14);
		panel.add(lblPath);
		lblPath.setFont(new Font("Trebuchet MS", Font.BOLD, 12));

		lblPathValue = new JLabel("");
		lblPathValue.setBounds(102, 31, 286, 14);
		panel.add(lblPathValue);
		lblPathValue.setBackground(Color.WHITE);

		txtAreaNameFiles = new TextArea();
		txtAreaNameFiles.setBounds(0, 86, 415, 378);
		frame.getContentPane().add(txtAreaNameFiles);
		frame.setIconImage(new ImageIcon(getClass().getResource("icon.png"))
				.getImage());
	}

	/**
	 * This method allows the user select a folder to search files
	 */
	private void selectPath() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			lblPathValue.setText(chooser.getSelectedFile().getPath());
			readFilesPath();
		}
	}

	/**
	 * This method allows show the content of the path selected by the user
	 */
	private void readFilesPath() {
		File folder = new File(chooser.getSelectedFile().getPath());
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String value = changeFileName(listOfFiles[i].getName());
				txtAreaNameFiles.append(value + "\n");
			}
		}
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

		// 2. Desopues remplazamos los puntos (.) por espacios en blanco
		value = value.replaceAll("\\.", " ");
		value = value.replaceAll("_", " ");
		value = value.replaceAll("-", " ");
		return value;
	}
}