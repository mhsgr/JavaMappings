package de.mhsgr.po.AddNamespace;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;


public class AddNamespace extends AbstractTransformation {
	
	@Override
	public void transform (TransformationInput transformationInput, TransformationOutput transformationOutput) throws StreamTransformationException {
		final InputStream  inputStream  = transformationInput.getInputPayload().getInputStream();
		final OutputStream outputStream = transformationOutput.getOutputPayload().getOutputStream();
	
		try {
			Document source = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
			source.getDocumentElement().normalize();

			Document dest = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			Node    rootNode     = source.getDocumentElement();
			Element destElement  = dest.createElement(rootNode.getNodeName());
			String  prefix       = getPrefix(rootNode.getNodeName());

			if (rootNode.hasAttributes()) {
				NamedNodeMap map = rootNode.getAttributes();
				
				for (int i = 0; i < map.getLength(); i++) {
					Node attribute = map.item(i);
					destElement.setAttribute(attribute.getNodeName(), attribute.getTextContent());
				}
			}

			dest.appendChild(destElement);
			
			domTree(dest, rootNode.getFirstChild(), destElement, prefix, "");

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource destDOM = new DOMSource(dest);
			transformer.transform(destDOM, new StreamResult(outputStream));
		} catch (Exception e) {
			throw new StreamTransformationException(e.getMessage());
		}
	}

	
	private boolean domTree (Document dest, Node node, Element element, String prefix, String indent) {
		boolean ret = false;
		
		do {
			if (node.getNodeName().equals("#text")) {
				continue;
			}
			
			if (node.getNodeName().equals("#comment")) {
				continue;
			}

			String nodeName = node.getNodeName();
			
			if ((getPrefix(nodeName) == null) && (prefix != null)) {
				nodeName = prefix + ":" + nodeName;
			}
			
			Element destElement = dest.createElement(nodeName);
			
			element.appendChild(destElement);
			
			if (node.hasAttributes()) {
				NamedNodeMap map = node.getAttributes();
				
				for (int i = 0; i < map.getLength(); i++) {
					Node attribute = map.item(i);
					destElement.setAttribute(attribute.getNodeName(), attribute.getTextContent());
				}
			}

			if (node.hasChildNodes()) {
				if ((ret = domTree(dest, node.getFirstChild(), destElement, prefix, indent + "  ")) == false) {
					destElement.setTextContent(node.getTextContent());
					ret = true;
				}
			}
		} while ((node = node.getNextSibling()) != null);		

		return ret;
	}
	
	
	private String getPrefix (String name) {
		if (name != null) {
			String names[] = name.split(":");
			
			if (names.length > 1) {
				return names[0];
			}
		}
		
		return null;
	}
}

