import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The village is where the females take a bloodmeal.
 * The actors represent the image and position.
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class Village extends Actor
{
    private int x = 0;
    private int y = 0;
    
    public Village(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        
    }
    
    /**
     * Returns the x-coordinate of the village.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the y-cordinate of the village.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Act - do whatever the Village wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // does nothing.
    }
}
