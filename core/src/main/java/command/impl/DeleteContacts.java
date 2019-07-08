package command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import filter.IdsValidator;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class DeleteContacts implements Command {
    private static final Logger log = Logger.getLogger(DeleteContacts.class);
    private static final int EXECUTE_ERROR = 503;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Integer> ids = objectMapper.readValue(req.getReader().readLine(), new TypeReference<ArrayList<Integer>>() {});
            log.info("Ids of contacts to delete: " + ids);
            IdsValidator idsValidator = new IdsValidator(ids);
            if(idsValidator.validate()) {
                ContactServiceImpl contactService = new ContactServiceImpl();
                contactService.deleteContacts(ids);
            }
            log.info("Contacts were deleted");
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("DeleteContacts response with status " + EXECUTE_ERROR);
        }
    }
}