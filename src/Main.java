// Projecto 2 - Elton Vieira, Francisco Ferreira
import java.util.Scanner;
import java.io.FileNotFoundException;



public class Main {
    private static final String CREATE_CMD = "create";
    private static final String SCHEDULE_CMD = "schedule";
    private static final String CANCEL_CMD = "cancel";
    private static final String SHOW_CMD = "show";
    private static final String TOP_CMD = "top";
    private static final String EXIT_CMD = "exit";
    private static final String INVALID = "Invalid command";
    private static final String CREATE_USER = "User successfully created.";
    private static final String CREATE_ERROR = "User already registered.";
    private static final String SCHEDULE_CREATED = "Event successfully created.";
    private static final String SCHEDULE_ERROR_EXIST = "Event already exists.";
    private static final String SCHEDULE_ERROR_NOT = "Some user not registered.";
    private static final String SCHEDULE_ERROR_USER = "Some user not available.";
    private static final String SCHEDULE_ERROR_PROPOSER = "Proposer not available.";
    private static final String CANCEL_SUCCESS = "Event successfully cancelled.";
    private static final String CANCEL_ERROR = "Event cancelled.";
    private static final String SHOW_ERROR = "User not registered.";
    private static final String EXITED = "Application exited.";

    // Criação da classe SystemManager  e leitura do Text file.

    static Scanner sc = new Scanner(System.in);
    String fileName = "Ficheiro";
    // FALTA DESCOBRIR O NOME DO FICHEIRO
    static SystemManager sysM = new SystemManager();
    {
        try {
            sysM.loadFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // cria um USER novo SE ainda não existe
    private static void createUser() {
        String name = sc.next();
        System.out.println(name);

        if (sysM.user_exists(name)) System.out.println(CREATE_ERROR);
        else {
            sysM.create_user(name);
            System.out.println(CREATE_USER);
            System.out.println(sysM.get_UserCount());
        }
    }

    // Cria um EVENTO novo SE ainda não existe
    private static void scheduleEvent() {
        // Read / Prepare

        String eventName = sc.next();
        int eDay = sc.nextInt();
        int eStart = sc.nextInt();
        int eEnd = sc.nextInt();
        int partCount = sc.nextInt();
        String[] parts = new String[partCount];
        for (int i=0;i<partCount;i++) parts[i]= sc.next();
        // 1-Checks if every user is valid
        boolean user_check = true;
        for (int i = 0; i < partCount; i++) {
            if (!(sysM.user_exists(parts[i]))) user_check = false;
        }
        if (!(user_check)) System.out.println(SCHEDULE_ERROR_NOT);

            // 2-Checks if event already exists

        else if (sysM.event_exists(eventName)) System.out.println(SCHEDULE_ERROR_EXIST);
        else {
            //Cria lista de Users
            User[] eventParts = new User[partCount];
            for (int n = 0; n < partCount; n++) eventParts[n] = sysM.get_user(parts[n]);

            // 3-Checks if proposer is available
            if (eventParts[0].conflit(eDay, eStart, eEnd)) System.out.println(SCHEDULE_ERROR_PROPOSER);
                // 4-Checks if all users are available
            else {
                boolean Avail = true;
                for (int z = 1; z < partCount; z++) {
                    if (eventParts[z].conflit(eDay, eStart, eEnd)) Avail = false;
                }
                if (!Avail) System.out.println(SCHEDULE_ERROR_USER);
                    //5-Creates event after all checks
                else {
                    sysM.create_event(eventName, eDay, eStart, eEnd, eventParts, partCount);
                    System.out.println(SCHEDULE_CREATED);
                }
            }
        }
    }


    // apaga o evento, so o proponente pode fazer
    private static void cancelEvent () {
        String eventName = sc.next();
        String user = sc.next();
        if (!(sysM.user_exists(user))) System.out.println("User not registered");
        else if (!(sysM.event_exists(eventName))) System.out.printf("Event not found in calendar of %s .\n",user);
        else if(!(sysM.checkProp(user,eventName))){System.out.printf("User %s did not create event %s.\n",user,eventName);}
        else{sysM.del_event(eventName,user);
        System.out.println(CANCEL_SUCCESS);
        }

    }
    // mostra o calendario de um user por ordem cronologica
    private static void showEvent () {
        String nome = sc.next();
        sc.nextLine();
        if (!(sysM.user_exists(nome))){
            System.out.println(SHOW_ERROR);
        }
        else {
            if ((sysM.get_user(nome)).get_eventCount() == 0){
                System.out.println("User "+nome+" has no events");
            }
            else {
                Event[] e_temp = (sysM.get_user(nome)).get_Events();
                for(int i=0;i<(sysM.get_user(nome)).get_eventCount();i++){
                    System.out.println(e_temp[i].get_name()+","+e_temp[i].get_day()+","+e_temp[i].get_start()+"-"+
                            e_temp[i].get_end()+","+e_temp[i].get_participantCount()+ "participants");
                }
            }
        }
    }
    // Mostra eventos por ordem de adesao
    private static void topEvents () {
        if(sysM.get_EventCount()==0){
            System.out.println("No events found");
        }

        else{
            Event [] e_temp=sysM.top_events();
            for(int i=0;i<(sysM.getCountTop());i++){
                System.out.println(e_temp[i].get_name()+","+e_temp[i].get_day()+","+e_temp[i].get_start()+"-"+
                        e_temp[i].get_end()+","+e_temp[i].get_participantCount()+ "participants");
            }


        }
    }

    private static void exit () {
        System.out.println(EXITED);
    }

    private static void executeCommand (){

        String command;
        do {

            command=sc.next();
            switch (command) {
                case CREATE_CMD -> {
                    createUser();
                    sc.nextLine();
                }
                case SCHEDULE_CMD -> {

                    scheduleEvent();
                }
                case CANCEL_CMD -> {
                    cancelEvent();
                }
                case SHOW_CMD -> {
                    showEvent();
                }
                case TOP_CMD -> {
                    topEvents();
                }
                case EXIT_CMD -> {
                    exit();
                }
                default -> System.out.println(INVALID);
            }

        } while (!command.equals(EXIT_CMD));
    }
    public static void main (String[]args){
        executeCommand();
    }
}
