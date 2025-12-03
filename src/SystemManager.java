import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

//SystemManager
public class SystemManager{

    // Users
    // max users = 100
    private int maxUsers = 100;
    private User[] users = new User[maxUsers];
    private int userCount =0;

    // Eventos
    // nr max de eventos= (12*5)*100 = 6000?
    private int maxEvent = 6000;                                                                //Mudar
    private Event[] totalEvents = new Event[maxEvent];
    private int countTop;
    private int topPart;
    private int totalEvCount=0;

    // USERS
    // existe o user no array?

    public boolean userExists(String name){
        for (int i = 0; i< userCount; i++){
            if ((users[i].getName()).equals(name)){
                return true;
            }
        }
        return false;
    }
    // Dá nos o user através do nome
    public User getUser(String name){
        for (int i = 0; i< userCount; i++) {
            if ((users[i].getName()).equals(name)) {
                return users[i];
            }
        }
        return null;
    }
    // create user
    public void createUser(String name){
        users[userCount]= new User(name);
        userCount++;
    }

    //Verifica se é o proponente
    public boolean isProponent(String name, String eventName){
        return getEvent(eventName).getProponent().equals(getUser(name));
    }
    //Selection sort
    private void sortEvents() {
      if(totalEvCount >=2) {
          for (int i = 0; i < totalEvCount; i++) {
              int maxIdx = i;
              for (int j = i + 1; j < totalEvCount; j++) {
                  if (isSortCriteria(totalEvents[maxIdx], totalEvents[j])) {
                      maxIdx = j;
                  }
              }
              Event tmpEventName = totalEvents[i];
              totalEvents[i] = totalEvents[maxIdx];
              totalEvents[maxIdx] = tmpEventName;
          }
      }

    }//Aqui estão o criterios de ordenação que estão no enunciado
    private boolean isSortCriteria (Event eventI,Event eventJ){
        if(eventI.getParticipantCount()!=eventJ.getParticipantCount()){
            return eventI.getParticipantCount()<eventJ.getParticipantCount();}          //Alterar a lógica dos returns
        if(eventI.getDay()!=eventJ.getDay()){
            return eventI.getDay()>eventJ.getDay();}
        if(eventI.getStart()!=eventJ.getStart()){
            return eventI.getStart()>eventJ.getStart();}
        if(eventI.getEnd()!=eventJ.getEnd()){
            return eventI.getEnd()>eventJ.getEnd();
        }
        else return eventI.getName().compareTo(eventJ.getName())>0;

        }
        public Event [] topEvents(){
        updateTopNumOfParticipant();//Procura qual é novo número top de participantes
        countTop=0;
        for(int i = 0; i< totalEvCount; i++){//Quantos têm esse número top
            if(topPart== totalEvents[i].getParticipantCount()){
                countTop++;
            }

        }Event[]tempEventName=new Event[countTop];//Guarda os tops num array temp
        for(int i=0;i<countTop;i++){
            tempEventName[i]= totalEvents[i];
            }

        return tempEventName;
        }

    // EVENTOS
    // existe o evento no array?
    public boolean eventExists(String name) {
        for (int i = 0; i < totalEvCount; i++) {
            if (totalEvents[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    //Verifica se está no calendário do user
    public boolean isInCalender(String eventName,String user) {
        for (int i = 0; i < getUser(user).getEventCount(); i++) {
            if (getUser(user).get_Events(i).equals(getEvent(eventName))) {
                return true;
            }
        }
        return false;
    }

    // Dá nos o evento através do nome
    public Event getEvent(String name){
        for (int i = 0; i< totalEvCount; i++) {
            if (totalEvents[i].getName().equals(name)) {
                return totalEvents[i];
            }
        }
        return null;
    }
    // create event
    public void createEvent(String name, int day, int start, int end, User[] parts, int count){
        totalEvents[totalEvCount] = new Event(name, day, start, end, parts, count);
        for (int i = 0;i<count; i++){
            parts[i].addEvent(totalEvents[totalEvCount]);
        }
        totalEvCount++;
        sortEvents();

    }

    // remove evento no SystemManager e substitui pelo ultimo elemento de total_events.
    public void deleteEvent(String event){
        User userName;
        Event eventName = getEvent(event);
        int eventP=eventName.getParticipantCount();
        for(int i=0;i<eventP;i++){//Apaga o evento em todos os participantes
            userName=eventName.getParticipants(i);
            userName.delEvent(eventName);}
        for (int i = 0; i< totalEvCount; i++) {
            if (totalEvents[i].equals(eventName)) {
                totalEvents[i] = totalEvents[totalEvCount - 1];
                totalEvCount--;
                sortEvents();
            }
        }

    }
    //GETTERs

    public int getCountTop(){
        return countTop;
    }
    public int getEventCount(){
        return totalEvCount;
    }
    public void updateTopNumOfParticipant(){
      topPart=0;
        for(int i = 0; i< totalEvCount; i++){
            if(totalEvents[i].getParticipantCount()>topPart){
                topPart= totalEvents[i].getParticipantCount();
            }
        }
    }
    // Carrega user e total_events a partir do ficheiro
    public void loadFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        Scanner sfr = new Scanner(fr);
        int numberUsers = sfr.nextInt();
        // Precorre o ficheiro consoante o nr de Users
        for (int i=0;i<numberUsers;i++){  //
            String userName = sfr.next();
            if (!(userExists(userName))) createUser(userName); //***//
        }
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
                if (userExists(participante)) participantUser[m]= getUser(participante);
                else{
                    createUser(participante);
                    participantUser[m]= getUser(participante);
                }
            }
            if (!eventExists(eventName)) {
                createEvent(eventName, eventDay, eventStart, eventEnd, participantUser, partCount);
            }
        }
    }
}
