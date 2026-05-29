package com.sebulli.fakturama.office;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TemplateProcessorTest {

    @Test
    void testInterpretParameters_placeholderHasNoParams_Success() {
        TemplateProcessor p = new TemplateProcessor();
        assertEquals("BOO", p.applyParameters("DINGSDA", "BOO"), "placeholder has no parameter!");
    }

    @Test
    void testInterpretParameters_placeholderHasPreParam_Success() {
        TemplateProcessor p = new TemplateProcessor();
        assertEquals("FooBar", p.applyParameters("DINGSDA$PRE:Foo", "Bar"), "placeholder was not correctly substituted with param!");
    }
    
    @Test
    void testInterpretParameters_placeholderHasMoreThanOneParam_Success() {
    	TemplateProcessor p = new TemplateProcessor();
    	assertEquals("Foo", p.applyParameters("DINGSDA$PRE:Foo$FIRST:3", "Bar"), "placeholder was not correctly substituted with param!");
    }

    @Test
    void testInterpretParameters_placeholderHasFirstParam_Success() {
        TemplateProcessor p = new TemplateProcessor();
        assertEquals("Foo", p.applyParameters("DINGSDA$FIRST:3", "FooBar"), "1. placeholder was not correctly substituted with param!");
        assertEquals("FooBar", p.applyParameters("DINGSDA$FIRST:8", "FooBar"), "2. placeholder was not correctly substituted with param!");
        assertEquals("", p.applyParameters("DINGSDA$FIRST:0", "FooBar"), "3. placeholder was not correctly substituted with param!");
        assertEquals("FooBar", p.applyParameters("DINGSDA$FIRST:nodeal", "FooBar"), "4. placeholder was not correctly substituted with param!");
    }

    @Test
    void testInterpretParameters_placeholderHasLastParam_Success() {
        TemplateProcessor p = new TemplateProcessor();
        assertEquals("Bar", p.applyParameters("DINGSDA$LAST:3", "FooBar"), "1. placeholder was not correctly substituted with param!");
        assertEquals("FooBar", p.applyParameters("DINGSDA$LAST:8", "FooBar"), "2. placeholder was not correctly substituted with param!");
        assertEquals("", p.applyParameters("DINGSDA$LAST:0", "FooBar"), "3. placeholder was not correctly substituted with param!");
        assertEquals("FooBar", p.applyParameters("DINGSDA$LAST:nodeal", "FooBar"), "4. placeholder was not correctly substituted with param!");
    }

    @Test
    void testInterpretParameters_placeholderHasRangeParam_Success() {
        TemplateProcessor p = new TemplateProcessor();
        assertEquals("ooB", p.applyParameters("DINGSDA$RANGE:2,4", "FooBar"), "1. placeholder was not correctly substituted with param!");
        assertEquals("ooBar", p.applyParameters("DINGSDA$RANGE:2,8", "FooBar"), "2. placeholder was not correctly substituted with param!");
        assertEquals("", p.applyParameters("DINGSDA$RANGE:2,0", "FooBar"), "3. placeholder was not correctly substituted with param!");
        assertEquals("ooBar", p.applyParameters("DINGSDA$RANGE:2,nodeal", "FooBar"), "4. placeholder was not correctly substituted with param!");
    }

    @Test
    void testInterpretParameters_placeholderHasExRangeParam_Success() {
        TemplateProcessor p = new TemplateProcessor();
        assertEquals("Far", p.applyParameters("DINGSDA$EXRANGE:2,4", "FooBar"), "1. placeholder was not correctly substituted with param!");
        assertEquals("F", p.applyParameters("DINGSDA$EXRANGE:2,8", "FooBar"), "2. placeholder was not correctly substituted with param!");
        assertEquals("FoBar", p.applyParameters("DINGSDA$EXRANGE:2,2", "FooBar"), "3. placeholder was not correctly substituted with param!");
        assertEquals("", p.applyParameters("DINGSDA$EXRANGE:0,6", "FooBar"), "4. placeholder was not correctly substituted with param!");
        assertEquals("F", p.applyParameters("DINGSDA$EXRANGE:2,nodeal", "FooBar"), "5. placeholder was not correctly substituted with param!");
    }

    @Test
    @Disabled("can't be testet at the moment because of missing services")
    void tetGivenParameterForSubstitution_returnSubstitutedValue() {

        //		IEclipseContext context = EclipseContextFactory.create();
        //		IPreferenceStore prefsMock = Mockito.mock(IPreferenceStore.class);
        //		INumberFormatterService numberServiceMock = new NumberFormatterService();
        //		context.set(IPreferenceStore.class, prefsMock);
        //		TemplateProcessor p = ContextInjectionFactory.make(TemplateProcessor.class, context);
        //		Assert.assertEquals("1. placeholder was not correctly substituted with param!", "Bemerkung:\nToast.", p.interpretParameters("DOCUMENT.MESSAGE$REPLACE:{Bemerkung: , }$PRE:Bemerkung:%NL", "Toast."));
        //		Assert.assertEquals("2. placeholder was not correctly substituted with param!", "3,25", 
        //				p.interpretParameters("ITEM.QUANTITY$FORMAT:0.00", "3.251"));
        //		Assert.assertEquals("3. placeholder was not correctly substituted with param!", "3,25", 
        //				p.interpretParameters("ITEM.QUANTITY$FORMAT:0.00", "3.25"));
        //		Assert.assertEquals("4. placeholder was not correctly substituted with param!", "3,20", 
        //				p.interpretParameters("ITEM.QUANTITY$FORMAT:0.00", "3.2"));

    }
}
