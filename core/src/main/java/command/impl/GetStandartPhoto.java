package command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import org.apache.log4j.Logger;
import service.ContactServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class GetStandartPhoto implements Command {
    private static final Logger log = Logger.getLogger(GetStandartPhoto.class);
    private static final int EXECUTE_ERROR = 503;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContactServiceImpl contactService = new ContactServiceImpl();
            String photo = contactService.getStandartPhoto();

            resp.setContentType("application/json; charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(photo));
        } catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            resp.setStatus(EXECUTE_ERROR);
            log.info("GetStandartPhoto response with status " + EXECUTE_ERROR);
        }
    }
}
