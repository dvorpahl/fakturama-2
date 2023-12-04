/**
 *
 */
package org.odftoolkit.helper.common.navigation;

import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.attribute.text.TextAnchorTypeAttribute;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.text.TextLineBreakElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.element.text.TextParagraphElementBase;
import org.odftoolkit.odfdom.dom.element.text.TextPlaceholderElement;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextExtractor;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.odftoolkit.odfdom.incubator.search.InvalidNavigationException;
import org.odftoolkit.odfdom.incubator.search.Selection;
import org.odftoolkit.odfdom.incubator.search.TextNavigation;
import org.odftoolkit.odfdom.incubator.search.TextSelection;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.type.Length;
import org.odftoolkit.odfdom.type.Length.Unit;
// import org.odftoolkit.simple.common.TextExtractor;
// import org.odftoolkit.simple.common.navigation.ImageSelection;
// import org.odftoolkit.simple.draw.Image;
// import org.odftoolkit.simple.style.StyleTypeDefinitions.AnchorType;
// import org.odftoolkit.simple.common.navigation.ImageSelection;
// import org.odftoolkit.simple.draw.Image;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
            theKey = theKey.contains("$") ? theKey.split("\\" + PlaceholderParameters.PARAMETER_SEPARATOR)[0].toUpperCase() : theKey.toUpperCase();
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
                    Node n = parentNode.getChildNodes().item(i);
                    if (n == getNode() || n.getTextContent().isEmpty()) {
                        continue;
                    }

                    // check only first level children
                    if (n.hasChildNodes()) {
                        canBeRemoved = false;
                        break;
                    }
                }

                if (canBeRemoved) {
                    parentNode.getParentNode().removeChild(parentNode);
                    return null;
                }
            } else if (!newText.contains("\r")) {
                OdfTextSpan s = new OdfTextSpan((OdfFileDom) parentNode.getOwnerDocument());
                s.addContent(newText);
                parentNode.replaceChild(s, getNode());
                return s;
            }
        }

        // else, we have to tokenize the new text,
        // clone the original node, put all the children _after_ the placeholder
        // again in the clone and append a new text node for each new line on the cloned node.
        // But wait - all reminding nodes _before_ the origin placeholder have to be retained, too.
        // We do this in reversed order for simplicity.
        // Sounds complicated, eh? But - yes, it is ;-).

        // a list with all created nodes
        // since we can't _append_ one node at another node we have to collect all the 
        // nodes and insert it after all nodes were created.  
        LinkedList<Node> substitutes = new LinkedList<>();

        // create a copy of all children, if any
        NodeList childNodes = parentNode.getChildNodes();
        Node childNodesAfterPlaceholder = parentNode.cloneNode(false);
        Node previousNode = null;
        if (childNodes.getLength() > 1) {
            int currentPosition = childNodes.getLength() - 1;

            // collect all nodes _after_ placeholder node, start at the end of tree
            // put all nodes into a clone of the origin parent node
            // giving them a new parent (this removes the nodes from origin parent)
            for (int i = currentPosition; i >= 0; i--) {
                if (childNodes.item(i).isSameNode(getNode())) {
                    break;
                }
                if (previousNode == null) {
                    previousNode = childNodesAfterPlaceholder.appendChild(childNodes.item(i));
                } else {
                    previousNode = childNodesAfterPlaceholder.insertBefore(childNodes.item(i), previousNode);
                }
            }
        }

        String[] st = StringUtils.splitByWholeSeparatorPreserveAllTokens(StringUtils.defaultString(newText), "\r");

        // if the first line is empty, we have a flag for this case
        boolean firstSkip = false;
        for (String s : st) {
            if (s.isEmpty() && !firstSkip) {
                continue;
            }
            firstSkip = true;
            // create a "template node" by cloning the original node
            Node templateNode = parentNode.cloneNode(false);
            templateNode.setTextContent(s);
            substitutes.add(templateNode);
        }

        // now lets assemble the nodes altogether.
        /*
         * BEFORE:
         * 
         *     O     parentNode
         *    /|\ 
         *  (O O O)
         *       +-- childNodesAfterPlaceholder
         *     +---- placeholder node
         *  +------- (unnamed children from parentNode)
         *  
         * AFTER:
         * 
         *     O     parentNode (cloned)
         *    /|\ 
         * (O  | O)
         *     | +-- childNodesAfterPlaceholder
         *     O
         *    /|\
         *   O O O 
         *     +---- substituted placeholder node
         *  +------- (unnamed children from parentNode)
         */

        Node insertedNode = null;

        // at first insert the (old) childNodes which were _after_ the placeholder node (if not empty)
        if (childNodesAfterPlaceholder.hasChildNodes()) {
            insertedNode = parentNode.getParentNode().insertBefore(childNodesAfterPlaceholder, parentNode.getNextSibling());
        }
        // ...put the collected nodes into parent container (before the origin parent node) ==> reversed order!
        Iterator<Node> it = substitutes.descendingIterator();
        while (it.hasNext()) {
            Node node = it.next();
            TextLineBreakElement lineBreakElement = new TextLineBreakElement((OdfFileDom) parentNode.getOwnerDocument());
            Node lineBreak = insertedNode == null ? parentNode.getParentNode().insertBefore(lineBreakElement, parentNode.getNextSibling())
                    : parentNode.getParentNode().insertBefore(lineBreakElement, insertedNode);
            insertedNode = parentNode.getParentNode().insertBefore(node, lineBreak);
        }
        // ...then remove the origin "placeholder" node...
        parentNode.removeChild(getNode());
        if (!parentNode.hasChildNodes()) {
            // delete (old) parent node if it's now empty
            parentNode.getParentNode().removeChild(parentNode);
        }
        return substitutes.isEmpty() ? null : substitutes.getFirst();
    }

    public Node replaceWith(final URI uri, final Integer width, final Integer height) {
        // find paragraph
        TextParagraphElementBase paragraphElement = (TextParagraphElementBase) findParentNode(TextPElement.ELEMENT_NAME.getQName(), getNode());
        // get selection
        TextSelection textSelection = getTextSelection(paragraphElement);
        OdfDrawFrame dfe = (OdfDrawFrame) paragraphElement.newDrawFrameElement();
        OdfDrawImage die = (OdfDrawImage) dfe.newDrawImageElement();
        try {

            String imagePackagePath = die.newImage(uri);
            //            die.getStyleHandler().setAchorType(AnchorType.AS_CHARACTER);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int leftLength = textSelection.getText().length();
        int index = textSelection.getIndex();
        OdfElement parentElement = textSelection.getContainerElement();

        //        OdfFileDom ownerDom = (OdfFileDom) parentElement.getOwnerDocument();
        int nodeLength = OdfTextExtractor.newOdfTextExtractor(textSelection.getContainerElement()).getText().length();
        DrawFrameElement imageContainer;
        //                Image mImage = null;
        //        
        //                try {
        //                    if (imageContainer == null) {
        //                      delete(index, leftLength, parentElement);
        //                      // PrepareContainer
        //                      imageContainer = ownerDom.newOdfElement(DrawFrameElement.class);
        //                      insertOdfElement(imageContainer, index, parentElement);
        //                    }
        //        //            else {
        //        //              NodeList nodeImages = imageContainer.getElementsByTagName("draw:image");
        //        //              Node nodeImage = nodeImages.item(0);
        //        //              DrawImageElement im = (DrawImageElement) nodeImage;
        //        //              Image oldimage = Image.getInstanceof(im);
        //        //              oldimage.remove();
        //        //              // PrepareContainer
        //        //              imageContainer = ownerDom.newOdfElement(DrawFrameElement.class);
        //        //              insertOdfElement(imageContainer, index, parentElement);
        //        //            }
        //                    // Insert Image resource to package
        //                    DrawImageElement imageElement = imageContainer.newDrawImageElement();
        //                    String imageRef = uri.toString();
        //                    String mediaType = OdfFileEntry.getMediaTypeString(imageRef);
        //                    OdfSchemaDocument mOdfSchemaDoc = (OdfSchemaDocument) ownerDom.getDocument();
        //                    String packagePath = Image.getPackagePath(mOdfSchemaDoc, imageRef);
        //                    mOdfSchemaDoc.getPackage().insert(uri, packagePath, mediaType);
        //                    packagePath = packagePath.replaceFirst(ownerDom.getDocument().getDocumentPath(), "");
        //                    Image.configureInsertedImage(
        //                        (OdfSchemaDocument) ownerDom.getDocument(), imageElement, packagePath, false);
        //                    // get image object
        //                    mImage = Image.getInstanceof(imageElement);
        //                    mImage.getStyleHandler().setAchorType(AnchorType.AS_CHARACTER);
        //                    mImage.setName("replace" + System.currentTimeMillis());
        //        
        //                  } catch (Exception e) {
        //                    Logger.getLogger(ImageSelection.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        //                  }
        //        
        //                
        //                ImageSelection sel = new ImageSelection(textSelection.);

        // replace image from URI
        //                Image img = sel.replaceWithImage(uri);

        // set formating as a character instead of floating in the line
        TextAnchorTypeAttribute anchorType = (TextAnchorTypeAttribute) dfe.getOdfAttribute(OdfDocumentNamespace.TEXT, "anchor-type");
        anchorType.setEnumValue(TextAnchorTypeAttribute.Value.AS_CHAR);

        // if scaling is needed
        if (width != null) {
            dfe.setSvgWidthAttribute(Length.mapToUnit(String.valueOf(width) + "px", Unit.CENTIMETER));
        }

        if (height != null) {
            dfe.setSvgHeightAttribute(Length.mapToUnit(String.valueOf(height) + "px", Unit.CENTIMETER));
        }

        /*
         * cleanup: The image was inserted inside the Placeholder tags. Therefore it
         * wouldn't be visible if you open the document. Thus, we change the parent of
         * the Frame element so that it hangs right before the placeholder tags. After
         * this we have to delete the (now empty) placeholder tag because else some
         * brackets would be left.
         */
        Node parentNode = getNode().getParentNode();
        //        parentNode.insertBefore(img.getFrame().getDrawFrameElement(), getNode());
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
     * Converts this placeholder to a {@link TextSelection} object.
     *
     * @param elementBase
     *
     * @return
     */
    private TextSelection getTextSelection(final OdfElement elementBase) {
        TextNavigation search;
        try {
            search = new TextNavigation(getNode().getTextContent(), ownerDocument);
            if (search.hasNext()) {
                return search.next();
            }
            //            search.match(elementBase);
            //            SelectionManager selectionManager = new SelectionManager();
            //            TextSelection ts = new TextSelection(getNode().getTextContent(), elementBase, 0, selectionManager);
            //            return ts;
        } catch (Exception e) {
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
     * @return the comparision result (0 if both node texts are equal)
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
