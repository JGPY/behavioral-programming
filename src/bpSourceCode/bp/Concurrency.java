package bpSourceCode.bp;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

/* concurrency control */
public class Concurrency implements Serializable {

	public Semaphore semaphore;
	public boolean control;

	public Concurrency() {
		String property = System.getProperty("concurrency", "inf");
		control = false;
		System.out.println("concurrency="
				+ System.getProperty("concurrency", "inf"));
		if (!property.equals("inf")) { // activate semaphore only if not
			// infinite
			semaphore = new Semaphore(Integer.parseInt(property));
			control = true;
		}
	}
}