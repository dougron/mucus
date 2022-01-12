Mucus is the framework for my ongoing phd research project, the Music Composition User System, creating infectious creative musical interactions with AI partners. This project contains the building blocks to create the interactive applications of this project. A description of the top level packages follows:


mucus/algorithms

	- a bunch of packages of different parts or approaches to the algorithmic composing process, some old and some more recent.
			
			bot_personality - a rudimentary framework for determining if an AI agent will attempt to please or provoke.
			contour - basic class for expressing a contour.
			generic_generator - classes and interfaces to generate generic parameter sets for algorithmic processes.
			mu_chord_tone_and_embellishment - an analysis tool to help with a chord-tone-and-embellishment composing model.
			mu_development_factory - an experiment to create multiple Mu phrases with parameterized relationships to each other.
			mu_generator - generator classes for a chord-tone-and-embellishment composing model.
			mu_relationship - an interface and class to describe musical relationships.
			mu_zzaj_dynamics - classes to implement .h5 model files trained to generate dynamics for a generated melody.
			random_melody_generator: a rule based generator of melodies based on a rich set of (relatively) human understandable parameters, as well as processes for generating variations on an existing parameter set based on user feedback related to generation paramters. Also includes an Euclidean distance measure for parameters.
			superimposifier - a experiment which preceded the mu_generator package for music generation.
		
mucus/mu_framework: 

	- this package is centred around the Mu class which is an object that can represent a musical structure of any size. A Mu could be a single note (or controller event, though this is not yet implemented) or an entire song/piece/movement. Mus are organized in hierarchical trees. They have position relationships with parents or siblings in the tree expressed in several humanly understandable metrics. They have length relationships that can be fixed or dependant on their child Mus. They can be tagged with metadata relevant to the composition and interaction process. They can return contextual information about prevailing keys, chords and timesignatures.

mucus/mucus_output_manager

	- interface and classes for handling the conversion of Mus into various output formats (in particular transfer to the Ableton Live Session grid for playback of midi files, and music xml files for opening in a music notation editor) and various text and json formats for logging data to the file system.

mucus/mucus_utils

	- utilities. Actually just the place I put things which did not fit anywhere else. One for hacking the paramters of the virtual instruments in Live, and another for converting musical material saved as a CorpusItem into a Mu. Don’t use these much.

