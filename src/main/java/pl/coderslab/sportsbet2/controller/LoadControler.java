package pl.coderslab.sportsbet2.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class LoadControler {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping
    public String load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> maps= new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters paremeters = new JobParameters(maps);
        JobExecution jobExecution=jobLauncher.run(job,paremeters);

        while(jobExecution.isRunning()){
            System.out.println("Batch is runnning...");
        }

        return "Job execution: "+jobExecution.getStatus();

    }

}
