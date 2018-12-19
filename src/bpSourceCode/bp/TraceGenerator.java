package bpSourceCode.bp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

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

import bpSourceCode.bp.eventSets.AllEvents;
import bpSourceCode.bp.eventSets.EmptyEventSet;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.RequestableEventSet;

public class TraceGenerator implements Serializable {

	// We need a Document
	Document doc;
	Element root;
	BProgram bp;
	
	public TraceGenerator(BProgram bp) {
		this.bp = bp;
		try {

			if (System.getProperty("geneVisTrace", "false").equals("true")) {

				DocumentBuilderFactory dbfac = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = dbfac.newDocumentBuilder();

				doc = docBuilder.newDocument();

				root = doc.createElement("EventTrace");

				doc.appendChild(root);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	void logToVisualizationTrace() {

		if (doc == null)
			return;

		// create child elements, add attributes, and add to root
		Element eventData = doc.createElement("syncpoint");

		eventData.setAttribute("number", "" + bp.eventCounter);

		eventData.appendChild(logObj("event", bp.lastEvent));

		root.appendChild(eventData);

		logDependencyTable(eventData);
		
		logHierarchyTable(eventData);
		
		writeXmlFile(bp.getName()+"_log.xml",doc);
	}

	void logHierarchyTable (Element eventData) {
		Element hierarchyTable = doc.createElement("hierarchyTbl");
		eventData.appendChild(hierarchyTable);

		for (BThread bt : bp.getAllBThreads().values()) {

			Element btElement = logObj("bthread", bt);

			hierarchyTable.appendChild(btElement);

			btElement.appendChild(logObj("requsted", bt.requestedEvents));
			btElement.appendChild(logObj("watched", bt.watchedEvents));
			btElement.appendChild(logObj("blocked", bt.blockedEvents));

		}
	}
	void logDependencyTable (Element eventData) {
		Element dependencyTable = doc.createElement("dependencyTbl");
		eventData.appendChild(dependencyTable);

		for (BThread sc : bp.getAllBThreads().values()) {
			if (sc.requestedEvents != null) {
								
				for (Event e :  sc.requestedEvents.getEventList()) {

					Element request = doc.createElement("request");

					request.appendChild(logObj("bthread", sc));
					request.appendChild(logObj("event", e));

					dependencyTable.appendChild(request);

					for (BThread watcher : bp.getAllBThreads().values()) {

						if (watcher.watchedEvents.contains(e)) {
							Element watch = doc.createElement("watch");

							watch.appendChild(logObj("bthread", watcher));
							watch.appendChild(logObj("filter",
									watcher.watchedEvents));

							request.appendChild(watch);
						}
					}

					for (BThread blocker : bp.getAllBThreads().values()) {

						if (blocker.blockedEvents.contains(e)) {
							Element block = doc.createElement("block");

							block.appendChild(logObj("bthread", blocker));
							block.appendChild(logObj("filter",
									blocker.blockedEvents));

							request.appendChild(block);
						}
					}
				}
			}
		}
		
	}
	Element logFilterInformation(Element element, Object o) {
		if (o instanceof Event)
			element.setAttribute("type", "Event");
		else if (o instanceof EventSet)
			element.setAttribute("type", "EventFilter");
		else if (o instanceof RequestableEventSet)
			element.setAttribute("type", "RequestEventSet");
		else if (o instanceof EmptyEventSet)
			element.setAttribute("type", "Empty");
		else if (o instanceof AllEvents)
			element.setAttribute("type", "All");
		else
			element.setAttribute("type", "AbstractFilter");


		try {
			ArrayList<Event> filter = ((RequestableEventSet) o).getEventList();

			for (Event e : filter) {
				element.appendChild(logObj("event", e));
			}
		} catch (ClassCastException e) {
		}

		try {
			EventSet filter = (EventSet) o;

			for (EventSetInterface f : filter) {
				element.appendChild(logObj("sub-filter", f));
			}
		} catch (ClassCastException e) {
		}

		return element;

	}

	Element logObj(String title, Object o) {
		Element element = doc.createElement(title);

		element.setAttribute("name", o.toString());
		element.setAttribute("id", Integer.toHexString(System
				.identityHashCode(o)));
		element.setAttribute("class", o.getClass().getSimpleName());

		if (o instanceof EventSetInterface)
			logFilterInformation(element, o);

		return element;

	}

	// This method writes a DOM document to a file
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
