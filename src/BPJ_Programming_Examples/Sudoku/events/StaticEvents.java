package BPJ_Programming_Examples.Sudoku.events;

import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.EventsOfClass;

public class StaticEvents {
	static public Event finished = new Event("Finished"); 
	static public Event pruning = new Event("Pruning"); 
	static public EventSetInterface allMoves = new EventsOfClass(Move.class);




	//very hard. didn't solved
		static public int[][] initialBox=
		 {
		 {0, 0, 0,	8, 0, 7,	0, 0, 0},
		 {7, 0, 8,	0, 3, 0,	6, 0, 4},
		 {0, 2, 0,	0, 0, 0,	0, 9, 0},
		
		 {9, 0, 0,	7, 0, 4,	0, 0, 6},
		 {0, 0, 4,	0, 0, 0,	2, 0, 0},
		 {8, 0, 0,	2, 0, 6,	0, 0, 5},
	
		 {0, 7, 0,	0, 0, 0,	0, 4, 0},
		 {6, 0, 5,	0, 4, 0,	7, 0, 3},
		 {0, 0, 0,	3, 0, 5,	0, 0, 0}
		 };

	//evil- didn't solved.
//	static public int[][] initialBox=
//		{
//		{8, 0, 0,	2, 0, 0,	0, 0, 0},
//		{0, 0, 0,	0, 7, 0,	6, 0, 0},
//		{0, 4, 0,	8, 0, 0,	7, 5, 0},
//		
//		{0, 0, 0,	7, 0, 0,	0, 0, 5},
//		{3, 0, 8,	1, 0, 2,	9, 0, 6},
//		{2, 0, 0,	0, 0, 9,	0, 0, 0},
//
//		{0, 6, 4,	0, 0, 7,	0, 1, 0},
//		{0, 0, 2,	0, 3, 0,	0, 0, 0},
//		{0, 0, 0,	0, 0, 1,	0, 0, 4}
//		};

//very hard -didn't solved
//		static public int[][] initialBox=
//			{
//			{3, 0, 0,	0, 0, 0,	0, 0, 9},
//			{0, 2, 0,	3, 0, 7,	0, 6, 0},
//			{0, 0, 5,	0, 0, 0,	1, 0, 0},
//			
//			{0, 3, 0,	2, 0, 6,	0, 5, 0},
//			{0, 0, 0,	0, 9, 0,	0, 0, 0},
//			{0, 9, 0,	4, 0, 8,	0, 7, 0},
//	
//			{0, 0, 8,	0, 0, 0,	4, 0, 0},
//			{0, 7, 0,	8, 0, 2,	0, 9, 0},
//			{6, 0, 0,	0, 0, 0,	0, 0, 5}
//			};
	
	
	
	
	//medium - solved
//	static public int[][] initialBox=
//		{
//		{6, 5, 0,	0, 3, 4,	0, 9, 0},
//		{0, 0, 0,	7, 0, 9,	0, 0, 0},
//		{0, 7, 0,	2, 0, 0,	0, 0, 0},
//
//		{0, 1, 2,	0, 0, 3,	0, 0, 8},
//		{3, 0, 4,	0, 0, 0,	6, 0, 2},
//		{8, 0, 0,	9, 0, 0,	3, 1, 0},
//
//		{0, 0, 0,	0, 0, 7,	0, 3, 0},
//		{0, 0, 0,	3, 0, 1,	0, 0, 0},
//		{0, 2, 0,	5, 9, 0,	0, 4, 1}
//		};


	//hard .solved
//			static public int[][] initialBox=
//				{
//				{1, 0, 0,	3, 0, 0,	0, 4, 0},
//				{0, 0, 6,	0, 0, 8,	0, 9, 0},
//				{0, 0, 0,	0, 0, 4,	7, 0, 8},
//				
//				{0, 0, 5,	0, 0, 0,	4, 7, 0},
//				{7, 1, 0,	0, 0, 0,	0, 5, 9},
//				{0, 6, 3,	0, 0, 0,	1, 0, 0},
//		
//				{6, 0, 8,	1, 0, 0,	0, 0, 0},
//				{0, 4, 0,	8, 0, 0,	9, 0, 0},
//				{0, 7, 0,	0, 0, 6,	0, 0, 1}
//				};






