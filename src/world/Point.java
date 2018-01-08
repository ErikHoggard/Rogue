package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

	public int x;
	public int y;
	public int z;
	
	public Point(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}





	public List<Point> neighbors8() {
		List<Point> points = new ArrayList<Point>();
		
		for (int ox = -1; ox < 2; ox++){
			for (int oy = -1; oy < 2; oy++){
				if (ox == 0 && oy == 0)
					continue;
				
				int nx = x+ox;
				int ny = y+oy;
				
				points.add(new Point (nx, ny, z));
			}
		}
		Collections.shuffle(points);
		return points;
	}
}
