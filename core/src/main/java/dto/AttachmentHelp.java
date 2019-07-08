package dto;

public class AttachmentHelp {
    private String fileName;
    private String filePath;

    public AttachmentHelp(){}

    public AttachmentHelp(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "AttachmentHelp{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
