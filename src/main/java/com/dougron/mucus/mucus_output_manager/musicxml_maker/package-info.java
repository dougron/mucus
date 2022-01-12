/**
 * musicxml file maker, for the Mucus project.
 * 
 * Intended to cover the following omissions from the old XMLMaker:
 * <ol>
 * <li>complex time signatures, tuplets.
 * <li>annotations for both Sibelius and Musescore, and extensible to the Zong library if necessary.
 * <li>actually uses the DOM xml maker library, don't know why I did not spot that first time around.
 * <li>any other shortcut that I made for XMLMaker based on the time limitations of the related project (that being RepetitionAnalysis)
 * </ol>
 * <p>
 * This is not a general music xml encoder. It is explicitly designed to take a rendered Mu hierarchy,
 * with some of the rendering guidelines emerging from the generation process rather than a post hoc
 * analysis of a low level symbolic representation of the output. The thinking is that written music
 * represents a way of thinking about the music, rather than solely being a tool for playback. By capturing 
 * the thought behind the music generation we therefore have the opportunity to display the intention behind
 * the music as opposed to a generic reproduction of the note output.
 * <p>
 * How it works:
 * <ul>
 * <li>MXML_Score is the holdall for the process. Generates the .musicxml file. 
 * 
 * <li>MXML_Part is added to the MXML_Score. This equates to a line in the score, although, in later versions may result in more than 
 * one line if appropriate to the instrument (piano grand staff) or if there is a voice overflow (more than 4 voices in a part)
 * 
 * <li>Mus containing note information are added to a Part. Mus are wrapped in a VoiceMu.
 * 
 * <li>VoiceMus take over the position and length information of a Mu, and can be split into more that one subsection of the total duration 
 * for printout purposes. So you can get the note information from the Mu, but the VoiceMu will dictate where the note is.
 * 
 * <li>MXML_Part.makeMeasures is the main method call for converting the loaded Mus and VoiceMus into a .musicxml file. As should be
 * apparent from the naming of method calls, this makes a list of MXML_Measure objects, sets time and key signatures, sorts VoiceMus (still 
 * held in the Part) into voices, adds these VoiceMus into the MXML_Measure in which they start where they are split into printable parts. Therafter
 * VoiceMuRests are added into the gaps between VoiceMus and themselves split into printable parts. Based on the length of the 
 * resultant VoiceMus and VoiceMuRests, a division value is calculated for the part and assigned as an attribute to the first measure. Lastly,
 * MXML_Measure.pack() is called on each measure which converts VoiceMus and VoiceMuRests into classes implementing the MXML_MeasureItemInterface
 * 
 * <li>VoiceMus in each part are assigned a voice 
 * and added to a TreeMap<Integer, ArrayList<VoiceMu>> according to the rule that if notes overlap and do not start and end in the
 * same place, they are assigned to voices from highest to lowest. Even if a lower note starts before a higher note, it will still
 * be assigned to a higher voice.
 * 
 * <li>Then VoiceMus are assigned to the MXML_Measure in which they start.
 * 
 * <li>VoiceMus are split according to the rules of the prevailing time signature for the bar. Anything overlapping the end of the bar is 
 * passed to the next bar, and undergoes a similar process until all notes are viably renderable in a .musicxml viewer.
 * 
 * <li>thereafter, VoiceMuRest are added to fill in the gaps. A completely empty measure will recieve only rests in voice 1. If a measure 
 * only contains a voice other than 1, rests in unrepresented parts will not be added. Rests are added to every voice where there are notes 
 * in a measure. VoiceMuRests are split according to their own rules into subsections that are renderable in .musicxml. Maybe its just me, 
 * but I would never write a dotted rest, even in a place which would take a dotted note where it a note, hence their own set of rules. 
 * 
 * <li>One the notes are finalized, everything is converted to a class implementing the MXML_MeasureItemInteraface and loaded into the
 * mXML_MeasureItemInterfaces list in the MXML_measure. These classes all implement a getElement(Document document) method which returns 
 * the Element to be added to the root xml file held by the MXML_Score. 
 * </ol>
 * 
 * Splitting of notes in MXML_Measure consists of 5 methods.These would be duplicated for VoiceMuRests
 * <ol>
 * <li>SplitVoiceMu(VoiceMu vm)
 * <li>isPrintableVoiceMu(VoiceMu vm)
 * <li>splitVoiceMuList(ArrayList<VoiceMu> list, ArrayList<Double> bpoints)
 * <li>getSecondBitOfSplitVoiceMu(VoiceMu vm, Double d)
 * <li>criteriaForSplittingNote(VoiceMu vm, Double d)
 * 
 * </ol>
 */
/**
 * @author dougr
 *
 */
package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;