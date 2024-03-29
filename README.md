# LeagueOOP
2D League of Legends in Java


Student: Murat Eren

Group: 324 CA

			Homework Assignment - Stage 1
	
	Project Objective:
	
	The project aims to create a 2D MOBA-esque game using object-oriented programming in Java.
	There are four types of champions in the game, each type having two unique abilites.
	The map is made of four types of terrain with special bonuses for every class. Moreover,
	a multiplier is applied depending on the opponent. The goal of this project was to highlight
	the use of the Java design patterns: singleton, factory, visitor.  
	
	Double Dispatch:
	
	- designed an abstract class Champions that is extended by four classes, one for a specific
	champion type;
	- implemented the Double Dispatch mechanism to take care of the fight between champions;
	- every champion class has 4 "attack" methods that calculate the damage fromboth abilities and,
	if necessary set damage over time (DOT) effects;
	- every champion has one method "isAttackedBy" that takes one parameter Champion and calls for
	the right attack method;
	- the "isAttackedBy" method is used in main to damage the enemy;
	- all champions are stored in a List of Champion, so when isAttackedBy is called, it will call
	the right attack function, based on the specific subclass.

	Map:
	
	- map is matrix of Tiles, that for now contains just the terrain type;
	- used Singleton pattern to create the map.
	
	Champion:
	
	- used Factory pattern to create champions;
	- all fields are private ("make everything as private as possible"), use getters and setters
	in subclasses;
	- one abstract class with all the common functionalities of all the champion types;
	- extended with Knight, Pyromancer, Rogue and Wizard that contain specific methods to calculate
	what damage to apply according to the opponent type;
	- stored DOT effects in an ArrayList that is updated every time one DOT effect is applied
	or overwritten.
