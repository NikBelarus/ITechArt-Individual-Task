package dao;

import dto.AttachmentHelp;
import entities.Attachment;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

public class AttachmentDAOImpl extends BaseImpl {
    private Properties mainProperties = new Properties();
    private InputStream mainPropStream = ContactDAOImpl.class.getClassLoader().getResourceAsStream("main.properties");
    private static final String SELECT_ALL_ATTACHMENTS_WITH_ID = "SELECT * FROM attachment WHERE contact_id = ?";
    private static final String SELECT_ATTACHMENT_BY_ID = "SELECT name FROM attachment WHERE id = ?";
    private static final String ADD_ATTACHMENT = "INSERT INTO attachment (contact_id, name, upload_date, comment) VALUES (?,?,?,?)";
    private static final String MAX_ID = "SELECT MAX(id) FROM attachment";
    private static final String DELETE_ATTACHMENT = "DELETE FROM attachment WHERE id = ?";
    private static final String UPDATE_ATTACHMENT = "UPDATE attachment SET name = ?, upload_date = ?, comment = ? WHERE id = ?";
    private static final String UPDATE_COMMENT = "UPDATE attachment SET comment = ? WHERE id = ?";
    private static final Logger log = Logger.getLogger(AttachmentDAOImpl.class);

    public ArrayList<Attachment> findByID(int cont_id) throws SQLException {
        log.info("AttachmentDAOImpl findByID enter");
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ATTACHMENTS_WITH_ID);
        preparedStatement.setInt(1, cont_id);
        ArrayList<Attachment> attachments = new ArrayList<>();
        try(ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()){
                Attachment attachment = new Attachment(rs.getInt(1), rs.getString(3), rs.getString(4),
                        rs.getString(5));
                attachments.add(attachment);
            }
        }
        log.info("AttachmentDAOImpl findByID exit");
        return attachments;
    }

    public void updateAttachments(ArrayList<Attachment> attachments, int cont_id) throws IOException, SQLException {
        log.info("AttachmentDAOImpl updateAttachments enter");
        mainProperties.load(mainPropStream);
        ArrayList<Attachment> oldAttachs = oldAttachs(attachments, cont_id);
        ArrayList<Attachment> newAttachs = new ArrayList<>();
        for(int i = 0; i < attachments.size(); i++){
            if(attachments.get(i).getId() > 0){//обновим если были изменения в этом аттачменте(то есть если аттач с таким именем лежит в папке)
                //есть два варианта: сам файл поменялся(тогда файл с таким именем лежит в директории)
                //либо файл не поменялся и тогда ищем его название по id
                if(new File(mainProperties.getProperty("files.path") + attachments.get(i).getName()).exists()){//тогда обновим
                    System.out.println("меняем файл");
                    String oldNameOfFile = attachments.get(i).getId() + getTypeOfFile(attachments.get(i).getName());
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ATTACHMENT);
                    preparedStatement.setString(1, attachments.get(i).getName());
                    preparedStatement.setString(2, getCurrentTime());
                    preparedStatement.setString(3, attachments.get(i).getComment());
                    preparedStatement.setInt(4, attachments.get(i).getId());
                    preparedStatement.executeUpdate();
                    renameFile(oldNameOfFile, attachments.get(i).getId());
                }
                else{//файл не менялся, то есть нужно обновить только комментарий
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENT);
                    preparedStatement.setString(1, attachments.get(i).getComment());
                    preparedStatement.setInt(2, attachments.get(i).getId());
                    preparedStatement.executeUpdate();
                }
            }
            else {
                newAttachs.add(attachments.get(i));
            }
        }
        //нужно изменить старые — удалить
        deleteAttachments(oldAttachs);
        //добавим новые
        addAttachments(newAttachs, cont_id);
        log.info("AttachmentDAOImpl updateAttachments exit");
    }

    private ArrayList<Attachment> oldAttachs(ArrayList<Attachment> attachments, int cont_id) throws SQLException {
        ArrayList<Attachment> delAttachs = findByID(cont_id);
        for(int i = 0; i < attachments.size(); i++){
            for(int j = 0; j < delAttachs.size(); j++){
                if(delAttachs.get(j).getId().equals(attachments.get(i).getId())){
                    delAttachs.remove(j);
                    break;
                }
            }
        }
        return delAttachs;
    }

    public void deleteAttachments(ArrayList<Attachment> attachments) throws SQLException, IOException {
        log.info("AttachmentDAOImpl deleteAttachments enter");
        mainProperties.load(mainPropStream);
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ATTACHMENT);
        File file = null;
        for(int i = 0; i < attachments.size(); i++) {
            String type = getTypeOfFile(attachments.get(i).getName());
            file = new File(mainProperties.getProperty("files.path") + attachments.get(i).getId() + type);
            preparedStatement.setInt(1, attachments.get(i).getId());
            preparedStatement.executeUpdate();
            file.delete();
        }
        log.info("AttachmentDAOImpl deleteAttachments exit");
    }

    public void addAttachments(ArrayList<Attachment> attachments, int cont_id) throws SQLException, IOException {
        log.info("AttachmentDAOImpl addAttachments enter");
        for(int i = 0; i < attachments.size(); i++) {
            String oldNameOfFile = attachments.get(i).getId() + getTypeOfFile(attachments.get(i).getName());
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ATTACHMENT);
            preparedStatement.setInt(1, cont_id);
            preparedStatement.setString(2, attachments.get(i).getName());
            preparedStatement.setString(3, getCurrentTime());
            preparedStatement.setString(4, attachments.get(i).getComment());
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = connection.prepareStatement(MAX_ID).executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    renameFile(oldNameOfFile, id);
                }
            }
        }
        log.info("AttachmentDAOImpl addAttachments exit");
    }

    private String getTypeOfFile(String nameOfFile){
        StringTokenizer stringTokenizer = new StringTokenizer(nameOfFile, ".");
        stringTokenizer.nextToken();
        return  "." + stringTokenizer.nextToken();
    }

    private String getCurrentTime(){
        StringTokenizer stringTokenizer = new StringTokenizer(LocalDateTime.now().toString(), "T");
        String resultTime = stringTokenizer.nextToken();
        String[] time = stringTokenizer.nextToken().split(":");
        resultTime += " " + time[0] + ":" + time[1];
        return resultTime;
    }

    private void renameFile(String oldName, int newName) throws IOException {
        mainProperties.load(mainPropStream);
        String type = getTypeOfFile(oldName);
        new File(mainProperties.getProperty("files.path") + oldName).renameTo(
                new File(mainProperties.getProperty("files.path") + newName + type)
        );
    }

    public AttachmentHelp getAttachmentByID(int id) throws SQLException, IOException {
        log.info("AttachmentDAOImpl getAttachmentByID enter");
        mainProperties.load(mainPropStream);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ATTACHMENT_BY_ID);
        preparedStatement.setInt(1, id);
        AttachmentHelp attachmentHelp = new AttachmentHelp();
        try(ResultSet rs = preparedStatement.executeQuery()){
            if(rs.next()){
                attachmentHelp.setFileName(rs.getString(1));
                String type = getTypeOfFile(attachmentHelp.getFileName());
                attachmentHelp.setFilePath(mainProperties.getProperty("files.path") + id + type);
            }
        }
        log.info("AttachmentDAOImpl getAttachmentByID exit");
        return attachmentHelp;
    }
}
