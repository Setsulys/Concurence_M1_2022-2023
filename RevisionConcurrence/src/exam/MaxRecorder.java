package exam;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class MaxRecorder {

	private volatile int size;
	private static final VarHandle SIZE_HANDLE;
	static {
		var lookup = MethodHandles.lookup();
		try {
			SIZE_HANDLE = lookup.findVarHandle(MaxRecorder.class, "size", int.class );
		}catch(NoSuchFieldException | IllegalAccessException e) {
			throw new AssertionError();
		}
	}
	public boolean process(int value) {
		if(value < 0) {
			throw new IllegalArgumentException();
		}
		int current = this.size;
		if(current < value) {
			if(SIZE_HANDLE.compareAndSet(this,current,value)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		var recorder = new MaxRecorder();
		System.out.println(recorder.process(1)); // => true
		System.out.println(recorder.process(2)); // => true
		System.out.println(recorder.process(0)); // => false
		System.out.println(recorder.process(5)); // => true
		System.out.println(recorder.process(4)); // => true
		System.out.println(recorder.process(3)); // => false
	}
}
