package daw.antipattern;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 3092653050800877903L;

	private String text;
    
	private Date timestamp;
	
	private User author;

    public Message(String text, Date timestamp, User author) {
        this.text = text;
        this.timestamp = timestamp;
        this.author = author;
    }

    @Override
	public String toString() {
		return "Message [text=" + text + ", timestamp=" + timestamp + ", author=" + author.getName() + "]";
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
