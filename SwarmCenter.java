import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * In the swarmcenter the mosquitoes swarm around. The swarmcenter is a bush.
 * The actors represent the image and position.
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class SwarmCenter extends Actor
{
    private int x = 0;
    private int y = 0;
    
    public SwarmCenter(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        
    }
    
    /**
     * Returns the x-coordinate of the swarmcenter.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the y-coordinate of the swarmcenter.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Act - do whatever the SwarmCenter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // does nothing.
    }
}
