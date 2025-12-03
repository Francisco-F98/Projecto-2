//Representa os utilizadores(nome, eventos, nr de eventos)
public class User{

    // max 200 events por user
    private int maxEvents = 200;
    // nome tem de ser unico
    private String name;
    // array dos eventos do user
    private Event[] events;
    // nr de eventos associados ao user
    private int eventCount;


    // constructor
    public User(String name){
        this.name = name;
        this.events = new Event[maxEvents];
        this.eventCount = 0;
    }


    //getters:
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


    // adiciona o proximo evento no calendario
    public void addEvent(Event eventName){
        events[eventCount]= eventName;
        eventCount++;
        if(eventCount>1){
            sortUserEvents();
        }

    }
    //Sort by selection order
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




    public boolean sortCriteria(Event eventI,Event eventJ){
        if(eventI.getDay()!=eventJ.getDay()){
            return eventI.getDay()>eventJ.getDay();} //Mudar
        if(eventI.getStart()!=eventJ.getStart()){
            return eventI.getStart()>eventJ.getStart();}
        if(eventI.getEnd()!=eventJ.getEnd()){
            return eventI.getEnd()>eventJ.getEnd();
        }
        else return eventI.getName().compareTo(eventJ.getName())>0;

    }


    // remove evento no calendario e substitui pelo ultimo elemento de events.
    public void delEvent(Event eventName){
        for (int i = 0; i< eventCount; i++) {
            if (events[i].equals(eventName)) {         // usar .equals??//eu não mudei nada aqui
                events[i] = events[eventCount - 1];
                eventCount--;

            }
        }
    sortUserEvents();
    }


    // verificar conflitos de horario-True se houver conflito  // day: 1-5.
    public boolean conflit(int day, int inicio, int fim){
        for (int i = 0; i< eventCount; i++){
            // metodos definidos na classe Event
            if (events[i].getDay() == day){
                // existe conflito? ( há sobreposição? )
                if ( (inicio < events[i].getEnd()) && (fim  > events[i].getStart()) ){
                    return true;
                }
            }
        }
        return false;
    }


}
