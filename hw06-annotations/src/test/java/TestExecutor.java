import ru.otus.homework.junitmini.TestRunner;

public class TestExecutor {
    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        runner.runTestsByClassName("ExampleTest1");
        runner.runTestsByClassName("ExampleTest2");
    }
}
