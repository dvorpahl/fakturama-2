/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2020 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.facturx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.transform.TransformerException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.XmpConstants;
import org.apache.xmpbox.schema.AdobePDFSchema;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAExtensionSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.schema.XMPBasicSchema;
import org.apache.xmpbox.schema.XMPSchema;
import org.apache.xmpbox.schema.XmpSchemaException;
import org.apache.xmpbox.type.ArrayProperty;
import org.apache.xmpbox.type.Attribute;
import org.apache.xmpbox.type.BadFieldValueException;
import org.apache.xmpbox.type.Cardinality;
import org.apache.xmpbox.type.ChoiceType;
import org.apache.xmpbox.type.DefinedStructuredType;
import org.apache.xmpbox.type.PDFAPropertyType;
import org.apache.xmpbox.type.PDFASchemaType;
import org.apache.xmpbox.type.TextType;
import org.apache.xmpbox.type.TypeMapping;
import org.apache.xmpbox.xml.DomXmpParser;
import org.apache.xmpbox.xml.XmpParsingException;
import org.apache.xmpbox.xml.XmpSerializer;
import org.fakturama.export.einvoice.ConformanceLevel;
import org.fakturama.export.einvoice.IPdfHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for generating Factur-x compliant PDF files.
 */
public class FacturXHelper implements IPdfHelper {

    private static final Logger log = LoggerFactory.getLogger(FacturXHelper.class);

    private static final String FACTURX_FILENAME = "factur-x.xml";
    private static final String FACTURX_PREFIX = "fx";
    private static final String FACTURX_URN = "urn:factur-x:pdfa:CrossIndustryDocument:invoice:1p0#";

    /**
     * Makes A PDF/A-3a-compliant document from a PDF/A-1 compliant document (on
     * the metadata level, this will not e.g. convert graphics to JPG-2000)
     * 
     * @return
     * @throws TransformerException
     * @throws IOException
     * @throws XmpParsingException
     * @throws XmpSchemaException
     */
    @Override
    public PDDocument makeA3Acompliant(final String pdfFileName, final ConformanceLevel level)
            throws IOException, TransformerException, XmpParsingException, XmpSchemaException {
        final Path pdfFile = Paths.get(pdfFileName);
        if (Files.notExists(pdfFile)) {
            return null;
        }

        final RandomAccessReadBufferedFile randomAccessRead = new RandomAccessReadBufferedFile(pdfFileName);
        final PDDocument doc = Loader.loadPDF(randomAccessRead);
        final PDDocumentCatalog catalog = doc.getDocumentCatalog();
        final PDMetadata metadata = catalog.getMetadata();
        XMPMetadata xmp;
        if (metadata != null) {
            final DomXmpParser xmpParser = new DomXmpParser();
            xmp = xmpParser.parse(metadata.createInputStream());
        } else {
            xmp = XMPMetadata.createXMPMetadata();
        }
        final TypeMapping tm = new TypeMapping(xmp);

        final DublinCoreSchema dcSchema = Optional.ofNullable(xmp.getDublinCoreSchema()).orElse(xmp.createAndAddDublinCoreSchema());
        final String creator = System.getProperty("user.name"); // set current (operating system) user name as (mandatory) human creator
        dcSchema.addCreator(creator);
        dcSchema.setAboutAsSimple("");

        final XMPBasicSchema basicSchema = Optional.ofNullable(xmp.getXMPBasicSchema()).orElse(xmp.createAndAddXMPBasicSchema());
        basicSchema.setAboutAsSimple("");
        basicSchema.setCreatorTool("Fakturama invoicing software");
        basicSchema.setCreateDate(GregorianCalendar.getInstance());

        final PDDocumentInformation pdi = doc.getDocumentInformation();
        final String producer = "Fakturama.org"; // (mandatory) producer application is Fakturama 
        pdi.setProducer(producer);
        pdi.setAuthor(creator);
        doc.setDocumentInformation(pdi);

        final AdobePDFSchema pdfSchema = Optional.ofNullable(xmp.getAdobePDFSchema()).orElse(xmp.createAndAddAdobePDFSchema());
        pdfSchema.setProducer(producer);
        pdfSchema.setAboutAsSimple("");

        /*
         * Mandatory: PDF/A3-a is tagged PDF which has to be expressed using a
         * MarkInfo dictionary (PDF A/3 Standard sec. 6.7.2.2)*/
        final PDMarkInfo markinfo = new PDMarkInfo();
        markinfo.setMarked(true);
        doc.getDocumentCatalog().setMarkInfo(markinfo);

        final PDFAIdentificationSchema pdfaid = Optional.ofNullable(xmp.getPDFAIdentificationSchema()).orElse(xmp.createAndAddPDFAIdentificationSchema());
        try {
            pdfaid.setConformance("B");
            pdfaid.setPart(3);
        } catch (final BadFieldValueException e) {
            log.error("Could not set PDF/A settings", e);
        }

        createExtensionSchema(xmp, tm);

        /*
         * This is what needs to be added to the RDF metadata - basically the name
         * of the embedded ZUGFeRD file
         */
        final XMPSchema xmpBasicSchema = tm.getSchemaFactory("http://www.aiim.org/pdfa/ns/extension/").createXMPSchema(xmp, FACTURX_PREFIX);
        xmpBasicSchema.addNamespace(FACTURX_URN, FACTURX_PREFIX);
        xmpBasicSchema.setAboutAsSimple("");

        final String conformanceLevel = "EN 16931";
        //        if (conformanceLevel == null) {
        //            conformanceLevel = CONFORMANCELEVEL;
        //        }

        xmpBasicSchema.setTextPropertyValue("ConformanceLevel", conformanceLevel);
        xmpBasicSchema.setTextPropertyValue("DocumentType", DOCTYPE_INVOICE);
        xmpBasicSchema.setTextPropertyValue("DocumentFileName", FACTURX_FILENAME); // ZF21: 
        xmpBasicSchema.setTextPropertyValue("Version", "1.0");

        final XmpSerializer serializer = new XmpSerializer();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serializer.serialize(xmp, baos, true);

        final PDMetadata pdMetadata = new PDMetadata(doc);
        pdMetadata.importXMPMetadata(baos.toByteArray());
        doc.getDocumentCatalog().setMetadata(pdMetadata);

        return doc;
    }

