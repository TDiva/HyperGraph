package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import core.Visualizator;
import entities.HyperGraph;

/* ќсновной класс приложени€
 * Ќаследуетс€ от JFrame, то есть представл€ет собой основное окно приложени€.
 * 
 */
public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	JTextArea input;
	JLabel img;

	private static Integer DEFAULT_NUMBER_OF_VERTEXS = 5;

	private HyperGraph hyperGraph = new HyperGraph(DEFAULT_NUMBER_OF_VERTEXS);

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
				img.setIcon(Visualizator.createImage(hyperGraph));
			}
		});
		buttonPanel.add(apply);

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
					input.setText(hyperGraph.toString());
					img.setIcon(Visualizator.createImage(hyperGraph));
				}
			}
		});
		buttonPanel.add(open);

		img = new JLabel();
		img.setIcon(Visualizator.createImage(hyperGraph));
		JScrollPane scrl2 = new JScrollPane(img);
		scrl2.setPreferredSize(new Dimension(460, 430));
		add(scrl2, BorderLayout.EAST);

		pack();
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
