package command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import entities.Contact;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class GetContact implements Command {
    private static final Logger log = Logger.getLogger(GetContact.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContactServiceImpl contactService = new ContactServiceImpl();
            Contact contact = contactService.findContactByID(Integer.parseInt(req.getParameter("id")));

            resp.setContentType("application/json; charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(contact));
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("GetContact response with status " + EXECUTE_ERROR);
        }
    }
}