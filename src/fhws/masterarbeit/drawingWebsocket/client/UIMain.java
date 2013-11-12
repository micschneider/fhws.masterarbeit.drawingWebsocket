package fhws.masterarbeit.drawingWebsocket.client;

public class UIMain {

	public static void main(String[] args) 
	{
		DrawingWindow wbw1 = new DrawingWindow("ws://localhost:8080/drawing.websocket/drawingWebsocket", 50, 10);
        DrawingWindow wbw2 = new DrawingWindow("ws://localhost:8080/drawing.websocket/drawingWebsocket", 500, 20);
	}
}
