
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
/*
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

*/
     import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HelloWorld{
	
	
	
    public static void main(String[] args) {
    	
    	
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable yourRunnable = new Runnable() {
			
			@Override
			public void run() {
				 System.out.println("Lanzamoooooooos tarea!!!!");
				
			}
		};
		scheduler.scheduleAtFixedRate(yourRunnable, 1, 2, TimeUnit.SECONDS);

		     }
    
    

}
