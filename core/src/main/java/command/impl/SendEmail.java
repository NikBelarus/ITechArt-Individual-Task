package command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import dto.EmailHelp;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class SendEmail implements Command {
    private static final Logger log = Logger.getLogger(SendEmail.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmailHelp emailHelp = objectMapper.readValue(req.getReader().readLine(), EmailHelp.class);
            ContactServiceImpl contactService = new ContactServiceImpl();
            contactService.sendEmail(emailHelp);
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("SendEmail response with status " + EXECUTE_ERROR);
        }
    }
}
