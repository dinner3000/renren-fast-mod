/**
 * 版权所有：版权所有(C) 2014-2024
 * 公    司：北京帮付宝网络科技有限公司
 * BaseApRequest
 * 创建时间：2016/7/8 15:15
 * 作者：张波(工号：AB045179)
 * 功能描述：
 * 版本：1.0.0
 */

package com.brh.channel.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * 功能描述:
 * <p>使用JAXB进行Bean及Xml转换</p>
 * <p>版本: 1.0.0</p>
 */
public final class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);
    private static final String DEFAULT_XML_CHARSET = "utf-8";

    public static String toXml(Object object) throws JAXBException {
        logger.trace("Building xml for Class:{}, object: {} ", object.getClass(), object);

        return toXml(object, null, true, DEFAULT_XML_CHARSET);
    }

    public static String toXml(Object object, String xmlHeader) throws JAXBException {
        logger.trace("Building xml for Class:{}, object: {} ", object.getClass(), object);

        return toXml(object, xmlHeader, true, DEFAULT_XML_CHARSET);
    }

    public static String toXml(Object object, boolean performFormat) throws JAXBException {
        logger.trace("Building xml for Class:{}, object: {} ", object.getClass(), object);

        return toXml(object, null, performFormat, DEFAULT_XML_CHARSET);
    }

    public static String toXml(Object object, boolean performFormat, String charset) throws JAXBException {
        logger.trace("Building xml for Class:{}, object: {} ", object.getClass(), object);

        return toXml(object, null, performFormat, charset);
    }

    public static String toXml(Object object, String xmlHeader, boolean performFormat) throws JAXBException {
        logger.trace("Building xml for Class:{}, object: {} ", object.getClass(), object);

        return toXml(object, xmlHeader, performFormat, DEFAULT_XML_CHARSET);
    }

    public static String toXmlSilently(Object object, String xmlHeader, boolean performFormat) {
        String xml = null;
        try {
            xml = toXml(object, xmlHeader, performFormat, DEFAULT_XML_CHARSET);
        } catch (JAXBException e) {
            logger.error("Failed to convert object to xml.", e);
        }

        return xml;
    }

    /**
     * obj to xml
     * @param object
     * @param xmlHeader
     * @param performFormat 是否格式化XML
     * @return
     * @throws JAXBException
     */
    public static String toXml(Object object, String xmlHeader, boolean performFormat, String charset) throws JAXBException {

        logger.trace("Building xml for Class:{}, object: {}, xml header:{} specified: ",
                object.getClass(), object, xmlHeader);

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        boolean ignoreJaxbFragment = xmlHeader != null;

        JAXBContext context = JAXBCache.instance().getJAXBContext(object.getClass());//创建JAXB环境
        Marshaller marshaller = context.createMarshaller();//创建编组
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, ignoreJaxbFragment);// 是否省略xm头声明信息
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, performFormat);//设置编组属性，使得输出的XML文档进行格式化（提供缩进）
        marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
        marshaller.marshal(object, output);

        String xml;
        try {
            if (ignoreJaxbFragment) {
                xml = xmlHeader + new String(output.toByteArray(), DEFAULT_XML_CHARSET);
            } else {
                xml = new String(output.toByteArray(), DEFAULT_XML_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            throw new JAXBException(e);
        }

//        logger.trace("Built xml:{}", xml);
        return xml;
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObjectSilently(Class<T> clazz, String xml) {
        T bean = null;
        try {
            bean = toObject(clazz, xml);
        } catch (JAXBException e) {
            logger.error("Failed to parse xml to object.", e);
        }

        return bean;
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(Class<T> clazz, String xml) throws JAXBException {
        logger.trace("Building Object:{} from xml:{} ", clazz, xml);
        JAXBContext context = JAXBCache.instance().getJAXBContext(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        T bean = (T) unmarshaller.unmarshal(new StringReader(xml));
        logger.trace("Built Object: {}", bean);
        return bean;
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(Class<T> clazz, String xml, boolean isCkNamespace) throws JAXBException, ParserConfigurationException, SAXException {
        logger.trace("Building Object:{} from xml:{} ", clazz, xml);
        JAXBContext context = JAXBCache.instance().getJAXBContext(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StringReader reader = new StringReader(xml);
        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setNamespaceAware(isCkNamespace);
        XMLReader xmlReader = sax.newSAXParser().getXMLReader();
        Source source = new SAXSource(xmlReader, new InputSource(reader));

        T bean = (T) unmarshaller.unmarshal(source);
        logger.trace("Built Object: {}", bean);
        return bean;
    }
}
