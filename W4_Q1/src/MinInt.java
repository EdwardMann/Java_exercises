
public class MinInt {

    int[] arr = {24,52,74,9,34,23,64,34};


    public static void main(String[] args){
    	MinInt m = new MinInt();
    	System.out.println("Minimum is :" + m.findMin());
    }

    public int findMin(){
    	return findMinAux(0);
    }

    public int findMinAux(int index){
    	
    	// checking the array index
    	int size = arr.length; 
    	if (size-1 == index ){
    		return arr[index];
    	}
    	int minimum = findMinAux(index+1) ;
    	if (arr[index] < minimum) {
    		return arr[index];
    	}

    	return minimum;

    }

   
}