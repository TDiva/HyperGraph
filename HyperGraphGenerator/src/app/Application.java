package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import core.Generator;
import core.Visualizator;

/* Основной класс приложения
 * Наследуется от JFrame, то есть представляет собой основное окно приложения.
 * 
 */
public class Application extends JFrame {
    private static final long serialVersionUID = 1L;

    Generator gen;

    JTable matrix;
    JLabel img;

    private static Integer DEFAULT_NUMBER_OF_VERTEXS = 5;

    // конструктор, в нем создается основное окно и запускается генерация
    public Application() {
        // пусть приложение завершает работу, если окно закрыли.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // генерируем граф и рисуем его в виде таблицы
        gen = new Generator();
        matrix = new JTable();
        setLayout(new BorderLayout());
        gen.generate(DEFAULT_NUMBER_OF_VERTEXS);
        Visualizator.fillMatrix(matrix, gen.getGraph());
        JScrollPane scrl = new JScrollPane(matrix);
        add(scrl, BorderLayout.CENTER);

        // создаем панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        JLabel inputLabel = new JLabel("Enter number of vertexs: ");
        buttonPanel.add(inputLabel);

        final JTextField edit = new JTextField(DEFAULT_NUMBER_OF_VERTEXS.toString());
        edit.setPreferredSize(new Dimension(50, 20));
        edit.addKeyListener(new KeyListener() {

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
        });
        buttonPanel.add(edit);

        JButton genBtn = new JButton("Generate");
        genBtn.addActionListener(new ActionListener() {

            // по нажатию кнопки будет запускаться перегенерация графа
            @Override
            public void actionPerformed(ActionEvent arg0) {
                gen.generate(Integer.parseInt(edit.getText()));
                Visualizator.fillMatrix(matrix, gen.getGraph());
                img.setIcon(Visualizator.createImage(gen.getGraph()));
            }

        });
        buttonPanel.add(genBtn);

        JButton subBtn = new JButton("SubGraphs");
        subBtn.addActionListener(new ActionListener() {

            // по нажатию кнопки будет открываться окно подграфов
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SubGraphsFrame frame = new SubGraphsFrame(gen.getGraph());
                frame.setVisible(true);
            }

        });
        buttonPanel.add(subBtn);

        JButton screedBtn = new JButton("Screed Graphs");
        screedBtn.addActionListener(new ActionListener() {

            // по нажатию кнопки будет открываться окно стяжек
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ScreedGraphFrame frame = new ScreedGraphFrame(gen.getGraph());
                frame.setVisible(true);
            }

        });
        buttonPanel.add(screedBtn);

        // загрузим в правую часть окна сгенерированное изображение графа
        img = new JLabel();
        img.setIcon(Visualizator.createImage(gen.getGraph()));
        add(img, BorderLayout.EAST);

        pack();
    }

    // точка входа программы. Создает окно и показывает его на экране
    public static void main(String[] args) {
        Application app = new Application();
        app.setVisible(true);
    }

}
