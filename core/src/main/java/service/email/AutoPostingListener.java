package service.email;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.PrintWriter;
import java.io.StringWriter;

public class AutoPostingListener implements ServletContextListener {
    private Scheduler scheduler = null;
    private static final Logger log = Logger.getLogger(AutoPostingListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            log.info("AutoPostingListener is initialized");
            JobDetail job = JobBuilder.newJob(AutoPosting.class).withIdentity("job", "group").build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger", "group")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 * * ?"))
                    .build();
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            scheduler.shutdown();
            log.info("AutoPostingListener is shutdown");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        }
    }
}
