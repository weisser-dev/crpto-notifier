package cryptodealer;

import java.util.List;

public class ThreadHandler {

	public static void startAndWaitForThreads(List<Thread> threads) {
		for (Thread thread : threads) {
			thread.start();
		}
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
