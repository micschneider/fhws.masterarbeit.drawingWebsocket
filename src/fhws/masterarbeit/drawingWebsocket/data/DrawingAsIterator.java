package fhws.masterarbeit.drawingWebsocket.data;

import java.util.Iterator;

public class DrawingAsIterator implements Iterator<String>
{
	private DrawingObject drawingObject;
	private int index = 0;
	
	public DrawingAsIterator(DrawingObject drawingObject) 
	{
		this.drawingObject = drawingObject;
	}

	@Override
	public boolean hasNext() 
	{
		return this.index < 11;
	}

	@Override
	public String next() 
	{
		index++;
		switch(index)
		{
			case 1:
				return DrawingObject.MESSAGE_NAME;
			case 2:
				return ",";
			case 3:
				return drawingObject.getShape().toString();
			case 4:
				return ",";
			case 5:
				return Integer.toString(drawingObject.getCenter().x);
			case 6:
				return ",";
			case 7:
				return Integer.toString(drawingObject.getCenter().y);
			case 8:
				return ",";
			case 9:
				return Integer.toString(drawingObject.getRadius());
			case 10:
				return ",";
			case 11:
				return Integer.toString(drawingObject.getColor().getRGB());
			default:
				throw new IllegalStateException("No more elements");
		}
	}

	@Override
	public void remove() 
	{
		throw new UnsupportedOperationException();
	}

}
