package service;

import dao.AttachmentDAOImpl;
import dto.AttachmentHelp;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AttachmentServiceImpl {
    private static final Logger log = Logger.getLogger(AttachmentServiceImpl.class);

    public AttachmentHelp getAttachmentByID(int id){
        log.info("AttachmentServiceImpl getAttachmentByID enter");
        AttachmentDAOImpl attachmentDAO = new AttachmentDAOImpl();
        try{
            attachmentDAO.setConnection();
            return attachmentDAO.getAttachmentByID(id);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        } finally {
            attachmentDAO.closeConnection();
            log.info("AttachmentServiceImpl getAttachmentByID exit");
        }
    }
}
