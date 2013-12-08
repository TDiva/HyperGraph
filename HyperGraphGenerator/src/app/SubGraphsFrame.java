package app;

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

import core.Visualizator;
import entities.HyperGraph;

/*
 *  Окно подграфов
 */
public class SubGraphsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JLabel img;

    JTable matrix;

    JLabel indexLabel;
    int index;

    public SubGraphsFrame(final HyperGraph graph) {
        // начинаем с первого подграфа
        index = 0;
        setLayout(new BorderLayout());

        // рисуем подграф в виде таблицы
        matrix = new JTable();
        JScrollPane scrl = new JScrollPane(matrix);
        add(scrl, BorderLayout.CENTER);
        Visualizator.fillMatrix(matrix, graph.getSubGraphs().get(0));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        add(btnPanel, BorderLayout.SOUTH);

        // создаем кнопки "вправо" и "влево"
        final BasicArrowButton leftBtn = new BasicArrowButton(BasicArrowButton.WEST);
        final BasicArrowButton rightBtn = new BasicArrowButton(BasicArrowButton.EAST);
        leftBtn.setEnabled(index > 0);
        rightBtn.setEnabled(index < graph.getSubGraphs().size() - 1);

        leftBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Visualizator.fillMatrix(matrix, graph.getSubGraphs().get(--index));
                indexLabel.setText(String.format("%d", index));
                leftBtn.setEnabled(index > 0);
                rightBtn.setEnabled(index < graph.getSubGraphs().size() - 1);
                img.setIcon(Visualizator.createImage(graph.getSubGraphs().get(index)));
            }

        });
        btnPanel.add(leftBtn);

        indexLabel = new JLabel("0");
        btnPanel.add(indexLabel);

        rightBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Visualizator.fillMatrix(matrix, graph.getSubGraphs().get(++index));
                indexLabel.setText(String.format("%d", index));
                leftBtn.setEnabled(index > 0);
                rightBtn.setEnabled(index < graph.getSubGraphs().size() - 1);
                img.setIcon(Visualizator.createImage(graph.getSubGraphs().get(index)));
            }

        });
        btnPanel.add(rightBtn);

        // загружаем картинку с графом
        img = new JLabel();
        img.setIcon(Visualizator.createImage(graph.getSubGraphs().get(index)));
        add(img, BorderLayout.EAST);

        pack();
    }

}
