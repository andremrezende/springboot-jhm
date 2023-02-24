package br.com.rezende.jhm.spring.benchmark;

import br.com.rezende.jhm.spring.SimpleSpringbootApplication;
import br.com.rezende.jhm.spring.model.User;
import br.com.rezende.jhm.spring.repositories.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
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
import org.springframework.util.CollectionUtils;

import java.util.List;
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
//    private UserService userService;
//    private MySQLContainer mySQLContainer;

    private UserRepository userRepository;

    final static int numberOfUsers = Integer.parseInt(System.getenv("numUsers"));

    @Setup
    public void setup() {
        this.context = new SpringApplication(SimpleSpringbootApplication.class).run();
        userRepository = this.context.getBean(UserRepository.class);

    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


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
                .timeUnit(TimeUnit.MILLISECONDS)
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

//    @Benchmark
//    public String readUserByName() {
//        userService.readUserByName(String.valueOf(2));
//        for (int i = 0; i < numberOfUsers; i++) {
//            userService.readUserByName(String.valueOf(i));
//        }
//        return "Success";
//    }

    @Benchmark
    public String findByName() throws InterruptedException {
//        Optional<User> users = userRepository.findByName("1");
//        if(users.isPresent()) {
//            return users.get().toString();
//        }
        List<User> users = userRepository.findByName("Andre");
        if(!CollectionUtils.isEmpty(users)) {
            return users.get(0).toString();
        }
        return "Fail";
    }

}
