package core;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;

	Generator gen;

	JTable matrix;

	public Application() {
		gen = new Generator();
		matrix = new JTable();
		setLayout(new BorderLayout());
		Visualizator.fillMatrix(matrix, gen.getGraph());
		JScrollPane scrl = new JScrollPane(matrix);
		add(scrl,BorderLayout.CENTER);
		
		JButton genBtn = new JButton("Generate");
		genBtn.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gen.generate();
				Visualizator.fillMatrix(matrix, gen.getGraph());
			}
			
		});
		add(genBtn,BorderLayout.WEST);
		
		
		pack();
	}

	

	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
