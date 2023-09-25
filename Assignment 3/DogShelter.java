package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DogShelter implements Iterable<Dog> {
	public DogNode root;

	public DogShelter(Dog d) {
		this.root = new DogNode(d);
	}

	private DogShelter(DogNode dNode) {
		this.root = dNode;
	}


	// add a dog to the shelter
	public void shelter(Dog d) {
		if (root == null) 
			root = new DogNode(d);
		else
			root = root.shelter(d);
	}

	// removes the dog who has been at the shelter the longest
	public Dog adopt() {
		if (root == null)
			return null;

		Dog d = root.d;
		root =  root.adopt(d);
		return d;
	}
	
	// overload adopt to remove from the shelter a specific dog
	public void adopt(Dog d) {
		if (root != null)
			root = root.adopt(d);
	}


	// get the oldest dog in the shelter
	public Dog findOldest() {
		if (root == null)
			return null;
		
		return root.findOldest();
	}

	// get the youngest dog in the shelter
	public Dog findYoungest() {
		if (root == null)
			return null;
		
		return root.findYoungest();
	}
	
	// get dog with highest adoption priority with age within the range
	public Dog findDogToAdopt(int minAge, int maxAge) {
		return root.findDogToAdopt(minAge, maxAge);
	}

	// Returns the expected vet cost the shelter has to incur in the next numDays days
	public double budgetVetExpenses(int numDays) {
		if (root == null)
			return 0;
		
		return root.budgetVetExpenses(numDays);
	}
	
	// returns a list of list of Dogs. The dogs in the list at index 0 need to see the vet in the next week. 
	// The dogs in the list at index i need to see the vet in i weeks. 
	public ArrayList<ArrayList<Dog>> getVetSchedule() {
		if (root == null)
			return new ArrayList<ArrayList<Dog>>();
			
		return root.getVetSchedule();
	}


	public Iterator<Dog> iterator() {
		return new DogShelterIterator();
	}


	public class DogNode {
		public Dog d;
		public DogNode younger;
		public DogNode older;
		public DogNode parent;

		public DogNode(Dog d) {
			this.d = d;
			this.younger = null;
			this.older = null;
			this.parent = null;
		}

		public DogNode shelter (Dog d) {
			if(d.getAge() < this.d.getAge()){
				if(this.younger != null)
					this.younger.shelter(d);
				else if (this.younger == null) {
					this.younger = new DogNode(d);
					this.younger.parent = this;
					if(this.d.getDaysAtTheShelter()<d.getDaysAtTheShelter())
						RotateRight(this.younger);
				}
			}
			else if(d.getAge() > this.d.getAge()) {
				if(this.older != null)
					this.older.shelter(d);
				else if (this.older == null) {
					this.older = new DogNode(d);
					this.older.parent = this;
					if(this.d.getDaysAtTheShelter()<d.getDaysAtTheShelter())
						RotateLeft(this.older);
				}
			}

			if(this.parent == null)
            	return this;
			else
				return this.parent;
		}

		public DogNode adopt(Dog d) {
			DogNode temp = this;
			boolean rootChange = false;
			while (!d.equals(temp.d)){
				if (temp.younger != null && d.getAge() < temp.d.getAge()) temp=temp.younger;
				else if (temp.older != null && d.getAge() > temp.d.getAge()) temp=temp.older;
				else return null;
			}

			DogNode yngerD = temp.younger;
			DogNode olderD = temp.older;
			DogNode oldD = null;
			//starting the switches
			if(yngerD != null && olderD !=null){
				if(yngerD.older != null) {
					oldD = yngerD.older;
					while (oldD.older != null) {
						oldD = oldD.older;
					}

					oldD.older = temp.older;
					if(oldD.younger == null)
						oldD.parent.older = null;
					else
						oldD.parent.older = oldD.younger;
					oldD.younger = temp.younger;

					//rotate here?
					//young child disappearance - should be fixed
				}
				else{
					oldD = yngerD;
					temp.younger = null;
				}
			}
			else if (olderD != null){
				oldD = olderD;
				temp.older = null;
			}
			else if (yngerD != null){
				oldD = yngerD;
				temp.younger = null;
			}

			if(oldD != null){
				oldD.parent = temp.parent;
				if(temp.parent != null) {
					if (temp.parent.older == temp)
						temp.parent.older = oldD;
					else if (temp.parent.younger == temp)
						temp.parent.younger = oldD;
				}
			}
			else if(temp.parent != null){
				if(temp.parent.older == temp)
					temp.parent.older = null;
				else if (temp.parent.younger == temp)
					temp.parent.younger = null;
			}

			temp.older = null;
			temp.younger = null;

			if(oldD != null) {
				temp.parent = null;
			}
			else{
				oldD = temp.parent;
				temp.parent = null;
			}
			temp = oldD;
			if(temp.younger != null)
				temp.younger.parent = temp;
			if(temp.older != null)
				temp.older.parent = temp;

			if(temp.younger != null && temp.d.getDaysAtTheShelter()<temp.younger.d.getDaysAtTheShelter()) {
				//System.out.println(temp);
				RotateRight(temp.younger);
				//root = temp.younger;
				//System.out.println(temp);
				//CheckForDownHeap(temp);
				if(temp.older.d.getDaysAtTheShelter()>temp.d.getDaysAtTheShelter())
					RotateLeft(temp.older);
			}


			while(temp.parent != null){
				temp = temp.parent;
			}
            return temp; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
		}

		public Dog findOldest() {
            if(this.older == null)
				return this.d;
            else
            	return this.older.findOldest();
		}

		public Dog findYoungest() {
			if(this.younger == null)
				return this.d;
			else
				return this.younger.findOldest();
		}

		public Dog findDogToAdopt(int minAge, int maxAge) {
			DogNode dogchamp = this;
            while(dogchamp.d.getAge()<minAge || dogchamp.d.getAge()>maxAge){
            	if(dogchamp.d.getAge()<minAge) {
					if (dogchamp.older != null) {
						dogchamp = dogchamp.older;
					}
					else
						return null;
				}
            	else if(dogchamp.d.getAge()>maxAge){
					if(dogchamp.younger !=null){
						dogchamp = dogchamp.younger;
					}
					else
						return null;
				}
			}
            return dogchamp.d;
		}
		
		public double budgetVetExpenses(int numDays) {
            return budgetVetExpenses(numDays, this); // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
		}

		private double budgetVetExpenses(int numDays, DogNode dn){
			double expense = 0;
			if(dn.younger != null) {
				expense += budgetVetExpenses(numDays, dn.younger);
			}
			if(dn.d.getDaysToNextVetAppointment()<numDays)
				expense = expense + dn.d.getExpectedVetCost();

			if(dn.older != null) {
				expense += budgetVetExpenses(numDays, dn.older);
			}

			return expense;
		}


		public ArrayList<ArrayList<Dog>> getVetSchedule() {
			int noOfWeeks = Dogterator(this)/7;
			ArrayList<ArrayList<Dog>> MasterList = new ArrayList<ArrayList<Dog>>();
			for(int i = 0; i<= noOfWeeks; i++){
				ArrayList<Dog> temp = new ArrayList<Dog>();
				Dogterator(this, temp, i*7, (i+1)*7);
				MasterList.add(temp);
			}
            return MasterList;
		}

		private int Dogterator(DogNode dn){
			int longest = 0;
			int temp = 0;
			if(dn.younger != null) {
				temp = Dogterator(dn.younger);
				if(temp>longest)
					longest = temp;
			}

			if(dn.d.getDaysToNextVetAppointment()>longest)
				longest = dn.d.getDaysToNextVetAppointment();

			if(dn.older != null) {
				temp = Dogterator(dn.older);
				if(temp>longest)
					longest = temp;
			}
			return longest;
		}

		private ArrayList<Dog> Dogterator(DogNode dn, ArrayList<Dog> dal, int min, int max){
			if(dn.younger != null) {
				Dogterator(dn.younger, dal, min, max);
			}

			if(dn.d.getDaysToNextVetAppointment()>=min && dn.d.getDaysToNextVetAppointment()<max)
				dal.add(dn.d);

			if(dn.older != null) {
				Dogterator(dn.older, dal, min, max);
			}
			return dal;
		}

		public String toString() {
			String result = this.d.toString() + "\n";
			if (this.younger != null) {
				result += "younger than " + this.d.toString() + " :\n";
				result += this.younger.toString();
			}
			if (this.older != null) {
				result += "older than " + this.d.toString() + " :\n";
				result += this.older.toString();
			}
			/*if (this.parent != null) {
				result += "parent of " + this.d.toString() + " :\n";
				result += this.parent.d.toString() +"\n";
			}*/
			return result;
		}

		private void RotateRight(DogNode dn){
			DogNode pp = dn.parent.parent;
			DogNode p = dn.parent;
			DogNode y = dn.older;

			dn.older = p;
			p.younger = null;
			p.parent = dn;
			dn.parent = null;

			if(y!=null){
				if(y.d.getAge()>dn.older.d.getAge()){
					dn.older.older = y;
					y.parent = dn.older;
				}
				else if(y.d.getAge()<dn.older.d.getAge()){
					dn.older.younger = y;
					y.parent = dn.older;
				}
			}

			if(pp != null){
				dn.parent = pp;
				if(pp.younger == p){
					pp.younger = dn;
					if(dn.d.getDaysAtTheShelter()>dn.parent.d.getDaysAtTheShelter())
						RotateRight(dn);
				}
				else if(pp.older == p){
					pp.older = dn;
					if(dn.d.getDaysAtTheShelter()>dn.parent.d.getDaysAtTheShelter())
						RotateLeft(dn);
				}
			}


//			if (p.older!=null && p.older.d.getDaysAtTheShelter()>p.d.getDaysAtTheShelter()){
//				RotateLeft(p.older);
//			}
		}
		private void RotateLeft(DogNode dn){
			DogNode pp = dn.parent.parent;
			DogNode p = dn.parent;
			DogNode y = dn.younger;

			dn.younger = p;
			p.older = null;
			p.parent = dn;
			dn.parent = null;

			if(y!=null){
				if(y.d.getAge()>dn.younger.d.getAge()){
					dn.younger.older = y;
					y.parent = dn.younger;
				}
				else if(y.d.getAge()<dn.younger.d.getAge()){
					dn.younger.younger = y;
					y.parent = dn.younger;
				}
			}

			if(pp != null){
				dn.parent = pp;
				if(pp.younger == p){
					pp.younger = dn;
					if(dn.d.getDaysAtTheShelter()>dn.parent.d.getDaysAtTheShelter())
						RotateRight(dn);
				}
				else if(pp.older == p){
					pp.older = dn;
					if(dn.d.getDaysAtTheShelter()>dn.parent.d.getDaysAtTheShelter())
						RotateLeft(dn);
				}
			}


		}

		private void DownHeap(DogNode dn, DogNode dh, boolean younger){
			boolean hasBoth = false;
			DogNode y = dn.younger;
			DogNode o = dn.older;


			if(dn.parent != null){
				dh.parent = dn.parent;
				if(dn.parent.younger == dn)
					dn.parent.younger = dh;
				if(dn.parent.older == dn)
					dn.parent.older = dh;
			}
			else
				dh.parent = null;
			dn.parent = dh;
			dn.younger = dh.younger;
			dn.older = dh.older;

			if(younger){
				dh.older = dn;
				dh.younger = y;
				y.parent = dh;
			}
			else{
				dh.younger = dn;
				dh.older = o;
				o.parent = dh;
			}
			//CHILD LOSS PROBLEM HERE
			CheckForDownHeap(dn);
		}
		private void CheckForDownHeap(DogNode dn){
			DogNode y = dn.younger;
			DogNode o = dn.older;
			DogNode priority = null;
			boolean younger = false;
			if(y!=null && o != null) {
				if (y.d.getDaysAtTheShelter() > o.d.getDaysAtTheShelter())
					priority = y;
				else
					priority = o;
			}
			else if(y != null)
				priority = y;
			else if(o != null)
				priority = o;

			if(priority == y)
				younger = true;

			if(priority != null && dn.d.getDaysAtTheShelter() < priority.d.getDaysAtTheShelter())
				DownHeap(dn,priority,younger);
		}
	}


	private class DogShelterIterator implements Iterator<Dog> {
		ArrayList<Dog> DSI = new ArrayList<Dog>();
		int i = 0;

		private DogShelterIterator() {
			Dogterator(root, DSI);
		}

		private ArrayList<Dog> Dogterator(DogNode dn, ArrayList<Dog> dal){
			if(dn.younger != null) {
				Dogterator(dn.younger, dal);
			}

			dal.add(dn.d);

			if(dn.older != null) {
				Dogterator(dn.older, dal);
			}
			return dal;
		}

		public Dog next(){
			if(!hasNext()) {
				throw new NoSuchElementException("Out of dogs");
			}
			i++;
			return DSI.get(i-1);

		}

		public boolean hasNext() {
            if(DSI.size() > i)
            	return true;
            else
            	return false;
		}

	}

}