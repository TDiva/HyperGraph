package core;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicArrowButton;

public class SubGraphsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//JLabel img;
	
	JTable matrix;
	
	JLabel indexLabel;
	int index;
	
	public SubGraphsFrame(final HyperGraph graph) {
		index = 0;
		setLayout(new BorderLayout());
//		img = new JLabel("Hello!");
//		add(img,BorderLayout.CENTER);
		
		matrix = new JTable();
		JScrollPane scrl = new JScrollPane(matrix);
		add(scrl, BorderLayout.CENTER);
		Visualizator.fillMatrix(matrix, graph.getSubGraphs().get(0));
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		add(btnPanel,BorderLayout.SOUTH);
		
		BasicArrowButton leftBtn = new BasicArrowButton(BasicArrowButton.WEST);
		leftBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Visualizator.fillMatrix(matrix, graph.getSubGraphs().get(++index));
				indexLabel.setText(String.format("%d", index));
			}
			
		});
		btnPanel.add(leftBtn);
		
		indexLabel = new JLabel("0");
		btnPanel.add(indexLabel);
		
		BasicArrowButton rightBtn = new BasicArrowButton(BasicArrowButton.EAST);
		rightBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Visualizator.fillMatrix(matrix, graph.getSubGraphs().get(++index));
				indexLabel.setText(String.format("%d", index));
				
			}
			
		});
		btnPanel.add(rightBtn);
		
		pack();
	}

}
