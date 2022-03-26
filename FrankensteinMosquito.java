import greenfoot.*;
/**
 * Frankensteinmosquitoes have the genedrive. They have fitnesscost that make them weaker. This Frankensteinmosquito is Anopheles gambiae s. s.
 * Here is used the Frankensteinmosquito with a FREP1-KO gene from Gong et al. 2018. The KO is not a gene drive ,but here the hypothetical case has been taken 
 * that the KO also possesses a gene drive.
 * See for the FREP1-KO of Gong et al. 2018 https://journals.plos.org/plospathogens/article?id=10.1371/journal.ppat.1006898# 
 * See how a gene drive works https://royalsociety.org/~/media/policy/Publications/2018/08-11-18-gene-drive-statement.pdf
 * See how the CRISPR/Cas9 works https://www.synthego.com/guide/how-to-use-crispr/sgrna and https://www.synthego.com/guide/how-to-use-crispr/pam-sequence
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class FrankensteinMosquito extends Anophelesgambiae 
{
    // The fitness-cost factor represent how much the fitness costs should contribute. A free-parameter from 0-1.
    // With 1 the fitness-cost is on default, with 0 there is no fitness-cost and with 0.5 the fitnes-cost is halved.  
    public static final float FITNESS_DEPENDENCY_FACTOR = 0.125f; 
    
    
    /**
     * Constructor for objects of class FrankensteinMosquito
     */
    public FrankensteinMosquito(Age age, Gender gender) {
      super(age,gender);  
    }
    
    /**
     * The Frankensteinmosquitoes die 1.53 times faster than wt mosquitoes. 
     */
    public double getPerCapitaAgeIndependentAdultMortalityRatePerDay(float airTemperatureInDegrees) {
      return (1.53f -1) * FITNESS_DEPENDENCY_FACTOR * super.getPerCapitaAgeIndependentAdultMortalityRatePerDay(airTemperatureInDegrees)
             + super.getPerCapitaAgeIndependentAdultMortalityRatePerDay(airTemperatureInDegrees);
    }
    
    /**
     * Frankensteinmosquitoes lay 9.20 times less eggs than wt mosquitoes. 
     */
    public int getNumberOfEggsLaid(float airTemperatureInDegrees) {
      double numberOfEggsLaid = ( (1/9.20f) -1) * FITNESS_DEPENDENCY_FACTOR * super.getNumberOfEggsLaid(airTemperatureInDegrees)
             + super.getNumberOfEggsLaid(airTemperatureInDegrees); 
      return (int) numberOfEggsLaid;
    }
    
    /**
     * Frankensteinmosquitoes hatch 11.5 time less than wt mosquitoes.
     */
    public double getProportionToHatch(float waterTemperatureInDegrees) {
      return ( (1/11.5f) -1) * FITNESS_DEPENDENCY_FACTOR * super.getProportionToHatch(waterTemperatureInDegrees)
             + super.getProportionToHatch(waterTemperatureInDegrees); 
    }
    
    /**
     * Frankensteinmosquitoes are 1.95 time longer larva than wt mosquitoes.
     */
    public double getLarvaeDurationInDays(float waterTemperatureInDegrees) {
      return (1.95f -1) * FITNESS_DEPENDENCY_FACTOR * super.getLarvaeDurationInDays(waterTemperatureInDegrees)
             + super.getLarvaeDurationInDays(waterTemperatureInDegrees);
    }
    
    /**
     * Frankensteinmosquitoes are 2 days longer a pupa than wt mosquitoes. 
     */
    public double getPupaeDurationInDays(float waterTemperatureInDegrees) {
      return 2 * FITNESS_DEPENDENCY_FACTOR + (8.56f + ( 20.654 / ( 1 + Math.pow(waterTemperatureInDegrees/19.759, 6.827)) ) 
             - super.getLarvaeDurationInDays(waterTemperatureInDegrees));    
    }
    
    /**
     * Returns the name of the mosquito. The mosquito with genedrive are named Frankensteinmosquito.
     */
    public String getName() {
       return "Frankenstein mosquito";
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
                  image = "Anophelesgambiae_EggsRed.png";
                  break;
              case Larvae:
                  image = "Anophelesgambiae_LarvaeRed.png";
                  break;
              case Pupae:
                  image = "Anophelesgambiae_PupaeRed.png";
                  break;
              case Adult:
                  if (needsBlood()) {
                     image = "Anophelesgambiae_AdultViolet.png";   
                  } else if (haveEggs() ) {
                       image = "Anophelesgambiae_AdultViolet_WithEggs.png";
                    } else if (getGender() == Gender.Male) {
                              image = "Anophelesgambiae_AdultGreen.png";
                           } else {
                                image = "Anophelesgambiae_AdultViolet_WithBlood.png";       
                             }
                  break;
              default: 
                  image = "Anophelesgambiae_AdultGray.png"; 
          }
      return image; 
    }    
}
