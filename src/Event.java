
public class Event{

    // Max participants per Event
    private int maxParticipants = 10;


    private String name;
    // Day goes from 1 to 5
    private int day;
    //Hours can go from 8 to 20
    private int start;
    private int end;
    //Array with Users participating in the Event
    private User[] participants;
    // Number of Users
    private int participantCount;

    // Constructor
    public Event(String name,int day,int start, int end, User[] parts, int part_count){

        this.name = name;
        this.day = day;
        this.start = start;
        this.end= end;

        this.participants = new User[maxParticipants];
        this.participantCount = part_count;

        for (int i = 0; i < part_count;i++){
            this.participants[i]=  parts[i];
        }
    }

    // Getters:
    public String getName(){
        return name;
    }
    public int getDay(){
        return day;
    }
    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
    public int getParticipantCount(){
        return participantCount;
    }
    public User getProponent(){
        return participants[0];
    }
    public User getParticipants(int idx){
        return participants[idx];
    }
}
