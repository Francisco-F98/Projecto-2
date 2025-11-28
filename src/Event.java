// Representa um evento
public class Event{

    //max participantes por evento
    private int max_part = 10;


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
    private int participant_count;

    // constructor
    public Event(String name,int day,int start, int end, User[] parts, int part_count){

        this.name = name;
        this.day = day;
        this.start = start;
        this.end= end;

        this.participants = new User[max_part];
        this.participant_count = part_count;

        for (int i = 0; i < part_count;i++){
            this.participants[i]=  parts[i];
        }
    }

    // getters:
    public String get_name(){
        return name;
    }
    public int get_day(){
        return day;
    }
    public int get_start(){
        return start;
    }
    public int get_end(){
        return end;
    }
    public int get_participantCount(){
        return participant_count;
    }
    public User get_proponente(){
        return participants[0];
    }
    public User get_participants(int idx){
        return participants[idx];

    }

}
