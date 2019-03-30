import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestCheckmap.class, TestCheckmap2.class, TestCheckmap3.class,
		TestCheckmap4.class, TestCheckmap5.class, TestCheckmap6.class,
		TestIO.class, TestPlayGame.class ,TestAttack.class, TestCardView1.class,TestCardView2.class
		,TestCardView3.class,TestCardView4.class,TestCardView5.class,TestCardView6.class
		,TestCardView7.class})
public class AllTests {

}
