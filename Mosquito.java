import greenfoot.*;

/**
 * Here are the basic mosquito parameters. It is not used for all mosquitoes, only the Culicidae are modelled here: the mosquitoes who can take
 * a bloodmeal as female.
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public abstract class Mosquito {
   // The age of the mosquito: egg, larva, pupa or imago.
   private Age age; 
   // The gender of the mosquito; female or male.
   private Gender gender;
   // Happy value of the mosquito.
   private int happy = 1;
   // If the mosquito needs blood. If true they go to the village.
   private boolean needsBlood = true;
   // If the mosquito have wildtype eggs. 
   private boolean haveWildTypeEggs = false;
   // If the mosquito have Frankensteineggs.
   private boolean haveFrankensteinEggs = false;
   // The number of ticks the mosquito live in the stage egg.
   private int ticksInEggAge = 0;
   // The number of ticks the mosquito live in the stage larva.
   private int ticksInLarvaeAge = 0;
   // The number of ticks the mosquito live in the stage pupa.
   private int ticksInPupaeAge = 0;
   // The number of ticks the mosquito live in the stage adult.
   private int ticksInAdultAge = 0; 
   
   public Mosquito(Age age, Gender gender) {
      this.age = age;
      this.gender = gender;
   }
   
   /**
    * Returns the gender of the mosquito.
    */
   public Gender getGender() {
      return gender;
   }
   
   /**
    * Returns the age of the mosquito.
    */
   public Age getAge() {
      return age;
   }
   
   /**
    * Set the age of the mosquito: egg, larva, pupa or adult.
    */
   public void setAge(Age age) {
       this.age = age;
   }
   
   /**
    * Set the gender of the mosquito: female or male.
    */
   public void setGender(Gender gender) {
       this.gender = gender;
   }
   
   /**
    * Returns if the female have wildtype eggs.
    */
   public boolean haveWildTypeEggs() {
      if (getGender() == Gender.Female) {
        return haveWildTypeEggs;    
      } else {
        return false;
      }  
   }
   
   /**
    * Set the female to have wildtype eggs.
    */
   public void setHaveWildTypeEggs(boolean haveWildTypeEggs) {
       this.haveWildTypeEggs = haveWildTypeEggs;
   }
   
   /**
    * Returns if the female have Frankenstein eggs.
    */
   public boolean haveFrankensteinEggs() {
      if (getGender() == Gender.Female) {
        return haveFrankensteinEggs;    
      } else {
        return false;
      }  
   }
   
   /**
    * Set the female to have Frankenstein eggs.
    */
   public void setHaveFrankensteinEggs(boolean haveFrankensteinEggs) {
       this.haveFrankensteinEggs = haveFrankensteinEggs;
   }
   
   /**
    * Return if the female have eggs.
    */
   public boolean haveEggs() {
       if ( haveWildTypeEggs() == true) {
           return true;
       } else {
          if ( haveFrankensteinEggs() == true) {
             return true;
          } else {
              return false;
          }
       } 
       
   }
   
   /**
    * Set the female to not have eggs. Is used if the female laid eggs.
    */
   public void setNotHaveEggs() {
      setHaveWildTypeEggs(false); 
      setHaveFrankensteinEggs(false);  
   }
   
   /**
    * Returns if the female needs blood. 
    */
   public boolean needsBlood() {
      if (getGender() == Gender.Female) {
        return needsBlood;    
      } else {
        return false;
      }
   }
   
   /**
    * Set if the female have blood or not: can be true or false.
    */
   public void setNeedsBlood(boolean needsBlood) {
       this.needsBlood = needsBlood;
   }
   
   /**
    * Returns if the mosquito is happy.
    */
   public boolean isHappy() {
      
      if (happy > 0 ) {
         return true;    
      } else {
         return false;
      }
   }
   
   /**
    * Set the happiness of the mosquito.
    */
   public void adjustHappy(int happyLevel) {
       happy = happy + happyLevel;
   }
   
   /**
    * Set the mosquito to be happy.
    */
   public void setHappy(int happyLevel) {
       this.happy = happyLevel;
   }
   
   /**
    * Returns the hapiness of the mosquito.
    */
   public int getHappy() {
       return happy;
   }
   
   /**
    * Returns the ticks in egg stage of the mosquito.
    */
   public int getTicksInEggAge() {
      return ticksInEggAge; 
   }
   
   /**
    * Each tick the time in egg stage increase with 1.
    */
   public void tickEggAge() {
      ticksInEggAge++;    
   }
   
   /**
    * Set the tick age of the mosquito in the egg age.
    */
   public void setTicksInEggAge(int ticksInEggAge) {
      this.ticksInEggAge = ticksInEggAge;    
   }
   
   /**
    * Returns the ticks in larva stage of the mosquito.
    */
   public int getTicksInLarvaeAge() {
      return ticksInLarvaeAge; 
   }
   
   /**
    * Each tick the time in larva stage increase with 1.
    */
   public void tickLarvaeAge() {
      ticksInLarvaeAge++;    
   }
   
   /**
    * Set the tick age of the mosquito in the larva age. 
    */
   public void setTicksInLarvaeAge(int ticksInLarvaeAge) {
      this.ticksInLarvaeAge = ticksInLarvaeAge;    
   }
   
   /**
    * Returns the ticks in pupa stage of the mosquito. 
    */
   public int getTicksInPupaeAge() {
      return ticksInPupaeAge; 
   }
   
   /**
    * Each tick the time in pupa stage increase with 1.
    */
   public void tickPupaeAge() {
      ticksInPupaeAge++;    
   }
   
   /**
    * Set the tick age of the mosquito in the pupa age.
    */
   public void setTicksInPupaeAge(int ticksInPupaeAge) {
      this.ticksInPupaeAge = ticksInPupaeAge;    
   }
   
   /**
    * Returns the ticks in adult stage of the mosquito.
    */
   public int getTicksInAdultAge() {
      return ticksInAdultAge; 
   }
   
   /**
    * Each tick the time in adult stage increase with 1.
    */
   public void tickAdultAge() {
      ticksInAdultAge++;    
   }
   
   /**
    * Set the tick age of the mosquito in the adult age.
    */
   public void setTicksInAdultAge(int ticksInAdultAge) {
      this.ticksInAdultAge = ticksInAdultAge;    
   }
   
   /**
    * Here are the parameters extended from the mosquito. Some are species specific, others are collectivily used. 
    */
   public abstract String getImage();
   public abstract float getFlySpeedInMeterPerMinutes();
   public abstract float getMeanBiomassInMilligram();
   public abstract float getProportionOfAdultsFemalesLayingEggs(); 
   public abstract float getPerCapitaEggMortalityRatePerDay();
   public abstract float getPerCapitaPupalMortalityRatePerDay(); 
   public abstract double getChanceOfEggDeadPerTick(float perCapitaEggMortalityRatePerDay, float minutesPerTick);
   public abstract double getChanceOfLarvaeAgeIndependentDeadPerTick(double perCapitaAgeIndependentLarvaeMortalityRatePerDay, float minutesPerTick);
   public abstract double getChanceOfDensityDependentLarvalDeadPerTick
                          (double perCapitaDensityDependentLarvalMortalityRatePerDay, float minutesPerTick ) ;
   public abstract double getChanceOfPupaeDeadPerTick(float perCapitaPupalMortalityRatePerDay, float minutesPerTick);
   public abstract double getChanceOfAdultAgeDependentDeadPerTick(double perCapitaAgeIndependentAdultMortalityRatePerDay, float minutesPerTick);
   public abstract double getPerCapitaAgeIndependentLarvaeMortalityRatePerDay(float waterTemperatureInDegrees);
   public abstract double getPerCapitaAgeIndependentAdultMortalityRatePerDay(float airTemperatureInDegrees);
   public abstract double getPerCapitaAgeDependentMortalityRatePerDay(double perCapitaAgeIndependentAdultMortalityRatePerDay, float daysInImagoAge);
   public abstract int getNumberOfEggsLaid(float airTemperatureInDegrees);
   public abstract double getProportionToHatch(float waterTemperatureInDegrees);
   public abstract double getEggDurationInDays(float waterTemperatureInDegrees);
   public abstract double getLarvaeDurationInDays(float waterTemperatureInDegrees);
   public abstract double getPupaeDurationInDays(float waterTemperatureInDegrees);
   public abstract int getPixelMovePerTick();                                                    
   public abstract String getName();
}
