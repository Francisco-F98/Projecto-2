// Representa um evento
public class Event{

    //max participantes por evento
    private int maxParticipants = 10;


    private String name;
    // dia do evento: 1-5
    private int day;
    //hora de inicio: 8-19
    private int start;
    //hora de fim: 9-20
    private int end;
    //array com os participantes no evento
    private User[] participants;
    // nr de participantes
    private int participantCount;

    // constructor
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

    // getters:
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
