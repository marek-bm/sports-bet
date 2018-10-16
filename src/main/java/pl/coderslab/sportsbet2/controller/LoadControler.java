package pl.coderslab.sportsbet2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadControler {

//    @Autowired
//    JobLauncher jobLauncher;
//
//    @Autowired
//    Job job;
//
//    @GetMapping
//    public String load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//
//        Map<String, JobParameter> maps= new HashMap<>();
//        maps.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters paremeters = new JobParameters(maps);
//        JobExecution jobExecution=jobLauncher.run(job,paremeters);
//
//        while(jobExecution.isRunning()){
//            System.out.println("Batch is runnning...");
//        }
//
//        return "Job execution: "+jobExecution.getStatus();
//
//    }

}
