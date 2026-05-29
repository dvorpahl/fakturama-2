/**
 *
 */
package org.odftoolkit.helper.common.navigation;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.attribute.text.TextAnchorTypeAttribute;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.element.text.TextParagraphElementBase;
import org.odftoolkit.odfdom.dom.element.text.TextPlaceholderElement;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextExtractor;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.odftoolkit.odfdom.incubator.search.InvalidNavigationException;
import org.odftoolkit.odfdom.incubator.search.Selection;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.type.Length;
import org.odftoolkit.odfdom.type.Length.Unit;
import org.w3c.dom.Node;

/**
 * Container for a placeholder. With some additional information.
 *
 *
 */
public class PlaceholderNode extends Selection {

    public enum PlaceholderNodeType {
        TABLE_NODE, NORMAL_NODE, IMAGE_NODE
    }

    public enum PlaceholderTableType {
        ITEMS_TABLE("ITEM."), VATLIST_TABLE("VATLIST."), SALESEQUALIZATIONTAX_TABLE("SALESEQUALIZATIONTAX."), DISCOUNT_TABLE("ITEMS.DISCOUNT."), DEPOSIT_TABLE(
                "DOCUMENT.DEPOSIT."), NO_TABLE("");

        private String key;

        PlaceholderTableType(final String key) {
            this.key = key;
        }

        /**
         * @return the key
         */
        public final String getKey() {
            return key;
        }
    }

    private Node node;

    /**
     * flag which indicates that this node is inside a table. default is
     * NORMAL_NODE.
     */
    private PlaceholderNodeType nodeType = PlaceholderNodeType.NORMAL_NODE;

    /**
     * If this placeholder is inside a table this field contains the info about
     * which table type it is.
     */
    private PlaceholderTableType tableType = PlaceholderTableType.NO_TABLE;

    /**
     * flag which indicates that this node is inside the styles section
     */
    private boolean styleNode;

    /**
     * the extracted and uppercased placeholder key null if node is no
     * placeholder (node text is empty or does not start with placeholder prefix
     * "<")
     */
    final String placeholderKey;

    public String getPlaceholderKey() {
        return this.placeholderKey;
    }

    /**
     * @param placeholder
     * @return true if placeholderKey != null
     */
    public boolean isPlaceholder() {
        return this.placeholderKey != null;
    }

    /**
     * @param placeholder
     * @return true if isPlaceholder() && placeholderKey.equals(placeholder)
     */
    public boolean isPlaceholder(final String placeholder) {
        return (this.isPlaceholder() && this.placeholderKey.equals(placeholder));
    }

    final private PlaceholderParameters params;

    private OdfTextDocument ownerDocument;

    /**
     * Constructor for a new PlaceholderNode.
     *
     * @param node
     *            the node to start
     */
    public PlaceholderNode(final Node node) {
        this(node, PlaceholderNodeType.NORMAL_NODE, null, false);
    }

    /**
     * Constructor for a new PlaceholderNode.
     *
     * @param node
     * @param styleNode
     *            if this node is a node inside style section or in
     *            header/footer section
     */
    public PlaceholderNode(final Node node, final boolean styleNode) {
        this(node, PlaceholderNodeType.NORMAL_NODE, null, styleNode);
    }

    /**
     * Create a {@link PlaceholderNode} with the given type.
     *
     * @param node
     * @param nodeType
     */
    public PlaceholderNode(final Node node, final PlaceholderNodeType nodeType) {
        this(node, nodeType, null, false);
    }

    /**
     * @param node
     * @param nodeType
     * @param tableType
     * @param styleNode
     */
    public PlaceholderNode(final Node node, final PlaceholderNodeType nodeType, final PlaceholderTableType tableType, final boolean styleNode) {
        this.node = node;
        this.nodeType = nodeType;
        // determine table type, if any
        String content = node.getTextContent();
        // w/o < and >
        String theKey = null;
        if (StringUtils.isNotBlank(content) && content.startsWith(PlaceholderNavigation.PLACEHOLDER_PREFIX)) {
            theKey = StringUtils.removeStart(StringUtils.removeEnd(content, PlaceholderNavigation.PLACEHOLDER_SUFFIX),
                    PlaceholderNavigation.PLACEHOLDER_PREFIX);
            // w/ parameter(s)
            theKey = theKey.contains("$")
            		? theKey.split("\\" + PlaceholderParameters.PARAMETER_SEPARATOR)[0].toUpperCase() 
            		: theKey.toUpperCase();
        }
        this.placeholderKey = theKey;
        if (tableType == null && nodeType == PlaceholderNodeType.TABLE_NODE && node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            if (StringUtils.defaultString(content).startsWith("<ITEM.")) {
                this.tableType = PlaceholderTableType.ITEMS_TABLE;
            }
        } else if (tableType != null) {
            this.tableType = tableType;
        }
        this.styleNode = styleNode;
        params = PlaceholderParameters.of(content);
    }

