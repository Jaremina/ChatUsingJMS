package zad1;

import javax.jms.*;
import javax.naming.*;


public class Sender {
   
	Connection con = null;
	
	public Sender (String admDestinationName, String message)
	{
		try {
			Context context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Destination destination = (Destination) context.lookup(admDestinationName);
			con = factory.createConnection();
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer sender = session.createProducer(destination);
			
			con.start();
			TextMessage msg =  session.createTextMessage();
			msg.setText(message);
			sender.send(msg);
			System.out.println("Sender send message " + message);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}finally{
			if(con != null)
			{
				try {
					con.close();
				} catch (JMSException e) {
					System.err.println(e);
				}
			}
		}
	}
}
