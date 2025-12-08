import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

//SystemManager
public class SystemManager{

    // Max Users = 100
    private int maxUsers = 100;
    private User[] users = new User[maxUsers];
    private int userCount =0;

    // Max Nr of Events
    private int maxEvent = 6000;
    private Event[] totalEvents = new Event[maxEvent];
    private int countTop;
    private int topPart;
    private int totalEvCount=0;

    // Checks if User is already registered
    public boolean userExists(String name){
        for (int i = 0; i< userCount; i++){
            if ((users[i].getName()).equals(name)){
                return true;
            }
        }
        return false;
    }

    // Gets User from String
    public User getUser(String name){
        for (int i = 0; i< userCount; i++) {
            if ((users[i].getName()).equals(name)) {
                return users[i];
            }
        }
        return null;
    }

    // Registers a new User in the System
    public void createUser(String name){
        users[userCount]= new User(name);
        userCount++;
    }

    // Verifies if User is the proposer
    public boolean isProponent(String name, String eventName){
        return getEvent(eventName).getProponent().equals(getUser(name));
    }

    //Selection sort
    private void sortEvents() {
        if(totalEvCount >=2) {
            for (int i = 0; i < totalEvCount; i++) {
                int maxIdx = i;
                for (int j = i + 1; j < totalEvCount; j++) {
                    if (isSortCriteria(totalEvents[maxIdx], totalEvents[j])) {
                        maxIdx = j;
                    }
                }

                Event tmpEventName = totalEvents[i];
                totalEvents[i] = totalEvents[maxIdx];
                totalEvents[maxIdx] = tmpEventName;
            }
        }
    }

    // Sort Criteria based on instructions
    private boolean isSortCriteria (Event eventI,Event eventJ){
        if(eventI.getParticipantCount()!=eventJ.getParticipantCount()){
            return eventI.getParticipantCount()<eventJ.getParticipantCount();}
        if(eventI.getDay()!=eventJ.getDay()){
            return eventI.getDay()>eventJ.getDay();}
        if(eventI.getStart()!=eventJ.getStart()){
            return eventI.getStart()>eventJ.getStart();}
        if(eventI.getEnd()!=eventJ.getEnd()){
            return eventI.getEnd()>eventJ.getEnd();
        }
        else return eventI.getName().compareTo(eventJ.getName())>0;
    }
    //Find the events with the top participant count
    public Event[] topEvents(){
        updateTopNumOfParticipant();
        countTop=0;
        for(int i = 0; i< totalEvCount; i++){
            if(topPart== totalEvents[i].getParticipantCount()){
                countTop++;
            }

        }Event[]tempEventName=new Event[countTop];
        for(int i=0;i<countTop;i++){
            tempEventName[i]= totalEvents[i];
        }
        return tempEventName;
    }


    // Verifies if the Event is already registered
    public boolean eventExists(String name) {
        for (int i = 0; i < totalEvCount; i++) {
            if (totalEvents[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Verifies if a User is already registered in an Event
    public boolean isInCalender(String eventName,String user) {
        for (int i = 0; i < getUser(user).getEventCount(); i++) {
            if (getUser(user).get_Events(i).equals(getEvent(eventName))) {
                return true;
            }
        }
        return false;
    }

    // Gets Event by String
    public Event getEvent(String name){
        for (int i = 0; i< totalEvCount; i++) {
            if (totalEvents[i].getName().equals(name)) {
                return totalEvents[i];
            }
        }
        return null;
    }

    // Registers a new Event in System
    public void createEvent(String name, int day, int start, int end, User[] parts, int count){
        totalEvents[totalEvCount] = new Event(name, day, start, end, parts, count);
        for (int i = 0;i<count; i++){
            parts[i].addEvent(totalEvents[totalEvCount]);
        }
        totalEvCount++;
        sortEvents();
    }

    // Removes an Event from System
    public void deleteEvent(String event){
        User userName;
        Event eventName = getEvent(event);
        int eventP=eventName.getParticipantCount();
        for(int i=0;i<eventP;i++){
            userName=eventName.getParticipants(i);
            userName.delEvent(eventName);}
        for (int i = 0; i< totalEvCount; i++) {
            if (totalEvents[i].equals(eventName)) {
                totalEvents[i] = totalEvents[totalEvCount - 1];
                totalEvCount--;
                sortEvents();
            }
        }
    }

    // Getters
    public int getCountTop(){
        return countTop;
    }
    public int getEventCount(){
        return totalEvCount;
    }
    public void updateTopNumOfParticipant(){
        topPart=0;
        for(int i = 0; i< totalEvCount; i++){
            if(totalEvents[i].getParticipantCount()>topPart){
                topPart= totalEvents[i].getParticipantCount();
            }
        }
    }


    // Loads Users and Events from a file
    public void loadFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        Scanner sfr = new Scanner(fr);
        int numberUsers = sfr.nextInt();

        for (int i=0;i<numberUsers;i++){
            String userName = sfr.next();
            if (!(userExists(userName))) createUser(userName);
        }

        int numberEvents = sfr.nextInt();
        for (int n=0;n<numberEvents;n++){
            String eventName = sfr.next();
            int eventDay = sfr.nextInt();
            int eventStart = sfr.nextInt();
            int eventEnd = sfr.nextInt();
            int partCount = sfr.nextInt();

            User[] participantUser = new User[partCount];
            for (int m =0;m<partCount;m++){
                String participante = sfr.next();
                if (userExists(participante)) participantUser[m]= getUser(participante);
                else{
                    createUser(participante);
                    participantUser[m]= getUser(participante);
                }
            }
            if (!eventExists(eventName)) {
                createEvent(eventName, eventDay, eventStart, eventEnd, participantUser, partCount);
            }
        }
    }
}
