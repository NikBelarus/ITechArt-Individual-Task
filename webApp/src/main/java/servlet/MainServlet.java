package servlet;

import command.Command;
import command.CommandFactory;
import dao.ContactDAOImpl;
import dao.PoolConnection;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;
import java.util.StringTokenizer;

@WebServlet("/api/*")
public class MainServlet extends HttpServlet {
    private String commandName;
    private CommandFactory commandFactory = new CommandFactory();
    private Properties mainProperties = new Properties();
    private InputStream mainPropStream = ContactDAOImpl.class.getClassLoader().getResourceAsStream("web.properties");
    private static final Logger log = Logger.getLogger(MainServlet.class);

    public void init() {
        log.info("MainServlet init enter");
        try {
            mainProperties.load(mainPropStream);
            PoolConnection.getInstance().init();
        } catch (IOException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        }
        //----------
        File dir = new File(mainProperties.getProperty("base.path") + "daschynski");
        if(!dir.exists()) {
            dir.mkdir();
        }
        File dir2 = new File(mainProperties.getProperty("base.path") + "daschynski//files");
        if(!dir2.exists()) {
            dir2.mkdir();
        }
        File dir3 = new File(mainProperties.getProperty("base.path") + "daschynski//pictures");
        if(!dir3.exists()) {
            dir3.mkdir();
        }
        //----------
        log.info("MainServlet init exit");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("MainServlet doGet enter");
        doProcess("GET", req, resp);
        log.info("MainServlet doGet exit");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("MainServlet doPost enter");
        doProcess("POST", req, resp);
        log.info("MainServlet doPost exit");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("MainServlet doDelete enter");
        doProcess("DELETE", req, resp);
        log.info("MainServlet doDelete exit");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("MainServlet doPut enter");
        doProcess("PUT", req, resp);
        log.info("MainServlet doPut exit");
    }

    private void doProcess(String method, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        commandName = urlCommand(req.getPathInfo());
        commandName = method + "_" + commandName;
        log.info(commandName);
        Command command = commandFactory.createCommand(commandName);
        command.execute(req, resp);
    }

    private String urlCommand(String url){
        url = url.substring(1, url.length());
        StringTokenizer stringTokenizer = new StringTokenizer(url, "?");
        return stringTokenizer.nextToken().toUpperCase();
    }
}