package main.java.com.dougron.mucus.mucus_output_manager.mucus_lom_injector;

public class InjectionTester
{

	public static void main(String[] args)
	{
		MuucusLOMInjector inj = new MuucusLOMInjector(7800);
		
//		inj.setTempo(120.0);
		
//		inj.stopPlayback();
		
//		inj.startPlayback();
		
		inj.createClip(1, 2, 64.0);
		inj.setClipName(1, 2, "poopy");
		
//		List<TimedNote> noteList = new ArrayList<TimedNote>();
//		noteList.add(new TimedNote(48, 96, 0.0, 1.0));
//		noteList.add(new TimedNote(52, 96, 0.0, 1.0));
//		noteList.add(new TimedNote(55, 96, 0.0, 1.0));
//		noteList.add(new TimedNote(48, 96, 1.0, 1.0));
//		noteList.add(new TimedNote(52, 96, 1.0, 1.0));
//		noteList.add(new TimedNote(55, 96, 1.0, 1.0));
//		noteList.add(new TimedNote(48, 96, 2.0, 1.0));
//		noteList.add(new TimedNote(52, 96, 2.0, 1.0));
//		noteList.add(new TimedNote(55, 96, 2.0, 1.0));
//		noteList.add(new TimedNote(48, 96, 3.0, 1.0));
//		noteList.add(new TimedNote(52, 96, 3.0, 1.0));
//		noteList.add(new TimedNote(55, 96, 3.0, 1.0));
//		inj.replaceNotes(1, 2, noteList);
//
//		inj.setClipStartPosition(1, 2, 1.5);
		
//		inj.setClipEndPosition(1, 2, 12.0);
		
//		inj.setClipLoopStartPosition(1, 2, 1.0);
		
//		inj.setClipLoopEndPosition(1, 2, 16.0);
		
//		inj.setClipSignatureNumerator(1, 2, 5);
		
//		inj.setClipSignatureDenominator(1, 2, 4);
		
//		inj.deleteClip(1, 2);
		
		// All the above work as expected
		
//		List<TimedNote> list = makeLargeNoteList();
//		inj.replaceNotes(1, 2, list, 10.0, 42, 50.0, 60);
		
	}

//	private static List<TimedNote> makeLargeNoteList()
//	{
//		List<TimedNote> list = new ArrayList<TimedNote>();
//		for (int i = 0; i < 50; i ++)
//		{
//			for (double j = 0.0; j < 50.0; j++)
//			{
//				list.add(new TimedNote(61 + i, 96, j + 12.0, 1.0));
//			}
//		}
//		return list;
//	}

}
