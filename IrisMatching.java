import java.io.File;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Jama.SingularValueDecomposition; 
import Jama.Matrix; 
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.List;
import java.util.*; 
/**
 * Iris Matching project using eigenvectors. 
 *
 * @author (Joe McDonald)
 * @version (2020)
 */
public class IrisMatching
{
    // instance variables
    private File file;// image file
    private BufferedImage image;//buffered image
    private int width; //image width
    private int height;//image height
    private int size, numberimages, h;// size = width*height, number of images, h is amount of images added to array
    private double[][] array; // array of rgb pixels
    private double[][] narray;// normalised array
    private double[][] pcarray;//eigenvectors array
    private double[][] warray;//weights array before being summed
    private double[][] testarray;// test array of rgb pixels
    private double[][] testwarray;// test weights array before being summed
    private double [][] average;// averages from normalisation 
    private double [][] weights;// training weights
    private double [][] testweights;// test weights
    private double [][] distanceE;// eudlidean distane with two weights
    private double [][] distanceM;// manhattan distane with two weights
    private double [][] distanceK;// minkowski^3 distane with two weights
    private double [][] distanceK4;// minkowski^4 distane with two weights
    private double [] distanceEu;// eudlidean distane with one weight
    private double [] distanceMu;// manhattan distane with one weight
    private double [] distanceKu;// minkowski^3 distane with one weight
    private double [] distanceKu4;// minkowski^4 distane with one weight
    private List<Double> list;//distance in arraylist form for euclidean distance
    private List<Double> listM;//distance in arraylist form for manhattam distance
    private List<Double> listK;//distance in arraylist form for minkowski^3 distance
    private List<Double> listK4;//distance in arraylist form for minkowski^4 distance

    /**
     * Constructor for objects of class IrisMatching
     */
    public IrisMatching()
    {
        // initialise instance variables
        file = null;
        image = null;
        width = 768; 
        height = 576;
        numberimages= 64;
        size = width * height;
        array = new double[size][numberimages];
        narray = new double[size][numberimages];
        pcarray = new double[size][numberimages];
        warray = new double[size][numberimages*2];
        testarray = new double [size][1];
        testwarray = new double [size][2];
        average = new double [size][1];
        weights = new double [numberimages][2];
        testweights = new double [1][2];
        distanceE = new double [numberimages][2];
        distanceEu = new double [numberimages];
        distanceM = new double [numberimages][2];
        distanceMu = new double [numberimages];
        distanceK = new double [numberimages][2];
        distanceKu = new double [numberimages];
        distanceK4 = new double [numberimages][2];
        distanceKu4 = new double [numberimages];
        list = new ArrayList<Double>();
        listM = new ArrayList<Double>();
        listK = new ArrayList<Double>();
        listK4 = new ArrayList<Double>();
        h= 0;
    
        
       

    }
    
