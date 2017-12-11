package dtm;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;

import jms.AllRestauranDelete;
import jms.AllRotondasMDB;
import tm.RotondAndesTm;
import vos.Items;
import vos.ListaProductos;



public class RotoAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotoAndesDistributed instance;
	
	private RotondAndesTm tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private AllRotondasMDB allRotoandesMD;
	private AllRestauranDelete delete;
	private static String path;
	

	private RotoAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allRotoandesMD = new AllRotondasMDB(factory, ctx);
		allRotoandesMD.start();
		delete = new AllRestauranDelete(factory, ctx);
		
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
	
    public ListaProductos getLocalitmes()
    {
    	ListaProductos rs = null ;
    	try {
			rs =  tm.darItems();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    public ListaProductos getallitmes()
    {
    	ListaProductos rs = null;
    	try {
			rs = allRotoandesMD.getRemoteItems();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    
    public void deleteres(String id)
    {
    	int rs = 0;
    	try {
			 delete.DeleteRemoteRes(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    public void deletereslocal(String id)
    {
    
    	try {
			 tm.eliminarRestaurante(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
	

}


