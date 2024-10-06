package org.example;

import java.util.concurrent.RecursiveAction;

public class IslandSimulationTask extends RecursiveAction {
    private static final int THRESHOLD = 10;
    private final Island island;
    private final int startRow, endRow, startCol, endCol;

    public IslandSimulationTask(Island island, int startRow, int endRow, int startCol, int endCol) {
        this.island = island;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    @Override
    protected void compute() {
        int numRows = endRow - startRow;
        int numCols = endCol - startCol;

        if (numRows <= THRESHOLD && numCols <= THRESHOLD) {
            // Обработка небольшой группы клеток последовательно
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    island.processCell(i, j);  // Метод для обработки содержимого одной клетки
                }
            }
        } else {
            // Разделение задачи на подзадачи
            int midRow = (startRow + endRow) / 2;
            int midCol = (startCol + endCol) / 2;

            invokeAll(
                    new IslandSimulationTask(island, startRow, midRow, startCol, midCol),
                    new IslandSimulationTask(island, midRow, endRow, startCol, midCol),
                    new IslandSimulationTask(island, startRow, midRow, midCol, endCol),
                    new IslandSimulationTask(island, midRow, endRow, midCol, endCol)
            );
        }
    }
}
