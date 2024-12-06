@ECHO OFF
@REM aktuelle Fakturama-Version
set VERSION=2.2.0

@REM Pfad setzen
set PATH=c:\Javalibs\fop-1.1\;%PATH%;
set XALAN=c:\Javalibs\xalan-j_2_7_2
@REM c:\Programme\Java\jdk1.8\bin;
set CLASSPATH=.;%XALAN%\serializer.jar;%XALAN%\xalan.jar;%XALAN%\xercesImpl.jar;%XALAN%\xml-apis.jar;%CLASSPATH%

@REM if you want to use FOP 2.1 you have to adapt fakturamaManualPDF.xsl
@REM set PATH=c:\Javalibs\fop-2.1\;%PATH%;c:\Programme\Java\jdk1.8\bin;

ECHO Creating manual for Fakturama %VERSION%.
ECHO.

@REM Erstellen des Titlepage-XSL
java -jar %XALAN%\xalan.jar -IN customization\titlepage.spec.xml -XSL c:\Projekte\herold-doclet\docbook-xsl-1.79.2\template\titlepage.xsl -OUT customization/fakturamaManualTitlepage.xsl

@REM Transformation mit FOP und customization layer
fop -xsl customization\fakturamaManualPDF.xsl -xml manual-main.xml -pdf Handbuch-Fakturama_%VERSION%.pdf

@REM Transformation mit xalan
@REM java -jar %XALAN%\xalan.jar -IN manual-main.xml -XSL customization\fakturamaManualPDF.xsl -OUT hb.fo

@REM create a DocBook targetdb file
@REM java -jar %XALAN%\xalan.jar -IN referencedb/olinkdb.xml -XSL /Projekte/herold-doclet/docbook-xsl-1.79.2/fo/docbook.xsl -PARAM collect.xref.targets "only"

@REM create a HTML file
java -jar %XALAN%\xalan.jar -IN manual-main.xml -OUT mybook.html -XSL c:\Projekte\herold-doclet\docbook-xsl-1.79.2\xhtml5\docbook.xsl
@rem xhtml5/chunk.xsl

java -Djava.endorsed.dirs="c:\Javalibs\xerces-2_11_0;%XALAN%" -Dorg.apache.xerces.xni.parser.XMLParserConfiguration=org.apache.xerces.parsers.XIncludeParserConfiguration org.apache.xalan.xslt.Process -out bookfile.html -in manual-main.xml -xsl c:\Projekte\herold-doclet\docbook-xsl-1.79.2\xhtml5\docbook.xsl
java -Djava.endorsed.dirs="c:\Javalibs\xerces-2_11_0;%XALAN%" -Dorg.apache.xerces.xni.parser.XMLParserConfiguration=org.apache.xerces.parsers.XIncludeParserConfiguration org.apache.xalan.xslt.Process -out bookfile.html -in manual-main.xml -xsl c:\Projekte\herold-doclet\docbook-xsl-1.79.2\webhelp\xsl\webhelp.xsl
java -Djava.endorsed.dirs="c:\Javalibs\xerces-2_11_0;%XALAN%" -Dorg.apache.xerces.xni.parser.XMLParserConfiguration=org.apache.xerces.parsers.XIncludeParserConfiguration org.apache.xalan.xslt.Process -out bookfile.epub -in manual-main.xml -xsl c:\Projekte\herold-doclet\docbook-xsl-1.79.2\epub3\chunk.xsl
java -Djava.endorsed.dirs="c:\Javalibs\xerces-2_11_0;%XALAN%" -Dorg.apache.xerces.xni.parser.XMLParserConfiguration=org.apache.xerces.parsers.XIncludeParserConfiguration org.apache.xalan.xslt.Process -out bookfile.epub -in manual-main.xml -xsl c:\Projekte\herold-doclet\docbook-xsl-1.79.2\epub\docbook.xsl