
public class User{

    // Max 200 events per user
    private int maxEvents = 200;
    private String name;
    // Event Array per User
    private Event[] events;
    // Number of Events User is registered
    private int eventCount;


    // Constructor
    public User(String name){
        this.name = name;
        this.events = new Event[maxEvents];
        this.eventCount = 0;
    }


    // Getters:
    public String getName(){
        return name;
    }
    public Event[] get_Events() {
        return events;
    }
    public Event get_Events(int idx) {
        return events[idx];
    }
    public int getEventCount(){
        return eventCount;
    }


    // Adds a new Event
    public void addEvent(Event eventName){
        events[eventCount]= eventName;
        eventCount++;
        if(eventCount>1){
            sortUserEvents();
        }

    }
    // Sort by selection order
    public void sortUserEvents(){
        for(int i = 0; i< eventCount; i++){
            int minIdx = i;
            for(int j = i+1; j< eventCount; j++){
                if(sortCriteria(events[minIdx],events[j])){
                    minIdx = j;
                }
            }
            Event tmpEventName=events[i];
            events[i]=events[minIdx];
            events[minIdx]=tmpEventName;
        }
    }

    // Sort Criteria based on instructions
    public boolean sortCriteria(Event eventI,Event eventJ){
        if(eventI.getDay()!=eventJ.getDay()){
            return eventI.getDay()>eventJ.getDay();}
        if(eventI.getStart()!=eventJ.getStart()){
            return eventI.getStart()>eventJ.getStart();}
        if(eventI.getEnd()!=eventJ.getEnd()){
            return eventI.getEnd()>eventJ.getEnd();
        }
        else return eventI.getName().compareTo(eventJ.getName())>0;
    }


    // Removes an Event
    public void delEvent(Event eventName){
        for (int i = 0; i< eventCount; i++) {
            if (events[i].equals(eventName)) {
                events[i] = events[eventCount - 1];
                eventCount--;
            }
        }
        sortUserEvents();
    }


    // Verifies if there are conflicts. True if conflicts found.
    public boolean conflit(int day, int inicio, int fim){
        for (int i = 0; i< eventCount; i++){
            if (events[i].getDay() == day){
                if ( (inicio < events[i].getEnd()) && (fim  > events[i].getStart()) ){
                    return true;
                }
            }
        }
        return false;
    }
}
