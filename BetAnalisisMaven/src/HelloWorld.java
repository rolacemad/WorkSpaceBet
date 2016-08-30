
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
    	
    	final String usernamee = "rolacemad@gmail.com";
        final String passwordd = "D4V1D5E6";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernamee, passwordd);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rolacemad@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("david.segura.v@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        
        
        
        

        URL url;

/*
        String urlA = "jdbc:mysql://localhost:3306/betsystem";
        String username = "root";
        String password = "root";
*/
        String urlA = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7131824";
        String username = "sql7131824";
        String password = "PUsZUZ83BD";
        System.out.println("Connecting database...");
      
      
        try {
        	  try (Connection connection = DriverManager.getConnection(urlA, username, password)) {
                  System.out.println("Database connected!");
                  
                  Statement stmt = connection.createStatement();
                  
                  
                   
            // get URL content
/*
            String a="http://sports.williamhill.es/bet_esp/es/betlive/9";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));

            String inputLine;
            String content = "";
            System.out.println("empieza");
           
            String message = org.apache.commons.io.IOUtils.toString(br);
            br.close();
            System.out.println("termina");*/
                  
              	String queryPatrones = "SELECT * FROM patterns";
        		  //pendiente de comprobar fecha
        		  Statement stt = connection.createStatement();
           	      ResultSet rss = stt.executeQuery(queryPatrones);
           	      // iterate through the java resultset
           	   PatternsBean pb = new PatternsBean();
           	   ArrayList <PatternsBean> Patrones = new ArrayList<PatternsBean>();
           	      while (rss.next())
           	      {
           	    	pb.setMinutoMax(rss.getInt("min_max"));
           	    	pb.setMinutoMin(rss.getInt("min_min"));
           	    	
           	        Patrones.add(pb);
           	      
           	      }

           	      
           	      
            System.out.println("empieza 2");
        	String urlStr = "http://sports.williamhill.es/bet_esp/es/betlive/9";
        	InputStream input = new URL(urlStr).openStream();
            Document doc = Jsoup.parse(input, "utf-8", urlStr);
            System.out.println("termina 2");
            System.out.println("parsea");
            Elements bloquesDeEventos = null;
            bloquesDeEventos = doc.select("div[id=ip_sport_9_types] > div");
            System.out.println("numero de bloques: " + bloquesDeEventos.size());
            for(Element bloqueDeEventos : bloquesDeEventos)
            {
            	String tipoLiga = bloqueDeEventos.select("h3 > a").text();
            	int idTipoEvento = 0;
            	System.out.println("tipo liga: " + tipoLiga);
            	
            	
            	
            	
            	String queryTipoPartido = "SELECT * FROM type_event where text_type_event = '"+tipoLiga+"'";
      		  //pendiente de comprobar fecha
      		  Statement statement = connection.createStatement();
         	      ResultSet resultSet = statement.executeQuery(queryTipoPartido);
         	      // iterate through the java resultset
         	      boolean encontradoTipoPartido = false;
         	      while (resultSet.next())
         	      {
         	    	 idTipoEvento = resultSet.getInt("id_type_event");
         	    	encontradoTipoPartido = true;
         	        
         	        // print the results
         	        System.out.format("tipo de evento encontrado!! ");
         	      }

      		  if(!encontradoTipoPartido)
      		  {
      		  String queryMatch = " insert into type_event (text_type_event)"
    			        + " values (?)";
      		  // create the mysql insert preparedstatement
           	 	PreparedStatement preparedStmt = connection.prepareStatement(queryMatch,
                        Statement.RETURN_GENERATED_KEYS);
   			      preparedStmt.setString(1, tipoLiga);
   			    
   			     

   			      // execute the preparedstatement
   			      preparedStmt.execute();
   			   try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	idTipoEvento = generatedKeys.getInt(1);
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }
           	 	 
      		  }
      		  
      		  
      		  
      		  
      		  
      		  
      		  
            	Elements eventos = bloqueDeEventos.select("tbody > tr");
            	for(Element evento : eventos)
                {
            		String matchTimer = evento.select("td").get(0).select("a").text();
            		String matchResult = evento.select("td").get(1).select("a").text();
            		String matchTeams = evento.select("td").get(2).select("a").select("span").text();
            		//matchTeams = new String(matchTeams.getBytes("utf-8"));
            		//matchTeams = matchTeams.replaceAll("\\?","_");
            		/*String quotedText = Pattern.quote( "   ?   ");
            		Pattern p = Pattern.compile( quotedText );
            		Matcher m = p.matcher( matchTeams );
            		matchTeams = m.replaceAll("_");*/
            		String localCuoteStr = evento.select("td").get(4).select("div > div").text();
            		String drawCouteStr = evento.select("td").get(5).select("div > div").text();
            		String visitCuoteStr = evento.select("td").get(6).select("div > div").text();
            		double localCuote = Double.parseDouble(localCuoteStr);
            		double drawCoute = Double.parseDouble(drawCouteStr);
            		double visitCuote = Double.parseDouble(visitCuoteStr);
            		
            		System.out.println("tiempo: " + matchTimer);
            		System.out.println("resultado: " + matchResult);
            		System.out.println("equipos: " + matchTeams);
            		System.out.println("couta local: " + localCuote);
            		System.out.println("couta empate: " + drawCoute);
            		System.out.println("couta visitante: " + visitCuote);
            		matchTeams = matchTeams.replaceAll("[^a-zA-Z0-9\\s]", "");
            	
            		int matchTimerMinutes = 0;
        			int matchTimerSeconds = 0;
            		
            		if(matchTimer.trim().length()==5&&matchTimer.contains(":"))
            		{
            			String matchTimerMinutesStr = matchTimer.trim().substring(0, matchTimer.indexOf(":"));
            			String matchTimerSecondsStr = matchTimer.trim().substring(matchTimer.indexOf(":")+1, matchTimer.length());
            			System.out.println("minutos: " + matchTimerMinutes + " segundos: " + matchTimerSeconds);
            			matchTimerSeconds = Integer.parseInt(matchTimerSecondsStr);
            			matchTimerMinutes = Integer.parseInt(matchTimerMinutesStr);
            			
            		}
            		int localGoals = 0;
            		int visitGoals = 0;
            		if(matchResult.trim().length()>2&&matchResult.contains("-"))
            		{
            			String localResult = matchResult.trim().substring(0, matchResult.indexOf("-"));
            			String visitResult = matchResult.trim().substring(matchResult.indexOf("-")+1, matchResult.length());
            			System.out.println("local: " + localResult + " visitante: " + visitResult);
            			localGoals = Integer.parseInt(localResult);
            			visitGoals = Integer.parseInt(visitResult);
            		}
            		String matchTeamLocal = null;
            		String matchTeamVisit = null;
            		int matchTeamLocalId = 0;
            		int matchTeamVisitId = 0;
            		if(matchTeams.length() > 4 )//&& matchTeams.contains("?"))
            		{
            		matchTeamLocal = matchTeams.substring(0, matchTeams.indexOf("   "));
        			matchTeamVisit = matchTeams.substring(matchTeams.indexOf("   ")+3, matchTeams.length());
        			System.out.println("local: " + matchTeamLocal + " visitante: " + matchTeamVisit);
            		}
            		
            		if(matchTeamLocal != null){
            		 String query = "SELECT * FROM teams_info where team_name = '"+matchTeamLocal+"'";
            		// create the java statement
           	      Statement st = connection.createStatement();
           	      
           	   // execute the query, and get a java resultset
        	      ResultSet rs = st.executeQuery(query);
        	      // iterate through the java resultset
        	      
        	      boolean encontrado = false;
        	      while (rs.next())
        	      {
        	    	  matchTeamLocalId = rs.getInt("id_team");
        	    	  encontrado = true;
        	        
        	        // print the results
        	        System.out.format("Equipo encontrado!!: " + rs.getString("team_name"));
        	      }
        	      if(!encontrado)
        	      {
        	 	 String queryTeams = " insert into teams_info (team_name)"
      			        + " values (?)";
        	 	 // create the mysql insert preparedstatement
			   //   PreparedStatement preparedStmt = connection.prepareStatement(queryTeams);
        	 	PreparedStatement preparedStmt = connection.prepareStatement(queryTeams,
                        Statement.RETURN_GENERATED_KEYS);
			      preparedStmt.setString (1, matchTeamLocal);
			     

			      // execute the preparedstatement
			      preparedStmt.execute();
		
			        try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			            	matchTeamLocalId = generatedKeys.getInt(1);
			            }
			            else {
			                throw new SQLException("Creating user failed, no ID obtained.");
			            }
			        }
        	 	 
        	      }
            	}
            		if(matchTeamVisit != null){
               		 String query = "SELECT * FROM teams_info where team_name = '"+matchTeamVisit+"'";
               		// create the java statement
              	      Statement st = connection.createStatement();
              	   // execute the query, and get a java resultset
           	      ResultSet rs = st.executeQuery(query);
           	      // iterate through the java resultset
           	      boolean encontrado = false;
           	      while (rs.next())
           	      {
           	    	matchTeamVisitId = rs.getInt("id_team");
           	    	  encontrado = true;
           	        
           	        // print the results
           	        System.out.format("Equipo encontrado!!: " + rs.getString("team_name"));
           	      }
           	      if(!encontrado)
           	      {
           	 	 String queryTeams = " insert into teams_info (team_name)"
         			        + " values (?)";
           	 	 // create the mysql insert preparedstatement
           	 	PreparedStatement preparedStmt = connection.prepareStatement(queryTeams,
                        Statement.RETURN_GENERATED_KEYS);
   			      preparedStmt.setString (1, matchTeamVisit);
   			     

   			      // execute the preparedstatement
   			      preparedStmt.execute();
   			   try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	matchTeamVisitId = generatedKeys.getInt(1);
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }
           	 	 
           	      }
               	}
            	      int matchId = 0;
            	     
            	  if(matchTeamLocalId > 0 && matchTeamVisitId > 0)
            	  {
            		  String query = "SELECT * FROM match_event where local_team = '"+matchTeamLocalId+"' and visit_team = '"+matchTeamVisitId+"'";
            		  //pendiente de comprobar fecha
            		  Statement st = connection.createStatement();
               	      ResultSet rs = st.executeQuery(query);
               	      // iterate through the java resultset
               	      boolean encontrado = false;
               	      while (rs.next())
               	      {
               	    	matchId = rs.getInt("id_event");
               	    	  encontrado = true;
               	        
               	        // print the results
               	        System.out.format("Partido encontrado!! ");
               	      }

            		  if(!encontrado)
            		  {
            		  String queryMatch = " insert into match_event (local_team, visit_team, id_type_event)"
          			        + " values (?, ?,?)";
            		  // create the mysql insert preparedstatement
                 	 	PreparedStatement preparedStmt = connection.prepareStatement(queryMatch,
                              Statement.RETURN_GENERATED_KEYS);
         			      preparedStmt.setInt (1, matchTeamLocalId);
         			     preparedStmt.setInt (2, matchTeamVisitId);
         			    preparedStmt.setInt (3, idTipoEvento);
         			    

         			      // execute the preparedstatement
         			      preparedStmt.execute();
         			   try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
      		            if (generatedKeys.next()) {
      		            	matchId = generatedKeys.getInt(1);
      		            }
      		            else {
      		                throw new SQLException("Creating user failed, no ID obtained.");
      		            }
      		        }
                 	 	 
            		  }
            	  }
            	    
            	      
            	      
            		
            	if(matchId > 0)
            	{
            		 String queryMatchStatus = " insert into match_status (TIME_MINUTES, TIME_seconds,id_match, coute_1, coute_x, coute_2, goals_local,goals_visit)"
            			        + " values (?, ?,?,?,?,?,?,?)";

            			      // create the mysql insert preparedstatement
            			      PreparedStatement preparedStmt = connection.prepareStatement(queryMatchStatus);
            			      preparedStmt.setInt (1, matchTimerMinutes);
            			      preparedStmt.setInt    (2, matchTimerSeconds);
            			      preparedStmt.setInt    (3, matchId);
            			      preparedStmt.setDouble(4, localCuote);
            			      preparedStmt.setDouble(5, drawCoute);
            			      preparedStmt.setDouble(6, visitCuote);
            			      preparedStmt.setInt    (7, localGoals);
            			      preparedStmt.setInt    (8, visitGoals);
            			      // execute the preparedstatement
            			      preparedStmt.execute();
            			      
            			      for(PatternsBean patron : Patrones)
            			      {
            			    	  //ITERAMOS TODOS LOS PATRONES Y LOS COMPROBAMOS
            			      }
                }
            	
            }

            }
           
               /* ResultSetMetaData md = rs.getMetaData();
                int cc = md.getColumnCount();

            	while (rs.next()){
            		for (int c = 1; c<= cc; c++){
            			System.out.println(md.getColumnLabel(c) + ": " + rs.getObject(c));
            		}
            	System.out.println("-----");
            	}
               */
               
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                throw new IllegalStateException("Cannot connect the database!", e);
            }
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable yourRunnable = new Runnable() {
			
			@Override
			public void run() {
				 System.out.println("Lanzamoooooooos tarea!!!!");
				
			}
		};
	//	scheduler.scheduleAtFixedRate(yourRunnable, 1, 2, TimeUnit.SECONDS);

		     }
    
    

}
