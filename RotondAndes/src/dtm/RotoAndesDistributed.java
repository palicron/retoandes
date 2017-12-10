package dtm;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import jms.AllRotondasMDB;
import tm.RotondAndesTm;




public class RotoAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotoAndesDistributed instance;
	
	private RotondAndesTm tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private AllRotondasMDB allRotoandesMD;
	private static String path;


	private RotoAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allRotoandesMD = new AllRotondasMDB(factory, ctx);
		allRotoandesMD.start();
		
	}
	
	public void stop() throws JMSException
	{
		
	}
	
	
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTm tm)
	{
	   this.tm = tm;
	}
	
	private static RotoAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotoAndesDistributed getInstance(RotondAndesTm tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotoAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotoAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTm tm = new RotondAndesTm(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTm tm = new RotondAndesTm(path);
		return getInstance(tm);
	}
	

	

}


