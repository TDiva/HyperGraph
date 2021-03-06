package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicArrowButton;

import core.Visualizator;
import entities.HyperGraph;
import entities.ScreedGraph;

/*
 * ���� ������ �����
 */
public class ScreedGraphFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    HyperGraph graph;

    JLabel img;

    JTable matrix;
    JList<String> list;

    JLabel indexLabel;
    int index;

    public ScreedGraphFrame(final HyperGraph graph) {
        // ������ � ������ ������ �����
        this.graph = graph;
        index = 0;
        setLayout(new BorderLayout());

        // ������������ ������� � ���� �������
        matrix = new JTable();
        JScrollPane scrl = new JScrollPane(matrix);
        add(scrl, BorderLayout.CENTER);
        Visualizator.fillMatrix(matrix, graph.getScreedGraphs().get(0));

        // �������� ������ � ��������
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        add(btnPanel, BorderLayout.SOUTH);

        // ������ "������" � "�����"
        final BasicArrowButton leftBtn = new BasicArrowButton(BasicArrowButton.WEST);
        final BasicArrowButton rightBtn = new BasicArrowButton(BasicArrowButton.EAST);

        // ��������, ����� �� ������������� ������ � �����
        leftBtn.setEnabled(index > 0);
        rightBtn.setEnabled(index < graph.getScreedGraphs().size() - 1);

        // ��������� ������� ������
        leftBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Visualizator.fillMatrix(matrix, graph.getScreedGraphs().get(--index));
                indexLabel.setText(String.format("%d", index));
                leftBtn.setEnabled(index > 0);
                rightBtn.setEnabled(index < graph.getScreedGraphs().size() - 1);
                setListOfCScreeds();
                img.setIcon(Visualizator.createImage(graph.getScreedGraphs().get(index)));
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
                rightBtn.setEnabled(index < graph.getScreedGraphs().size() - 1);
                setListOfCScreeds();
                img.setIcon(Visualizator.createImage(graph.getScreedGraphs().get(index)));
            }

        });
        btnPanel.add(rightBtn);

        // �������� ������ ������
        list = new JList<String>();
        JScrollPane scrl1 = new JScrollPane(list);
        add(scrl1, BorderLayout.WEST);
        setListOfCScreeds();

        // �������� ������������� �����
        img = new JLabel();
        img.setIcon(Visualizator.createImage(graph.getScreedGraphs().get(index)));
        add(img, BorderLayout.EAST);

        pack();
    }

    // ������� ������ ������ � ��������� ��� � ��������������� ����������
    private void setListOfCScreeds() {
        DefaultListModel<String> model = new DefaultListModel<String>();
        HyperGraph g = graph.getScreedGraphs().get(index);
        while (g instanceof ScreedGraph) {
            model.addElement(((ScreedGraph) g).getDiff());
            g = ((ScreedGraph) g).getParent();
        }
        list.setModel(model);
    }

}
