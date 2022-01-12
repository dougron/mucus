package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.contour.ContourFunction;

class ContourFunction_Test
{
	
	ContourFunction cf;
	
	
	@BeforeEach
	void makeCountour()
	{
		cf = new ContourFunction();
	}

	
	@Test
	void contourfunction_with_no_breakpoints_returns_0_over_entire_range()
	{
		assertEquals(0.0, cf.getRawFunctionOutput(0.0));
		assertEquals(0.0, cf.getRawFunctionOutput(0.5));
		assertEquals(0.0, cf.getRawFunctionOutput(1.0));
	}
	
	
	@Test
	void given_breakpoint_at_0_1_of_value_1_then_returns_correct_values() throws Exception
	{
		cf.addBreakpoint(0.5, 1.0);
		assertEquals(0.0, cf.getRawFunctionOutput(0.0));
		assertEquals(0.5, cf.getRawFunctionOutput(0.25));
		assertEquals(1.0, cf.getRawFunctionOutput(0.5));
		assertEquals(0.5, cf.getRawFunctionOutput(0.75));
		assertEquals(0.0, cf.getRawFunctionOutput(1.0));
	}
	
	
	@Test
	void contourfunction_with_no_breakpoints_returns_result_from_staright_line_between_start_and_end_note()
	{
		int startPitch = 60;
		int endPitch = 58;
		double startInFloatBars = 0.0;
		double endInFloatBars = 2.0;
		
		assertEquals(60, cf.getPitch(0.0, 1.0, startPitch, endPitch, startInFloatBars, endInFloatBars));
		assertEquals(59, cf.getPitch(1.0, 1.0, startPitch, endPitch, startInFloatBars, endInFloatBars));
		assertEquals(58, cf.getPitch(2.0, 1.0, startPitch, endPitch, startInFloatBars, endInFloatBars));
	}
	
	@Test
	void contourfunction_with_breakpoint_returns_result_from_staright_line_between_start_and_end_note_plus_offset_value()
	{
		int startPitch = 60;
		int endPitch = 58;
		double startInFloatBars = 0.0;
		double endInFloatBars = 2.0;
		double noteRangeMultiplier = 7.0;
		
		cf.addBreakpoint(0.5, 1.0);
		
		assertEquals(60, cf.getPitch(0.0, noteRangeMultiplier, startPitch, endPitch, startInFloatBars, endInFloatBars));
		assertEquals(66, cf.getPitch(1.0, noteRangeMultiplier, startPitch, endPitch, startInFloatBars, endInFloatBars));
		assertEquals(58, cf.getPitch(2.0, noteRangeMultiplier, startPitch, endPitch, startInFloatBars, endInFloatBars));
	}

	
	
}