	// medium -solved.
	//			static public int[][] initialBox=
	//			{
	//			{0, 0, 0,	0, 0, 1,	0, 9, 5},
	//			{0, 0, 0,	0, 6, 3,	2, 1, 0},
	//			{0, 0, 0,	5, 0, 9,	6, 0, 0},
	//			
	//			{0, 4, 0,	0, 7, 0,	5, 8, 0},
	//			{0, 0, 0,	1, 4, 0,	0, 0, 0},
	//			{9, 7, 2,	3, 5, 0,	4, 0, 0},
	//	
	//			{2, 1, 4,	8, 9, 0,	3, 0, 0},
	//			{0, 0, 0,	0, 0, 0,	0, 7, 0},
	//			{8, 9, 0,	6, 3, 5,	0, 0, 0}
	//			};


	//very hard -solved. 31 numbers entered
	//won't work without both PlaceANumberInAColumn && PlaceANumberInARow
//			static public int[][] initialBox=
//				{
//				{4, 0, 0,	0, 0, 0,	0, 6, 0},
//				{0, 6, 0,	0, 0, 4,	0, 0, 9},
//				{0, 0, 0,	0, 0, 2,	4, 3, 5},
//				
//				{0, 0, 0,	4, 6, 7,	0, 0, 3},
//				{5, 1, 3,	0, 0, 9,	7, 0, 0},
//				{0, 0, 0,	1, 0, 0,	0, 2, 0},
//		
//				{0, 0, 0,	9, 1, 3,	8, 0, 0},
//				{8, 7, 0,	5, 0, 0,	3, 9, 0},
//				{3, 0, 0,	0, 0, 8,	0, 0, 0}
//				};




	//very hard -solved. 31 numbers entered
	//	static public int[][] initialBox=
	//		{
	//		{2, 0, 0,	0, 0, 0,	0, 0, 7},
	//		{6, 0, 0,	2, 8, 0,	0, 0, 5},
	//		{0, 0, 0,	0, 1, 0,	0, 0, 3},
	//
	//		{0, 4, 1,	0, 6, 0,	0, 2, 9},
	//		{0, 2, 0,	5, 4, 0,	0, 0, 0},
	//		{7, 6, 0,	3, 0, 0,	5, 0, 0},
	//
	//		{0, 0, 0,	0, 0, 0,	9, 0, 4},
	//		{9, 0, 4,	0, 0, 0,	0, 7, 0},
	//		{0, 7, 2,	9, 3, 0,	1, 5, 0}
	//		};

	//very hard-  solved. 26 numbers entered . http://www.solvemysudoku.com/ haven't solved 
	//won't work without PlaceANumberInABox OR EliminateEight
//		static public int[][] initialBox=
//			{
//			{0, 0, 0,	1, 0, 0,	4, 0, 5},
//			{6, 0, 0,	0, 4, 0,	9, 7, 0},
//			{0, 8, 0,	0, 0, 0,	0, 0, 0},
//	
//			{0, 0, 8,	6, 0, 0,	0, 0, 0},
//			{0, 0, 6,	9, 0, 2,	5, 0, 0},
//			{0, 0, 0,	0, 0, 3,	1, 0, 0},
//	
//			{0, 0, 0,	0, 0, 0,	0, 3, 0},
//			{0, 9, 2,	0, 5, 0,	0, 0, 7},
//			{7, 0, 4,	0, 0, 9,	0, 0, 0}
//			};



	//medium - solved. 30 numbers entered
	//	static public int[][] initialBox=
	//		{
	//		{0, 8, 0,   3, 0, 4,   5, 0, 0},
	//		{0, 1, 6,   0, 0, 0,   0, 9, 2},
	//		{7, 0, 0,   0, 0, 0,   0, 1, 0},
	//
	//		{2, 0, 0,   0, 6, 0,   0, 0, 4},
	//		{0, 0, 0,   5, 0, 7,   0, 0, 0},
	//		{8, 6, 0,   0, 9, 0,   0, 0, 7},
	//
	//		{0, 7, 0,   0, 0, 0,   0, 0, 3},
	//		{4, 2, 0,   7, 0, 0,   9, 8, 0},
	//		{0, 0, 9,   2, 0, 8,   0, 6, 0}
	//	};

	//template
//		static public int[][] initialBox=
//			{
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//	
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			{0, 0, 0,	0, 0, 0,	0, 0, 0},
//			{0, 0, 0,	0, 0, 0,	0, 0, 0}
//			};


}