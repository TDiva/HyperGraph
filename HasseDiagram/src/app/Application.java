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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Point;
import core.Visualizator;
import entities.HyperGraph;

// ����� ��������� ����������
public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// ������� ����
	public static final int WIDTH = 900;
	public static final int HEIGHT = 650;

	// ����� ��� �������� ������
	JTextPane input;
	// ��� ��������
	JLabel img;

	// ��������� ����� (����� ���������� �� �� ��������)
	Point selected = null;

	// ��������������� ���������, ��������� �������� ��������
	private HyperGraph hyperGraph = new HyperGraph(0);

	// �����������
	public Application() {
//		��� ���� (����� ��� �������� ������ ��������� �����������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ������������ �������� � ������
		setLayout(new BorderLayout());

//		�������� ��� ����� ������
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.WEST);

//		�����
		JLabel lbl = new JLabel("Input hypergraph description:",
				SwingConstants.CENTER);
		inputPanel.add(lbl, BorderLayout.NORTH);

//		���� ����� ������ � ������������ �������
		input = new JTextPane();
		// input.setLineWrap(true);
		// input.setWrapStyleWord(true);
		input.setPreferredSize(new Dimension(350, HEIGHT));
		JScrollPane scrl = new JScrollPane(input);
		inputPanel.add(scrl, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		add(buttonPanel, BorderLayout.SOUTH);

		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = input.getText();
//				������ �����, ������� ����, ��������� �� ������, ������ ��������
				hyperGraph = HyperGraph.readFromString(text);
				hyperGraph.outputErrors(input);

				img.setIcon(Visualizator.createImageIcon(hyperGraph));
			}
		});
		buttonPanel.add(apply);

		buttonPanel.add(new JLabel(" or "));

		JButton open = new JButton("Open file");
		
//		��� ��������� �� ������� ������
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
				fc.setAcceptAllFileFilterUsed(false);

				int returnVal = fc.showOpenDialog(Application.this);

//				���� ������ ����, ��������� ���� � ������ ��������
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

//		���������� � ����� (����� ����� ������� ����������)
		JButton saveGraph = new JButton("Save to text");
//		������� ������
		saveGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				����� �����
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

//				������ ����:
				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					��������� ��� ����������, ���� �� ������� ������
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

//		��������� ���� � ��������
		JButton saveImage = new JButton("Save image");
		saveImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				������� ����
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

//				������ ��
				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					��������� ���������� jpg ���� �� ������� ������
					String name = addFileExtIfNecessary(fc.getSelectedFile()
							.getPath(), ".jpg");
					File file = new File(name);

					try {
						file.createNewFile();
						PrintWriter p = new PrintWriter(file);
						ImageIO.write(Visualizator.getImage(hyperGraph), "jpg",
								file);

						p.close();
//						���� �������� �����-�� ������
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Application.this,
								"WARNING.", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});
		buttonPanel.add(saveImage);
		
//		���������� � ����� ���������
		JButton saveDiagram = new JButton("Diagram to text");
		saveDiagram.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					��������� ��� ����������, ���� �� ������� ������
					String name = addFileExtIfNecessary(fc.getSelectedFile()
							.getPath(), ".txt");
					File file = new File(name);

					try {
						file.createNewFile();
						PrintWriter p = new PrintWriter(file);
						p.println(Visualizator.getHasseDiagramText(hyperGraph));
						p.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Application.this,
								"WARNING.", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		buttonPanel.add(saveDiagram);


//		������ ��� ���������
		img = new JLabel();
		img.setIcon(Visualizator.getImageIcon(hyperGraph));
//		����� ����� ����������
		JScrollPane scrl2 = new JScrollPane(img);
		scrl2.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(scrl2, BorderLayout.EAST);

//		������� ������� �����
		img.addMouseListener(new MouseListener() {

//			��������� ������ �����
			@Override
			public void mouseReleased(MouseEvent e) {
//				���� ���� ������� ������� ���������
				if (selected != null) {
//					�� ����� ������ ���� ���������� �������
					if (SwingUtilities.isLeftMouseButton(e)) {
						selected.move(e.getX(), selected.y);
						img.setIcon(Visualizator.getImageIcon(hyperGraph));
					} else if (SwingUtilities.isRightMouseButton(e)) {
//						�� ������ ���������� ��������
						Visualizator.showPoint(e.getX(), e.getY());
						img.setIcon(Visualizator.getImageIcon(hyperGraph));
					}
				}
				selected = null;
			}

//			������ ������ �����
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

//	���� ������ ���� ��� ����������, �������� ���
	public String addFileExtIfNecessary(String file, String ext) {
		if (file.lastIndexOf('.') == -1)
			file += ext;

		return file;
	}

//	���� ����� � ���������
	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
