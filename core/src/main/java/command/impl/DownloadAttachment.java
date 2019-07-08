package command.impl;

import command.Command;
import dto.AttachmentHelp;
import org.apache.log4j.Logger;
import service.AttachmentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadAttachment implements Command {
    private static final Logger log = Logger.getLogger(DownloadAttachment.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp){
        try {
            int id = Integer.parseInt(req.getParameter("attachmentId"));
            AttachmentServiceImpl attachmentService = new AttachmentServiceImpl();
            AttachmentHelp attachmentHelp = attachmentService.getAttachmentByID(id);
            log.info("Attachment for download: " + attachmentHelp);

            OutputStream os = resp.getOutputStream();
            resp.setHeader("Content-Disposition", "attachment; filename=" + attachmentHelp.getFileName());//название файла
            InputStream is = new FileInputStream(new File(attachmentHelp.getFilePath()));//путь к файлу
            int read;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            os.close();
            log.info("Succesful dowmload of attachment");
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("DownloadAttachment response with status " + EXECUTE_ERROR);
        }
    }
}
