package improve;
import java.lang.Math;

import literature.GABC;

/**
 * The best so far.
 * @author Jez
 *
 */
public  class GABCL2L2Sdot1 extends GABCL2L2Sdot25{
	public GABCL2L2Sdot1(String fun, int d2, int mc, double lb, double ub) {
		super(fun, d2, mc, lb, ub);
		power = 0.1;
	}
}
