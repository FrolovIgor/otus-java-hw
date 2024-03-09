import ru.otus.homework.junitmini.TestRunner;

public class TestExecutor {
    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        runner.run("ExampleTest1");
        runner.run("ExampleTest2");
    }
}
