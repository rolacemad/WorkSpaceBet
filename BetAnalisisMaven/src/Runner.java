import java.util.TimerTask;

public class Runner extends TimerTask  {
	
	public interface Runnable {
	    public abstract void run();
	}

	   public Runner(){
		   

	     //Constructor

	   }

	   public void run() {
	    try {

	         System.out.println("Lanzamoooooooos tarea!!!!");

	            } catch (Exception ex) {

	        System.out.println("error running thread " + ex.getMessage());
	    }

}
}