    /**
     * Replace the text content of this placeholder with a new string. If the
     * string is a multiline string (with one or more line breaks), the string
     * is split into multiple text nodes. This is, because a simple line-break
     * element doesn't create a hard line break (e.g., if you format the
     * paragraph with "justify").
     *
     * @param newText
     *            the replace text String
     * @return the first replaced Node
     */
    public Node replaceWith(final String newText) {
        Node parentNode = getNode().getParentNode();

        // the simple case: if a placeholder sits in a node with only one child
        // AND the new text has no line breaks then we can simply replace this 
        // placeholder node with some text.
        if (newText != null) {
            if (newText.isEmpty()) {
                // if parent node contains only the placeholder node but the text value
                // is empty, we have to remove the complete paragraph node since else 
                // confusing empty lines are generated
                boolean canBeRemoved = true;
                for (int i = 0; i < parentNode.getChildNodes().getLength(); i++) {
                    Node childNode = parentNode.getChildNodes().item(i);
                    if (childNode == getNode() || childNode.getTextContent().isEmpty()) {
                        continue;
                    }

                    // check only first level children
                    if (childNode.hasChildNodes()) {
                        canBeRemoved = false;
                        break;
                    }
                }

                if (canBeRemoved) {
                    parentNode.getParentNode().removeChild(parentNode);
                    return null;
                }
            } else {
                OdfTextSpan span = new OdfTextSpan((OdfFileDom) parentNode.getOwnerDocument());
                span.addContentWhitespace(newText);
                parentNode.replaceChild(span, getNode());
                return span;
            }
        }
        return null;
    }

