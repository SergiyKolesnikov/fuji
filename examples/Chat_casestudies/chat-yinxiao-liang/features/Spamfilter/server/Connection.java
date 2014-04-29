package server;

import server.WordFilter;

import common.TextMessage;

/**
 * TODO description
 */
public class Connection {
	public void send(String line)
	{
		String tmp = WordFilter.filter(line);
		original(tmp);
	}
}