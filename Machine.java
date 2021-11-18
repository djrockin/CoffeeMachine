
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//This class mimics the behavior of a coffee machine
//We create a thread pool with number of threads equal to the number of outlets
public class Machine {
	private int outlets;
	private static Machine machineInstance=null;
	private ExecutorService pool;
	 
	private Machine(int outlets)
	{
		this.outlets=outlets;
		pool=Executors.newFixedThreadPool(this.outlets);
	}
	public static Machine initMachine(int outlets)
	{
		//initializes the instance variable
		if(machineInstance==null)
		{
		//create the object	
		machineInstance=new Machine(outlets);
		}
		return machineInstance;
	}
	public static Machine getInstance()
	{
		return machineInstance;
	}
	public void process(Beverage beverageName) {
		//pool the requests
	    pool.execute(beverageName);
	}	
	public void reset()
	{
		pool.shutdown();
	}
}
