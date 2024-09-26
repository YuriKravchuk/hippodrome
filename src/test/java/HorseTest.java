import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    void constructor_NullName_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> new Horse(null,1,1));
    }

    @Test
    void constructor_NullName_ExceptionContainsNameCannotBeNull(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new Horse(null,1,1));
        assertEquals( "Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "   ", "\t\t", "\n\n" })
    void constructor_EmptySymbols_IllegalArgumentException(String input) {
        assertThrows(IllegalArgumentException.class,() -> new Horse(input,1,1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "   ", "\t\t", "\n\n" })
    void constructor_EmptySymbols_ExceptionContainsNameCannotBeBlank(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new Horse(input,1,1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_MinusSpeed_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> new Horse("horse",-1,1));
    }

    @Test
    void constructor_MinusSpeed_ExceptionContainsSpeedCannotBeNegative(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new Horse("horse",-1,1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_MinusDistance_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> new Horse("horse",1,-1));
    }

    @Test
    void constructor_MinusDistanceExceptionContainsDistanceCannotBeNegative(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new Horse("horse",1,-1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetName() {
        Horse horse = new Horse("horse", 1,2);
        assertEquals("horse", horse.getName());
    }

    @Test
    void testGetSpeed() {
        Horse horse = new Horse("horse", 1,2);
        assertEquals(1, horse.getSpeed());
    }

    @Test
    void testGetDistance() {
        Horse horse = new Horse("horse", 1,2);
        assertEquals(2, horse.getDistance());
    }

    @Test
    void testGetDistanceZeroWithTwoParametersInHorseConstructor() {
        Horse horse = new Horse("horse", 1);
        assertEquals (0, horse.getDistance());
    }

    @Test
    void move_CallGetRandomDoubleRandomMethod() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("horse",1,1);

            horse.move();

            horseMockedStatic.verify(()-> Horse.getRandomDouble(0.2,0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.2, 0.5, 0.9, 10})
    void move_CalculateDistanceToFormula(double randomDouble) {
        double speed = 1;
        double distance = 2;
        Horse horse = new Horse("horse", speed,distance);
        double expectedDistance = distance + speed * randomDouble;

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(()->Horse.getRandomDouble(0.2,0.9)).thenReturn(randomDouble);

            horse.move();
            assertEquals(expectedDistance,horse.getDistance());
        }
    }
}