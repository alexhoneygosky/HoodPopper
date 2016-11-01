import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.JUnit4TestAdapter;

public class HoodPopperTestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new JUnit4TestAdapter(HoodPopperTests.class));
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
