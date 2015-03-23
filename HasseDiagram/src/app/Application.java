package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Point;
import core.Visualizator;
import entities.HyperGraph;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	JTextArea input;
	JLabel img;

	Point selected = null;

	private HyperGraph hyperGraph = new HyperGraph(0);

	public Application() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.WEST);

		JLabel lbl = new JLabel("Input hypergraph description:",
				SwingConstants.CENTER);
		inputPanel.add(lbl, BorderLayout.NORTH);

		input = new JTextArea();
		input.setLineWrap(true);
		input.setWrapStyleWord(true);
		input.setPreferredSize(new Dimension(300, 400));
		JScrollPane scrl = new JScrollPane(input);
		inputPanel.add(scrl, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		add(buttonPanel, BorderLayout.SOUTH);

		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = input.getText();
				hyperGraph = HyperGraph.readFromString(text);
				img.setIcon(Visualizator.createImageIcon(hyperGraph));
			}
		});
		buttonPanel.add(apply);

		buttonPanel.add(new JLabel(" or "));

		JButton open = new JButton("Open file");
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
				fc.setAcceptAllFileFilterUsed(false);

				int returnVal = fc.showOpenDialog(Application.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fc.getSelectedFile();
						hyperGraph = HyperGraph.readFromFile(file);
						input.setText(hyperGraph.toString());
						img.setIcon(Visualizator.createImageIcon(hyperGraph));
					} catch (Exception e) {
						String text = "Error while reading";
						if (e instanceof NumberFormatException) {
							text = "File content is incorrect";
						} else if (e instanceof FileNotFoundException) {
							text = "File not found";
						}
						JOptionPane.showMessageDialog(Application.this, text,
								"Warning", JOptionPane.WARNING_MESSAGE);
					}

				}
			}
		});
		buttonPanel.add(open);

		buttonPanel.add(new JLabel("|"));

		JButton saveGraph = new JButton("Save to text");
		saveGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String name = addFileExtIfNecessary(fc.getSelectedFile()
							.getPath(), ".txt");
					File file = new File(name);

					try {
						file.createNewFile();
						PrintWriter p = new PrintWriter(file);
						hyperGraph.printToFile(p);
						p.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Application.this,
								"WARNING.", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		buttonPanel.add(saveGraph);

		JButton saveImage = new JButton("Save image");
		saveImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String name = addFileExtIfNecessary(fc.getSelectedFile()
							.getPath(), ".jpg");
					File file = new File(name);

					try {
						file.createNewFile();
						PrintWriter p = new PrintWriter(file);
						ImageIO.write(Visualizator.getImage(hyperGraph), "jpg",
								file);

						p.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Application.this,
								"WARNING.", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});
		buttonPanel.add(saveImage);

		img = new JLabel();
		img.setIcon(Visualizator.getImageIcon(hyperGraph));
		JScrollPane scrl2 = new JScrollPane(img);
		scrl2.setPreferredSize(new Dimension(460, 430));
		add(scrl2, BorderLayout.EAST);

		img.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected != null) {
					int x = e.getX();
					int y = e.getY();
					selected.move(x, selected.y);
					selected = null;
					img.setIcon(Visualizator.getImageIcon(hyperGraph));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				selected = Visualizator.getPoint(x, y);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		pack();
	}

	public String addFileExtIfNecessary(String file, String ext) {
		if (file.lastIndexOf('.') == -1)
			file += ext;

		return file;
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
