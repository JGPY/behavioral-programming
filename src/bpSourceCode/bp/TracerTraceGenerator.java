package bpSourceCode.bp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This generates 2 files for tracer,
 * the static and the dynamic one.
 */
public class TracerTraceGenerator implements Serializable {

	public enum Action {
		ADD,
		CUT,
		REMOVE
	}
	
	private static final long serialVersionUID = 1L;

	// Enabled/Disabled.
	private boolean tracerEnabled = false;
	
	// The bProgram.
	private BProgram bp;
	
	// Fields for the static XML.
	private Document staticDocument;
	private Element staticSpecification;
	// Map from bthread name to a list of IDs which represents the instance number.
	private Map<String, List<String>> staticBthreadMapOfLists;
	
	// Fields for the dynamic file.
	private int eventCounter;
	private int cutCounter;
	private BufferedWriter dynamicBufferedWriter;
	
	public TracerTraceGenerator(BProgram bp) {
		this.bp = bp;
		this.staticBthreadMapOfLists = new HashMap<String, List<String>>();
		this.eventCounter = 1;
		this.cutCounter = 1;
		try {
			if (System.getProperty("geneTracerTrace", "false").equals("true")) {
				// We're in the game.
				this.tracerEnabled = true;
				// Create the static XML.
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				staticDocument = documentBuilder.newDocument();
				// Append the Specification element.
				staticSpecification = staticDocument.createElement("Specification");
				staticDocument.appendChild(staticSpecification);
				
				// Create the dynamic file.
				FileWriter fstream = new FileWriter(bp.getName()+"_tracer_log.log");
				dynamicBufferedWriter = new BufferedWriter(fstream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method gets a bthread,
	 * If this bthread wasn't already seen,
	 * it appends it's name to the static XML.
	 * 
	 * @param bthreadName The bthreadName to check and maybe append to the static XML.
	 */
	void logToStaticTrace(Action action, BThread bthread) {
		// Enabled/Disabled.
		if (!tracerEnabled) {
			return;
		}
		
		// Sanity.
		if (action != Action.ADD) {
			return;
		}
		
		// Safety.
		if (staticDocument == null) {
			return;
		}
		
		// The name.
		String bthreadName = bthread.toString();
		
		// Case 1: We didn't see the bthread class at all,
		// so it's name is not in the staticBthreadMap as a key.
		if (!staticBthreadMapOfLists.containsKey(bthreadName)) {
			// In this case, we should create a new list,
			// and put the newly seen bthread in the map with this new list.
			// Also, we need to output this newly seen bthread to the static XML.
			List<String> newList = new LinkedList<String>();
			newList.add(bthread.hashCode() + "");
			staticBthreadMapOfLists.put(bthreadName, newList);
			// Create and append child (and grandchild).
			Element bthreadElement = staticDocument.createElement("Bthread");
			Element nameElement = staticDocument.createElement("Name");
			nameElement.appendChild(staticDocument.createTextNode(bthreadName));
			bthreadElement.appendChild(nameElement);
			staticSpecification.appendChild(bthreadElement);
			// Write the XML file.
			writeXmlFile(bp.getName()+"_tracer_log.bpj", staticDocument);
			return;
		}

		// Case 2: We did see the bthread class,
		// so it's name is in the staticBthreadMap as a key,
		// but we didn't see this exact instance of the class,
		// so it's hashCode (as a string) is not in the list of the bthread.
		if (staticBthreadMapOfLists.containsKey(bthreadName) && !staticBthreadMapOfLists.get(bthreadName).contains(bthread.hashCode() + "")) {
			// In this case, we should append this newly seen instance to the list.
			staticBthreadMapOfLists.get(bthreadName).add(bthread.hashCode() + "");
		}
		
		// Case 3: We did see the bthread class,
		// so it's name is in the static instance of the class,
		// and also we did see this exact instance of the class,
		// so it's hasCode (as a string) is in the list of the bthread.
		if (staticBthreadMapOfLists.containsKey(bthreadName) && staticBthreadMapOfLists.get(bthreadName).contains(bthread.hashCode() + "")) {
			// Nothing interesting to do in that case.
			return;
		}
	}

	void logToDynamicTrace(Action action) {
		logToDynamicTrace(action, null);
	}
	
	/**
	 * This method appends to the dynamic trace.
	 */
	void logToDynamicTrace(Action action, BThread bthread) {
		// Enabled/Disabled.
		if (!tracerEnabled) {
			return;
		}

		// We have here 2 actions,
		// CUT and REMOVE.
		if (action == Action.CUT) {
			// Here, a cut as been reached in the BProgram,
			// so we write "E" line and "C" line.

			// Write an "E" (event) line.
			// The format is: "E: <timestamp> <event_number>: <event_signature>
			long timeStamp = System.currentTimeMillis();
			int eventNumber = this.eventCounter++;
			String eventSignature = bp.lastEvent.getName();
			// Write the line.
			writeDynamicFile("E: " + timeStamp + " " + eventNumber + ": " + eventSignature + "\n");
	
			// Write a "C" (cut) line.
			// The format is: "C: <bthread_name>[<instance_number>] <cut_name> <Hot|Cold>
			for (Iterator<BThread> iterator = bp.getAllBThreads().values().iterator(); iterator.hasNext();) {
				BThread iteratorBthread = (BThread) iterator.next();
				String bthreadName = iteratorBthread.toString();
				// Get the instanceNumber from the static map.
				int instanceNumber = staticBthreadMapOfLists.get(bthreadName).indexOf(iteratorBthread.hashCode() + "");
				String cutName = "Cut_" + cutCounter++;
				String temprature = "Hot";
				// Write the line.
				writeDynamicFile("C: " + "uc.MSDAspect" + bthreadName + "[" + instanceNumber + "] " + cutName + " " + temprature + "\n");
			}
		} else {
			if (action == Action.REMOVE) {
				// Here, a BThread has been removed (finished).
				// We write a "F" line.
				
				// Write a "F" (remove) line.
				// The format is: "F: <bthread_name>[<instance_number>] <Completion|Violation>
				String bthreadName = bthread.toString();
				// Get the instanceNumber from the static map.
				int instanceNumber = staticBthreadMapOfLists.get(bthreadName).indexOf(bthread.hashCode() + "");
				String finishReason = "Completion";
				// Write the line.
				writeDynamicFile("F: " + "uc.MSDAspect" + bthreadName + "[" + instanceNumber + "] " + finishReason + "\n");
			}
		}
	}
	
	// This method writes to the dynamic file.
	private void writeDynamicFile(String line) {
		try {
			dynamicBufferedWriter.write(line);
			dynamicBufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// This method writes a DOM document to a file.
	static void writeXmlFile(String filename, Document doc) {
		try {
			// Prepare the DOM document for writing
			Source source = new DOMSource(doc);

			// Prepare the output file
			File file = new File(filename);
			Result result = new StreamResult(file);

			// Write the DOM document to the file
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);
			
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
}
