package angles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SILab2Test {
    static SILab2 converter;

    @BeforeAll
    static void init(){
        System.out.println("Instancing converter");
        converter = new SILab2();
    }

    @Test
    void testConvertibleAngles() {
        System.out.println("Testing convertible angles");
        assertEquals(
                List.of(
                        360 * 3600,
                        300*3600+20*60+20
                ),
                converter.function(
                        List.of(
                                new Angle(360, 0, 0),
                                new Angle(300, 20, 20)
                        )
                ));
        assertEquals(List.of(0), converter.function(List.of(new Angle(0, 0, 0))));
        assertEquals(List.of(),converter.function(List.of()));
        assertEquals(
                List.of(100*3600 + 10*60 + 10),
                converter.function(List.of(new Angle(100,10,10)))
        );

    }

    @Test
    void testExceptions() {
        System.out.println("Testing exceptions");
        String INVALID_MINUTES = "The minutes of the angle are not valid!";
        String INVALID_SECONDS = "The seconds of the angle are not valid";
        String INVALID_ANGLE = "The angle is smaller or greater then the minimum";
        String ANGLE_GREATER_THAN_MAX = "The angle is greater then the maximum";

        RuntimeException ex;
        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(new Angle(500, 20, 20))));
        assertTrue(ex.getMessage().contains(INVALID_ANGLE));

        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(300, -10, -10))));
        assertTrue(ex.getMessage().contains(INVALID_MINUTES));

        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(300, 20, -20))));
        assertTrue(ex.getMessage().contains(INVALID_SECONDS));

        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(360, 1, 2))));
        assertTrue(ex.getMessage().contains(ANGLE_GREATER_THAN_MAX));

    }

    @Test
    public void multipleCondition(){
        String INVALID_MINUTES = "The minutes of the angle are not valid!";
        String INVALID_SECONDS = "The seconds of the angle are not valid";
        String INVALID_ANGLE = "The angle is smaller or greater then the minimum";
        String ANGLE_GREATER_THAN_MAX = "The angle is greater then the maximum";

        System.out.println("Testing multiple condition criteria");

        System.out.println(String.format("Testing the branching: %s",
                "if (deg >= 0 && deg < 360) //4"));
        assertEquals(List.of(100*3600+10*60+10), converter.function(List.of(new Angle(100,10,10))));
        RuntimeException ex;
        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(new Angle(-10, 10, 10))));
        assertTrue(ex.getMessage().contains(INVALID_ANGLE));

        System.out.println(String.format("Testing the branching: %s",
                "if (min < 0 || min > 59) //5"));
        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(100, -10, 10))));
        assertTrue(ex.getMessage().contains(INVALID_MINUTES));
        assertEquals(List.of(100*3600+10*60+10), converter.function(List.of(new Angle(100,10,10))));

        System.out.println(String.format("Testing the branching: %s",
                "if (sec < 0 || sec > 59) //8"));
        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(100, 10, -10))));
        assertTrue(ex.getMessage().contains(INVALID_SECONDS));
        assertEquals(List.of(100*3600+10*60+10), converter.function(List.of(new Angle(100,10,10))));

        System.out.println(String.format("Testing the branching: %s",
                "else if (deg == 360) //12"));
        assertEquals(List.of(360*3600), converter.function(List.of(new Angle(360,0,0))));
        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(365, 10, 10))));
        assertTrue(ex.getMessage().contains(INVALID_ANGLE));

        System.out.println(String.format("Testing the branching: %s",
                "if (min == 0 && sec == 0) //13"));
        assertEquals(List.of(360*3600), converter.function(List.of(new Angle(360,0,0))));
        ex = assertThrows(RuntimeException.class, () -> converter.function(List.of(
                new Angle(360, 10, 0))));
        assertTrue(ex.getMessage().contains(ANGLE_GREATER_THAN_MAX));

        System.out.println(String.format("Testing the branching: %s",
                "i < angleList.size(); во for (int i = 0; i < angleList.size(); i++) //2.2"));
        assertEquals(List.of(),converter.function(List.of()));
        assertEquals(List.of(100*3600+10*60+10),converter.function(List.of(new Angle(100,10,10))));
    }


}