package control;

import model.CellLifeWorld;

public class GameProcess {
    protected CellLifeWorld world;

    public GameProcess() {
        world = new CellLifeWorld(10, 10);
    }

    public GameProcess(int row, int col) {
        world = new CellLifeWorld(row, col);
    }
    /*********
     *繁衍过程
     */
    public void cellMultiply() {
        //细胞下一状态判断
        for (int i = 0; i < world.getWorldRow(); i++) {
            for (int j = 0; j < world.getWorldCol(); j++) {
                //获取周边细胞数量
                int cellNum = world.getPericyteCellNum(i, j);
                //如果细胞状态发生改变,设置改变标识
                if (world.getCellStatus(i, j) != cellSavePrinciple(world.getCellStatus(i, j), cellNum)) {
                    world.setChangeFlag(i, j);
                }
            }
        }
        //细胞状态改变
        for (int i = 0; i < world.getWorldRow(); i++) {
            for (int j = 0; j < world.getWorldCol(); j++) {
                if (world.getChangeStatus(i, j)) {
                    world.changeCellStatus(i, j);
                    world.canelChangeFlag(i, j);
                }
            }
        }
    }

    /*****************
     * 细胞生存规则
     */
    private boolean cellSavePrinciple(boolean curStatus, int surCellNumber) {
        if (surCellNumber == 3) {
            return true;
        } else if (surCellNumber == 2) {
            return curStatus;
        } else {
            return false;
        }
    }

    /*************
     * 修改某一位置细胞状态 并返回原状态
     */
    public boolean changeCellStatus(int row, int col) {
        return world.changeCellStatus(row, col);
    }

    /*****
     * 设置细胞初始状态为随机
     */
    public void setDefaultWorld() {
        world.randomCellsStatus();
    }

    /***********
     * 返回细胞生存矩阵的二维数组
     */
    public int[][] getAllCellStatus() {
        int width = world.getWorldRow();
        int length = world.getWorldCol();

        int[][] cellSaveRect = new int[width][length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (world.getCellStatus(i, j)) {
                    cellSaveRect[i][j] = 1;
                } else {
                    cellSaveRect[i][j] = 0;
                }
            }
        }
        return cellSaveRect;
    }

}
