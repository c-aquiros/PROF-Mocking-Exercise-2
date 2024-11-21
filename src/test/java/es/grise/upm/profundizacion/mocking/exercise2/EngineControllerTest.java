import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import es.grise.upm.profundizacion.mocking.exercise2.Time;
import java.lang.reflect.Method;

public class EngineControllerTest {

    @Test
    public void recordGearTest() {
        Time time = mock(Time.class);
        when(time.getCurrentTime());
    }
}