     /**
     * Read Image
     * 
     * Each image in read using getRGB, the values are then added to a column
     * The array is then filled out with different images
     *
     *
     * @param  String- the file input 
     * @return   Array of the rgb values
     */
    public double[][] readImage1(String filename)throws IOException{
       {
        try{
        file = new File(filename); //image file path
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        image = ImageIO.read(file);
        int z = 0;
          for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                 int t = image.getRGB(x, y);
                 array [z] [h] = t;
                 z++;
           }
         }
        h++;
        System.out.println("image" + h + "read");
        }catch(IOException e){
         System.out.println("Error: "+e);
        }
        
       }
       
       return array;
     }

     /**
     * Normalise array
     * 
     * The array from the read image method is then normalised
     * 
     * Average row value is found in the first loop and stored in array so 
     * can be employed later
     *  
     * The average row value is then subtracted from the respected row
     *  
     *  
     *  
     * @return   normalised array
     */
     public double[][] normaliseArray(){

  
      double rowTotal = 0;
      double av = 0;

      for (int row = 0; row < size; row++){
        for (int column = 0; column < numberimages; column++){
            rowTotal += array[row][column];
        }
        av = rowTotal / numberimages; // calc average
        average [row][0] = av;
        rowTotal = 0; // start over (for next row)
       }
     
    
      for (int i = 0; i<size; i++){
         for (int j = 0; j<numberimages; j++){
            narray [i][j] =  array [i][j] - average [i][0];
            
        }
       }
        
      return narray; 

    }
   
     /**
     * SVD
     *  
     * Uses Jamas SVD to return the eigenvectors in pc array 
     *  
     * Normalised array needs to be converted to matrix and back to array
     *  
     * @return   eigenvectors array
     */
    public double[][] SVD(){
       Matrix h = new Matrix(narray);
       SingularValueDecomposition svd = h.svd();
       pcarray = convertMatrix(svd.getU());
       return pcarray;
    
    }
    
     /**
     * Converts martix to array
     *  
     *  
     * @return   double array
     */
    public double[][] convertMatrix(Matrix m){
       double[][] d = m.getArray();
       return d;
    }
    
     /**
     * Finds Weights
     *  
     * finds the weights by multiplying normalised array with eigenvectors 
     * saved into new array which is then summed to find the weights for each image
     * these weights are saved into a new array 
     *  
     * @return   double array of weights
     */
     public double[][] findWeights(){
        
           
       for (int i = 0; i < size; i++) {
          for (int j = 0; j < numberimages*2; j++ ){ 
           warray [i][j] = narray [i][j/2] * pcarray[i][j%2];
          }  
        }
       
       for(int j = 0; j < size; j++)
        for(int i = 0; i < numberimages*2; i++){
           int temp = 0;
           temp += warray[j][i];
           weights[i/2][0] += temp;
           temp = 0;
           i++;
        }
        
       for(int j = 0; j < size; j++)
        for(int i = 1; i < numberimages*2; i++){
           int temp = 0;
           temp += warray[j][i];
           weights[i/2][1] += temp;
           temp = 0;
           i++;
        }
       
       return weights;
    }

     /**
     * Read Test Image
     * 
     * Test image is read using getRGB, the values are then added to a column
     * 
     *
     * @param  String- the test file input 
     * @return   Array of the rgb values
     */
    public double[][] readTestImage(String filename)throws IOException{
      {
      try{
       file = new File(filename); //image file path
       image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       image = ImageIO.read(file);
       int z = 0;
          for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                 int t = image.getRGB(x, y);
                 testarray [z] [0] = t;
                 z++;
           }
        }
       System.out.println("test image read");  
       }catch(IOException e){
       System.out.println("Error: "+e);
      }// put your code here
        
      }
     
      return testarray;
    }

    
    /**
     * Normalise array
     * 
     * The array from the read test image method is then normalised
     * with the averages calculated in normalise array method
     *  
     *  
     *  
     * @return   normalised test array
     * */
    public double[][] normaliseTestArray(){

     for (int i = 0; i<size; i++){
            testarray [i][0] =  testarray [i][0]- average [i][0];
     }
     return testarray;
    }
   
   
   
     /**
     * Finds Test Weights
     *  
     * finds the weights by multiplying normalised array with eigenvectors 
     * saved into new array which is then summed to find the weights for each image
     * these weights are saved into a new array 
     *  
     * @return   double array of test weights
     */
    public double[][] findTestWeights(){
        for (int i = 0; i < size; i++) {
           testwarray [i][0] = testarray [i][0] * pcarray[i][0];
           testwarray [i][1] = testarray [i][0] * pcarray[i][1]; 

        } 
 
        for(int j = 0; j < size; j++){  
              int temp = 0;
              temp += testwarray[j][0];
              testweights[0][0] += temp;
              temp = 0;
        }  
            
        for(int j = 0; j < size; j++){  
              int temp = 0;
              temp += testwarray[j][1];
              testweights[0][1] += temp;
              temp = 0;
        }  
        return testweights;
    }
    
     /**
     * Match Euclidean Distance
     *  
     * finds the distane between the training weights with the test weights 
     * using Euclidean distance
     *  
     *  
     * @return   double training image that has the shortest distance to test image
     */
    public double matchEuclidean(){
        for(int i = 0; i < numberimages; i++){
            distanceE[i][0] = weights[i][0] - testweights[0][0];
            distanceE[i][1] = weights[i][1] - testweights[0][1];
          }
          
        for(int i = 0; i < numberimages; i++){
            distanceEu[i] = (Math.pow(distanceE[i][0], 2)) + (Math.pow(distanceE[i][1], 2));
            distanceEu[i] = Math.sqrt(distanceEu[i]);
           }  
           
         
        for (double no : distanceEu) {
         list.add(Double.valueOf(no));
         }
          
        double max= Collections.min(list);
        double min =  list.indexOf(max) + 1;    
        System.out.println("(euclidean) the unknown image matches image " + (min)); 
        return min;
     }
     /**
     * Match Manhattan Distance
     *  
     * finds the distane between the training weights with the test weights 
     * using Manhattan distance
     *  
     *  
     * @return   double training image that has the shortest distance to test image
     */  
   public double matchManhattan(){
        for(int i = 0; i < numberimages; i++){
            distanceM[i][0] = weights[i][0] - testweights[0][0];
            distanceM[i][1] = weights[i][1] - testweights[0][1];
          }
          
        for(int i = 0; i < numberimages; i++){
            distanceMu[i] = Math.abs(distanceM[i][0] + distanceM[i][1]);
            
           } 
      
        for (double no : distanceMu) {
           listM.add(Double.valueOf(no));
         }
          
        double max= Collections.min(listM);
        double min =  listM.indexOf(max) + 1;    
        System.out.println("(Manhattan) the unknown image matches image " + (min)); 
        return min;
     }
        /**
     * Match Minkowski^3 Distance
     *  
     * finds the distane between the training weights with the test weights 
     * using Minkowski^3 distance
     *  
     *  
     * @return   double training image that has the shortest distance to test image
     */  
   public double matchMinkowski(){
        for(int i = 0; i < numberimages; i++){
            distanceK[i][0] = weights[i][0] - testweights[0][0];
            distanceK[i][1] = weights[i][1] - testweights[0][1];
          }
          
        for(int i = 0; i < numberimages; i++){
            distanceKu[i] = (Math.pow(distanceK[i][0], 3)) + (Math.pow(distanceK[i][1], 3));
            distanceKu[i] = Math.cbrt(distanceKu[i]);
            distanceKu[i] = Math.abs(distanceKu[i]);
           }  

        for (double no : distanceKu) {
         listK.add(Double.valueOf(no));
         }
          
        double max= Collections.min(listK);
        double min =  listK.indexOf(max) + 1;    
        System.out.println("(Minkowski) the unknown image matches image " + (min)); 
        return min;
     }
           /**
     * Match Minkowski^4 Distance
     *  
     * finds the distane between the training weights with the test weights 
     * using Minkowski^4 distance
     *  
     *  
     * @return   double training image that has the shortest distance to test image
     */    
     public double matchMinkowski4(){
        for(int i = 0; i < numberimages; i++){
            distanceK4[i][0] = weights[i][0] - testweights[0][0];
            distanceK4[i][1] = weights[i][1] - testweights[0][1];
          }
          
        for(int i = 0; i < numberimages; i++){
            distanceKu4[i] = (Math.pow(distanceK4[i][0], 3)) + (Math.pow(distanceK4[i][1], 3));
            distanceKu4[i] = Math.pow(distanceKu4[i], 0.25);
            distanceKu4[i] = Math.abs(distanceKu4[i]);
           }  
 
        for (double no : distanceKu4) {
         listK4.add(Double.valueOf(no));
         }
          
        double max= Collections.min(listK4);
        double min =  listK4.indexOf(max) + 1;    
        System.out.println("(Minkowski 4) the unknown image matches image " + (min)); 
        return min;
     } 
   
  
     
    }
    
     

   
   

