package com.synway.governace.util;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * @ClassName WordToHtmlUtil
 * @Descroption TODO
 * @Author majia
 * @Date 2020/5/15 12:26
 * @Version 1.0
 **/
public class WordToHtmlUtil {
    private static Logger logger = Logger.getLogger(WordToHtmlUtil.class);


    public static String docToHtml(MultipartFile file, String imagepath) throws Exception {
        String html = "";
        InputStream input = file.getInputStream();
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> suggestedName);
        wordToHtmlConverter.processDocument(wordDocument);
        List pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(imagepath
                            + pic.suggestFullFileName()));

                } catch (FileNotFoundException e) {
                    logger.error("docToHtml失败:", e);
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        org.jsoup.nodes.Document doc = Jsoup.parse(content, "utf-8");
        Elements imgs = doc.body().getElementsByTag("img");

        for (Element img : imgs) {
            String src = img.attr("src");
            img.attr("src", ImageUtil.imageToBase64Head(imagepath + src));
        }
        html = doc.html();
        return html;
    }

    public static String docxToHtml(MultipartFile file, String imagepath, String filepath) throws Exception {
        String html = "";
        InputStream input = file.getInputStream();
        XWPFDocument document = new XWPFDocument(input);
        try {
            XHTMLOptions options = XHTMLOptions.create();
//            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagepath)));
//            // html中图片的路径
//            options.URIResolver(new BasicURIResolver("image"));
            List<XWPFPictureData> list = document.getAllPictures();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, baos, options);
            String s = new String(baos.toByteArray());
            org.jsoup.nodes.Document doc = Jsoup.parse(s);
            Elements elements = doc.getElementsByTag("img");
            if (elements != null && elements.size() > 0 & list != null) {
                for (Element element : elements) {
                    String src = element.attr("src");
                    for (XWPFPictureData data : list) {
                        if (src.contains(data.getFileName())) {
                            element.attr("src", ImageUtil.imageToBase64Head(imagepath + src));
                            break;
                        }
                    }
                }
            }
            html = doc.toString();
        } finally {
            document.close();
        }
        return html;
    }

}
