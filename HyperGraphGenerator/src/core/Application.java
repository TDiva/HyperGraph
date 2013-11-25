package core;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	Generator gen;

	JTable matrix;

	public Application() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gen = new Generator();
		matrix = new JTable();
		setLayout(new BorderLayout());
		Visualizator.fillMatrix(matrix, gen.getGraph());
		JScrollPane scrl = new JScrollPane(matrix);
		add(scrl, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		add(buttonPanel, BorderLayout.SOUTH);

		JButton genBtn = new JButton("Generate");
		genBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gen.generate();
				Visualizator.fillMatrix(matrix, gen.getGraph());
			}

		});
		buttonPanel.add(genBtn);

		JButton subBtn = new JButton("SubGraphs");
		subBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SubGraphsFrame frame = new SubGraphsFrame(gen.getGraph());
				frame.setVisible(true);
			}

		});
		buttonPanel.add(subBtn);

		JButton screedBtn = new JButton("Screed Graphs");
		screedBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ScreedGraphFrame frame = new ScreedGraphFrame(gen.getGraph());
				frame.setVisible(true);
			}

		});
		buttonPanel.add(screedBtn);

		pack();
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
