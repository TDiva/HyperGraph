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

// класс основного приложения
public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// размеры окна
	public static final int WIDTH = 900;
	public static final int HEIGHT = 650;

	// место для введения данных
	JTextPane input;
	// для картинки
	JLabel img;

	// выбранная точка (чтобы перемещать их на картинке)
	Point selected = null;

	// рассматриваемый гиперграф, диаграмма которого рисуется
	private HyperGraph hyperGraph = new HyperGraph(0);

	// конструктор
	public Application() {
//		так надо (чтобы при закрытии окошка программа закрывалась
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// расположения объектов в окошке
		setLayout(new BorderLayout());

//		панелька для ввода данных
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.WEST);

//		лейбл
		JLabel lbl = new JLabel("Input hypergraph description:",
				SwingConstants.CENTER);
		inputPanel.add(lbl, BorderLayout.NORTH);

//		поле ввода данных с возможностью скролла
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
//				читаем текст, создаем граф, проверяем на ошибки, рисуем картинку
				hyperGraph = HyperGraph.readFromString(text);
				hyperGraph.outputErrors(input);

				img.setIcon(Visualizator.createImageIcon(hyperGraph));
			}
		});
		buttonPanel.add(apply);

		buttonPanel.add(new JLabel(" or "));

		JButton open = new JButton("Open file");
		
//		что случиться по нажатию кнопки
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
				fc.setAcceptAllFileFilterUsed(false);

				int returnVal = fc.showOpenDialog(Application.this);

//				если выбран файл, загружаем граф и рисуем картинку
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

//		сохранение в текст (потом можно открыть программой)
		JButton saveGraph = new JButton("Save to text");
//		нажатие кнопки
		saveGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				выбор файла
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

//				нажали окей:
				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					добавляем тхт расширение, если не указано другое
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

//		сохраняем граф в картинку
		JButton saveImage = new JButton("Save image");
		saveImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				выбрать файл
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

//				нажали ок
				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					добавляем расширение jpg если не указано другое
					String name = addFileExtIfNecessary(fc.getSelectedFile()
							.getPath(), ".jpg");
					File file = new File(name);

					try {
						file.createNewFile();
						PrintWriter p = new PrintWriter(file);
						ImageIO.write(Visualizator.getImage(hyperGraph), "jpg",
								file);

						p.close();
//						если прозошла какая-то ошибка
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Application.this,
								"WARNING.", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});
		buttonPanel.add(saveImage);
		
//		сохранение в текст диаграммы
		JButton saveDiagram = new JButton("Diagram to text");
		saveDiagram.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Application.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					добавляем тхт расширение, если не указано другое
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


//		окошко для рисования
		img = new JLabel();
		img.setIcon(Visualizator.getImageIcon(hyperGraph));
//		чтобы могло скролиться
		JScrollPane scrl2 = new JScrollPane(img);
		scrl2.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(scrl2, BorderLayout.EAST);

//		слушаем события мышки
		img.addMouseListener(new MouseListener() {

//			отпустили кнопку мышки
			@Override
			public void mouseReleased(MouseEvent e) {
//				если была выбрана вершина диаграммы
				if (selected != null) {
//					по левой кнопки мыши перемещаем вершину
					if (SwingUtilities.isLeftMouseButton(e)) {
						selected.move(e.getX(), selected.y);
						img.setIcon(Visualizator.getImageIcon(hyperGraph));
					} else if (SwingUtilities.isRightMouseButton(e)) {
//						по правой показываем название
						Visualizator.showPoint(e.getX(), e.getY());
						img.setIcon(Visualizator.getImageIcon(hyperGraph));
					}
				}
				selected = null;
			}

//			нажали кнопку мышки
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

//	если выбран файл без расширения, добавить его
	public String addFileExtIfNecessary(String file, String ext) {
		if (file.lastIndexOf('.') == -1)
			file += ext;

		return file;
	}

//	точк входа в программу
	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
