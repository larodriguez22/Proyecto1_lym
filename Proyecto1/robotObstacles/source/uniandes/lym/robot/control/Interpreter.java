package uniandes.lym.robot.control;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import uniandes.lym.robot.kernel.*;



/**
 * Receives commands and relays them to the Robot. 
 */

public class Interpreter   {

	/**
	 * Robot's world
	 */
	private RobotWorldDec world;
	
	StringBuffer output=new StringBuffer("SYSTEM RESPONSE: -->\n");


	public Interpreter()
	{
	}


	/**
	 * Creates a new interpreter for a given world
	 * @param world 
	 */


	public Interpreter(RobotWorld mundo)
	{
		this.world =  (RobotWorldDec) mundo;

	}


	/**
	 * sets a the world
	 * @param world 
	 */

	public void setWorld(RobotWorld m) 
	{
		world = (RobotWorldDec) m;

	}



	/**
	 *  Processes a sequence of commands. A command is a letter  followed by a ";"
	 *  The command can be:
	 *  M:  moves forward
	 *  R:  turns right
	 *  
	 * @param input Contiene una cadena de texto enviada para ser interpretada
	 */

	public String process(String input) throws Error
	{   


			

		int i;
		int n;
		boolean ok = true;
		n= input.length();

		if(input.contains("ROBOT_R"))
		{
			ROBOT_R(input);
		}
		else{

			i  = 0;
			try	    {
				while (i < n &&  ok) {
					switch (input.charAt(i)) {
					case 'L': moveinDir("south", 4); output.append("move \n");break;
					case 'M': world.moveForward(1); output.append("move \n");break;
					case 'R': world.turnRight(); output.append("turnRignt \n");break;
					case 'C': world.putChips(1); output.append("putChip \n");break;
					case 'B': world.putBalloons(1); output.append("putBalloon \n");break;
					case  'c': world.pickChips(1); output.append("getChip \n");break;
					case  'b': world.grabBalloons(1); output.append("getBalloon \n");break;
					default: output.append(" Unrecognized command:  "+ input.charAt(i)); ok=false;
					}

					if (ok) {
						if  (i+1 == n)  { output.append("expected ';' ; found end of input; ");  ok = false ;}
						else if (input.charAt(i+1) == ';') 
						{
							i= i+2;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								System.err.format("IOException: %s%n", e);
							}

						}
						else {output.append(" Expecting ;  found: "+ input.charAt(i+1)); ok=false;
						}
					}

				}

			}
			catch (Error e ){
				output.append("Error!!!  "+e.getMessage());

			}
		}

		return output.toString();
	}

	public void ROBOT_R(String cadena){
		String arreglo[]=cadena.split("ROBOT_R");
		int i=1;
		
		
		while (i < arreglo.length) {
			String [] var;
			String a2[]=arreglo[i].split("VARS");
			String a3[]=a2[1].split("BEGIN");
			// VARS
			if(arreglo[i].contains("VARS")){
				
				String a4[]=a3[0].split(",");
				var=a4;
				
			}
			
		}
	}
	
	public int varval(String a, String[] var)
	{
		int k=0;
		for(int i=0; i<var.length; i++)
		{
			if(var[i]==a){
				k=i;
			}
		}
		return k;
	}
	
	public void assign(String name, int n, String[] var, int[] val)
	{
		val[varval(name, var)]=n;		
	}
	
	public void move(int n)
	{
		world.moveForward(n); output.append("move"+n+"\n");
	}
	
	public void turn(String uribito)
	{
		if(uribito=="right")
		{
			world.turnRight(); output.append("turnRigt \n");
		}
		if(uribito=="around")
		{
			world.turnRight(); world.turnRight(); output.append("around \n");
		}
		if(uribito=="left")
		{
			world.turnRight(); world.turnRight(); world.turnRight(); output.append("left \n");
		}
	}
	
	public void face(String petro)
	{
		int corona=-1;
		if(petro=="north"){
			corona=0;
		}
		if(petro=="south"){
			corona=1;
		}
		if(petro=="east"){
			corona=2;
		}
		if(petro=="west"){
			corona=3;
		}
		while(world.getFacing()!=corona)
		{
			world.turnRight(); 
		}
		output.append("face"+petro+"\n");
		
	}
	
	public void put(int val, String x)
	{
		if(x=="chips")
		{
			world.putChips(val); output.append("put"+x+" "+val+"\n");
		}
		if(x=="baloons")
		{
			world.putBalloons(val); output.append("put"+x+" "+val+"\n");
		}
		
	}
	
	public void pick(int val, String x)
	{
		if(x=="chips")
		{
			world.pickChips(val); output.append("put"+x+" "+val+"\n");
		}
		if(x=="baloons")
		{
			for(int i=0; i<val;i++){
				world.pickupBalloon(); 
			}
			output.append("put"+x+" "+val+"\n");
		}
		
	}
	
	public void moveDir(String elTibio, int n)
	{
		for(int i=0; i<n; i++){
			if(elTibio=="left"){
				world.left(); 
			}
			if(elTibio=="right"){
				world.right(); 
			}
			if(elTibio=="front"){
				world.up(); 
			}
			if(elTibio=="back"){
				world.down(); 
			}
			
		}
		output.append("moveDir"+n+"\n");
		
	}
	
	public void moveinDir(String elTibio, int n)
	{
		face(elTibio);
		move(n); output.append("move"+n+"\n");
		
	}
	
	public void skip()
	{
	}

	
	
	
	
	
	
	
	
	
	



}
