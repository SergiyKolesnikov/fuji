package common; 

import java.io.*; 

public  class  Logging {
	
	protected PrintWriter mOut;

	
	
	public Boolean open(String aFilepath)
	{
		try
		{
			// Versuchen, die angegebene Zieldatei zu �ffnen.
			File tmp = new File(aFilepath);
			Integer n = 0;
			while( !tmp.canWrite() && tmp.exists() ) 
			{
				tmp = new File(aFilepath + "-" + n.toString());
				System.out.println(tmp.getAbsolutePath());
				n++;
			}

			mOut  = new PrintWriter(new FileWriter(tmp.getAbsolutePath()));
			
			// Erfolg
			return true;
		}
		catch(IOException e)
		{
			// Kein Erfolg
			return false;
		}
	}

	
	
	public void log(String aText)
	{
		mOut.print(aText);
		mOut.flush();
	}

	
	
	public void close()
	{
		mOut.close();
	}


}
