package exist.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses(
        {UnauthorizedUserTest.class,
        AuthorizedUserTest.class}
)

public class TestSuite {
}