    /**
     * Additionally to adding a RDF namespace with a indication which file
     * attachment if ZUGFeRD, this namespace has to be described in a PDFA
     * Extension Schema.
     */
    private void createExtensionSchema(final XMPMetadata xmp, final TypeMapping tm) {
        final PDFAExtensionSchema extSchema = Optional.ofNullable(xmp.getPDFExtensionSchema()).orElse(xmp.createAndAddPDFAExtensionSchemaWithDefaultNS());
        extSchema.addNamespace(PDFA_EXTENSION_SCHEMA_NAMESPACE, PDFA_EXTENSION_SCHEMA_PREFIX);
        extSchema.addNamespace("http://www.aiim.org/pdfa/ns/property#", "pdfaProperty");

        /*
        * What we attach is basically this:
        * pdfaExtension:schemas-node
        * +--bag
        *    +--rdf:li
        *       +--some text node (multiple)
        *       +--property node
        *          +--rdf:Seq
        *             +--rdf:li (multiple) attribute node
        *                +--some attribute property description text node (multiple)
        */
        final ArrayProperty newBag = extSchema.createArrayProperty("schemas", Cardinality.Bag);
        final DefinedStructuredType li = new DefinedStructuredType(xmp, FACTURX_URN, FACTURX_PREFIX, XmpConstants.LIST_NAME);
        li.setAttribute(new Attribute(FACTURX_URN, XmpConstants.PARSE_TYPE, XmpConstants.RESOURCE_NAME));

        newBag.addProperty(li);
        extSchema.addProperty(newBag);

        final TextType pdfa1 = tm.createText(PDFA_EXTENSION_SCHEMA_NAMESPACE, PDFA_EXTENSION_SCHEMA_PREFIX, PDFASchemaType.SCHEMA,
                "Factur-x PDFA Extension Schema");
        li.addProperty(pdfa1);

        final TextType obj = tm.createText(PDFA_EXTENSION_SCHEMA_NAMESPACE, PDFA_EXTENSION_SCHEMA_PREFIX, "namespaceURI", FACTURX_URN);
        li.addProperty(obj);

        final TextType obj1 = tm.createText(PDFA_EXTENSION_SCHEMA_NAMESPACE, PDFA_EXTENSION_SCHEMA_PREFIX, "prefix", FACTURX_PREFIX);
        li.addProperty(obj1);

        final ArrayProperty newSeq = tm.createArrayProperty(PDFA_EXTENSION_SCHEMA_NAMESPACE, PDFA_EXTENSION_SCHEMA_PREFIX, PDFASchemaType.PROPERTY,
                Cardinality.Seq);
        li.addProperty(newSeq);

        newSeq.addProperty(createProperty(xmp, "DocumentFileName", "Text", "external", "name of the embedded XML invoice file"));
        newSeq.addProperty(createProperty(xmp, "DocumentType", "Text", "external", DOCTYPE_INVOICE));
        newSeq.addProperty(createProperty(xmp, "Version", "Text", "external", "The actual version of the ZUGFeRD XML schema"));
        newSeq.addProperty(createProperty(xmp, "ConformanceLevel", "Text", "external", "The conformance level of the embedded ZUGFeRD data"));

        final ArrayProperty newValType = tm.createArrayProperty(PDFA_EXTENSION_SCHEMA_NAMESPACE, PDFA_EXTENSION_SCHEMA_PREFIX, PDFASchemaType.VALUE_TYPE,
                Cardinality.Seq);
        li.addProperty(newValType);
    }

