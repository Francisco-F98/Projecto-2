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
    public Event get_Events(int idx) {
        return events[idx];
    }
    public int get_eventCount(){
        return event_count;
    }


    // adiciona o proximo evento no calendario
    public void add_event(Event eventname){
        events[event_count]= eventname;
        event_count ++;
        sortUserEvents();

    }
    //Sort by selection order
    public void sortUserEvents(){
        if(event_count>=2){
            for(int i=0;i<event_count;i++){
                int minIdx = i;
                for(int j=i+1;j<event_count;j++){
                    if(sortCriteria(events[minIdx],events[j])){
                        minIdx = j;
                    }
                }
                Event tmpEventName=events[i];
                events[i]=events[minIdx];
                events[minIdx]=tmpEventName;
            }
        }
    }




    public boolean sortCriteria(Event eventI,Event eventJ){
        if(eventI.get_day()!=eventJ.get_day()){
            return eventI.get_day()>eventJ.get_day();}
        if(eventI.get_start()!=eventJ.get_start()){
            return eventI.get_start()>eventJ.get_start();}
        if(eventI.get_end()!=eventJ.get_end()){
            return eventI.get_end()>eventJ.get_end();
        }
        else return eventI.get_name().compareTo(eventJ.get_name())>0;

    }


    // remove evento no calendario e substitui pelo ultimo elemento de events.
    public void del_event_user(Event eventname){
        for (int i = 0; i< event_count; i++) {
            if (events[i].equals(eventname)) {         // usar .equals??//eu não mudei nada aqui
                events[i] = events[event_count - 1];
                event_count--;

            }
        }

        sortUserEvents();
    }


    // verificar conflitos de horario-True se houver conflito  // day: 1-5.
    public boolean conflit(int day, int inicio, int fim){
        for (int i=0; i< event_count; i++){
            // metodos definidos na classe Event
            if (events[i].get_day() == day){
                // existe conflito? ( há sobreposição? )
                if ( (inicio < events[i].get_end()) && (fim  > events[i].get_start()) ){
                    return true;
                }
            }
        }
        return false;
    }


}
