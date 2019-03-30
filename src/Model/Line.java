package Model;

import java.awt.Point;

/**
 * <h1>Line</h1> 
 * This class implement a line which connect with two countries.
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */
public class Line {

	private Point start;
	private Point end;

	/**
	 * This is a constructor and initializes start point and end point.
	 *
	 * @param start A start point of the line.
	 * @param end  A end point of the line.
	 * 
	 */
	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * This method obtains the start point of line.
	 *
	 * @return A start point
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * This method set the start point of line.
	 *
	 * @param start A start point of the line.
	 */
	public void setStart(Point start) {
		this.start = start;
	}

	/**
	 * This method obtains the end point of lone.
	 *
	 * @return The end point of line.
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * This method set the end point of line.
	 *
	 * @param end The end point of line.
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

}
