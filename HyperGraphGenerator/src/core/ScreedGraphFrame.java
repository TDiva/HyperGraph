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


public class ScreedGraphFrame extends JFrame {

	
private static final long serialVersionUID = 1L;
	
	//JLabel img;
	
	JTable matrix;
	
	JLabel indexLabel;
	int index;
	
	public ScreedGraphFrame(final HyperGraph graph) {
		index = 0;
		setLayout(new BorderLayout());
//		img = new JLabel("Hello!");
//		add(img,BorderLayout.CENTER);
		
		matrix = new JTable();
		JScrollPane scrl = new JScrollPane(matrix);
		add(scrl, BorderLayout.CENTER);
		Visualizator.fillMatrix(matrix, graph.getScreedGraphs().get(0));
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		add(btnPanel,BorderLayout.SOUTH);
		
		final BasicArrowButton leftBtn = new BasicArrowButton(BasicArrowButton.WEST);
		final BasicArrowButton rightBtn = new BasicArrowButton(BasicArrowButton.EAST);
		leftBtn.setEnabled(index > 0);
		rightBtn.setEnabled(index < graph.getScreedGraphs().size()-1);
		
		leftBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Visualizator.fillMatrix(matrix, graph.getScreedGraphs().get(--index));
				indexLabel.setText(String.format("%d", index));
				leftBtn.setEnabled(index > 0);
				rightBtn.setEnabled(index < graph.getScreedGraphs().size()-1);
			}
			
			
		});
		btnPanel.add(leftBtn);
		
		indexLabel = new JLabel("0");
		btnPanel.add(indexLabel);
		
		rightBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Visualizator.fillMatrix(matrix, graph.getScreedGraphs().get(++index));
				indexLabel.setText(String.format("%d", index));
				leftBtn.setEnabled(index > 0);
				rightBtn.setEnabled(index < graph.getScreedGraphs().size()-1);
			}
			
		});
		btnPanel.add(rightBtn);
		
		pack();
	}

}
