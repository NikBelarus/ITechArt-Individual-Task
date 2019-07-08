package command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartRequest;
import command.Command;
import command.ErrorType;
import dao.ContactDAOImpl;
import entities.Contact;
import filter.ContactValidator;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Properties;

public class AddContact implements Command {
    private Properties mainProperties = new Properties();
    private InputStream mainPropStream = ContactDAOImpl.class.getClassLoader().getResourceAsStream("main.properties");
    private static final Logger log = Logger.getLogger(AddContact.class);
    private static final int CONTACT_EXIST = 400;
    private static final int SUCCESS = 200;
    private static final int ROLLBACK = 403;
    private static final int BAD_ROLLBACK = 500;
    private static final int EXECUTE_ERROR = 503;
    private static final int NOT_VALID = 406;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            mainProperties.load(mainPropStream);
            ObjectMapper objectMapper = new ObjectMapper();
            MultipartRequest multipartRequest = new MultipartRequest(req, mainProperties.getProperty("files.path"), 10000*1024, "UTF-8");
            Contact contact = objectMapper.readValue(multipartRequest.getParameter("contact"), Contact.class);
            log.info(contact);
            if (contact.getPhones() == null) {
                contact.setPhones(new ArrayList<>());
            }
            ContactValidator contactValidator = new ContactValidator(contact);
            if (contactValidator.validate()) {
                ContactServiceImpl contactService = new ContactServiceImpl();
                ErrorType status = contactService.addContact(contact);
                if (status.name().equals("CONTACT_EXIST")) {
                    log.info("CONTACT_EXIST");
                    resp.setStatus(CONTACT_EXIST);
                    log.info("AddContact response with status " + CONTACT_EXIST);
                } else if (status.name().equals("SUCCESS")) {
                    log.info("SUCCESS");
                    resp.setStatus(SUCCESS);
                    log.info("AddContact response with status " + SUCCESS);
                } else if (status.name().equals("ROLLBACK")) {
                    log.info("ROLLBACK");
                    resp.setStatus(ROLLBACK);
                    log.info("AddContact response with status " + ROLLBACK);
                } else {
                    log.info("BAD_ROLLBACK");
                    resp.setStatus(BAD_ROLLBACK);
                    log.info("AddContact response with status " + BAD_ROLLBACK);
                }
            }
            else {
                log.info("NOT_VALID");
                resp.setStatus(NOT_VALID);
                log.info("AddContact response with status " + NOT_VALID);
            }
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("AddContact response with status " + EXECUTE_ERROR);
        }
    }
}