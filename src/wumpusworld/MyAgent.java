package wumpusworld;
import java.io.*;
/**
 * Contains starting code for creating your own Wumpus World agent.
 * Currently the agent only make a random decision each turn.
 * 
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent
{
    private World w;
    int rnd;
    
    /**
     * Creates a new instance of your solver agent.
     * 
     * @param world Current world state 
     */
    public MyAgent(World world)
    {
        w = world;   
    }
    
            
    /**
     * Asks your solver agent to execute an action.
     */


    public void doAction()
    {
        //Location of the player
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();
        // created an object for the AI model of the Wumpus Game.
        AI_Model aIModel = new AI_Model();
        
        //Basic action:
        //Grab Gold if we can.
        if (w.hasGlitter(cX, cY))
        {
            w.doAction(World.A_GRAB);
            aIModel.adjecent_list_deletion();
            aIModel.X_Y_Resetting();
            return;
        }
        
        //Basic action:
        //We are in a pit. Climb up.
        if (w.isInPit())
        {
            w.doAction(World.A_CLIMB);
            return;
        }
        // Outputs the action breeze if found in a block and records the action by incrementing it.
        if (w.hasBreeze(cX, cY))
        {
            System.out.println("I am in a Breeze");
            boolean xCordadj=(cX==aIModel.getting_the_adjecent_X());
            boolean yCordadj=(cY==aIModel.getting_the_adjecent_Y());
            int breezeFound=(xCordadj && yCordadj )?1:0;
            switch(breezeFound) {
            case 1:
            	aIModel.breeze_Incrementation();
            }
        }
        // Outputs the action stench if found in a block and records the action by incrementing it.
        if (w.hasStench(cX, cY))
        {   boolean xCordadj=(cX==aIModel.getting_the_adjecent_X());
            boolean yCordadj=(cY==aIModel.getting_the_adjecent_Y());
            int stenchFound=(xCordadj && yCordadj)?1:0;
            switch(stenchFound)
            {
            case 1:
               System.out.println("I am in a Stench");
               aIModel.stench_Incrementation();
            }
        }
        if (w.hasPit(cX, cY))
        {
            System.out.println("I am in a Pit");
            aIModel.pit_Incrementation();
        }
        if (w.getDirection() == World.DIR_RIGHT)
        {
            System.out.println("I am facing Right");
        }
        if (w.getDirection() == World.DIR_LEFT)
        {
            System.out.println("I am facing Left");
        }
        if (w.getDirection() == World.DIR_UP)
        {
            System.out.println("I am facing Up");
        }
        if (w.getDirection() == World.DIR_DOWN)
        {
            System.out.println("I am facing Down");
        }
        
         rnd = aIModel.get_path(w);
         
        
        if (rnd==0)
        {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }
        
        if (rnd==1)
        {
            w.doAction(World.A_MOVE);
        }
                
        if (rnd==2)
        {
            w.doAction(World.A_TURN_LEFT);   
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);

        }
                        
        if (rnd==3)
        {
            w.doAction(World.A_TURN_RIGHT);
            w.doAction(World.A_MOVE);
  
        }
        // Used for checking whether the Wumpus is in the block or not.
        int wumpusCheck=(w.hasWumpus(w.getPlayerX(), w.getPlayerY()))?1:0;
        // if Wumpus is present then it is recorded and below procedure is implemented.
        switch(wumpusCheck)
        {
        case 1:
           aIModel.wumpus_Incrementation();
           aIModel.adjecent_list_deletion();
           aIModel.X_Y_Resetting();
        }
                
    }    
     /**
     * Genertes a random instruction for the Agent.
     */
    public int decideRandomMove()
    {
      return (int)(Math.random() * 4);
    }
    
    
}

