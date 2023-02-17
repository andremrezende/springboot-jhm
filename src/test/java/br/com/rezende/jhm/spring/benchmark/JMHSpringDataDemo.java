package br.com.rezende.jhm.spring.benchmark;

import br.com.rezende.jhm.spring.SimpleSpringbootAerospikeDemoApplication;
import br.com.rezende.jhm.spring.objects.User;
import br.com.rezende.jhm.spring.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;

/* Environment variables example
forkNum=1;warmIterNum=1;warmTime=1;measurementIterations=1;numThreads=1;numUsers=10
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(timeUnit = TimeUnit.SECONDS)
@Measurement(timeUnit = TimeUnit.SECONDS)
public class JMHSpringDataDemo {

    volatile ConfigurableApplicationContext context;

    // Can add any existing Spring Service from the application
    private UserService userService;

    final static int numberOfUsers = Integer.parseInt(System.getenv("numUsers"));

    @Test
    public void contextLoads() throws RunnerException {
        Options jmhRunnerOptions = new OptionsBuilder()
                // Which benchmark to add
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(Integer.parseInt(System.getenv("warmIterNum")))
                .warmupTime(TimeValue.valueOf(System.getenv("warmTime")))
                //.warmupMode(WarmupMode.BULK)
                .measurementIterations(Integer.parseInt(System.getenv("measurementIterations")))
                .forks(Integer.parseInt(System.getenv("forkNum")))
                .threads(Integer.parseInt(System.getenv("numThreads")))
                .shouldDoGC(true)
                // Much more information
                .verbosity(VerboseMode.EXTRA)

                 // Profilers
                .addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                 //  .addProfiler(LinuxPerfNormProfiler.class)
                 //      .addProfiler(LinuxPerfAsmProfiler.class)
                 //      .addProfiler(WinPerfAsmProfiler.class)
                 //      .addProfiler(DTraceAsmProfiler.class)

                // JVM tuning
                .jvmArgs("-Xmx10g")
                .jvmArgs("-XX:+UseG1GC")

                //.syncIterations(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result("./reports/"+ System.nanoTime()+ ".json")
                .jvmArgs("-server")
                .build();
        new Runner(jmhRunnerOptions).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(SimpleSpringbootAerospikeDemoApplication.class).run();
        userService = this.context.getBean(UserService.class);
    }


    @Benchmark
    public String addUsers() {
        User user ;
        for (int i = 0; i < numberOfUsers; i++) {
            user = new User(i , "<User Name> ", "<User Email>", i+10);
            userService.addUser(user);
            //System.out.println("User-> " + user);
        }
        return "Success";
    }

    @Benchmark
    public String readUserById() {
        userService.readUserById(2);
        for (int i = 0; i < numberOfUsers; i++) {
            userService.readUserById(i);
        }
        return "Success";
    }

    @Benchmark
    public String removeUsers() {
        User user = null;
        for (int i=0 ; i<numberOfUsers;i++){
            userService.removeUserById(i);
        }
        return "Success";
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }
}
