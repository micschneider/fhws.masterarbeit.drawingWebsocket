package fhws.masterarbeit.drawingWebsocket.data;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class DrawingFromReader 
{
	private Reader reader;
	
	public DrawingFromReader(Reader r) throws IOException 
	{
		this.reader = r;
	}

	public DrawingObject getDrawingObject() 
	{
		Iterator<String> readerItr = new DrawingObjectReaderIterator(this.reader);
		String message_name = readerItr.next();
		Shape shape = Shape.fromString(readerItr.next());
		Point center = new Point(new Integer(readerItr.next()), new Integer(readerItr.next()));
		int radius =  new Integer(readerItr.next());
		Color color = new Color(new Integer(readerItr.next()));
		return new DrawingObject(shape, center, radius, color);
	}
	
	class DrawingObjectReaderIterator implements Iterator<String>
	{
		private Reader reader;
		private boolean hasNext;
		
		DrawingObjectReaderIterator(Reader reader)
		{
			this.reader = reader;
		}
		
		@Override
		public boolean hasNext()
		{
			return hasNext;
		}
		
		@Override
		public String next()
		{
			try
			{
				StringBuilder sb = new StringBuilder();
				int i = 0;
				while ((i=reader.read()) != -1)
				{
					if ((char) i == ',')
					{
						break;
					}
					else
					{
						sb.append((char) i);
					}
				}
				if (i == -1)
				{
					this.hasNext = false;
				}
				String s = sb.toString();
				return s;
			}
			catch (IOException ioe)
			{
				throw new RuntimeException("Error parsing from reader");
			}
		}
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

}
