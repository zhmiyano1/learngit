package model;

public class CellMes {
    private boolean cellStatus;
    private boolean isChangeStatus;

    public CellMes() {
        //ϸ��״̬
        cellStatus = false;
        //��һʱ��״̬�Ƿ���Ҫ�޸�
        isChangeStatus = false;
    }

    //���캯��
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
