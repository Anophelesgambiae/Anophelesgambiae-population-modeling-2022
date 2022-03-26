/**
 * Mosquitoes can be female and male in the adult stage. Females can lay egg for reproduction. This is used to determine and set the gender.
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public enum Gender {
   // Here are the genders.
   Male(0),
   Female(1);
   
   private int genderValue;
   
   /**
    * Set the gender of the mosquito to the writen one. Example: mosquito.setGender(Gender.Female)
    */
   private Gender(int genderValue) {
    this.genderValue = genderValue;
   }
   
   /**
    * Returns the gender of the mosquito. Example: mosquito.getGender()
    */
   public int getGenderValue() {
    return genderValue;   
   }
}
