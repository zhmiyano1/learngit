package model;

import java.util.Random;

public class CellLifeWorld {
    private int worldRow;
    private int worldCol;
    private CellMes[][] cells;

    /**************
     * 根据输入固定默认世界大小
     */
    public CellLifeWorld(int worldRow, int worldCol) {
        this.worldRow = worldRow;
        this.worldCol = worldCol;
        cells = new CellMes[worldRow][worldCol];
        for (int i = 0; i < worldRow; i++) {
            for (int j = 0; j < worldCol; j++) {
                cells[i][j] = new CellMes();
            }
        }
    }

    /************
     * 获取世界长宽
     */
    public int getWorldRow() {
        return worldRow;
    }

    public int getWorldCol() {
        return worldCol;
    }

    /************
     * 初始化生命序列
     */
    public void randomCellsStatus() {
        for (int i = 0; i < worldRow; i++) {
            for (int j = 0; j < worldCol; j++) {

                // 生成0~9随机数 大于7时此细胞设置为生
                //Random random = new Random();
                //int randomNum = random.nextInt(10);

                int randomNum = (int) (Math.random() * 10);
                if (randomNum > 7) {
                    cells[i][j].setCellStatus(true);
                } else {
                    cells[i][j].setCellStatus(false);
                }
                //细胞状态设置为不改变
                cells[i][j].setChangeStatus(false);
            }
        }
    }

    /**************
     * 获取某位置上细胞状态 默认边界外全为死细胞
     */
    public boolean getCellStatus(int row, int col) {
        if ((row < 0) || (row > worldRow - 1) || (col < 0) || (col > worldCol - 1)) {
            return false;
        } else {
            return cells[row][col].isCellStatus();
        }
    }

    /*********************
     * 改变某一位置上的细胞状态
     */
    public boolean changeCellStatus(int row, int col) {
        boolean beforeStatus = cells[row][col].isCellStatus();
        cells[row][col].setCellStatus(!beforeStatus);

        return beforeStatus;
    }

    /***************
     * 获取改变标识
     */
    public boolean getChangeStatus(int row, int col) {
        return cells[row][col].isChangeStatus();
    }

    /*****************************
     * 设置改变标志
     */
    public void setChangeFlag(int row, int col) {
        cells[row][col].setChangeStatus(true);
    }

    /*************************
     *取消改变标识
     */
    public void canelChangeFlag(int row, int col) {
        cells[row][col].setChangeStatus(false);
    }

    /**************
     * 获取周边生存细胞数量
     */
    public int getPericyteCellNum(int row, int col) {
        int curNum = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((i == row) && (j == col)) {
                } else if (getCellStatus(i, j)) {
                    curNum++;
                }
            }
        }
        return curNum;
    }
}