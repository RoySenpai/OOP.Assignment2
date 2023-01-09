package Ex2_2;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

public class Tests {
	public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @org.junit.jupiter.api.Test
	public void partialTest(){
		CustomExecutor customExecutor = new CustomExecutor();

		var task = Task.createTask(()->{
			int sum = 0;

			for (int i = 1; i <= 10; i++)
				sum += i;

			return sum;
		}, TaskType.COMPUTATIONAL);

		var sumTask = customExecutor.submit(task);
		final int sum;

		try
		{
			sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
		}

		catch (InterruptedException | ExecutionException | TimeoutException e)
		{
			throw new RuntimeException(e);
		}

		logger.info(()-> "Sum of 1 through 10 = " + sum);

		Callable<Double> callable1 = ()-> {
			return 1000 * Math.pow(1.02, 5);
		};

		Callable<String> callable2 = ()-> {
			StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			return sb.reverse().toString();
		};

		// var is used to infer the declared type automatically
		var priceTask = customExecutor.submit(()-> {
			return 1000 * Math.pow(1.02, 5);
		}, TaskType.COMPUTATIONAL);

		var reverseTask = customExecutor.submit(callable2, TaskType.IO);
		final Double totalPrice;
		final String reversed;

		try
		{
			totalPrice = priceTask.get();
			reversed = reverseTask.get();
		}

		catch (InterruptedException | ExecutionException e)
		{
			throw new RuntimeException(e);
		}

		logger.info(()-> "Reversed String = " + reversed);
		logger.info(()->String.valueOf("Total Price = " + totalPrice));
		logger.info(()-> "Current maximum priority = " + customExecutor.getCurrentMax());
		customExecutor.gracefullyTerminate();
	}

    @org.junit.jupiter.api.Test
	public void Tests2() {
		CustomExecutor customExecutor = new CustomExecutor();

		for (int i = 0; i < 100; ++i)
		{
			Callable<Double> callable1 = () -> {
				return 1000 * Math.pow(1.02, 5);
			};

			Callable<String> callable2 = () -> {
				StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
				return sb.reverse().toString();
			};

			customExecutor.submit(() -> {
				return 1000 * Math.pow(1.02, 5);
			}, TaskType.COMPUTATIONAL);

			customExecutor.submit(callable1, TaskType.OTHER);

			customExecutor.submit(callable2, TaskType.IO);

			System.out.println(customExecutor);
		}

		System.out.println(customExecutor);
		customExecutor.gracefullyTerminate();

	}

	@org.junit.jupiter.api.Test
	public void Tests3() {
		CustomExecutor customExecutor = new CustomExecutor();

		for (int i = 0; i < 1000; ++i)
		{
			int finalI = i;
			Callable<Integer> callable1 = () -> {
				return finalI;
			};

			int rnd = (int)(Math.random() * 3);

			switch (rnd)
			{
				case 0:
				{
					customExecutor.submit(callable1, TaskType.COMPUTATIONAL);
					customExecutor.submit(callable1, TaskType.IO);
					customExecutor.submit(callable1, TaskType.OTHER);
					break;
				}

				case 1:
				{
										customExecutor.submit(callable1, TaskType.IO);
					customExecutor.submit(callable1, TaskType.COMPUTATIONAL);
					customExecutor.submit(callable1, TaskType.OTHER);
					break;
				}

				case 2:
				{
					customExecutor.submit(callable1, TaskType.OTHER);
					customExecutor.submit(callable1, TaskType.COMPUTATIONAL);
					customExecutor.submit(callable1, TaskType.IO);
					break;
				}
			}

			logger.info(()-> customExecutor.toString());
		}

		logger.info(()-> customExecutor.toString());
		customExecutor.gracefullyTerminate();
	}
}