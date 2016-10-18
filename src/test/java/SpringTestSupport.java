import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {WebConfig.class})
public abstract class SpringTestSupport implements ApplicationContextAware {

    protected ApplicationContext spring;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.spring = applicationContext;
    }

}
