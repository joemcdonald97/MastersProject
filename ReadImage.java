

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

/**
 * The test class ReadImage.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ReadImage
{
    /**
     * Default constructor for test class ReadImage
     */
    public ReadImage()
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
    public void ReadImage()
    {
        IrisMatching irisMatc2 = new IrisMatching();
        try {
        assertNotNull(irisMatc2.readImage1("001L_1.png"));
             }
        catch (IOException e) {
        
        }
        
    }
}

