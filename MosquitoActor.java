import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;


/**
 * In the mosquitoActor the behavior is progammed and were are the parameters as mortality are used.  
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class MosquitoActor extends Actor {
    // The mosquitoActor have the parameters of the mosquito. Mosquito is connected to the mosquitospecies.
    private Mosquito mosquito;
    // The gotoLakeX and -Y are where the females will fly to lay eggs.
    private int gotoLakeX;
    private int gotoLakeY;
    // The beginbiomass of the larvae, used for the first time to calculate the carrying capacity.. 
    public static float larvaeBiomassInMilligram = Anophelesgambiae.MAX_BIOMASS_IN_MILLIGRAM;
    // The time the swarm starts.
    public static final int START_TIME_OF_SWARM = 12;
    // The time the swarm ends.
    public static final int END_TIME_OF_SWARM = 15;
    // The hapiness increase per tick if they are out of swarm range. Determents the density of the mosquito swarm.
    public static final int HAPPINESS_INCREASE = 12;
    
    public MosquitoActor(final Mosquito mosquito)
    {
       super();
        
       this.mosquito = mosquito;
       // The function to set the image. 
       setImage(mosquito.getImage());
       // The mosquitoes will look a random direction if they enter the world.
       begin_direction();
       
       
    }
    
    /**
     * De first random direction a mosquito looks if they enter the world.
     */
    private void begin_direction()
    {
        // The mosquito looks a random direction of 360 degrees. 
        turn(Greenfoot.getRandomNumber(360));
    }
   
    /**
     * Act - do whatever the mosquitoActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {    
       // This image will be set to update the image as example they grow from egg to larva.
       setImage(mosquito.getImage());
       // Take the humdity from the world. The worldparameters are explained in the world.
       float relativeHumidity = MyWorld.relativeHumidity;
       // Take the cloudcover from the world.
       float cloudCover = MyWorld.getCloudCover(relativeHumidity);
       // Take the absorbedsunlight coeficient of the lake. The lakeparameters are explained in the lake. 
       float absorbedSunlight = Lake.getAbsorbedSunlight(cloudCover);
       // Take the watervolume in kL of the lake.
       float waterVolumeInKiloLiters = Lake.getWaterVolumeInKiloLiters();
       // Take the difference between de air- and watertemperature in celsius degrees.
       float differenceBetweenAirAndWaterTemperatureInDegrees = Lake.getDifferenceBetweenAirAndWaterTemperatureInDegrees(waterVolumeInKiloLiters,absorbedSunlight);
       // Take the airtemperature in degrees celsius. Have an impact of the adults and lay egg function.
       float airTemperatureInDegrees = getMyWorld().getAirTemperatureInDegrees();
       // The watertemeprature in degrees celsius have an impact for the aquatic stages. To calculate the watertemperature from the airtemperature we need 
       // de humidity and watervolume as important factors for the watertemperature. That is why we need it in mosquitoActor.
       float waterTemperatureInDegrees = Lake.getWaterTemperatureInDegrees(differenceBetweenAirAndWaterTemperatureInDegrees,airTemperatureInDegrees);
       // Take the egg duration in days of the mosquito. They are not in the if statements, because de larvae duration is dependent of the larvae duration what is
       // dependent of the egg duration. Thus it is needded for all aquatic stages.
       double eggDurationInDays = mosquito.getEggDurationInDays(waterTemperatureInDegrees);
       // Take the larvae duration in days of the mosquito.
       double larvaeDurationInDays = mosquito.getLarvaeDurationInDays(waterTemperatureInDegrees);
       // Take the pupae duration in days of the mosquito.
       double pupaeDurationInDays = mosquito.getPupaeDurationInDays(waterTemperatureInDegrees);
       
       
       // For all mosquitoes in the egg stage.  
       if (mosquito.getAge() == Age.Egg ) {
          // Each tick the eggtime increase with one.
          mosquito.tickEggAge();
          // Take the per capita mortality per day of the eggs from mosquito.  
          float perCapitaEggMortalityRatePerDay = mosquito.getPerCapitaEggMortalityRatePerDay();
          // Greenfoot works with thicks, so to use mortality there is used a mortality for each tick. 
          double chanceOfEggDeadPerTick = mosquito.getChanceOfEggDeadPerTick(perCapitaEggMortalityRatePerDay,getMinutesPerTick()); 
          
          // The reason there the chance of both is multiplied with a big number is because the random geneator can only generate int numbers.
          // So the chance for egg dead per tick is bigger because it can have a nmuber of 0 very easy. Now it is more difficult to generate a smaller number than the
          // chance of egg to die per tick.
          if (Greenfoot.getRandomNumber(1*100000) <= (chanceOfEggDeadPerTick*100000) ) {
             getWorld().removeObject(this);
             return;
          }
          
          // The mosquito will be a larvae if the duration in ticks is equal as the egg duration in ticks.
          if (mosquito.getTicksInEggAge() >= (mosquito.getEggDurationInDays(waterTemperatureInDegrees) * 24 *
                                                    ( 60/getMinutesPerTick()) ) ) {
                // Not all egg hatch, some egg will not hatch and are than seen as dead eggs.                                        
                if ( Greenfoot.getRandomNumber(100) <= 100 * mosquito.getProportionToHatch(waterTemperatureInDegrees) ) {
                   mosquito.setAge(Age.Larvae);
                   return;
                } else {
                   getWorld().removeObject(this);
                   return;
                }
                 
          }
          return; 
       }
              
       
       // For all mosquitoes in the larva stage.         
       if (mosquito.getAge() == Age.Larvae) {
          // True if the LakeActor is in range.
          List<LakeActor> inRange = getObjectsInRange(140, LakeActor.class);
          mosquito.tickLarvaeAge();
          
          // Take the per capita mortality per day of the larvae from mosquito that is not age-dependent.
          double perCapitaAgeIndependentLarvaeMortalityRatePerDay = mosquito.getPerCapitaAgeIndependentLarvaeMortalityRatePerDay(waterTemperatureInDegrees);
          // Take the chance to die for larvae per tick.   
          double chanceOfLarvaeAgeIndependentDeadPerTick = mosquito.getChanceOfLarvaeAgeIndependentDeadPerTick(perCapitaAgeIndependentLarvaeMortalityRatePerDay
                                                                                                               , getMinutesPerTick() );
          // Take the mean biomass of the mosquito adult.                                                    
          float meanBiomassInMilligram = mosquito.getMeanBiomassInMilligram();
          // Take the maximum biomass in each m2 in milligram that the lake can handle.
          float maxTotalLarvaeBiomassInMilligramPerSquareMeter = Lake.getMaxTotalLarvaeBiomassInMilligramPerSquareMeter(meanBiomassInMilligram);
          // Take the waterbody carrying capacity what represent how many biomass in total the lake can handle.
          double waterbodyCarryingCapacity = Lake.getWaterBodyCarryingCapacity(maxTotalLarvaeBiomassInMilligramPerSquareMeter);
          // Take the larvaepopulation size from the world.
          int larvaePopulationSize = getMyWorld().getTotalPopulationCount(Age.Larvae);
          // Take the total larvae biomass in mg in the lake. 
          double totalLarvaeBiomassInMilligram = Lake.getTotalLarvaeBiomassInMilligram(larvaeBiomassInMilligram,larvaePopulationSize);
          // Take the biomass in mg for each larvae. This value is lower with more larvae, because there is more competition for food.
          double larvaeBiomassInMilligram = Lake.getLarvaeBiomassInMilligram(totalLarvaeBiomassInMilligram, waterbodyCarryingCapacity);
          // Take the capita mortality rate what is dependent of the density of the lake.
          double perCapitaDensityDependentLarvalMortalityRatePerDay = Lake.getPerCapitaDensityDependentLarvalMortalityRatePerDay(totalLarvaeBiomassInMilligram, 
                                                                                                                              waterbodyCarryingCapacity);
          // Take the chance to die in each tick for the larvae. 
          double chanceOfCapitaDensityDependentLarvalDeadPerTick = 
          mosquito.getChanceOfDensityDependentLarvalDeadPerTick(perCapitaDensityDependentLarvalMortalityRatePerDay,
                                                             getMinutesPerTick());
          // The chance that a larvae die per tick is the sum of the chance of densitydependent larvae dead per tick and constant larvae dead per tick.
          if ( Greenfoot.getRandomNumber(1 * 100000) <= ( (chanceOfCapitaDensityDependentLarvalDeadPerTick+chanceOfLarvaeAgeIndependentDeadPerTick) * 100000 ) ) {
              getWorld().removeObject(this);
              return;
          }
          
          // In extreme situations the new larvae population of eggs is bigger than the carrying capacity what results to a negative larvaebiomass.
          // Normally before even the larvae population can reach the carrying capacity value the dead rate is very high, but with low
          // carrying capacity the new larvae from eggs is already bigger than the carrying capacity. To correct this all larvae are killed such as the equations says.
          if (larvaeBiomassInMilligram < 0) {
             getWorld().removeObject(this);
             return;    
          }
          
          // The mosquito will be a pupa if the duration in ticks is equal as the larvae duration in ticks. 
          if ( mosquito.getTicksInLarvaeAge() >= larvaeDurationInDays * 24 * (60 / getMinutesPerTick()) ){
                 mosquito.setAge(Age.Pupae);
                 return;
          }
          
          // If the larvae is not in range of the Lake, they swim to center. But if the ranges is good they have a chance to swim in another direction.
          if ( inRange.size() == 0 ) {
             turnTowards( getMyWorld().getLakeActor().getX(), getMyWorld().getLakeActor().getY() );         
          } else {
             
             if (Greenfoot.getRandomNumber(100) < 2 * getMinutesPerTick() ) {
                // Larvae turn in a random time and direction.
                turn(Greenfoot.getRandomNumber(360));
             }
          
          }
          // The movement speed of the larvae. Is 1/6 of the adult fly speed. 
          move(Math.round(mosquito.getPixelMovePerTick()/6));
          return;
       }
       
       // For all mosquitoes in the pupa stage.
       if ( mosquito.getAge() == Age.Pupae ) {
          // Each tick the pupa tick increase with 1.
          mosquito.tickPupaeAge();
          // Take the per capita mortality of the pupae.
          float perCapitaPupalMortalityRatePerDay = mosquito.getPerCapitaPupalMortalityRatePerDay();
          // Take the chance that the pupa die per tick.
          double chanceOfPupaeDeadPerTick = mosquito.getChanceOfPupaeDeadPerTick(perCapitaPupalMortalityRatePerDay, getMinutesPerTick() );
          
          // The chance that a pupal will die.
          if (Greenfoot.getRandomNumber(1 * 100000) <= (chanceOfPupaeDeadPerTick * 100000) ) {
              getWorld().removeObject(this);
              return;
          }
          
          // The mosquito will be an adult if the duration in ticks is equal as the pupal duration in ticks. 
          if ( mosquito.getTicksInPupaeAge() >= pupaeDurationInDays * 24 * (60 / getMinutesPerTick() ) ) {
                mosquito.setAge(Age.Adult);
          }           
          return;
       }
       
       // For all mosquitoes in the adult stage, there is difference for mosquitoes in behavior.
       // Females will take a bloodmeal if they have laid eggs. If they took blood they can lay eggs when they mate.
       // Each day there is a time the mosquitoes mate with each other. 
       if (mosquito.getAge() == Age.Adult) {
             // Each tick the adult tick increase with 1.
             mosquito.tickAdultAge();
             // Take the per capita mortality per day with age 0.
             double perCapitaAgeIndependentAdultMortalityRatePerDay = mosquito.getPerCapitaAgeIndependentAdultMortalityRatePerDay(airTemperatureInDegrees);
             // Take the age of this mosquito in ticks.
             float daysInImagoAge = (mosquito.getTicksInAdultAge()*getMinutesPerTick()/(24*60 ) );
             // Take the per capita mortality of the adults with age dependency.
             double perCapitaAgeDependentAdultMortalityRatePerDay = mosquito.getPerCapitaAgeDependentMortalityRatePerDay(perCapitaAgeIndependentAdultMortalityRatePerDay
                                                                                                                         , daysInImagoAge);
             // Take the chance to die for adults in each tick.
             double chanceOfAdultAgeDependentDeadPerTick = mosquito.getChanceOfAdultAgeDependentDeadPerTick(
                                                                  perCapitaAgeDependentAdultMortalityRatePerDay, getMinutesPerTick());
             // The chance to die for each adult.
             if (Greenfoot.getRandomNumber(1 * 100000) < (chanceOfAdultAgeDependentDeadPerTick * 100000) ) {
                   getWorld().removeObject(this);
                   return;
             } 
             
       }
       // For females the function to set the behavior for searching a host.   
       calculateBlood();
       // For females the function to set the behavior for laying eggs.
       calculateEggs();
       // For all adults the function to swarm in a specific time. 
       calculateHappy();
    }
       
    /**
     * Mosquitoes moves random if they have not eggs, blood and are happy.
     */
    private void movement()
    {
       if (mosquito.needsBlood() || mosquito.haveEggs() || !mosquito.isHappy() ) {
          return;    
       }
       
       // By the edge they turn so the can move again.
       if (isAtEdge())
       {
          turn(90); 
       }
       
       // Mosquitoes fly with a specific speed.
       move(mosquito.getPixelMovePerTick());
       
       // Mosquitoes turn in a random direction and random time.
       if (Greenfoot.getRandomNumber(100) < 2 * getMinutesPerTick() ) {
          turn(Greenfoot.getRandomNumber(360));
       }
    }
    
    /**
     * De calculate happy function make mosquitoes go to swarm in a specific day. If they feel happy they fly a bit of the swarmcenter, but if they feel not happy
     * they fly back to the swarmcenter. Mosquitoes who need blood do not swarm.
     */
    public void calculateHappy() {
       if (mosquito.needsBlood() || mosquito.haveEggs() ) {
          return; 
       }
       
       // On the specific time to set the swarm to sart and end.
       if ( getMyWorld().getWorldTimeInTicksOfTheDay() >= ( START_TIME_OF_SWARM * (60/getMinutesPerTick() ) ) && 
                (getMyWorld().getWorldTimeInTicksOfTheDay() <= ( END_TIME_OF_SWARM * (60/getMinutesPerTick()) ) )) {
          // The range from the mosquito to the lake. If inRange.size() == 0 then the mosquito is out of range.           
          int range = 4 * getMinutesPerTick();
          List<SwarmCenter> inRange = getObjectsInRange(range, SwarmCenter.class);
          if( inRange.size() == 0 ) {
             mosquito.adjustHappy(-2 * getMinutesPerTick() );
          } else {
               mosquito.adjustHappy( HAPPINESS_INCREASE * getMinutesPerTick() );
               } 
       } else {
           // To make the mosquito happy on the rest of the day.
           mosquito.setHappy(1);
           
       }
       
       // If the mosquito is happy they do the movement function. If they are not happy they go to the swarm, even faster. 
       if (mosquito.isHappy() ) {
          movement();    
       } else {
            turnTowards(getMyWorld().getSwarmCenter().getX(),getMyWorld().getSwarmCenter().getY());
            move(2*mosquito.getPixelMovePerTick()); 
       }
       
       
    }
    
    /**
     * De calculate blood function determents of a female needs blood. If a mosquito needs blood she go to the village. In the range she will sit still and take blood
     * from a human host.
     */
    private void calculateBlood() {
        if (mosquito.needsBlood() ) {
           // Multiply with minutes per tick to compensate for more speed. 
           List<Village> inRange = getObjectsInRange( 3 * ( getMinutesPerTick() ), Village.class);
           
           if (inRange.size() > 0 ) {
              
              if (Greenfoot.getRandomNumber(1000) < getMinutesPerTick()) {
                 // Take blood from human host.
                 mosquito.setNeedsBlood(false);
              }
              
           }   else {
                  // Go to the village.
                  turnTowards(getMyWorld().getVillage().getX(),getMyWorld().getVillage().getY());
                  move(mosquito.getPixelMovePerTick());
               }
        }
        
    }
    
    /**
     * The calculate eggs function determents when the mosquitoes will have eggs.
     */
    private void calculateEggs() { 
       // The females will have a chance to have eggs at the end of the swarm.
       if ((getMyWorld().getWorldTimeInTicksOfTheDay()) == ( END_TIME_OF_SWARM * (60/getMinutesPerTick())) ) {
          // At the end of the swarm some females may mate and will have eggs.
          if ( !mosquito.needsBlood() && !mosquito.haveEggs() ) {
             // Take the population size of females.
             int totalAdultFemalePopulationSize = getMyWorld().getTotalGenderSpecificPopulationCount(Age.Adult, Gender.Female);
             // Take the population size of males.
             int totalAdultMalePopulationSize = getMyWorld().getTotalGenderSpecificPopulationCount(Age.Adult, Gender.Male);
             // Take the chance that a female mate with a male. It is assumed that each female and male can mate one time per day.
             float mateChanceForFemale = getMyWorld().getMateChanceOfFemale(totalAdultMalePopulationSize,totalAdultFemalePopulationSize);
             // Take the population size of males with gene drive. 
             int adultMalePopulationSizeWithGeneDrive = getMyWorld().getFrankensteinGenderSpecificPopulationCount(Age.Adult,Gender.Male);
             // Take the proportion of males who have the gene drive.
             float proportionOfAdultMalesWhoHaveGeneDrive = getMyWorld().getProportionOfAdultMalesWhoHaveGeneDrive(adultMalePopulationSizeWithGeneDrive
                                                                                                               , totalAdultMalePopulationSize);
             // Take the chance of a female to mate with a gene drive male.                                                                                                  
             float mateChanceOfFemaleForGeneDriveMales = getMyWorld().getMateChanceOfFemaleForGeneDriveMales(mateChanceForFemale,proportionOfAdultMalesWhoHaveGeneDrive);
             
             // The Frankenstein females have always Frankenstein eggs. The wild type females only have Frankenstein eggs if they mate with
             // a Frankenstein male. The chance is a factor, not a percent.
             if (mosquito instanceof FrankensteinMosquito) {
                  if (Greenfoot.getRandomNumber(1*10000000) <= (mateChanceForFemale*10000000)) {
                     mosquito.setHaveFrankensteinEggs(true);
                     return;
                  }
             } else if (Greenfoot.getRandomNumber(1*10000000) <= (mateChanceOfFemaleForGeneDriveMales *10000000)) {
                  mosquito.setHaveFrankensteinEggs(true);        
                  } else if (Greenfoot.getRandomNumber(1*10000000) <= (mateChanceForFemale *10000000)) {
                       mosquito.setHaveWildTypeEggs(true);
                       return;
                    }
          }
       }
       
       if (mosquito.haveEggs() && !mosquito.needsBlood()  ) {
          if (gotoLakeX == 0 ) {
            
          // The coordinates where the female fly with eggs.
          gotoLakeX = getMyWorld().getLakeActor().getX()+Greenfoot.getRandomNumber(120)
                      -Greenfoot.getRandomNumber(120);
          gotoLakeY = getMyWorld().getLakeActor().getY()+Greenfoot.getRandomNumber(180)
                      -Greenfoot.getRandomNumber(180);
          }
          List<LakeActor> inRange = getObjectsInRange(800, LakeActor.class);
          
          // If the mosquito is in the range, that is always true for laying eggs.
          if (inRange.size() > 0) {
              if (layingEgg(gotoLakeX,gotoLakeY,getX(),getY() )) {
                  layEggs();
              } else {
                  // Go to the lake.
                  turnTowards( gotoLakeX, gotoLakeY );
                  move(mosquito.getPixelMovePerTick()); 
            }
          } 
       }
    }
    
    /**
     * The lay egg function determents how many and what for eggs the mosquito lays. Also where they eggs will be laid.
     */
    private void layEggs() {
       // Take the airtemperature of the world.
       float airTemperatureInDegrees = getMyWorld().getAirTemperatureInDegrees();
       // Take the number of egg laid per female.
       int numberOfEggsLaid = mosquito.getNumberOfEggsLaid(airTemperatureInDegrees);
       
       // A proportion of the females cannot lay eggs, they have failed because they lay eggs in the wrong place or have not enough blood taken.
       if ( Greenfoot.getRandomNumber(100) <= 100 * mosquito.getProportionOfAdultsFemalesLayingEggs() ) {
          //Lay a number of eggs, see down.    
       } else {
          mosquito.setNotHaveEggs();
          mosquito.setNeedsBlood(true);
          return;    
       }
       
       // Lay a nmuber of eggs.
       for (int i=0; i < numberOfEggsLaid; i++ ) {
          Mosquito egg;
          // If the female have Frankensteineggs they lay Frankenstein eggs, otherwise they lay wild type eggs.
          if (mosquito.haveFrankensteinEggs()) {                
             if (Greenfoot.getRandomNumber(100) < 50 ){
                // It is assumed as Christiansen-Jucht et al. 2015 said that 1/2 of the mosquitoes will be females.
                egg = new FrankensteinMosquito(Age.Egg,Gender.Male);
             } else {
                egg = new FrankensteinMosquito(Age.Egg,Gender.Female);
             }   
          } else {   
             if (Greenfoot.getRandomNumber(100) < 50 ){
                egg = new Anophelesgambiae(Age.Egg,Gender.Male);
             } else {
                egg = new Anophelesgambiae(Age.Egg,Gender.Female);
             }
         
          } 
          
          // The X- and Y-coordinate where the egg will be laid. To make a bit variation of the place there are randomnumbers used. 
          // The getX() and getY() are returns for the actors coordinates. 
          int layX = getX()+Greenfoot.getRandomNumber(20)
                      -Greenfoot.getRandomNumber(20);
          int layY = getY()+Greenfoot.getRandomNumber(20)
                      -Greenfoot.getRandomNumber(20);
          
          MosquitoActor eggActor = new MosquitoActor(egg);
          getWorld().addObject(eggActor, layX, layY);
          // Reset the X- and Y-coordinate where the eggs will be laid to make variation.
          layX = 0;
          layY = 0;
       }
       
       // The mosquito have not eggs and needs blood.
       mosquito.setNotHaveEggs();
       mosquito.setNeedsBlood(true);
       // Reset the X- en Y-coordinate where the mosquito go.
       gotoLakeX = 0;    
       gotoLakeY = 0;   
    }
    
    /**
     * This is used to calculate the chance that a female lay eggs. The females go to a specific coordinate of the lake. How closer to the coordinate how more likely
     * the femlae will lay eggs. With the Pythagorean theorem the distance between the female and lake is calculated. The distance is used for the chance. 
     */
    public boolean layingEgg(int xLake, int yLake,int xMosquito, int yMosquito ) {
       // Difference of the coordinates between mosquito and lake.
       int xDelta = Math.abs(xLake-xMosquito);
       int yDelta = Math.abs(yLake-yMosquito);
       // Pythagorean theorem 
       double xyDistance = Math.sqrt(Math.pow(xDelta,2)+Math.pow(yDelta,2) ); 
       
       if (Greenfoot.getRandomNumber(100*100000000) > (xyDistance*100000000) ) {
          return true;    
       }
       
       return false;
    }
    
    /**
     * This is used to calculate the chance that a female lay eggs. The females go to a specific coordinate of the village. How closer to the coordinate how more likely
     * the femlae will lay eggs. With the Pythagorean theorem the distance between the female and lake is calculated. The distance is used for the chance. 
     */
    public boolean takeBlood(int xVillage, int yVillage,int xMosquito, int yMosquito ) {
       // Difference of the coordinates between mosquito and lake.
       int xDelta = Math.abs(xVillage-xMosquito);
       int yDelta = Math.abs(yVillage-yMosquito);
       // Pythagorean theorem 
       double xyDistance = Math.sqrt(Math.pow(xDelta,2)+Math.pow(yDelta,2) ); 
       
       if (Greenfoot.getRandomNumber(10*100000000) > (xyDistance*100000000) ) {
          return true;    
       }
       
       return false;
    }
    
    /**
     * Returns the mosquito.
     */
    public Mosquito getMosquito() {
        return mosquito;
    }
    
    /**
     * Returns how many minutes each tick has. 
     */
    private int getMinutesPerTick() {
        return getMyWorld().MINUTES_PER_TICK;
    }
    
    /**
     * Returns the movement of the mosquito in meters per pixel.
     */
    private int getDistanceInMetersPerPixel() {
        int distanceInMetersPerPixel = getMyWorld().DISTANCE_IN_METERS_PER_PIXEL;
        return distanceInMetersPerPixel; 
    }
    
    /**
     * Returns the methods of MyWorld. A more easy way to use the methods of MyWorld.
     */
    private MyWorld getMyWorld() {
        return ((MyWorld)getWorld());
    }

    /**
     * Returns the name of the mosquito.
     */
    public String getName() {
        return mosquito.getName();
    }
    
}
