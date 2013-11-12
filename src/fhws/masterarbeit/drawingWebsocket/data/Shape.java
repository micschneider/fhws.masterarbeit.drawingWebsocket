package fhws.masterarbeit.drawingWebsocket.data;

public enum Shape 
{
	CIRCLE,
	TRIANGLE,
	SQUARE,
	PENTAGON;
	
	public String toString()
	{
		if (this == CIRCLE)
			return "Circle";
		else if (this == TRIANGLE)
			return "Triangle";
		else if (this == SQUARE)
			return "Square";
		else if (this == PENTAGON)
			return "Pentagon";
		else
			return "unknown";
	}
	
	public static Shape fromString(String s)
	{
		for (Shape shape : Shape.values())
		{
			if (shape.toString().equals(s))
				return shape;
		}
		throw new IllegalArgumentException("Cannot make shape from: " + s);
	}
}