    public Node replaceWith(final URI uri, final Integer width, final Integer height) {
        // find paragraph
        TextParagraphElementBase paragraphElement = (TextParagraphElementBase) findParentNode(TextPElement.ELEMENT_NAME.getQName(), getNode());
        OdfDrawFrame odfDrawFrame = (OdfDrawFrame) paragraphElement.newDrawFrameElement();
        OdfDrawImage odfDrawImage = (OdfDrawImage) odfDrawFrame.newDrawImageElement();
        try {
            // new image packages this and includes it in the document
            odfDrawImage.newImage(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // set formating as a character instead of floating in the line
        TextAnchorTypeAttribute anchorType = (TextAnchorTypeAttribute) odfDrawFrame.getOdfAttribute(OdfDocumentNamespace.TEXT, "anchor-type");
        anchorType.setEnumValue(TextAnchorTypeAttribute.Value.AS_CHAR);

        // if scaling is needed
        if (width != null) {
            odfDrawFrame.setSvgWidthAttribute(Length.mapToUnit(String.valueOf(width) + "px", Unit.CENTIMETER));
        }

        if (height != null) {
            odfDrawFrame.setSvgHeightAttribute(Length.mapToUnit(String.valueOf(height) + "px", Unit.CENTIMETER));
        }

        /*
         * cleanup: The image was inserted inside the Placeholder tags. Therefore it
         * wouldn't be visible if you open the document. Thus, we change the parent of
         * the Frame element so that it hangs right before the placeholder tags. After
         * this we have to delete the (now empty) placeholder tag because else some
         * brackets would be left.
         */
        Node parentNode = getNode().getParentNode();

        // if the placeholder has siblings only delete the placeholder
        if (getNode().getPreviousSibling() != null || getNode().getNextSibling() != null) {
            parentNode.removeChild(getNode());
        } else {
            // remove the placeholder node and all empty parent nodes up to the
            // parent paragraph
            Node currentNode = getNode();
            while (!currentNode.getNodeName().contentEquals(TextPElement.ELEMENT_NAME.getQName())) {
                if (currentNode.getNodeName().contentEquals(TextPlaceholderElement.ELEMENT_NAME.getQName()) || currentNode.getPreviousSibling() == null
                        || currentNode.getNextSibling() == null) {
                    parentNode = currentNode.getParentNode();
                    parentNode.removeChild(currentNode);
                }
                currentNode = parentNode;
            }
            parentNode = currentNode.getParentNode();
        }
        return parentNode;
    }

    /**
     * Replaces the placeholder with an image.
     *
     * @param uri
     *            URI for the image
     * @return the replaced Node
     */
    public Node replaceWith(final URI uri) {
        return replaceWith(uri, null, null);
    }

    /**
     * Look for a parent node with the given class, beginning from startNode.
     *
     * @param qName
     * @param startNode
     * @return
     */
    public OdfElement findParentNode(final String qName, final Node startNode) {
        // OdfElement retval = null;
        if (startNode != null && startNode.getParentNode() != null) {
            if (startNode.getParentNode().getNodeName().contentEquals(qName)) {
                return (OdfElement) startNode.getParentNode();
            } else {
                return findParentNode(qName, startNode.getParentNode());
            }
        }
        return null;
    }

    /**
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    /**
     * @param node
     *            the node to set
     */
    public void setNode(final Node node) {
        this.node = node;
    }

    /**
     * @return the styleNode
     */
    public boolean isStyleNode() {
        return styleNode;
    }

    /**
     * @param styleNode
     *            the styleNode to set
     */
    public void setStyleNode(final boolean styleNode) {
        this.styleNode = styleNode;
    }

    /**
     * @return the nodeType
     */
    public PlaceholderNodeType getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType
     *            the nodeType to set
     */
    public void setNodeType(final PlaceholderNodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * @return the tableType
     */
    public PlaceholderTableType getTableType() {
        return tableType;
    }

    /**
     * @param tableType
     *            the tableType to set
     */
    public void setTableType(final PlaceholderTableType tableType) {
        this.tableType = tableType;
    }

    /**
     * Compares the (text) content of this node to another
     * {@link PlaceholderNode}.
     *
     * @param other
     * @return
     */
    public int compareTo(final PlaceholderNode other) {
        return getNodeText().compareTo(other.getNodeText());
    }

    /**
     * Compares the (text) content of two nodes to another
     * {@link PlaceholderNode}. This is a convenience method for comparing with
     * a functional interface.
     *
     * @param node1
     *            the first node to compare
     * @param node2
     *            the second node to compare
     * @return the comparison result (0 if both node texts are equal)
     */
    public static int compareByText(final PlaceholderNode node1, final PlaceholderNode node2) {
        return node1.compareTo(node2);
    }

    /**
     * The text content of a node. If the node is a text node (of type
     * {@link Node#TEXT_NODE}) then the node value is returned. Otherwise the
     * content of the appropriate {@link OdfElement} is returned.
     *
     * @return a string containing the text content of this node.
     */
    public String getNodeText() {
        if (node.getNodeType() == Node.TEXT_NODE) {
            return node.getNodeValue();
        }
        if (node instanceof OdfElement) {
            // GS/ [TPR] always return the real (complete) text of the node as expected
            //				otherwise it might interfere elsewhere unexpected (as was with param handling)
            //				If needed, the extraction of the node/placeholder's key is done
            //				also the field placeholderKey was introduced.
            //			String nodeText = TextExtractor.getText((OdfElement) node);
            // only return the "base node text"
            //			return nodeText.contains("$") ? StringUtils.appendIfMissing(nodeText.split("\\$")[0], ">") : nodeText;
            return OdfTextExtractor.newOdfTextExtractor((OdfElement) node).getText();
            // GS/ [TPR] -end-
        }
        return "";
    }

    @Override
    public String toString() {
        StringBuffer retval = new StringBuffer("PlaceholderNode for '");
        if (getNode() != null) {
            retval.append(getNode().getTextContent()).append("'");
        }
        return retval.toString();
    }

    @Override
    public void cut() throws InvalidNavigationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void pasteAtFrontOf(final Selection positionItem) throws InvalidNavigationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void pasteAtEndOf(final Selection positionItem) throws InvalidNavigationException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void refreshAfterFrontalDelete(final Selection deletedItem) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void refreshAfterFrontalInsert(final Selection insertedItem) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void refresh(final int offset) {
        // TODO Auto-generated method stub

    }

    public OdfDocument getOwnerDocument() {
        return ownerDocument;
    }

    public void setOwnerDocument(final OdfTextDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public String getParameter(final String key) {
        return params.getParameterBody(key, null);
    }

    public PlaceholderParameters getParameters() {
        return this.params;
    }
}
