/*
 * Copyright (c) 2002-2013 TIBCO Software Inc.
 * All rights reserved.
 * For more information, please contact:
 * TIBCO Software Inc., Palo Alto, California, USA
 *
 * $Id: tibjmsMsgConsumer.java 66719 2013-04-12 20:30:15Z $
 *
 */

/*
 * This is a simple sample of a basic tibjmsMsgConsumer.
 *
 * This sampe subscribes to specified destination and
 * receives and prints all received messages.
 *
 * Notice that the specified destination should exist in your configuration
 * or your topics/queues configuration file should allow
 * creation of the specified destination.
 *
 * If this sample is used to receive messages published by
 * tibjmsMsgProducer sample, it must be started prior
 * to running the tibjmsMsgProducer sample.
 *
 * Usage:  java tibjmsMsgConsumer [options]
 *
 *    where options are:
 *
 *      -server     Server URL.
 *                  If not specified this sample assumes a
 *                  serverUrl of "tcp://localhost:7222"
 *
 *      -user       User name. Default is null.
 *      -password   User password. Default is null.
 *      -topic      Topic name. Default is "topic.sample"
 *      -queue      Queue name. No default
 *      -ackmode    Message acknowledge mode. Default is AUTO.
 *                  Other values: DUPS_OK, CLIENT, EXPLICIT_CLIENT,
 *                  EXPLICIT_CLIENT_DUPS_OK and NO.
 *
 *
 */

import java.text.SimpleDateFormat;
import java.text.Normalizer;
import java.io.FileWriter;
import java.io.IOException;

import java.io.File;
import java.io.PrintWriter;
import javax.jms.*;

import com.tibco.tibjms.Tibjms;


public class tibjmsMsgConsumer implements ExceptionListener {
    /*-----------------------------------------------------------------------
     * Parameters
     *----------------------------------------------------------------------*/

    String serverUrl = null;
    String userName = null;
    String password = null;
    String name = null;
    boolean useTopic = false;
    int ackMode = Session.AUTO_ACKNOWLEDGE;

    /*-----------------------------------------------------------------------
     * Variables
     *----------------------------------------------------------------------*/
    Connection connection = null;
    Session session = null;
    MessageConsumer msgConsumer = null;
    Destination destination = null;
    private static final int MAX_SLUG_LENGTH = 500;

    public static String slugify(final String s) {
        final String intermediateResult = Normalizer
                .normalize(s, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^-_a-zA-Z0-9]", "-").replaceAll("\\s+", "-")
                .replaceAll("[-]+", "-").replaceAll("^-", "")
                .replaceAll("-$", "").toLowerCase();
        return intermediateResult.substring(0,
                Math.min(MAX_SLUG_LENGTH, intermediateResult.length()));
    }