    private PDFAPropertyType createProperty(final XMPMetadata metadata, final String name, final String type, final String category, final String description) {

        final TypeMapping tm = new TypeMapping(metadata);
        final PDFAPropertyType li = new PDFAPropertyType(metadata);
        li.setAttribute(new Attribute(PDFA_EXTENSION_SCHEMA_NAMESPACE, XmpConstants.PARSE_TYPE, XmpConstants.RESOURCE_NAME));

        ChoiceType pdfa2 = tm.createChoice(li.getNamespace(), li.getPreferedPrefix(), PDFAPropertyType.NAME, name);
        li.addProperty(pdfa2);

        pdfa2 = tm.createChoice(li.getNamespace(), li.getPreferedPrefix(), PDFAPropertyType.VALUETYPE, type);
        li.addProperty(pdfa2);

        pdfa2 = tm.createChoice(li.getNamespace(), li.getPreferedPrefix(), PDFAPropertyType.CATEGORY, category);
        li.addProperty(pdfa2);

        pdfa2 = tm.createChoice(li.getNamespace(), li.getPreferedPrefix(), PDFAPropertyType.DESCRIPTION, description);
        li.addProperty(pdfa2);

        return li;
    }

    /**
     * embed the ZUGFeRD XML structure in a file named ZUGFeRD-invoice.xml
     * 
     * @throws IOException
     */
    @Override
    public PDDocument attachZugferdFile(final PDDocument doc, final ByteArrayOutputStream baos) throws IOException {

        if (doc == null) {
            return null;
        }

        // first create the file specification, which holds the embedded file
        final PDComplexFileSpecification fs = new PDComplexFileSpecification();
        fs.setFile(FACTURX_FILENAME);
        fs.setFileUnicode(FACTURX_FILENAME);
        fs.setFileDescription("electronical invoice according to ZUGFeRD standard");

        final COSDictionary dict = fs.getCOSObject();
        // Relation "Source" for linking with eg. catalog
        // TODO ZF21: MINIMUM & BASIC WL ==> Data
        // TODO ZF21: in FR ==> Source
        dict.setName("AFRelationship", "Alternative"); // as defined in ZUGFeRD standard

        dict.setString("UF", FACTURX_FILENAME);

        // create a data stream from given byte array
        final byte[] zugferdData = baos.toByteArray();
        final ByteArrayInputStream fileData = new ByteArrayInputStream(zugferdData);
        final PDEmbeddedFile ef = new PDEmbeddedFile(doc, fileData);
        // now lets some of the optional parameters
        ef.setSubtype("text/xml");// as defined in ZUGFeRD standard
        ef.setSize(zugferdData.length);
        ef.setCreationDate(GregorianCalendar.getInstance());
        ef.setModDate(GregorianCalendar.getInstance());

        // use both methods for backwards, cross-platform and cross-language compatibility.
        fs.setEmbeddedFile(ef);
        fs.setEmbeddedFileUnicode(ef);

        // embedded files are stored in a named tree
        final PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();

        // now add the entry to the embedded file tree and set in the document.
        efTree.setNames(Collections.singletonMap(FACTURX_FILENAME, fs));
        final PDDocumentNameDictionary names = new PDDocumentNameDictionary(doc.getDocumentCatalog());
        names.setEmbeddedFiles(efTree);
        doc.getDocumentCatalog().setNames(names);

        // AF entry (Array) in catalog with the FileSpec
        final COSArray cosArray = new COSArray();
        cosArray.add(fs);
        doc.getDocumentCatalog().getCOSObject().setItem("AF", cosArray);
        return doc;
    }

}
