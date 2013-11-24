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

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	Generator gen;

	JTable matrix;

	public Application() {
		setSize(700,500);
		gen = new Generator();
		matrix = new JTable();
		matrix.setSize(700, 450);
		setLayout(new BorderLayout());
		Visualizator.fillMatrix(matrix, gen.getGraph());
		JScrollPane scrl = new JScrollPane(matrix);
		add(scrl,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		add(buttonPanel,BorderLayout.SOUTH);
		
		
		JButton genBtn = new JButton("Generate");
		genBtn.addActionListener(new ActionListener () {

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
				SubGraphsFrame frame = new SubGraphsFrame();
				frame.setVisible(true);
			}
			
		});
		buttonPanel.add(subBtn);
		
		JButton pullBtn = new JButton("SubGraphs");
		pullBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: pullGraph Dialog
			}
			
		});
		buttonPanel.add(pullBtn);
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
		
		/*Generator gen = new Generator();
		gen.generate();
		gen.getGraph().print();
		System.out.println();
		for (HyperGraph g: gen.getGraph().getSubGraphs()) {
			g.print();
			System.out.println();
		}*/
	}

}
