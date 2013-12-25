package main;


import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SAXReaderDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		File file = new File("zzz.xml");
		Document document;
		try {
			document = reader.read(file);
			Element root = document.getRootElement();
			List<Element> childElements = root.elements();
			for (Element child : childElements) {
				//????????
				/*List<Attribute> attributeList = child.attributes();
				for (Attribute attr : attributeList) {
					System.out.println(attr.getName() + ": " + attr.getValue());
				}*/
				
				//????????
//				System.out.println("id: " + child.attributeValue("id"));
				
				//?????????
				/*List<Element> elementList = child.elements();
				for (Element ele : elementList) {
					System.out.println(ele.getName() + ": " + ele.getText());
				}
				System.out.println();*/
				
				//??????????
//				System.out.println("title" + child.elementText("title"));
//				System.out.println("author" + child.elementText("author"));
				
				System.out.println(child.elementText("word"));
				//?????????????
//				System.out.println();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
