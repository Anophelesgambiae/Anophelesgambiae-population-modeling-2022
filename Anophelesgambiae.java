import greenfoot.*;
import java.util.*;

/**
 * Here are the parameters of the mosquito species Anopheles gambiae s. s. Most parameters are specific for Anopheles gambiae s. s., but not all.   
 * The models are based of the model from Céline Christiansen-Jucht et al. 2015.
 * See https://spiral.imperial.ac.uk/bitstream/10044/1/29212/2/Modelling%20Anopheles%20gambiae%20s.s.%20Population%20Dynamics
 * %20with%20Temperature-%20and%20Age-Dependent%20Survival.pdf for the article of Céline Christiansen-Jucht et al. 2015.
 * For time conversion of mortality and age-dependent mortality are from SM Niaz Arifin 2014.
 * See https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4233045/
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class Anophelesgambiae extends Mosquito {
   // The proportion of females who are laying eggs. Free-parameter ranges from 0.61-0.85.
   public static final float PROPORTION_OF_ADULT_FEMALES_LAYING_EGGS = 0.61f;
   // The proportion of eggs who will die per day. Free-parameter ranges from 0.32-0.8. 
   public static final float PER_CAPITA_EGG_MORTALITY_RATE_PER_DAY = 0.6f;
   // The proportion of pupae who will die per day. It is a constant parameter with a value of 0.25.
   public static final float PER_CAPITA_PUPAL_MORTALITY_RATE_PER_DAY = 0.25f;
   // The expontential mortality increase with the age of the adult. Default 0.04. Free-parameter.
   public static final float EXPONENTIAL_MORTALITY_INCREASE_WITH_AGE = 0.07f;
   // The deceleration of the age dependent mortality with high age. Default 0.1. Free-parameter.
   public static final float DEGREE_OF_MORTALITY_DECELERATION = 0.1f;
   // The movementspeed of the mosquito while flying in meters per second, mosquitoes fly with 25 m/min or 1.5 km/h.
   public static final float FLY_SPEED_IN_METER_PER_MINUTE = 25;
   // The maximum possible biomass in mg for adults.
   public static final float MAX_BIOMASS_IN_MILLIGRAM = 0.383f;  
   // The minimum possible biomass in mg for adults.
   public static final float MIN_BIOMASS_IN_MILLIGRAM = 0.236f;
   
   public Anophelesgambiae(Age age, Gender gender) {
      super(age,gender);
   }
   
   /**
    * Returns the flyspeed in meters per second of the mosquito.
    */
   public float getFlySpeedInMeterPerMinutes() {
      return FLY_SPEED_IN_METER_PER_MINUTE; 
   }
   
   /**
    * Returns the mean biomass in mg of the adult mosquitoes. This have influence of the lake and larvae with foodcompetition, because they
    * must eat more if they need more biomass to be adult.
    */
   public float getMeanBiomassInMilligram() {
      return ( MIN_BIOMASS_IN_MILLIGRAM + MAX_BIOMASS_IN_MILLIGRAM) / 2;      
   }
   
   /**
    * Returns the proportion of females who can lay eggs. Here used as a chance of a female to lay an egg.
    */
   public float getProportionOfAdultsFemalesLayingEggs() {
      return PROPORTION_OF_ADULT_FEMALES_LAYING_EGGS; 
   }
   
   /**
    * Returns the proportion of eggs who will die per day.
    */
   public float getPerCapitaEggMortalityRatePerDay() {
      return PER_CAPITA_EGG_MORTALITY_RATE_PER_DAY; 
   }
   
   /**
    * Returns the proportion of pupae who will die per day.
    */
   public float getPerCapitaPupalMortalityRatePerDay() {
      return  PER_CAPITA_PUPAL_MORTALITY_RATE_PER_DAY; 
   } 
   
   /**
    * The chanse that an egg will die per tick.  
    */
   public double getChanceOfEggDeadPerTick(float perCapitaEggMortalityRatePerDay, float minutesPerTick) {
      return 1 - Math.pow( (1 - perCapitaEggMortalityRatePerDay), 1/(24 * (60/minutesPerTick) ));
   }
   
   /**
    * The first chanse that a larvae will die per tick and is temperature dependent but age-independent.
    */
   public double getChanceOfLarvaeAgeIndependentDeadPerTick(double perCapitaAgeIndependentLarvaeMortalityRatePerDay, float minutesPerTick) {
      return 1 - Math.pow( (1 - perCapitaAgeIndependentLarvaeMortalityRatePerDay), 1/(24 * (60/minutesPerTick) ));
   }
   
   /**
    * The second chanse that a larvae will die per tick and is densitydependent.
    */
   public double getChanceOfDensityDependentLarvalDeadPerTick
                        (double perCapitaDensityDependentLarvalMortalityRatePerDay, float minutesPerTick ) {
      return 1 - Math.pow( (1 - perCapitaDensityDependentLarvalMortalityRatePerDay), 1/(24 * (60/minutesPerTick) ));                                                             
   }
   
   /**
    * The chance that a pupae will die per tick.
    */
   public double getChanceOfPupaeDeadPerTick(float perCapitaPupalMortalityRatePerDay, float minutesPerTick) {
      return 1 - Math.pow( (1 - perCapitaPupalMortalityRatePerDay), 1/(24 * (60/minutesPerTick) ));
   }
   
   /**
    * The chance that a aldult will die per tick and is temperature dependent but age-independent.
    */ 
   public double getChanceOfAdultAgeDependentDeadPerTick(double perCapitaAgeDependentAdultMortalityRatePerDay, float minutesPerTick) {
      return 1 - Math.pow( (1 - perCapitaAgeDependentAdultMortalityRatePerDay), 1/(24 * (60/minutesPerTick) ));                                                     
   }
   
   /**
    * The per capita age independent mortality is the basis mortality. The per capita age dependent mortality increase with more age. 
    * The exponential mortality increase with age is an exponential function with Euler's number and exponent age. The exponential mortality increase with age.
    * It determents how fast the mortality will increase with more age.
    * The degree of mortality deceleration is a function what decrease the acceleration of the mortality on high age. With this there comes an
    * horizontal asymtoop on higher age.
    */
   
   public double getPerCapitaAgeDependentMortalityRatePerDay(double perCapitaAgeIndependentAdultMortalityRatePerDay, float daysInImagoAge) {
      return ( perCapitaAgeIndependentAdultMortalityRatePerDay * Math.exp(daysInImagoAge*EXPONENTIAL_MORTALITY_INCREASE_WITH_AGE) / 
             ( 1 + (( perCapitaAgeIndependentAdultMortalityRatePerDay * DEGREE_OF_MORTALITY_DECELERATION ) / EXPONENTIAL_MORTALITY_INCREASE_WITH_AGE ) *
             (Math.exp(daysInImagoAge*EXPONENTIAL_MORTALITY_INCREASE_WITH_AGE) - 1)   ) );                                                             
   }
   
   /**
    * The proportion of larvae who will die per day and is temperature dependent but age-independent.
    */
   public double getPerCapitaAgeIndependentLarvaeMortalityRatePerDay(float waterTemperatureInDegrees) {
      return 0.0013 * Math.pow(waterTemperatureInDegrees,2) - 0.0704 * waterTemperatureInDegrees + 0.9581; 
   }
   
   /**
    * The proportion of adults who will die per day and is temperature dependent but age-independent. The mortality on age 0.
    */
   public double getPerCapitaAgeIndependentAdultMortalityRatePerDay(float airTemperatureInDegrees) {
      return 5.37 * Math.pow(10, -5)*Math.pow(Math.E,0.228f * airTemperatureInDegrees); 
   }
   
   /**
    * The number of eggs laid per female and is temperature dependent. Only discrete values are used. 
    * The number is not round up, but only the integer numbers are used.  
    */
   public int getNumberOfEggsLaid(float airTemperatureInDegrees) {
      double numberOfEggsLaid = -1.1057f * Math.pow( airTemperatureInDegrees , 2) 
       + 56.208f * airTemperatureInDegrees - 662.1f;
      
      return (int) numberOfEggsLaid;
   }
   
   /**
    * The proportion of eggs who will be larvae if the egg stage is over. The others will be dead.
    */
   public double getProportionToHatch(float waterTemperatureInDegrees) {
      return -0.0034f * Math.pow( waterTemperatureInDegrees , 2) + 0.1719f * (waterTemperatureInDegrees) - 1.248f;
   }
   
   /**
    * The duration of the egg stage in days and is temperature dependent. Next stage is the larvae.
    */
   public double getEggDurationInDays(float waterTemperatureInDegrees) {
      return 1.011f + ( ( 20.212f ) / ( 1 + Math.pow( (waterTemperatureInDegrees/12.096f),4.839f ) ) );
   }
   
   /**
    * The duration of the larvae stage in days and is temperature dependent. Next stage is the pupae.
    */
   public double getLarvaeDurationInDays(float waterTemperatureInDegrees) {
      return 8.13f +( 13.794f / ( 1 + Math.pow( (waterTemperatureInDegrees/20.742f),8.946f )) ) - getEggDurationInDays(waterTemperatureInDegrees);
   }
   
   /**
    * The duration of the pupae stage in days and is temperature dependent. Next stage is the adult.
    */
   public double getPupaeDurationInDays(float waterTemperatureInDegrees) {
      return 8.56f + ( 20.654 / ( 1 + Math.pow(waterTemperatureInDegrees/19.759,6.827)) ) - getLarvaeDurationInDays(waterTemperatureInDegrees);
   }
   
   /**
    * Here is de calculation of the pixelmove per tick. It means how many pixels a mosquito fly in each tick.
    */
   public int getPixelMovePerTick() {
      float pixelMovePerTick;
      pixelMovePerTick = (FLY_SPEED_IN_METER_PER_MINUTE/MyWorld.DISTANCE_IN_METERS_PER_PIXEL) * MyWorld.MINUTES_PER_TICK;
      
      return (int) Math.round(pixelMovePerTick);
   }
   
   /**
    * Returns the name of the mosquito. The mosquito without genedrive are named wildtype mosquito.
    */
   public String getName() {
      return "Wild Type";
   }
   
   /**
    * Each mosquito stage have another image; egg, larva, pupa and adult. If the mosquitoes have a genedrive they are red. With the adults it is more complicated. 
    * The females without genedrive are yellow, with are purple. The males without genedrive are blue, with genedrive are green.
    * Females who have take a bloodmeal are red in the abdomen. If females have eggs they drag them behind. 
    */
   public String getImage() { 
          String image = "";
          
          switch(getAge()) {
              case Egg:          
                  image = "Anophelesgambiae_EggsGray.png";
                  break;
              case Larvae:
                  image = "Anophelesgambiae_LarvaeGray.png";
                  break;
              case Pupae:
                  image = "Anophelesgambiae_PupaeGray.png";
                  break;
              case Adult:
                  if (needsBlood()) {
                     image = "Anophelesgambiae_AdultYellow.png";   
                  } else if (haveWildTypeEggs()) {
                       image = "Anophelesgambiae_AdultYellow_WithEggs.png";
                    } else if(haveFrankensteinEggs()) {
                         image = "Anophelesgambiae_AdultYellow_WithRedEggs.png";  
                      } else if (getGender() == Gender.Male) {
                              image = "Anophelesgambiae_AdultBlue.png";
                           } else {
                                image = "Anophelesgambiae_AdultYellow_WithBlood.png";       
                             }
                  break;
              default: 
                  image = "Anophelesgambiae_AdultGray.png"; 
          }
      return image; 
   }
}

