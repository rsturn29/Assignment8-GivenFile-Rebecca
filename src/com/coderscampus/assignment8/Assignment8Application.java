package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {

	public static void main(String[] args) {

		Assignment8 assignment8 = new Assignment8();

		List<Integer> allNumbers = Collections.synchronizedList(new ArrayList<>(1000));

		ExecutorService executor = Executors.newFixedThreadPool(10000);

		List<CompletableFuture<Void>> tasks = new ArrayList<>(1000);

		for (int i = 0; i < 1000; i++) {
			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), executor)
					.thenAccept(numbers -> allNumbers.addAll(numbers));
			tasks.add(task);
		}

		while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000)

		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		System.out.println("Done getting 1,000,000 numbers");
		System.out.println("This number should be one million: " + allNumbers.size());

		
		Map<Integer, Integer> numbers = new HashMap<Integer, Integer>();
		for (Integer i :allNumbers) {
			Integer j = numbers.get(i);
			numbers.put(i, (j == null)? 1 : j + 1);
		}
		for (Entry<Integer, Integer> value : numbers.entrySet()) {
			System.out.println("The number " + value.getKey() + " " + "occurs " + value.getValue());
		}


	}

}
