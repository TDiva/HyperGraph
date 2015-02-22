package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import core.Visualizator;
import entities.HyperGraph;

/* ќсновной класс приложени€
 * Ќаследуетс€ от JFrame, то есть представл€ет собой основное окно приложени€.
 * 
 */
public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	JTable matrix;
	JLabel img;

	private static Integer DEFAULT_NUMBER_OF_VERTEXS = 5;
	private static Integer DEFAULT_NUMBER_OF_EDGES = 5;

	private HyperGraph hyperGraph = new HyperGraph(DEFAULT_NUMBER_OF_VERTEXS,
			DEFAULT_NUMBER_OF_EDGES);

	// конструктор, в нем создаетс€ основное окно и запускаетс€ генераци€
	public Application() {
		// пусть приложение завершает работу, если окно закрыли.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// генерируем граф и рисуем его в виде таблицы
		matrix = new JTable();
		setLayout(new BorderLayout());
		matrix.setAutoCreateColumnsFromModel(true);
		updateMatrix();

		JScrollPane scrl = new JScrollPane(matrix);
		add(scrl, BorderLayout.CENTER);

		// создаем панель с кнопками
		JPanel buttonPanel = new JPanel(new FlowLayout());
		add(buttonPanel, BorderLayout.SOUTH);

		JLabel inputLabel = new JLabel("Enter dimension: ");
		buttonPanel.add(inputLabel);

		KeyListener numeric = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		};

		final JTextField vertexs = new JTextField(
				DEFAULT_NUMBER_OF_VERTEXS.toString());
		vertexs.setPreferredSize(new Dimension(50, 20));
		vertexs.addKeyListener(numeric);
		buttonPanel.add(vertexs);

		final JTextField edges = new JTextField(
				DEFAULT_NUMBER_OF_EDGES.toString());
		edges.setPreferredSize(new Dimension(50, 20));
		edges.addKeyListener(numeric);
		buttonPanel.add(edges);

		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int v = Integer.valueOf(vertexs.getText());
				int r = Integer.valueOf(edges.getText());
				hyperGraph = new HyperGraph(v, r);
				updateMatrix();
			}
		});
		buttonPanel.add(apply);

		buttonPanel.add(new JLabel(" and "));

		JButton draw = new JButton("Draw");
		draw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int n = Integer.valueOf(vertexs.getText());
				int m = Integer.valueOf(edges.getText());

				hyperGraph = new HyperGraph(n);
				((AbstractTableModel) matrix.getModel()).fireTableDataChanged();

				for (int i = 0; i < m; i++) {
					List<Integer> edge = new ArrayList<>();
					for (int j = 0; j < n; j++) {
						String o = matrix.getModel().getValueAt(i, j)
								.toString();
						int c = Integer.valueOf(o);

						if (c > 0) {
							edge.add(j);
						}
					}
					hyperGraph.addEdge(edge);
				}

				img.setIcon(Visualizator.createImage(hyperGraph));
			}
		});
		buttonPanel.add(draw);

		buttonPanel.add(new JLabel(" or "));

		JButton open = new JButton("Open file");
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				// Handle open button action.
				int returnVal = fc.showOpenDialog(Application.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					hyperGraph = HyperGraph.readFromFile(file);
					vertexs.setText(String.valueOf(hyperGraph.getV()));
					edges.setText(String.valueOf(hyperGraph.getR()));
					updateMatrix();
					
					img.setIcon(Visualizator.createImage(hyperGraph));
				}
			}
		});
		buttonPanel.add(open);

		// загрузим в правую часть окна сгенерированное изображение графа
		img = new JLabel();
		img.setIcon(Visualizator.createImage(hyperGraph));
		add(img, BorderLayout.EAST);

		pack();
	}

	private void updateMatrix() {
		String[] headers = new String[hyperGraph.getV()];

		for (int i = 0; i < hyperGraph.getV(); i++) {
			headers[i] = (new Integer(i + 1)).toString();

		}
		matrix.setModel(new DefaultTableModel(hyperGraph.toArray(), headers));
		((AbstractTableModel) matrix.getModel()).fireTableDataChanged();
	}

	// точка входа программы. —оздает окно и показывает его на экране
	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
