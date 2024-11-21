package es.grise.upm.profundizacion.mocking.exercise2;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Timestamp;

public class EngineControllerTest {

    private Logger logger;
    private Speedometer speedometer;
    private Gearbox gearbox;
    private Time time;
    private EngineController controller;

    @BeforeEach
    public void setUp() {
        logger = mock(Logger.class);
        speedometer = mock(Speedometer.class);
        gearbox = mock(Gearbox.class);
        time = mock(Time.class);
        controller = new EngineController(logger, speedometer, gearbox, time);
    }
    @Test
    public void recordGearTest() {
        when(time.getCurrentTime()).thenReturn(Timestamp.valueOf("2024-11-22 09:00:00"));
        GearValues newGear = GearValues.STOP;
        controller.recordGear(newGear);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).log(argumentCaptor.capture());
        assertEquals("2024-11-22 09:00:00 Gear changed to STOP",
                argumentCaptor.getValue());
    }

    @Test
    public void getInstantaneousSpeedTest() {
        when(speedometer.getSpeed()).thenReturn(20.0).thenReturn(45.0).thenReturn(25.0);
        assertEquals(30.0, controller.getInstantaneousSpeed());
    }


    @Test
    public void adjustGearInvokeTest() {
        when(time.getCurrentTime()).thenReturn(Timestamp.valueOf("2024-11-22 09:00:00"));
        when(speedometer.getSpeed()).thenReturn(20.0).thenReturn(45.0).thenReturn(25.0);
        controller.adjustGear();
        verify(speedometer, times(3)).getSpeed();
    }

    @Test
    public void adjustGearLogTest() {
        when(time.getCurrentTime()).thenReturn(Timestamp.valueOf("2024-11-22 09:00:00"));
        when(speedometer.getSpeed()).thenReturn(5.0).thenReturn(10.0).thenReturn(20.0);
        controller.adjustGear();
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).log(argumentCaptor.capture());  // Capture the log message
        assertTrue(argumentCaptor.getValue().contains("Gear changed to " + GearValues.FIRST));
    }

    @Test
    public void adjustGearSetTest() {
        when(time.getCurrentTime()).thenReturn(Timestamp.valueOf("2024-11-22 09:00:00"));
        when(speedometer.getSpeed()).thenReturn(20.0).thenReturn(45.0).thenReturn(25.0);
        controller.adjustGear();
        ArgumentCaptor<GearValues> argumentCaptor = ArgumentCaptor.forClass(GearValues.class);
        verify(gearbox).setGear(argumentCaptor.capture());
        assertEquals(GearValues.SECOND, argumentCaptor.getValue());
    }
}