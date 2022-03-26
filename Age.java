/**
 * Mosquitoes have 4 stages, the egg, larva, pupa and adult. These stages determinent the behavior and characteristics of the mosquito.
 * There are not substages used.
 * 
 * @author Robert de Haas 
 * @version 25 Marhc 2022
 */
public enum Age { 
   // Here are the 4 stages.  
   Egg(0), 
   Larvae(1),
   Pupae(2),
   Adult(3);
   
   private int ageValue;
   
   /**
    * Set the age of the mosquito to the writen one. Example: mosquito.setAge(Gender.Egg)  
    */
   private Age(int ageValue) {
    this.ageValue = ageValue;
   }
   
   /**
    * Returns the age of the mosquito. Example: mosquito.getAge()
    */
   public int getAgeValue() {
    return ageValue;   
   }
}
