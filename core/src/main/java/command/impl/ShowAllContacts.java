package command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import dto.ContactsAndNum;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ShowAllContacts implements Command {
    private static final Logger log = Logger.getLogger(ShowAllContacts.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
            int contactsPerPage = Integer.parseInt(req.getParameter("contactsPerPage"));
            ContactServiceImpl contactService = new ContactServiceImpl();
            ContactsAndNum contactsAndNum = contactService.getContacts(pageNumber, contactsPerPage);
            log.info("Get contacts for pageNumber " + pageNumber + " and contactsPerPage " + contactsPerPage);
            resp.setContentType("application/json; charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(contactsAndNum));
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("ShowAllContacts response with status " + EXECUTE_ERROR);
        }
    }
}