



abstract class Starter$$Base {
	
	public static void main(String args[]) 
	{

		System.out.print("starting ");

	}

	

}



public class Starter extends  Starter$$Base  
{
	
	public static void main(String args[]) 
	{
		Starter$$Base.main(args);
		System.out.println("server... ");
		
		new ServerGui();

	}
	
	
}