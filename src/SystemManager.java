import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

//SystemManager
public class SystemManager{

    // Users
    // max users = 100
    private int max_users = 100;
    private User[] users = new User[max_users];
    private int user_count =0;

    // Eventos
    // nr max de eventos= (12*5)*100 = 6000?
    private int max_event = 6000;
    private Event[] total_events = new Event[max_event];
    private int TotalEvCount =0;

    // USERS
    // existe o user no array?
    public boolean user_exists(String name){
        for (int i = 0;i<user_count; i++){
            if ((users[i].get_name()).equals(name)){
                return true;
            }
        }
        return false;
    }
    // Dá nos o user através do nome
    public User get_user(String name){
        for (int i = 0;i<user_count; i++) {
            if ((users[i].get_name()).equals(name)) {
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
        for (int i = 0; i < TotalEvCount; i++) {
            if (total_events[i].get_name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Dá nos o evento através do nome
    public Event get_event(String name){
        for (int i = 0; i< TotalEvCount; i++) {
            if (total_events[i].get_name().equals(name)) {
                return total_events[i];
            }
        }
        return null;
    }
    // create event
    public void create_event(String name, int day, int start, int end,User[] parts, int count){
        total_events[TotalEvCount] = new Event(name, day, start, end, parts, count);
        System.out.println("total ev count: "+TotalEvCount);
        for (int i = 0;i<count; i++){
            parts[i].add_event(total_events[TotalEvCount]);
        }
        TotalEvCount++;
    }

    // remove evento no SystemManager e substitui pelo ultimo elemento de total_events.
    public void del_event(Event eventname){
        for (int i = 0; i< TotalEvCount; i++) {
            if (total_events[i].equals(eventname)) {
                total_events[i] = total_events[TotalEvCount - 1];
                TotalEvCount--;
            }
        }
    }

    //GETTERs
    public int get_EventCount(){
        return TotalEvCount;
    }
    public int get_UserCount(){
        return user_count;
    }
    public Event[] get_Events(){
        return total_events;
    }

    // Carrega user e total_events a partir do ficheiro
    public void loadFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        Scanner sfr = new Scanner(fr);
        int numberUsers = sfr.nextInt();
        // Precorre o ficheiro consoante o nr de Users
        for (int i=0;i<numberUsers;i++){
            String user_name = sfr.next();
            if (!(user_exists(user_name))) create_user(user_name);

            // Percorre os eventos por user
            int numberEvents = sfr.nextInt();
            for (int n=0;n<numberEvents;n++){
                String eventName = sfr.next();
                int eventDay = sfr.nextInt();
                int eventStart = sfr.nextInt();
                int eventEnd = sfr.nextInt();
                int partCount = sfr.nextInt();

                // percorre o nr de participantes por evento e cria users se não existirem.
                User[] participantUser = new User[partCount];
                for (int m =0;m<partCount;m++){
                    String participante = sfr.next();
                    if (user_exists(participante)) participantUser[m]= get_user(participante);
                    else{
                        create_user(participante);
                        participantUser[m]= get_user(participante);
                    }
                }
                if (!(event_exists(eventName))) {
                    create_event(eventName, eventDay, eventStart, eventEnd, participantUser, partCount);
                }


            }
        }
    }
}
