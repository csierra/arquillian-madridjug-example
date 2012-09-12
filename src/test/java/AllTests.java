import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bizonos.test.ejb.ListTest;
import com.bizonos.test.ejb.MemberManagerTest;
import com.bizonos.test.rest.MembersRestTest;

@RunWith(Suite.class)
@SuiteClasses({MembersRestTest.class, MemberManagerTest.class, ListTest.class})
public class AllTests {

}
