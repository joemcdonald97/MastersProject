

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

/**
 * The test class NormaliseArray.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NormaliseArray
{
    /**
     * Default constructor for test class NormaliseArray
     */
    public NormaliseArray()
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
    }

    @Test
    public void NormaliseArray()
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
    }
}

