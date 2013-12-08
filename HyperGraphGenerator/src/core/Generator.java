package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.HyperGraph;

/*
 * Генератор случайного гипеграфа
 */
public class Generator {
    private HyperGraph graph = null;

    private final static int MAX_VERTEX = 8;
    private final static int DELTA_EDGE = 3;
    // вершины, доступные для использования в ребрах
    private List<Integer> fixedVertex;

    private Random r;

    // инициируем генератор
    public Generator() {
        fixedVertex = new ArrayList<Integer>();
        r = new Random();
        generate();
    }

    // создаем случайный гиперграф
    public void generate() {
        fixedVertex.clear();
        // выбираем случайное число вершин
        int v = r.nextInt(MAX_VERTEX) + 2;
        // делаем все вершины доступными
        for (int i = 0; i < v; i++)
            fixedVertex.add(i);
        // создае пустой гиперграф с уже определенным числом вершин
        graph = new HyperGraph(v);
        int i;
        // создаем рандомное ребро, используя только доступные вершины
        // последнюю вершину в ребре - удаляем из доступных, то есть больше из
        // нее ребер не будет выходить
        // так как в каждом ребре минимум 2 вершины, то граф будет связным
        // так как ребер на 1 меньше чем вершин, то на последней итерации будет
        // доступно только 2 вершины
        // а значит однозначно будет ребро степени 2
        for (i = 0; i < v - 1; i++) {
            fixedVertex.remove(generateEdge());
        }
        // на всякий случай почистим память
        System.gc();
    }

    // создаем случайное ребро
    private int generateEdge() {
        // выбираем, сколько вершин ребро будет соединять: это максимум или 4
        // или количество доступных вершин
        int k = Math.min(r.nextInt(DELTA_EDGE) + 2, fixedVertex.size());
        List<Integer> edge = new ArrayList<Integer>();
        int e = 0;
        // генерируем случайную позицию в списке свободных вершин
        while (edge.size() < k) {
            e = r.nextInt(fixedVertex.size());
            if (!edge.contains(fixedVertex.get(e)))
                edge.add(fixedVertex.get(e));
        }
        // добавляем получаное ребро
        graph.addEdge(edge);
        // возвращаем номер последней вершины в ребре, чтобы далее ее удалить из
        // доступных
        return e;
    }

    public HyperGraph getGraph() {
        return graph;
    }

    public void setGraph(HyperGraph graph) {
        this.graph = graph;
    }

}
