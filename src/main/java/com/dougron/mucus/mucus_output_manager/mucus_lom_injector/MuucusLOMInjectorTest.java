package main.java.com.dougron.mucus.mucus_output_manager.mucus_lom_injector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import UDPUtils.OSCMessMaker;
import timed_notes_and_controllers.Note;
import timed_notes_and_controllers.TimedNote;

class MuucusLOMInjectorTest
{

	private MuucusLOMInjector injector;

	
	@BeforeEach
	public void createInjector()
	{
		injector = new MuucusLOMInjector(7800);
	}
	
	
	@Test
	final void instantiates()
	{
		assertTrue(injector != null);
	}
	
	
	@Test
	void test_path_message_generator() throws Exception
	{
		OSCMessMaker mess = injector.getClipPathMessage(0, 0);
		assertEquals("path live_set tracks 0 clip_slots 0 clip ", mess.toShortString());
	}
	
	
	@Test
	void append_notes_message_test() throws Exception
	{
		List<TimedNote> list = new ArrayList<TimedNote>();
		list.add(new TimedNote(new Note(64, 96), 0.0, 1.0));
		OSCMessMaker mess = injector.getAddNotesMessage(2, 3, list, "append_notes");
		assertEquals("append_notes 64 0.0 1.0 96 1 2 3 ", mess.toShortString());
	}
	
	
	@Test
	void replace_range_of_notes_message_test() throws Exception
	{
		List<TimedNote> list = new ArrayList<TimedNote>();
		list.add(new TimedNote(new Note(64, 96), 0.0, 1.0));
		list.add(new TimedNote(new Note(65, 99), 2.0, 1.5));
		OSCMessMaker mess = injector.getReplaceNoteRangeMessage(1, 3, list, 0.5, 22, 3.5, 59);
		assertEquals("replace_notes_range 64 0.0 1.0 96 65 2.0 1.5 99 2 0.5 22 3.5 59 1 3 ", mess.toShortString());
	}
	
	
	@Test
	void create_clip_message() throws Exception
	{
		OSCMessMaker mess = injector.getCreateClipMessage(1, 2, 4.0);
		assertEquals("call create_clip 4.0 ", mess.toShortString());
	}

}
