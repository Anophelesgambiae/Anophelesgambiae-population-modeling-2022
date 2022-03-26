/**
 * In the lake eggs, larvae and pupae lives. They are namely aquatic. The larvae moves in the lake. Here are eggs laid by the females adults.
 * In the lake are the parameters who belongs to a lake. The lake have many to do with larvae parameters.
 * See https://europepmc.org/backend/ptpmcrender.fcgi?accid=PMC514565&blobtype=pdf for the article of Depinay et al. 2004. 
 * 
 * @author Robert de Haas 
 * @version 25 March 2022
 */
public class Lake  
{
    // The coefficient what represent of the lake is in the sun or schaduw, from Jean-Marc O Depinay et al. 2004. 
    // 1 for complete sun and 0 for complete schaduw. Free-paramter from 0-1.
    public static final float SUN_EXPOSURE_COEFFICIENT = 1;
    // A factor to correct for particulary water-body characteristics, from Jean-Marc O Depinay et al. 2004.
    // Free paramter, but must be minimal 0.
    public static final float CARRYING_CAPACITY_DEPENDENCY_COEFFICIENT = 1;
    // The maximum larvae denisty per m2 what is measured. Represents what the maximum possible populationdensity per m2 is.
    // It is measured by the peak season.
    public static final float MAX_LARVAE_DENSITY_PER_SQUARE_METER = 50;
    // The lengt of the waterbody.
    public static final float LAKE_LENGTH_IN_METERS = 10;
    // The width of the waterbody.
    public static final float LAKE_WIDTH_IN_METERS = 6;
    // The height or depth of the lake.
    public static final float LAKE_HEIGHT_IN_METERS = 20;
    
    
    public Lake() {
        super();
    }
    
    /**
     * Returns the watertemepratur in degrees what is determined from the airtemperature and the difference between them.
     */
    public static float getWaterTemperatureInDegrees(float differenceBetweenAirAndWaterTemperatureInDegrees,float airTemperatureInDegrees) {
       return airTemperatureInDegrees + differenceBetweenAirAndWaterTemperatureInDegrees;  
    }
    
    /**
     * Returns the difference between the air- and watertemperature and is dependent of the absorbed sunlight.
     */
    public static float getDifferenceBetweenAirAndWaterTemperatureInDegrees(float waterVolumeInKiloLiters, float absorbedSunlight) {
        if (waterVolumeInKiloLiters > 100) {
           return -1 + (6 * absorbedSunlight);    
        } else {
           // A warning that the volume of the water must be bigger to use this formula properly.
           System.out.println("The volume of the water is not big enough, set bigger values");
           return 0;    
        }
           
    }
    
    /**
     * Returns the absorbed sunlight what is a factor of how many sunlight is absorped by the lake. 
     * Is dependent of the cloudocver and sunexposure coefficient.
     */
    public static float getAbsorbedSunlight(float cloudCover ) {
       return SUN_EXPOSURE_COEFFICIENT * (1- cloudCover); 
    }
    
    /**
     * Returns the watervolume in kiloliters. The volume of m3 is the same as kL. Must be higher than 100 kl. 
     */
    public static float getWaterVolumeInKiloLiters() {
        return LAKE_HEIGHT_IN_METERS * LAKE_WIDTH_IN_METERS * LAKE_LENGTH_IN_METERS;
    }
    
    /**
     * Returns the per capita densitydependent larval mortality rate. It is dependent of the totalLarvaeBiomassInMilligram and waterbody carrying capacity.
     * The carrying capacity represent how many biomass there can be in the waterbody.
     */
    public static double getPerCapitaDensityDependentLarvalMortalityRatePerDay(double totalLarvaeBiomassInMilligram, double waterbodyCarryingCapacity) {
       return totalLarvaeBiomassInMilligram / waterbodyCarryingCapacity;   
    }
    
    /**
     * Returns the waterbody carrying capacity what is dependent of the waterbody area and maximum larvae biomass in mg.
     */
    public static double getWaterBodyCarryingCapacity(float maxTotalLarvaeBiomassInMilligramPerSquareMeter) {
        return maxTotalLarvaeBiomassInMilligramPerSquareMeter * (LAKE_WIDTH_IN_METERS * LAKE_LENGTH_IN_METERS) * CARRYING_CAPACITY_DEPENDENCY_COEFFICIENT; 
    }
    
    /**
     * Returns the maximum larvae biomass in milligram per m2 what is dependent of the maximum larvae density per m2 and mean biomass of the adult in mg.
     * It is divided by 2 because most larvae are lighter than the meanbiomass of the adults. Larvae can be adults if they have the same
     * minimum to maximum biomass as the adults. That is why the adult biomass is used for the equation.
     */
    public static float getMaxTotalLarvaeBiomassInMilligramPerSquareMeter(float meanBiomassInMilligram) {
        return (MAX_LARVAE_DENSITY_PER_SQUARE_METER * meanBiomassInMilligram)/2;   
    }
    
    /**
     * Returns the total biomass in mg for the larvae and is determined to multiply the biomass of each larve with the larvae population size.
     */
    public static double getTotalLarvaeBiomassInMilligram(double larvaeBiomassInMilligram, int larvaePopulationSize) {
        return larvaeBiomassInMilligram * larvaePopulationSize;  
    }
    
    /**
     * Returns the larvae biomass in mg and is dependent of the total larvae biomass and waterbody carrying capacity and the maximum
     * possible biomass in mg of the adult. With more larvae there comes food competition of the larvea. If the total larvae biomass is much lower than 
     * the waterbodycarrying capacity then the larvae will reach the maximum weight. If the total larvae biomass is almost even big as the waterbody 
     * carrying capacity then the biomass will be 0. In practise it will never be reached because the mortality per capita is then almost 1. So 
     * each larvae will die very quick. The biomass per larvae is a mean, the younger larvae are lighter than adult minimum weight.
     */
    public static double getLarvaeBiomassInMilligram(double totalLarvaeBiomassInMilligram, double waterbodyCarryingCapacity) {
        return Anophelesgambiae.MAX_BIOMASS_IN_MILLIGRAM * (1 - (totalLarvaeBiomassInMilligram / waterbodyCarryingCapacity) ); 
    }
}
