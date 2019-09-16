/**
 * 
 */
package subbu.blogspot.wcmaem.core.transformer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;

import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author vraoadavi
 *
 */
/**
 * A Sling Rewriter Transformer which appends in an icon to the beginning of
 * links to document links.
 * {@link https://sling.apache.org/documentation/bundles/output-rewriting-pipelines-org-apache-sling-rewriter.html}
 * @author vraoadavi
 */
public class LinkTypeTransformer implements Transformer {
 
    private static final Logger log = LoggerFactory.getLogger(LinkTypeTransformer.class);
 
    private ContentHandler contentHandler;
 
    private Map<Pattern, String> iconMap = new HashMap<Pattern, String>();
 
    @Override
    public void setDocumentLocator(Locator locator) {
        contentHandler.setDocumentLocator(locator);
    }
 
    @Override
    public void startDocument() throws SAXException {
        contentHandler.startDocument();
    }
 
    @Override
    public void endDocument() throws SAXException {
        contentHandler.endDocument();
    }
 
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        contentHandler.startPrefixMapping(prefix, uri);
    }
 
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        contentHandler.endPrefixMapping(prefix);
    }
 
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (HTML.Tag.A.toString().equalsIgnoreCase(localName) && isDefinedType(atts)) {
            contentHandler.startElement(uri, localName, qName, atts);
            addTypeIcon(atts);
        } else {
            contentHandler.startElement(uri, localName, qName, atts);
        }
    }
 
    private void addTypeIcon(Attributes elAttrs) throws SAXException {
        log.trace("addTypeIcon");
 
        String href = elAttrs.getValue(HTML.Attribute.HREF.toString());
        String icon = null;
        for (Entry<Pattern, String> entry : iconMap.entrySet()) {
            if (entry.getKey().matcher(href).matches()) {
                icon = entry.getValue();
                break;
            }
        }
 
        if (icon != null) {
            log.debug("Adding icon {} to link {}", icon, href);
            AttributesImpl atts = new AttributesImpl();
            atts.addAttribute("", HTML.Attribute.SRC.toString(), "", "", icon);
            contentHandler.startElement("", HTML.Tag.IMG.toString(), "", atts);
            contentHandler.endElement("", HTML.Tag.IMG.toString(), "");
        } else {
            log.warn("Unable to find icon for {}", href);
        }
    }
 
    private boolean isDefinedType(Attributes atts) {
        String href = atts.getValue(HTML.Attribute.HREF.toString());
        if (href != null) {
            for (Pattern pattern : iconMap.keySet()) {
                if (pattern.matcher(href).matches()) {
                    log.debug("URL {} matches pattern {}", href, pattern);
                    return true;
                }
            }
        }
        return false;
    }
 
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        contentHandler.endElement(uri, localName, qName);
    }
 
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        contentHandler.characters(ch, start, length);
    }
 
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        contentHandler.ignorableWhitespace(ch, start, length);
    }
 
    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        contentHandler.processingInstruction(target, data);
    }
 
    @Override
    public void skippedEntity(String name) throws SAXException {
        contentHandler.skippedEntity(name);
    }
 
    @Override
    public void dispose() {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    public void init(ProcessingContext context, ProcessingComponentConfiguration config) throws IOException {
        log.trace("init");
        for (String type : new String[] { "pdf", "doc", "xls" }) {
            iconMap.put(Pattern.compile(".+\\." + type + "($|#.*|\\?.*)", Pattern.CASE_INSENSITIVE),
                    "/etc/designs/sling-rewriting-pipeline-demo/img/" + type + ".png");
        }
    }
 
    @Override
    public void setContentHandler(ContentHandler handler) {
        this.contentHandler = handler;
    }
 
}
