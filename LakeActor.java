import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Lake actor is where the mosquitoes females lay eggs and where the aquatic stages lives.
 * The actors represent the image and the position.
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class LakeActor extends Actor
{
    private int x = 0;
    private int y = 0;
    private final Lake lake;
    
    public LakeActor(final Lake lake, int x, int y) {
        super();
        this.x = x;
        this.y = y;
        
        this.lake = lake;
    }
    
    /**
     * Returns the x-coordinate of the lake.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the y-coordinate of the lake.
     */
    public int getY() {
        return y;
    }
        
    /**
     * Act - do whatever the Lake wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Here comes the showText action because here it is later activated than the WorldActor. 
        ((MyWorld)getWorld()).showText();
        
        
    }
}

