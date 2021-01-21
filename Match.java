

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
/**
 * The test class Match.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Match
{
    /**
     * Default constructor for test class Match
     */
    public Match()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
                                 IrisMatching irisMatc1 = new IrisMatching();
        try {
        assertNotNull(irisMatc1.readImage1("001L_1.png"));
             }
        catch (IOException e) {
        
        }
                        try {
        assertNotNull(irisMatc1.readImage1("002L_1.png"));
             }
        catch (IOException e) {
        
        }
                                try {
        assertNotNull(irisMatc1.readImage1("003L_1.png"));
             }
        catch (IOException e) {
        
        }
        assertNotNull(irisMatc1.normaliseArray());
        assertNotNull(irisMatc1.SVD());
        assertNotNull(irisMatc1.findWeights());
                                try {
        assertNotNull(irisMatc1.readTestImage("001L_2.png"));
             }
        catch (IOException e) {
        
        }
        assertNotNull(irisMatc1.normaliseTestArray());
        assertNotNull(irisMatc1.findTestWeights());
        assertNotNull(irisMatc1.matchEuclidean());
        assertNotNull(irisMatc1.matchManhattan());
        assertNotNull(irisMatc1.matchMinkowski());
        assertNotNull(irisMatc1.matchMinkowski4());
        
        
    }
}
