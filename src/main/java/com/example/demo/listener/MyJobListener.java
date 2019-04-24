package com.example.demo.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println(jobExecution.getJobInstance().getJobName() + "job execution before job");
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println(jobExecution.getJobInstance().getJobName() +  "job execution after job");
	}
}
