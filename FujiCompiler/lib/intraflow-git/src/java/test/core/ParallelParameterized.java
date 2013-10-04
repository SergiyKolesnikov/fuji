package test.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

/**
 * Runs parameterized tests in parallel
 */
public class ParallelParameterized extends Parameterized {

	private static final int NUM_THREADS = 16;

	/**
	 * Constructor
	 * @param klass
	 * @throws Throwable
	 */
	public ParallelParameterized(Class<?> klass) throws Throwable {
		super(klass);
		setScheduler(new ThreadPoolScheduler());
	}

	private static class ThreadPoolScheduler implements RunnerScheduler {
		private final ExecutorService executor;

		public ThreadPoolScheduler() {
			executor = Executors.newFixedThreadPool(NUM_THREADS);
		}

		@Override
		public void finished() {
			executor.shutdown();
			try {
				executor.awaitTermination(10, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void schedule(Runnable test) {
			executor.submit(test);
		}
	}

}
