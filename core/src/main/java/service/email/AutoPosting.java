package service.email;

import dao.ContactDAOImpl;
import dto.ContactInfoForAutoPosting;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class AutoPosting implements Job {
    private Properties mailProperties = new Properties();
    private InputStream mailPropStream = AutoPosting.class.getClassLoader().getResourceAsStream("mail.properties");
    private static final Logger log = Logger.getLogger(AutoPosting.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            mailProperties.load(mailPropStream);
            LocalDate localDate = LocalDate.now();
            ContactDAOImpl contactDAO = new ContactDAOImpl();
            try{
                contactDAO.setConnection();
                ArrayList<ContactInfoForAutoPosting> people = contactDAO.getContactsByCurrentDate(localDate);
                if(!people.isEmpty()){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Contacts who have a birthdate today:\n");
                    for(int i = 0; i < people.size(); i++){
                        stringBuilder.append(people.get(i).getSurName() + " " + people.get(i).getName() + " " + people.get(i).getFathersName() + " (" + people.get(i).getEmail() + ")\n");
                    }
                    contactDAO.sendEmail(mailProperties.getProperty("mail.smtp.user"), "Birthdates", stringBuilder.toString());
                }
            }
            catch (Exception e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                log.error(errors);
            }
            finally {
                contactDAO.closeConnection();
            }
        } catch (IOException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        }
    }
}
