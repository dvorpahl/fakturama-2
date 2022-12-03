package com.sebulli.fakturama.office;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TemplateProcessorTest {

	@Test
	public void testInterpretParameters_placeholderHasNoParams_Success() {
		TemplateProcessor p = new TemplateProcessor();
		Assert.assertEquals("placeholder has no parameter!", "BOO", p.interpretParameters("DINGSDA", "BOO"));
	}
	
	@Test
	public void testInterpretParameters_placeholderHasPreParam_Success() {
		TemplateProcessor p = new TemplateProcessor();
		Assert.assertEquals("placeholder was not correctly substituted with param!", "FooBar", p.interpretParameters("DINGSDA$PRE:Foo", "Bar"));
	}
	
	@Test
	public void testInterpretParameters_placeholderHasFirstParam_Success() {
		TemplateProcessor p = new TemplateProcessor();
		Assert.assertEquals("1. placeholder was not correctly substituted with param!", "Foo", p.interpretParameters("DINGSDA$FIRST:3", "FooBar"));
		Assert.assertEquals("2. placeholder was not correctly substituted with param!", "FooBar", p.interpretParameters("DINGSDA$FIRST:8", "FooBar"));
		Assert.assertEquals("3. placeholder was not correctly substituted with param!", "", p.interpretParameters("DINGSDA$FIRST:0", "FooBar"));
		Assert.assertEquals("4. placeholder was not correctly substituted with param!", "FooBar", p.interpretParameters("DINGSDA$FIRST:nodeal", "FooBar"));
	}

	@Test
	public void testInterpretParameters_placeholderHasLastParam_Success() {
		TemplateProcessor p = new TemplateProcessor();
		Assert.assertEquals("1. placeholder was not correctly substituted with param!", "Bar", p.interpretParameters("DINGSDA$LAST:3", "FooBar"));
		Assert.assertEquals("2. placeholder was not correctly substituted with param!", "FooBar", p.interpretParameters("DINGSDA$LAST:8", "FooBar"));
		Assert.assertEquals("3. placeholder was not correctly substituted with param!", "", p.interpretParameters("DINGSDA$LAST:0", "FooBar"));
		Assert.assertEquals("4. placeholder was not correctly substituted with param!", "FooBar", p.interpretParameters("DINGSDA$LAST:nodeal", "FooBar"));
	}
	
	@Test
	public void testInterpretParameters_placeholderHasRangeParam_Success() {
		TemplateProcessor p = new TemplateProcessor();
		Assert.assertEquals("1. placeholder was not correctly substituted with param!", "ooB", p.interpretParameters("DINGSDA$RANGE:2,4", "FooBar"));
		Assert.assertEquals("2. placeholder was not correctly substituted with param!", "ooBar", p.interpretParameters("DINGSDA$RANGE:2,8", "FooBar"));
		Assert.assertEquals("3. placeholder was not correctly substituted with param!", "", p.interpretParameters("DINGSDA$RANGE:2,0", "FooBar"));
		Assert.assertEquals("4. placeholder was not correctly substituted with param!", "ooBar", p.interpretParameters("DINGSDA$RANGE:2,nodeal", "FooBar"));
	}
	
	@Test
	public void testInterpretParameters_placeholderHasExRangeParam_Success() {
		TemplateProcessor p = new TemplateProcessor();
		Assert.assertEquals("1. placeholder was not correctly substituted with param!", "Far", p.interpretParameters("DINGSDA$EXRANGE:2,4", "FooBar"));
		Assert.assertEquals("2. placeholder was not correctly substituted with param!", "F", p.interpretParameters("DINGSDA$EXRANGE:2,8", "FooBar"));
		Assert.assertEquals("3. placeholder was not correctly substituted with param!", "FoBar", p.interpretParameters("DINGSDA$EXRANGE:2,2", "FooBar"));
		Assert.assertEquals("4. placeholder was not correctly substituted with param!", "", p.interpretParameters("DINGSDA$EXRANGE:0,6", "FooBar"));
		Assert.assertEquals("5. placeholder was not correctly substituted with param!", "F", p.interpretParameters("DINGSDA$EXRANGE:2,nodeal", "FooBar"));
	}
	
	@Test
	@Ignore("can't be testet at the moment because of missing services")
	public void tetGivenParameterForSubstitution_returnSubstitutedValue() {
		
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
