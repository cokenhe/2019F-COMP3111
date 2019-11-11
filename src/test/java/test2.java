package sample;
import org.junit.Assert;
import org.junit.Test;
public class test2 {
@Test
    
    public void testGetID(){
        Fox fox = new Fox();
        Assert.assertEquals(fox.getID(),0);
        
    }
}