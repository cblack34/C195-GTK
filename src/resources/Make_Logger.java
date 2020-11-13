package resources;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Creates a logger
 */
public class Make_Logger {
    /**
     * @param filename The path and name of the log file to log to.
     * @return a Logger that outputs to the filename given.
     */
    public static Logger getLogger(String filename){
        Logger log = Logger.getLogger(filename);
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tc %2$s %4$s: %5$s%n");

        try {
            FileHandler fh = new FileHandler(filename, true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);

        } catch (IOException ex) {
            Logger.getLogger(Make_Logger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Make_Logger.class.getName()).log(Level.SEVERE, null, ex);
        }

        log.setLevel(Level.CONFIG);

        return log;
    }
}
