/**
 * Here are the climate variable of Saõ Tomé. Each month there are other mean parameters. This is important for the mosquito survivalship.
 * See http://www.sao-tome.climatemps.com/index.php for the climate variable of Saõ Tomé.
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public enum ClimateVariableInMonth  
{
  // The climate variable each month with the variable: date number; airtemperature; relative humidity.
  Jan(0, 28.0f, 78f),
  Feb(1, 26.0f, 78f),
  Mar(2, 26.0f, 76f),
  Apr(3, 26.0f, 77f),
  May(4, 22.0f, 79f),
  Jun(5, 21.5f, 74f),
  Jul(6, 21.5f, 70f),
  Aug(7, 22.5f, 70f),
  Sep(8, 24.5f, 74f),
  Oct(9, 25.5f, 79f),
  Nov(10, 25.0f, 79f),
  Dec(11, 23.0f, 79f);
  
  private int month;
  private float temp;
  private float humidity;
  
  /**
   * Returns the month.
   */
  public static ClimateVariableInMonth getClimateVariableInMonth(int month) {
     for (ClimateVariableInMonth t: values() ) {
         if (t.month == month ) {
             return t;
         } 
     }
     return null;
  }
  
  /**
   * Returns the variable of the climate.
   */
  private ClimateVariableInMonth(int month, float temp, float humidity) {
      this.month = month;
      this.temp = temp;
      this.humidity = humidity;
  }
  
  /**
   * Returns the airtemperature of the month.
   */
  public float getTemp() {
      return temp;
  }
  
  /**
   * Return the humidiy of the month.
   */
  public float getHumidity() {
      return humidity;
  }
}

