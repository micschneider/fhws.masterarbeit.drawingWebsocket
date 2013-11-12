package fhws.masterarbeit.drawingWebsocket.server;

import java.awt.Color;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import javax.websocket.DecodeException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.drawingWebsocket.data.DrawingAsIterator;
import fhws.masterarbeit.drawingWebsocket.data.DrawingFromReader;
import fhws.masterarbeit.drawingWebsocket.data.DrawingObject;

@ServerEndpoint("/drawingWebsocket")
public class DrawingServer 
{
	private Session session;
	
	@OnOpen
	public void initSession(Session session)
	{
		this.session = session;
	}
	
	@OnMessage
	public void shapeCreated(Reader reader)
	{
		DrawingObject drawingObject;
		try(Reader rdr = reader)
		{
			DrawingFromReader dp = new DrawingFromReader(rdr);
			drawingObject = dp.getDrawingObject();
		}
		catch(IOException e)
		{
			System.out.println("There was an error reading the incoming message.");
			return;
		}
		DrawingObject toSend = new DrawingObject(drawingObject.getShape(), drawingObject.getCenter(), drawingObject.getRadius(), this.getFadedColor(drawingObject.getColor()));
		for(Session otherSession : this.session.getOpenSessions())
		{
			if(!otherSession.equals(this.session))
			{
				try
				{
					DrawingAsIterator dai = new DrawingAsIterator(toSend);
					sendDrawing(otherSession, dai);
				}
				catch(IOException e)
				{
					System.out.println("Communication error: " + e.getMessage());
				}
			}
		}			
	}

	private void sendDrawing(Session aSession, Iterator<String>drawingAsIterator) throws IOException
	{
		RemoteEndpoint.Basic remote = aSession.getBasicRemote();
		while(drawingAsIterator.hasNext())
		{
			String partialMessage = drawingAsIterator.next();
			boolean isLast = !drawingAsIterator.hasNext();
			remote.sendText(partialMessage, isLast);
		}
	}

	private Color getFadedColor(Color c) 
	{
		Color faded = new Color((int)255-((255-c.getRed())/2),
								(int)255-((255-c.getGreen())/2),
								(int)255-((255-c.getBlue())/2));
		return faded;
	}
	
	@OnError
	public void handleError(Throwable t)
	{
		if(t instanceof DecodeException)
			System.out.println("Error decoding incoming message: " + ((DecodeException)t).getText());
		else
			System.out.println("Server Websocket error: " + t.getMessage());
	}
}
