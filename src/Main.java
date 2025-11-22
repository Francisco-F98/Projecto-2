// Projecto 2 - Elton Vieira, Francisco Ferreira
import java.util.Scanner;

// classes a implementar: User, Evento, SystemManager,

//Representa os utilizadores(nome, eventos, nr de eventos)
public class User{

    // max 200 events por user
    private int max_events = 200;
    // nome tem de ser unico
    private String name;
    // array dos eventos do user
    private event[] events;
    // nr de eventos associados ao user
    private int event_count;


    // constructor
    public User(String name){
        this.name = name;
        this.events = new event[max_events];
        this.event_count = 0;
    }


    //getters:
    public String get_name(){
        return name;
    }
    public Event[] get_Events() {
        return events;
    }
    public int get_eventCount(){
        return event_count;
    }


    // adiciona o proximo evento no calendario
    public void add_event(Event eventname){
        events[event_count]= eventname;
        event_count ++;
    }


    // remove evento no calendario e substitui pelo ultimo elemento de events.
    public void del_event(Event eventname){
        for (int i = 0; i< event_count; i++) {
            if (events[i].equals(eventname)) {         // usar .equals??
                events[i] = events[event_count - 1];
                event_count--;
            }
        }
    }


    // verificar conflitos de horario   // day: 1-5.
    public boolean conflit(int day, int inicio, int fim){
        for (int i=0; i< event_count; i++){
            Event eventname = events[i];
            // metodos definidos na classe Event
            if (eventname.get_day() == day){
                // existe conflito? ( há sobreposição? )
                if ( (inicio < eventname.getEnd()) && (fim  > eventname.getStart()) ){
                    return true;
                }
            }
        }
        return false;
    }


}

// Representa um evento
public class Event{

}




public class Main {
    private static final String CREATE_CMD="create";
    private static final String SCHEDULE_CMD="schedule";
    private static final String CANCEL_CMD="cancel";
    private static final String SHOW_CMD="show";
    private static final String TOP_CMD="top";
    private static final String EXIT_CMD ="exit";
    private static final String INVALID="Invalid command";
    private static final String CREATE_USER="User successfully created.";
    private static final String CREATE_ERROR="User already registered.";
    private static final String SCHEDULE_CREATED="Event successfully created.";
    private static final String SCHEDULE_ERROR_EXIST="Event already exists.";
    private static final String SCHEDULE_ERROR_NOT="Some user not registered.";
    private static final String SCHEDULE_ERROR_USER="Some user not available.";
    private static final String SCHEDULE_ERROR_PROPOSER="Proposer not available.";
    private static final String CANCEL_CREATED="Event successfully cancelled.";
    private static final String CANCEL_ERROR="Event cancelled.";
    private static final String EXITED="Application exited.";


    // não percebi?? criar um user fora da classe?
    private static void createUser(UsersPlatform user,String[] name) {
        String username=name[1];
        if(user.exists(username)){System.out.println(CREATE_ERROR);}
        else{user.createUser(username);
            System.out.println(CREATE_USER);
        }
    }
    private static void scheduleEvent(){}
    private static void cancelEvent(){}
    private static void showEvent(){}
    private static void topEvents(){}
    private static void exit(){
        System.out.println(EXITED);
    }
    private static void executeCommand(UsersPlatform users,Scanner sc){
        String command;
        do{
            command=sc.nextLine();
            String[] substrings =command.split("\\s+");
            switch(substrings[0]){
                case CREATE_CMD->{createUser(users,substrings);}
                case SCHEDULE_CMD ->{scheduleEvent();}
                case CANCEL_CMD ->{cancelEvent();}
                case SHOW_CMD ->{showEvent();}
                case TOP_CMD ->{topEvents();}
                case EXIT_CMD ->{exit();}
                default -> System.out.println(INVALID);
            }
        }while(!command.equals(EXIT_CMD));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsersPlatform users=new UsersPlatform();
        executeCommand(users,sc);
        sc.close();

    }
}