    public tibjmsMsgConsumer(String[] args) {
        parseArgs(args);

        try {
            tibjmsUtilities.initSSLParams(serverUrl, args);
        } catch (JMSSecurityException e) {
            System.err.println("JMSSecurityException: " + e.getMessage() + ", provider=" + e.getErrorCode());
            e.printStackTrace();
            System.exit(0);
        }

        /* print parameters */
        System.err.println("\n------------------------------------------------------------------------");
        System.err.println("tibjmsMsgConsumer SAMPLE");
        System.err.println("------------------------------------------------------------------------");
        System.err.println("Server....................... " + ((serverUrl != null) ? serverUrl : "localhost"));
        System.err.println("User......................... " + ((userName != null) ? userName : "(null)"));
        System.err.println("Destination.................. " + name);
        System.err.println("------------------------------------------------------------------------\n");

        try {
            run();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /*-----------------------------------------------------------------------
     * usage
     *----------------------------------------------------------------------*/
    void usage() {
        System.err.println("\nUsage: tibjmsMsgConsumer [options] [ssl options]");
        System.err.println("");
        System.err.println("   where options are:");
        System.err.println("");
        System.err.println(" -server   <server URL> - EMS server URL, default is local server");
        System.err.println(" -user     <user name>  - user name, default is null");
        System.err.println(" -password <password>   - password, default is null");
        System.err.println(" -topic    <topic-name> - topic name, default is \"topic.sample\"");
        System.err.println(" -queue    <queue-name> - queue name, no default");
        System.err.println(" -ackmode  <ack-mode>   - acknowledge mode, default is AUTO");
        System.err.println("                          other modes: CLIENT, DUPS_OK, NO,");
        System.err.println("                          EXPLICIT_CLIENT and EXPLICIT_CLIENT_DUPS_OK");
        System.err.println(" -help-ssl              - help on ssl parameters\n");
        System.exit(0);
    }

    /*-----------------------------------------------------------------------
     * parseArgs
     *----------------------------------------------------------------------*/
    void parseArgs(String[] args) {
        int i = 0;

        while (i < args.length) {
            if (args[i].compareTo("-server") == 0) {
                if ((i + 1) >= args.length) usage();
                serverUrl = args[i + 1];
                i += 2;
            } else if (args[i].compareTo("-topic") == 0) {
                if ((i + 1) >= args.length) usage();
                name = args[i + 1];
                i += 2;
            } else if (args[i].compareTo("-queue") == 0) {
                if ((i + 1) >= args.length) usage();
                name = args[i + 1];
                i += 2;
                useTopic = false;
            } else if (args[i].compareTo("-user") == 0) {
                if ((i + 1) >= args.length) usage();
                userName = args[i + 1];
                i += 2;
            } else if (args[i].compareTo("-password") == 0) {
                if ((i + 1) >= args.length) usage();
                password = args[i + 1];
                i += 2;
            } else if (args[i].compareTo("-ackmode") == 0) {
                if ((i + 1) >= args.length) usage();
                if (args[i + 1].compareTo("AUTO") == 0)
                    ackMode = Session.AUTO_ACKNOWLEDGE;
                else if (args[i + 1].compareTo("CLIENT") == 0)
                    ackMode = Session.CLIENT_ACKNOWLEDGE;
                else if (args[i + 1].compareTo("DUPS_OK") == 0)
                    ackMode = Session.DUPS_OK_ACKNOWLEDGE;
                else if (args[i + 1].compareTo("EXPLICIT_CLIENT") == 0)
                    ackMode = Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE;
                else if (args[i + 1].compareTo("EXPLICIT_CLIENT_DUPS_OK") == 0)
                    ackMode = Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE;
                else if (args[i + 1].compareTo("NO") == 0)
                    ackMode = Tibjms.NO_ACKNOWLEDGE;
                else {
                    System.err.println("Unrecognized -ackmode: " + args[i + 1]);
                    usage();
                }
                i += 2;
            } else if (args[i].compareTo("-help") == 0) {
                usage();
            } else if (args[i].compareTo("-help-ssl") == 0) {
                tibjmsUtilities.sslUsage();
            } else if (args[i].startsWith("-ssl")) {
                i += 2;
            } else {
                System.err.println("Unrecognized parameter: " + args[i]);
                usage();
            }
        }
    }


    /*---------------------------------------------------------------------
     * onException
     *---------------------------------------------------------------------*/
    public void onException(JMSException e) {
        /* print the connection exception status */
        System.err.println("CONNECTION EXCEPTION: " + e.getMessage());
    }

    /*-----------------------------------------------------------------------
     * run
     *----------------------------------------------------------------------*/
    void run() throws JMSException {
        Message msg = null;
        String msgType = "UNKNOWN";

        System.err.println("Subscribing to destination: " + name + "\n");

        ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);

        /* create the connection */
        connection = factory.createConnection(userName, password);

        /* create the session */
        session = connection.createSession(ackMode);

        /* set the exception listener */
        connection.setExceptionListener(this);

        /* create the destination */
        if (useTopic)
            destination = session.createTopic(name);
        else
            destination = session.createQueue(name);

        /* create the consumer */
        msgConsumer = session.createConsumer(destination);

        /* start the connection */
        connection.start();

        /* read messages */
        while (true) {
            /* receive the message */
            msg = msgConsumer.receive();
            if (msg == null)
                break;

            if (ackMode == Session.CLIENT_ACKNOWLEDGE ||
                    ackMode == Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE ||
                    ackMode == Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE) {
                msg.acknowledge();
            }
            Integer hashCode = msg.hashCode();
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            String slugDestination = tibjmsMsgConsumer.slugify(destination.toString());
            String file_name = hashCode.toString() + "_" + slugDestination+ "_" + timeStamp +".xml";
            String path = "/data/output/" + file_name;
            String msgBody = msg.getBody(String.class);
            try {
                System.out.println(path);
                FileWriter myWriter = new FileWriter(path);
                myWriter.write(msgBody);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.err.println("Received message: " + msgBody);
            break;
        }

        /* close the connection */
        connection.close();
    }

    /*-----------------------------------------------------------------------
     * main
     *----------------------------------------------------------------------*/
    public static void main(String[] args) {
        new tibjmsMsgConsumer(args);
    }
}
