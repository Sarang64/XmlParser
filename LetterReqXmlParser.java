
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LetterReqXmlParser {

	private static final String FILENAME = "D:\\CSV\\letters.xml";

	public List<LetterRequest> parse() {
		List<LetterRequest> letterReqList = new ArrayList<LetterRequest>();
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// optional, but recommended
			// process XML securely, avoid attacks like XML External Entities (XXE)
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File(FILENAME));

			// optional, but recommended
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			System.out.println("------");

			NodeList list = doc.getElementsByTagName("LetterRequest");
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node node = list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					// get text
					//TODO: add other fields from xml
					String agentId = element.getElementsByTagName("AgentId").item(0).getTextContent();
					String applicationId = element.getElementsByTagName("ApplicationId").item(0).getTextContent();
					String clientId = element.getElementsByTagName("ClientId").item(0).getTextContent();
					LetterRequest letterReq = new LetterRequest(agentId, applicationId, clientId);
					letterReqList.add(letterReq);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return letterReqList;
	}

	public static void main(String[] args) {
		LetterReqXmlParser instance = new LetterReqXmlParser();
		List<LetterRequest> letterReqList = instance.parse();
		letterReqList.forEach(letter -> System.out.println(letter.toString()));
	}
}

class LetterRequest {
	String agentId;
	String applicationId;
	String clientId;

	public LetterRequest(String agentId, String applicationId, String clientId) {
		super();
		this.agentId = agentId;
		this.applicationId = applicationId;
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "LetterRequest [agentId=" + agentId + ", applicationId=" + applicationId + ", clientId=" + clientId
				+ "]";
	}
}
