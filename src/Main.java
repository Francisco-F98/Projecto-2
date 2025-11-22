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
    private Event[] events;
    // nr de eventos associados ao user
    private int event_count;


    // constructor
    public User(String name){
        this.name = name;
        this.events = new Event[max_events];
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
                if ( (inicio < eventname.get_end()) && (fim  > eventname.get_start()) ){
                    return true;
                }
            }
        }
        return false;
    }


}

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
    public User[] get_participants(){
        return participants;
    }


}

//SystemManager
public class SystemManager{

    // Users
    // max users = 100
    private int max_users = 100;
    private User[] users = new User[max_users];
    private int user_count =0;

    // Eventos
    // nr max de eventos= (12*5)*100 = 6000
    private int max_event = 6000;
    private Event[] events = new Event[max_event];
    private int event_count =0;

    // USERS
    // existe o user no array?
    public boolean user_exists(String name){
        for (int i = 0;i<user_count; i++){
            if (users[i].get_name().equals(name)){
                return true;
            }
        }
        return false;
    }
    // Dá nos o user através do nome
    public User get_user(String name){
        for (int i = 0;i<user_count; i++) {
            if (users[i].get_name().equals(name)) {
                return users[i];
            }
        }
        return null;
    }
    // create user
    public void create_user(String name){
        users[user_count]= new User(name);
        user_count++;
    }



    // EVENTOS
    // existe o evento no array?
    public boolean event_exists(String name) {
        for (int i = 0; i < user_count; i++) {
            if (events[i].get_name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Dá nos o evento através do nome
    public Event get_event(String name){
        for (int i = 0;i<event_count; i++) {
            if (events[i].get_name().equals(name)) {
                return events[i];
            }
        }
        return null;
    }
    // create event
    public Event create_event(String name, int day, int start, int end,User[] parts, int count){
        Event new_Event = new Event(name, day, start, end, parts, count);
        events[event_count] = new_Event;
        event_count++;
        return new_Event;
    }
    // remove evento no SystemManager e substitui pelo ultimo elemento de events.
        public void del_event(Event eventname){
        for (int i = 0; i< event_count; i++) {
            if (events[i].equals(eventname)) {
                events[i] = events[event_count - 1];
                event_count--;
            }
        }
    }

    //GETTERs
    public int get_EventCount(){
        return event_count;
    }
    public Event[] get_Events(){
        return events;
    }
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
        UsersPlatform users = new UsersPlatform();
        executeCommand(users, sc);


        boolean program_on = true;
        while (program_on) {
            String command = sc.next();

            switch (command) {
                case "create":
                    //... por implementar
                    break;

                case "schedule":
                    //... por implementar
                    break;

                case "cancel":
                    //... por implementar
                    break;

                case "show":
                    //... por implementar
                    break;

                case "exit":
                    System.out.println(EXITED);
                    return;

                case "top":
                    //... por implementar
                    break;

                default:
                    System.out.println(INVALID);
            }
        }
    }
}
