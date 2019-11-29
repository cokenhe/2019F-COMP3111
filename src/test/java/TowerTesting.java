import org.junit.Assert;
import org.junit.Test;

import controller.*;
import monster.*;
import tower.*;
import helper.*;


public class TowerTesting {


@Test
public void testFindNearestMonster() {
    Tower iceTower = new IceTower(10,0);
    Tower basicTower = new BasicTower(100,100);
    Monster[] monsters = new Monster[5];
    monsters[0] = new Fox(0);
    Assert.assertEquals(iceTower.findNearestMonster(monsters), monsters[0]);
    Assert.assertEquals(basicTower.findNearestMonster(monsters), null);
}

}