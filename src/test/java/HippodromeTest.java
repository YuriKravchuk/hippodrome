import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void constructor_NullHorses_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> new Hippodrome(null));
    }

    @Test
    void constructor_NullHorses_ExceptionContainsHorsesCannotBeNull(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new Hippodrome(null));
        assertEquals( "Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_EmptyList_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void constructor_EmptyList_ExceptionContainsHorsesCannotBeEmpty(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }


    @Test
    void testGetHorsesList() {
       List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("horse#" + i, i, i + 1));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);

        for (int k = 0; k < 30; k++) {
            assertEquals("horse#" + k, hippodrome.getHorses().get(k).getName());
        }
        assertEquals(horseList, hippodrome.getHorses());
        assertEquals(30, hippodrome.getHorses().size());
    }

    @Test
    void move_CallMoveMethodForEachHorse() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse:hippodrome.getHorses()){
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    void testGetWinnerReturnsWinnerHorseWithBiggestDistance() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("horse#1",1,2),
                new Horse("horse#2",3,4),
                new Horse("horse#3",5,6)));

        assertEquals("horse#3", hippodrome.getWinner().getName());
    }
}