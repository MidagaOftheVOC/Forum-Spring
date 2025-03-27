package app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
    GLOBAL CONFIGURATION VARIABLES

    DEBUG_MODE is the most important variable here.
    Final builds of the app must have its value changed to false,
    or ideally change the function to *return false* by default.

    DODGE_EXCEPTION is a boolean, which if set, focuses on printing
    informatoin using Slf4j or similar instead of throwing exception
    to ease debugging by reducing crashes. Workarounds may need to be
    made to make effective use of this.
 */
public class GCV {

    final static private boolean DEBUG_MODE = true;
    final static private boolean DODGE_EXCEPTION = true;

    /**
    * DANGEROUS FUNCTION
    *
    * Read GCV class description.
    * */
    static public boolean isDodgingExceptions() {return DODGE_EXCEPTION;}
    /**
     * DANGEROUS FUNCTION
     *
     * Read GCV class description.
     * */
    static public boolean isDebugging(){
        return DEBUG_MODE;
    }



    /**
     * HACK: lombok problems. All solved now, some functions may use this instead of normal slf4j
     * */
    final static private Logger logger = LoggerFactory.getLogger(GCV.class);

    public static void debug(String _msg){
        logger.debug(_msg);
    }

    public static void info(String _msg){
        logger.info(_msg);
    }

    public static void warn(String _msg){
        logger.warn(_msg);
    }

    public static void error(String _msg){
        logger.error(_msg);
    }
}
