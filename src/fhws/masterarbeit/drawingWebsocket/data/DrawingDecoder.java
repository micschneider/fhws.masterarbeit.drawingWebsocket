package fhws.masterarbeit.drawingWebsocket.data;

import java.awt.Color;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class DrawingDecoder implements Decoder.Text<DrawingObject>
{

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig arg0) {}

	@Override
	public DrawingObject decode(String s) throws DecodeException 
	{
		StringTokenizer st = new StringTokenizer(s, ",");
		String message_name = st.nextToken();
		Shape shape = Shape.fromString(st.nextToken());
		Point center = new Point(new Integer(st.nextToken()), new Integer(st.nextToken()));
		int radius = new Integer(st.nextToken());
		Color color = new Color(new Integer(st.nextToken()));
		return new DrawingObject(shape, center, radius, color);
		
	}

	@Override
	public boolean willDecode(String s) 
	{
		return s.startsWith(DrawingObject.MESSAGE_NAME);
	}
}
