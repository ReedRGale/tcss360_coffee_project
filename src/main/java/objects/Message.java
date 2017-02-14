/*
 * A message object to contain different message information.
 */
package objects;
import java.sql.Date;

/**
 *
 * @author Jared Lowery
 */
public class Message 
{
    private int messageId;
    private int userId;
    private String message;
    private Date dateadded;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }
    
}
