package fr.ozonex.myozonex;

public class StructureBt {
    private String data;
    private boolean progressDialogVisible;

    public StructureBt(String data, boolean progressDialogVisible) {
        this.data = data;
        this.progressDialogVisible = progressDialogVisible;
    }

    public String getData() {
        return data;
    }

    public boolean getProgressDialogVisible() {
        return progressDialogVisible;
    }
}
