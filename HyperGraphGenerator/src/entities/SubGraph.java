package entities;

import java.util.ArrayList;
import java.util.List;

/*
 * Подграф - тоже гиперграф
 */
public class SubGraph extends HyperGraph {

    private List<Integer> vertexs;

    // инициализация подграфа с множеством вершин v (кодировано), когда всего
    // вершин было n
    public SubGraph(int n, int v) {
        super(n);
        vertexs = new ArrayList<Integer>();
        for (int k = v, i = 0; k > 0; k /= 2, i++) {
            if (k % 2 == 1) {
                vertexs.add(i);
            }
        }
    }

    public List<Integer> getVertexs() {
        return vertexs;
    }

    public void setVertexs(List<Integer> vertexs) {
        this.vertexs = vertexs;
    }

    // представление матрицы инцидентности в виде массива - для визуализации в
    // виде таблицы
    public Integer[][] toArray() {
        Integer[][] arr = new Integer[getR()][];
        for (int i = 0; i < getR(); i++) {
            arr[i] = new Integer[vertexs.size()];
        }

        for (int i = 0; i < matrix.size(); i++) {
            int row = matrix.get(i);
            for (int j = 0, k = 0; j < v; j++, row /= 2) {
                if (vertexs.contains(j)) {
                    arr[i][k++] = row % 2;
                }
            }
        }

        return arr;
    }

}
