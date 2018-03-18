package cs656.com.firebasemessengerapp.model;

/**
 * Created by michaelestwanick on 11/20/16.
 */

public class Message {

    private String sender;
    private String message;
    private Boolean multimedia = false;
    private String contentType = "";
    private String contentLocation = "";
    private String timestamp = "";
    private String senderName;
    public Message(){

    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", multimedia=" + multimedia +
                ", contentType='" + contentType + '\'' +
                ", contentLocation='" + contentLocation + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", senderName='" + senderName + '\'' +
                '}';
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String sendername) {
        this.senderName = sendername;
    }

    //Constructor for plain text message
    public Message(String sendername,String sender, String message, String time){
        this.sender = sender;
        this.message = message;
        this.timestamp = time;
        this.multimedia = false;
        this.senderName =sendername;
    }

    //Constructor for Multimedia message
    public Message(String sender, String message, String contentType, String contentLocation, String time){
        this.sender = sender;
        this.message = message;
        this.multimedia = true;
        this.contentType = contentType;
        this.timestamp = time;
        this.contentLocation = contentLocation;
    }

    public String getSender() {
        return sender;
    }
    public String getTimestamp(){return timestamp;}
    public String getMessage() {
        return message;
    }

    public String getContentLocation() {
        return contentLocation;
    }

    public Boolean getMultimedia() {
        return multimedia;
    }

    public String getContentType() {
        return contentType;
    }
    /*for(User user1 : list1) {
        for(User user2 : list2) {
            if(user1.getEmpCode().equals(user2.getEmpCode())) {
                if(!user1.getFirstName().equals(user2.getFirstName()) ||
                        !user1.getLastName().equals(user2.getLastName()) ||
                        !user1.getEmail().equals(user2.getEmail())) {
                    resultList.add(user1);
                }
            }
        }
    }
*/}
