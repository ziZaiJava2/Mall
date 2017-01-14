package com.zzty.schedle.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DBScan {

	@PostConstruct
	public void postConStruct() {

//		ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
//		es.scheduleAtFixedRate(new Runnable() {
//
//			@Override
//			public void run() {
//				System.out.println("db scan task start");
//			}
//
//		}, 0, 5, TimeUnit.SECONDS);
	}
}
