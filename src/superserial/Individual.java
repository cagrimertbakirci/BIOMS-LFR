package superserial;
import java.util.Comparator;


/**
 * @author Scott Watkins
 */
public class Individual implements Comparable<Individual> {
    public int[][] Genetics;
    private int Number;
    private double Fitness;
    private int[] SensorTimes;
    private int LastSeen;
    private static double Threshold = 0;
    private static double[] CoeffPosition = {1,1.1,1.2,1.3,1.3,1.2,1.1,1};
    
    /**
     * Constructor
     * @param genetics - int[][] 2X10 genetics of the individual
     * @param number - int , number assigned to the individual
     * @param sensorTimes - double[] 1X8 time values found for each sensor
     */
    Individual(int[][] genetics,int number,int[] sensorTimes){
        Genetics = genetics;
        Number = number;
        SensorTimes = sensorTimes;
        Fitness = calcFitness(genetics,sensorTimes);
    }
    
    public void setVals(int[][] genetics,int number,int[] sensorTimes){
        Genetics = genetics;
        Number = number;
        SensorTimes = sensorTimes;
        Fitness = calcFitness(genetics,sensorTimes);
    }
    
    /**
     * Constructor (this is dangerous, try not to use it)
     * @param genetics - int[][] 2X10 genetics of the individual
     * @param number - int , number assigned to the individual
     */
    Individual(int[][] genetics,int number){
        Genetics = genetics;
        Number = number;
        SensorTimes = new int[]{0,0,0,0,0,0,0,0,0,0};
        Fitness=0;
    }
    
    Individual(){
        Genetics= new int[2][10];
        for(int i=0;i<2;i++){
            for(int j=0;j<10;j++){
                Genetics[i][j]=(int)(Math.random()*255*2-255);
            }
        }
        Number=0;
        Fitness=0;
        SensorTimes = new int[]{0,0,0,0,0,0,0,0,0,0};
        
    }
    
    //private methods
    /**
     * getsFitness for a bot not allowed to leave the line
     * @param genetics - int[][], 2X10 genetics of the individual
     * @param sensorTime - double[], 1X8 sensorTime array of tested individual
     * @return - double fitness
     */
    private double calcFitness(int[][] genetics,int[] sensorTime){
        double fitness = 0;
        for(int s = 0;s<8;s++){
            fitness = fitness + (CoeffPosition[s]*sensorTime[s]*(.5)*(negToZero(Math.abs(genetics[0][s])+Math.abs(genetics[1][s])-Threshold)));
            
        }
        return fitness;
    }
    /**
     * To recalculate fitness in the case of parameter change
     */
    public void recalcFitness(){
        double fitness = 0;
        for(int s = 0;s<8;s++){
            fitness = fitness + (CoeffPosition[s]*SensorTimes[s]*(.5)*(negToZero(Math.abs(Genetics[0][s])+Math.abs(Genetics[1][s])-Threshold)));
            
        }
        Fitness = fitness;
    }
    /**
     * To set threshold for all individuals, default threshold is 0
     * @param threshold - double , new threshold
     */
    public void setThreshold(double threshold){
        Threshold = threshold;
    }
    /**
     * For reseting the coefficient values of the sensors for the fitness function, default is {1,2,3,4,4,3,2,1}
     * @param coeffPosition double[] - 1X8 
     */
    public void setCoeffPosition(double[] coeffPosition){
        CoeffPosition = coeffPosition;
    }
    /**
     * Supplemental method used in fitness function
     * brings a number to 0 if the number is negative, else the number remains the same
     * @param a - double , number to be tested
     * @return - double , new value
     */
    private double negToZero(double a){
        if(a<0){
            return 0;
        }else{
            return a;
        }
    }
    /**
     * Allows for the comparison of individuals by fitness
     * Comparator comp = new IndCompare();
     * @param objA
     * @return 
     */
    @Override 
    public int compareTo(Individual objA){
        if(Fitness > objA.getFitness())
            return 1;
        else if(Fitness < objA.getFitness())
            return -1;
        else
            return 0;
    }
    //Get Variables
    
    /**
     * Returns the genetics for the individual
     * @return - int[][], 2X10 matrix of genetics
     */
    public int[][] getGenetics(){
        return Genetics;
    }
    /**
     * Returns the number assigned to the individual
     * @return - int, individual's number
     */
    public int getNumber(){
        return Number;
    }
    /**
     * Returns the fitness of the individual
     * @return - double, fitness
     */
    public double getFitness(){
        return Fitness;
    }
    /**
     * Returns the time values given for each sensor
     * @return double[], 1X8 sensor time values
     */
    public int[] getSensorTimes(){
        return SensorTimes;
    }
    /**
     * returns the static array of the sensor coefficients 
     * @return - double[] , 1X8
     */
    public double[] getCoeffPosition(){
        return CoeffPosition;
    }
    /**
     * returns the static threshold value
     * @return - double , Threshold
     */
    public double getThreshold(){
        return Threshold;
    }
    
    /**
     * Sets what side the line was last seen on.
     * @param lastSeen the side that was last seen, -1 is left, 0 is center, 1 is right
     */
    public void setLastSeen(int lastSeen){
        LastSeen=lastSeen;
    }
    
    public void setFitness(double i){
        Fitness=i;
    }
    
    public void setSensorTimes(int times[]){
        SensorTimes=times.clone();
        Fitness = calcFitness(Genetics,SensorTimes);
    }
    
    /**
     * Mutates the individual
     * @param rate How often mutations occur out of 1000
     * @param severity How much mutations can change an individual
     */
    public void mutate(int rate, int severity){
        for( int i=0; i<2; i++ ){
            for( int j=0; j<10; j++ ){
                if( ( Math.random()*1000 ) <= rate){
                    Genetics[i][j]+= severity - 2*severity*Math.random();
                    if(Genetics[i][j]>255)
                        Genetics[i][j]=255;
                    if(Genetics[i][j]<-255)
                        Genetics[i][j]=-255;
                }
            }
        }
    }
    
    /**
     * Crosses over the chromosomes of the genome
     * @param chance How often mutations occur out of 1000
     */
    public void crossover(int chance){
        if( (Math.random()*1000) <= chance){
            for(int i=(int)(Math.random()*10); i<10; i++){
                if(Genetics[0][i]!=Genetics[1][i]){
                    Genetics[0][i]^=Genetics[1][i];
                    Genetics[1][i]^=Genetics[0][i];
                    Genetics[0][i]^=Genetics[1][i];
                }
            }
        }
    }
    
    public Individual reproduce(Individual in){
        Individual newInd=new Individual();
        double rand=Math.random()*2;
        if(rand <= 1){
            newInd.Genetics[0]=this.Genetics[0];
            newInd.Genetics[1]=in.Genetics[1];
        }else{
            newInd.Genetics[0]=in.Genetics[0];
            newInd.Genetics[1]=this.Genetics[1];
        }
        return newInd;
    }
}