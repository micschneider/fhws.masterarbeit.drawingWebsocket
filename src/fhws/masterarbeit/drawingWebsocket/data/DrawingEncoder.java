package fhws.masterarbeit.drawingWebsocket.data;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DrawingEncoder implements Encoder.Text<DrawingObject>
{

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig arg0) {}

	@Override
	public String encode(DrawingObject drawingObject) throws EncodeException 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(DrawingObject.MESSAGE_NAME);
		sb.append(",");
		sb.append(drawingObject.getShape().toString());
		sb.append(",");
		sb.append(drawingObject.getCenter().x);
		sb.append(",");
		sb.append(drawingObject.getCenter().y);
		sb.append(",");
		sb.append(drawingObject.getRadius());
		sb.append(",");
		sb.append(drawingObject.getColor().getRGB());
		return sb.toString();
	}
}
