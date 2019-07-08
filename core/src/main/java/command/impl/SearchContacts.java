package command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import entities.Contact;
import filter.ContactValidator;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class SearchContacts implements Command {
    private static final Logger log = Logger.getLogger(SearchContacts.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Contact contact = objectMapper.readValue(req.getReader().readLine(), Contact.class);
            log.info("Contact info for search: " + contact);
            ContactValidator contactValidator = new ContactValidator(contact);
            boolean b = true;
            if (contact.getBirthdate() != null) {
                b = contactValidator.checkBirthdate(contact.getBirthdate());
            }
            if (b) {
                ContactServiceImpl contactService = new ContactServiceImpl();
                ArrayList<Integer> ids = contactService.searchContacts(contact);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write(objectMapper.writeValueAsString(ids));
            }
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("SearchContacts response with status " + EXECUTE_ERROR);
        }
    }
}