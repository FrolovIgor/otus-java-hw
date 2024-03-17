import ru.otus.homework.annotations.*;

public class ExampleTest1 {

    @Before
    private void before1() {
    }
    @Before
    private void before2() {
    }
    @Before
    private void before3() {
    }

    @Test
    private void test1() {
        throw new RuntimeException();
    }
    @Test
    private void test2() {
    }
    @Test
    private void test3() {
    }

    @After
    private void after1() {
    }
    @After
    private void after2() {
    }

}
