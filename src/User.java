//Representa os utilizadores(nome, eventos, nr de eventos)
public class User{

    // max 200 events por user
    private int max_Events = 200;
    // nome tem de ser unico
    private String name;
    // array dos eventos do user
    private Event[] events;
    // nr de eventos associados ao user
    private int event_count;


    // constructor
    public User(String name){
        this.name = name;
        this.events = new Event[max_Events];
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


    // verificar conflitos de horario-True se houver conflito  // day: 1-5.
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
