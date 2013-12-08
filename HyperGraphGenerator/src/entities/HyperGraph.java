package entities;

import java.util.ArrayList;
import java.util.List;

/*
 * Класс гиперграфа
 */
public class HyperGraph {

    protected List<Integer> matrix;
    protected int v;

    private List<SubGraph> subgraphs;
    private List<ScreedGraph> screedGraphs;

    // инициализация пустого графа с v вершинами
    public HyperGraph(int v) {
        matrix = new ArrayList<Integer>();
        this.v = v;
    }

    public List<Integer> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<Integer> matrix) {
        this.matrix = matrix;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getR() {
        return matrix.size();
    }

    // добавляем ребро: list - список номеров вершин
    public void addEdge(List<Integer> list) {
        if (list.size() < 2)
            return;
        int row = 0;
        // кодируем его: в числе на i-ой позиции в двоичном коде стоит единица,
        // если i-ая вершина взоди в ребро и 0 - если нет
        for (int i = v - 1; i >= 0; i--) {
            if (list.contains(i)) {
                row = row * 2 + 1;
            } else {
                row *= 2;
            }
        }
        matrix.add(row);
    }

    // добавляем уже закодированное ребро
    public void addEdge(int edge) {
        matrix.add(edge);
    }

    // удаляем ребро по индексу
    public void removeEdge(int index) {
        matrix.remove(index);
    }

    // переводит список ребер в двумерный массив - для визуализации в таблицу
    public Integer[][] toArray() {
        Integer[][] arr = new Integer[getR()][];
        for (int i = 0; i < getR(); i++) {
            arr[i] = new Integer[v];
        }

        for (int i = 0; i < matrix.size(); i++) {
            int row = matrix.get(i);
            for (int j = 0; j < v; j++, row /= 2) {
                arr[i][j] = row % 2;
            }
        }

        return arr;
    }

    // проверяет, можно ли на доступном множестве вешин v (закодировано)
    // построить ребро е
    // то есть все ли вершины, находящиеся в e принадлежат и v
    private boolean isEdgeBasedOnVertexs(int e, int v) {
        for (; e > 0; v /= 2, e /= 2) {
            if (e % 2 == 1 && v % 2 == 0)
                return false;
        }
        return true;
    }

    // генерируем подграфы на множестве вершин v (закодировано)
    private void generateSubgraphs(int v) {
        List<Integer> edges = new ArrayList<Integer>();
        // для любого ребря графа, проверяем, можно ли его добавить в подграф
        for (Integer e : matrix) {
            if (isEdgeBasedOnVertexs(e, v))
                edges.add(e);
        }
        // создаем подграфы из множества вершин v и всех возможных комбинаций
        // ребер из edges
        // то есть тех, которые можно добавить в этот подграф
        for (int e = 0; e < Math.pow(2d, edges.size()); e++) {
            SubGraph sub = new SubGraph(this.v, v);
            for (int k = e, i = 0; k > 0; k /= 2, i++) {
                if (k % 2 == 1)
                    sub.addEdge(edges.get(i));
            }
            subgraphs.add(sub);
        }
    }

    // генерируем подгафы
    private void generateSubGraphs() {
        subgraphs = new ArrayList<SubGraph>();
        // для каждого подмножества множества вершин генерируем все возможные
        // подграфы
        for (int i = 1; i < Math.pow(2d, v); i++) {
            generateSubgraphs(i);
        }
    }

    public List<SubGraph> getSubGraphs() {
        if (subgraphs == null)
            generateSubGraphs();
        return subgraphs;
    }

    // если ребро содержит одну вершину - это уже не ребро :D
    protected boolean isSingleEdge(int k) {
        int edge = matrix.get(k);
        int count = 0;
        for (int i = edge; i > 0; i /= 2) {
            count += i % 2;
        }
        return (count == 2);
    }

    // проверяем, содержит ли ребро 2 вершины, то есть можно ли его стянуть
    protected boolean isDoubleEdge(int k) {
        int edge = matrix.get(k);
        int count = 0;
        for (int i = edge; i > 0; i /= 2) {
            count += i % 2;
        }
        return (count == 2);
    }

    // генерация стяжек (рекурсивная)
    protected void generateScreedGraphs() {
        screedGraphs = new ArrayList<ScreedGraph>();
        for (int i = 0; i < matrix.size(); i++) {
            // для каждого ребра, если оно соединяет 2 вершины, пробуем его
            // стянуть
            if (isDoubleEdge(i)) {
                // создаем стяжку
                ScreedGraph g = new ScreedGraph(this, i);
                // находим корневой граф, дял которого запскался поиск
                HyperGraph root = this;
                while (root instanceof ScreedGraph)
                    root = ((ScreedGraph) root).getParent();
                // добавляем стяжку, если ее еще не было
                if (!root.screedGraphs.contains(g))
                    root.getScreedGraphs().add(g);
                // запускаем поиск стяжек для уже найденой стяжки
                g.generateScreedGraphs();
            }
        }
    }

    public List<ScreedGraph> getScreedGraphs() {
        if (screedGraphs == null)
            generateScreedGraphs();
        return screedGraphs;
    }

}
