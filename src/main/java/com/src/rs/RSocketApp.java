package com.src.rs;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class RSocketApp {
	public static void main(String[] args) {
		SpringApplication.run(RSocketApp.class, args);
	}
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class Employee {
	private String empName;
	private long salary;
}

@Controller
@Slf4j
class RSocketController{
	
	@MessageMapping("request-response")
	Mono<Employee> getEmployee(Employee emp){
		return Mono.just(new Employee(emp.getEmpName(),12345));
	}
	
	
	@MessageMapping("fire-and-forget")
	public Mono<Void> fireAndForget(Employee emp) {
		log.info(emp.toString());
		return Mono.empty();
	}
	
	@MessageMapping("request-stream")
	public Flux<Employee> streamOutput(Map<String,Integer> data) {
		return Flux.interval(Duration.ofSeconds(1))
				   .map(second -> new Employee("RRR-"+second, data.get("key")))
				   .log();
	}
	
	@MessageMapping("stream-stream")
	public Flux<Employee> streamStream(Flux<Integer> streamInput) {
		log.info("Received request for stream-stream");
		return streamInput
				.doOnNext(i -> log.info("next item--"+i))
				.doOnCancel(() -> log.info("Cancelled operation"))
				.switchMap(i -> Flux.just(new Employee("RRR-"+i, new Random().nextLong())))
				.map(e -> e).log();
	}
	
}
