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
    private int countTop;
    private int topPart = 0;
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
    public boolean checkProp(String name,String eventName){
        return get_event(eventName).get_proponente().equals(get_user(name));
    }
    //Selection sort
    private void sortEvents() {
      if(TotalEvCount>=2){
        for(int i=0;i<TotalEvCount;i++){
           int maxIdx = i;
           for(int j=i+1;j<TotalEvCount;j++){
               if(sortCriteria(total_events[i],total_events[j])){
                   maxIdx = j;
               }
           }

            Event tmpEventName=total_events[i];
           total_events[i]=total_events[maxIdx];
           total_events[maxIdx]=tmpEventName;
            topPart =total_events[0].get_participantCount();
        }}else topPart=total_events[0].get_participantCount();

    }//Aqui estão o criterios de ordenação que estão no enunciado
    private boolean sortCriteria (Event eventI,Event eventJ){
        if(eventI.get_participantCount()!=eventJ.get_participantCount()){
            return eventI.get_participantCount()<eventJ.get_participantCount();}
        if(eventI.get_day()!=eventJ.get_day()){
            return eventI.get_day()<eventJ.get_day();}
        if(eventI.get_start()!=eventJ.get_start()){
            return eventI.get_start()>eventJ.get_start();}
        if(eventI.get_end()!=eventJ.get_end()){
            return eventI.get_end()>eventJ.get_end();
        }
        else return eventI.get_name().compareTo(eventJ.get_name())>0;

        }
        public Event []top_events(){
         countTop=0;
        for(int i=0;i<TotalEvCount;i++){
            if(topPart==total_events[i].get_participantCount()){
                countTop++;
            }
        }Event[]tempEventName=new Event[countTop];
        for(int i=0;i<countTop;i++){
            tempEventName[i]=total_events[i];
            }

        return tempEventName;
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
        sortEvents();

    }

    // remove evento no SystemManager e substitui pelo ultimo elemento de total_events.
    public void del_event(String event,String user){
        User userName =get_user(user);
        Event eventName =get_event(event);
        userName.del_event(eventName);
        for (int i = 0; i< TotalEvCount; i++) {
            if (total_events[i].equals(eventName)) {
                total_events[i] = total_events[TotalEvCount - 1];
                TotalEvCount--;
            }
        }

    }

    //GETTERs


    public int getCountTop(){
        return countTop;
    }
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
