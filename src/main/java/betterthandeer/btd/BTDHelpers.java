package betterthandeer.btd;

import org.joml.Matrix2d;

public class BTDHelpers {

	public static Matrix2d rotateVec2(double radians) {
		return new Matrix2d(Math.cos(radians), -Math.sin(radians), Math.sin(radians), Math.cos(radians));
	}
}
