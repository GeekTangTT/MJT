package com.olym.mjt.utils;

import com.olym.mjt.network.responsedata.CheckVersionResponseBean;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class VersionCheckXmlUtil
{
    public static CheckVersionResponseBean parseXml(String paramString)
            throws Exception
    {
        CheckVersionResponseBean localCheckVersionResponseBean = new CheckVersionResponseBean();
        paramString = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(paramString.getBytes("utf-8")))).getDocumentElement().getChildNodes();
        int i = 0;
        if (i < paramString.getLength())
        {
            Object localObject = paramString.item(i);
            if (((Node)localObject).getNodeType() == 1)
            {
                localObject = (Element)localObject;
                if (!"version".equals(((Element)localObject).getNodeName())) {
                    break label119;
                }
                localCheckVersionResponseBean.setVersion(((Element)localObject).getFirstChild().getNodeValue());
            }
            for (;;)
            {
                i += 1;
                break;
                label119:
                if ("name".equals(((Element)localObject).getNodeName())) {
                    localCheckVersionResponseBean.setName(((Element)localObject).getFirstChild().getNodeValue());
                } else if ("url".equals(((Element)localObject).getNodeName())) {
                    localCheckVersionResponseBean.setUrl(((Element)localObject).getFirstChild().getNodeValue());
                } else if ("resultDesc".equals(((Element)localObject).getNodeName())) {
                    localCheckVersionResponseBean.setResultDesc(((Element)localObject).getFirstChild().getNodeValue());
                } else if ("mustupdate".equals(((Element)localObject).getNodeName())) {
                    localCheckVersionResponseBean.setMustupdate(((Element)localObject).getFirstChild().getNodeValue());
                } else if ("resultCode".equals(((Element)localObject).getNodeName())) {
                    localCheckVersionResponseBean.setResultCode(((Element)localObject).getFirstChild().getNodeValue());
                }
            }
        }
        return localCheckVersionResponseBean;
    }
}
