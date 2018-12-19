package bpSourceCode.bp;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for the {@link BProgram} class.
 * 
 */
public class BProgramTestSuite {

	@Test
	public void addJavaThreadToBProgram() {
		BProgram bp = new BProgram();
		bp.add(2d);
		bp.bSync(new Event(), none, none);
		bp.remove();
		
		assertTrue( true );
	}
}
