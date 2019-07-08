package command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import entities.Contact;
import filter.IdsValidator;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class ShowContactsByIds implements Command {
    private static final Logger log = Logger.getLogger(ShowContactsByIds.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Integer> ids = objectMapper.readValue(req.getReader().readLine(), new TypeReference<ArrayList<Integer>>() {});

            IdsValidator idsValidator = new IdsValidator(ids);
            if (idsValidator.validate()) {
                ContactServiceImpl contactService = new ContactServiceImpl();
                ArrayList<Contact> contacts = contactService.getContactsById(ids);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write(objectMapper.writeValueAsString(contacts));
            }
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("ShowContactsByIds response with status " + EXECUTE_ERROR);
        }
    }
}