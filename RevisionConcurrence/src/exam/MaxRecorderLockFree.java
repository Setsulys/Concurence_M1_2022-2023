package exam;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class MaxRecorderLockFree {
	 private final AtomicReferenceArray<Integer> maximums;

	    public MaxRecorderLockFree(int size) {
	        if (size < 1) {
	            throw new IllegalArgumentException("Invalid size");
	        }
	        this.maximums = new AtomicReferenceArray<>(size);
	        //Arrays.setAll(maximums, __ -> -1); // the elements processed are >=0, we can safely initialize with -1
	        for(var i= 0; i < size; i++) {
	        	this.maximums.getAndUpdate(i, x->-1);
	        }
	    }

	    /**
	     * Return the index of the first occurrence of the minimal element in a
	     * non-empty array
	     */
	    private static int findIndexOfMinimum(AtomicReferenceArray<Integer> array) {
	        if (array.length() == 0) {
	            throw new IllegalArgumentException();
	        }
//	        var min = t[0];
//	        var index = 0;
//	        for (var i = 1; i < t.length; i++) {
//	            if (t[i] < min) {
//	                min = t[i];
//	                index = i;
//	            }
//	        }
	        var min = array.get(0);
	        var index =0;
	        for(var i = 1;i < array.length();i++) {
	        	if(array.get(i)< min ) {
	        		min = array.get(i);
	        		index = i;
	        	}
	        }
	        return index;
	    }

	    public boolean process(int value) {
	        if (value < 0) {
	            throw new IllegalArgumentException("Argument must be >=0");
	        }
	        var indexMin = findIndexOfMinimum(maximums);
	        if (value > maximums.get(indexMin)) {
	        	maximums.getAndUpdate(indexMin,x-> value);
	        	System.out.println(maximums);
	            return true;
	        }
	        System.out.println(maximums);
	        return false;
	    }
	    
		public static void main(String[] args) {
			var recorder = new MaxRecorderLockFree(2);
			System.out.println(recorder.process(1)); // => true
			System.out.println(recorder.process(2)); // => true
			System.out.println(recorder.process(0)); // => false
			System.out.println(recorder.process(5)); // => true
			System.out.println(recorder.process(4)); // => true
			System.out.println(recorder.process(3)); // => false
		}
}
