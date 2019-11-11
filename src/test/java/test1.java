package sample;
import org.junit.Assert;
import org.junit.Test;
public class test1 {
@Test
    public void testGetName() {
        Fox fox = new Fox("Ms. Fox", 0);    
        Assert.assertEquals(fox.getName(),"Ms. Fox");
        Fox fox2 = new Fox("Ms. Fox", 1);
        Assert.assertEquals(fox2.getName(),"asdasd");
        Fox fox3 = new Fox("Ms. Fox", 5);
        Assert.assertEquals(fox3.getName(),"ddd");

    }

   

    
}