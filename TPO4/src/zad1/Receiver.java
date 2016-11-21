package zad1;

import javax.jms.*;
import javax.naming.*;


public class Receiver {

	Connection con = null; 
	String msg = ""; 
	
	public Receiver (String admDestinationName) 
	{
		try{
			Context ctx =  new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
			Destination destination = (Destination) ctx.lookup(admDestinationName);
			con = factory.createConnection();
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer receiver = session.createConsumer(destination);
			
			con.start();
			System.out.println("receiver started");
			Message message = receiver.receive();
			
			if(message instanceof Message)
			{
				TextMessage text = (TextMessage) message;
				msg = text.getText();
				System.out.println("Reseived: " + text.getText());
			}else if (message != null)
			{
				System.out.println("Received non text message");
			}
		}catch(Exception exc)
		{
			exc.printStackTrace();
			System.exit(1);
		}finally {
			if(con != null)
			{
				try{
					con.close();
				}catch (JMSException exc)
				{
					System.err.println(exc);
				}
			}
		}
		
	}
	
	public String getMSG() {
		return msg; 		
	}
}
