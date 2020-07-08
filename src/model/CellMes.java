package model;

public class CellMes {
    private boolean cellStatus;
    private boolean isChangeStatus;

    public CellMes() {
        //细胞状态
        cellStatus = false;
        //下一时刻状态是否需要修改
        isChangeStatus = false;
    }

    //构造函数
    public CellMes(boolean cellStatus, boolean isChangeStatus) {
        this.cellStatus = cellStatus;
        this.isChangeStatus = isChangeStatus;
    }

    public boolean isCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(boolean cellStatus) {
        this.cellStatus = cellStatus;
    }

    public boolean isChangeStatus() {
        return isChangeStatus;
    }

    public void setChangeStatus(boolean isChangeStatus) {
        this.isChangeStatus = isChangeStatus;
    }
}
