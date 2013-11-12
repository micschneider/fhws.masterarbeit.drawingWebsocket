package fhws.masterarbeit.drawingWebsocket.client;

import java.io.IOException;
import java.net.*;

import javax.websocket.*;

import fhws.masterarbeit.drawingWebsocket.data.DrawingDecoder;
import fhws.masterarbeit.drawingWebsocket.data.DrawingEncoder;
import fhws.masterarbeit.drawingWebsocket.data.DrawingObject;

@ClientEndpoint (decoders = {DrawingDecoder.class}, 
				 encoders = {DrawingEncoder.class})
public class DrawingClient 
{
	private Session session;
	private DrawingWindow window;
	
	public DrawingClient(DrawingWindow window)
	{
		this.window = window;
	}
	
	@OnOpen 
	public void init(Session session)
	{
		this.session = session;
	}
	
	@OnMessage
	public void drawingChanged(DrawingObject drawingObject)
	{
		window.addDrawingObject(drawingObject);
	}
	
	@OnError
	public void handleError(Throwable t)
	{
		if(t instanceof DecodeException)
			System.out.println("Error decoding incoming message: " + ((DecodeException)t).getText());
		else
			System.out.println("Client Websocket error: " + t.getMessage());
	}
	
	public static DrawingClient connect(DrawingWindow window, String path) 
	{
		WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
		try
		{
			DrawingClient client = new DrawingClient(window);
			wsc.connectToServer(client,  new URI(path));
			return client;
		}
		catch (IOException e)
		{
			System.out.println("Error Connecting: " + e.getMessage());
		}
		catch (DeploymentException e)
		{
			System.out.println("Error deploying: " + e.getMessage());
		}
		catch (URISyntaxException e)
		{
			System.out.println("Bad path: " + path);
		}
		return null;
	}
	
	public void disconnect() 
	{
		if (this.session != null)
		{
			try
			{
				this.session.close();
			}
			catch(IOException e)
			{
				System.out.println("Error closing the session: " + e);
			}
		}
	}
	
	public void notifyServerDrawingChanged(DrawingObject drawingObject) 
	{
		try
		{
			this.session.getBasicRemote().sendObject(drawingObject);
		}
		catch(IOException e)
		{
			System.out.println("Error: IO " + e.getMessage());
		}
		catch(EncodeException e)
		{
			System.out.println("Error encoding object: " + e.getObject());
		}
	}
}
