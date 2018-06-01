import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChainTest {
private static final Boolean True = Boolean.TRUE;
private static final Boolean False = Boolean.FALSE;
private static final PointOnMap pointOnMap = new PointOnMap(1,1,1);
private static final Integer firstChainlength = 2;
private static final Integer secondChainlength = 4;
private static final Integer thirdChainlength = 3;

private static final Integer firstAssertionResult = 8;
private static final Integer secondAssertionResult = 100;
private static final Integer thirdAssertionResult = 0;

private Chain firstChain;
private Chain secondChain;
private Chain thirdChain;


@Before
public void setUp(){
	firstChain = new Chain(True,True,firstChainlength, pointOnMap, pointOnMap);
	secondChain = new Chain(True,False,secondChainlength, pointOnMap, pointOnMap);
	thirdChain = new Chain(False,False,thirdChainlength, pointOnMap, pointOnMap);
	firstChain.countValue();
	secondChain.countValue();
	thirdChain.countValue();
}

@Test
public void countValue() throws Exception {
	Assert.assertEquals(firstAssertionResult,firstChain.getValue());
	Assert.assertEquals(secondAssertionResult,secondChain.getValue());
	Assert.assertEquals(thirdAssertionResult,thirdChain.getValue());
}

}