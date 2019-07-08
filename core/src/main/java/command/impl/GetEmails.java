package command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class GetEmails implements Command {
    private static final Logger log = Logger.getLogger(SendEmail.class);
    private static final int EXECUTE_ERROR = 503;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ContactServiceImpl contactService = new ContactServiceImpl();
            ArrayList<String> emails = contactService.getEmails(objectMapper.readValue(req.getReader().readLine(), new TypeReference<ArrayList<Integer>>() {}));

            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(objectMapper.writeValueAsString(emails));
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("GetEmails response with status " + EXECUTE_ERROR);
        }
    }
}
