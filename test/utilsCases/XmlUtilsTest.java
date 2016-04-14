package utilsCases;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.succez.server.utils.XmlUtils;

public class XmlUtilsTest {

	@Test
	public void testProperties() {
		File file = new File("src/properties.xml");
		try {
			Element root = XmlUtils.getRoot(XmlUtils.load(file));
			System.out.println(root.getNodeName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetChidren() {
		File file1 = new File("F:/test/config.xml");
		try {
			Document doc1 = XmlUtils.load(file1);
			Element root = XmlUtils.getRoot(doc1);
			Element[] eles = XmlUtils.getChildren(root);
			Assert.assertEquals(2, eles.length);
			Assert.assertEquals("Hubei", eles[0].getNodeName());
			Assert.assertEquals("Henan", eles[1].getNodeName());

			Element a = XmlUtils.getChildByName(root, "tianjin");
			Assert.assertNull(a);

			Element zhoukou = XmlUtils.getChildByName(eles[1], "zhoukou");
			Assert.assertEquals("zhoukou", zhoukou.getNodeName());
			Assert.assertEquals("9000", XmlUtils.getElementText(XmlUtils
					.getChildByName(zhoukou, "area")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRoot() {
		File file1 = new File("F:/test/config.xml");
		File file2 = new File("F:/test/a1.xml");
		try {
			Document doc1 = XmlUtils.load(file1);
			Document doc2 = XmlUtils.load(file2);
			Element root1 = XmlUtils.getRoot(doc1);
			Element root2 = XmlUtils.getRoot(doc2);
			Assert.assertEquals("China", root1.getNodeName());
			Assert.assertNull(root2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoad() {
		File file = new File("F:/test/config.xml");
		try {
			Document doc = XmlUtils.load(file);
			Element ele = doc.getDocumentElement();
			System.out.println(ele.getNodeName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testIsXML() {
		File file1 = new File("F:/test/a1.xml");
		File file2 = new File("F:/test/a2.axml");
		File file3 = new File("F:/test/a3.xm");
		File file4 = new File("F:/test/a4.XML");
		File file5 = new File("F:/test/a5.xMl");
		File file6 = new File("F:/test/a6xml");
		File file7 = new File("F:/test/xml");

		Assert.assertEquals(true, XmlUtils.isXml(file1));
		Assert.assertEquals(false, XmlUtils.isXml(file2));
		Assert.assertEquals(false, XmlUtils.isXml(file3));
		Assert.assertEquals(true, XmlUtils.isXml(file4));
		Assert.assertEquals(true, XmlUtils.isXml(file5));
		Assert.assertEquals(false, XmlUtils.isXml(file6));
		Assert.assertEquals(false, XmlUtils.isXml(file7));
	}
}
