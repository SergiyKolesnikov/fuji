package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage implements Serializable
{
	private static final long serialVersionUID = -9161595018411902079L;
	private String content;

	private boolean encoded = false;

	public TextMessage(String content)
	{
		super();
		this.content = content;
	}

	public String getContent()
	{
		return content;
	}
	
	public void setContent (String content)
	{
		if (this.encoded)
			this.content = Security.Encode(content);
		else
			this.content = content;
	}

	public boolean isEncoded()
	{
		return this.encoded;
	}

	public void Encode()
	{
		if (!this.encoded)
			this.content = Security.Encode(this.content);

		this.encoded = true;
	}

	public void Decode()
	{
		if (this.encoded)
			this.content = Security.Decode(this.content);

		this.encoded = false;
	}
}
