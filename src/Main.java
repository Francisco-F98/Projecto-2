// Projecto 2 - Elton Vieira nº 73581, Francisco Ferreira nº73415

import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    private static final String CREATE_CMD = "create";
    private static final String SCHEDULE_CMD = "schedule";
    private static final String CANCEL_CMD = "cancel";
    private static final String SHOW_CMD = "show";
    private static final String TOP_CMD = "top";
    private static final String EXIT_CMD = "exit";
    private static final String INVALID_CMD = "Invalid command.";
    private static final String CREATE_USER = "User successfully created.";
    private static final String CREATE_ERROR = "User already registered.";
    private static final String SCHEDULE_CREATED = "Event successfully created.";
    private static final String SCHEDULE_ERROR_EXIST = "Event already exists.";
    private static final String SCHEDULE_ERROR_NOT = "Some user not registered.";
    private static final String SCHEDULE_ERROR_USER = "Some user not available.";
    private static final String SCHEDULE_ERROR_PROPOSER = "Proposer not available.";
    private static final String CANCEL_SUCCESS = "Event successfully canceled.";
    private static final String SHOW_ERROR = "User not registered.";
    private static final String TOP_ERROR = "No events registered.";
    private static final String EXITED = "Application exited.";




    private static boolean allAvailable(SystemManager sysM,String[] parts,int eDay,int eStart,int eEnd){
        for (int i = 1; i < parts.length; i++) {
            if (sysM.getUser(parts[i]).conflit(eDay, eStart, eEnd)) return false;
        }
        return true;
    }

    private static boolean areRegistered(SystemManager sysM, String[]participants, int participantsCount){
        for (int i = 0; i < participantsCount; i++) {
            if (!sysM.userExists(participants[i]))
                return false;
        }
        return true;
    }

    private static void createEvent(SystemManager sysM, String eName, int eDay, int eStart, int eEnd, String[] parts) {
        User[] eventParts = new User[parts.length];
        for (int i = 0; i < parts.length; i++) {
            eventParts[i] = sysM.getUser(parts[i]);
        }
        sysM.createEvent(eName, eDay, eStart, eEnd, eventParts, parts.length);
    }
    private static String handlesSchedule(SystemManager sysM,String eName,int eDay,int eStart,int eEnd,String[]parts){
        int proponent=0;
        if (!areRegistered(sysM,parts,parts.length))
            return SCHEDULE_ERROR_NOT;
        if (sysM.eventExists(eName))
            return SCHEDULE_ERROR_EXIST;
        if (sysM.getUser(parts[proponent]).conflit(eDay, eStart, eEnd))
            return SCHEDULE_ERROR_PROPOSER;
        if (!allAvailable(sysM,parts,eDay,eStart,eEnd))
            return SCHEDULE_ERROR_USER;
        createEvent(sysM,eName,eDay,eStart,eEnd,parts);
        return SCHEDULE_CREATED;
    }

    private static String formatEvent(Event e){
        return e.getName()+", day "+e.getDay()+", "+e.getStart()+"-"+
                e.getEnd()+", "+e.getParticipantCount()+ " participants.";
    }

    // Creates a new user
    private static void createUser(SystemManager sysM,Scanner sc) {
        String name = sc.next();
        if (sysM.userExists(name))
            System.out.println(CREATE_ERROR);
        else {
            sysM.createUser(name);
            System.out.println(CREATE_USER);
        }
    }

    // Creates a new Event
    private static void scheduleEvent(SystemManager sysM,Scanner sc) {

        String eventName = sc.next();
        int eDay = sc.nextInt();
        int eStart = sc.nextInt();
        int eEnd = sc.nextInt();
        String[] parts = new String[sc.nextInt()];
        for (int i=0;i< parts.length;i++)
            parts[i]= sc.next();
        System.out.println(handlesSchedule(sysM,eventName,eDay,eStart,eEnd,parts));
    }

    // Deletes an Event
    private static void cancelEvent (SystemManager sysM,Scanner sc) {
        String eventName = sc.next();
        String user = sc.next();
        if (!sysM.userExists(user))
            System.out.println(SHOW_ERROR);
        else if (!sysM.isInCalender(eventName,user))
            System.out.printf("Event not found in calendar of %s.\n",user);
        else if(!sysM.isProponent(user,eventName))
            System.out.printf("User %s did not create event %s.\n",user,eventName);
        else{
            sysM.deleteEvent(eventName);
            System.out.println(CANCEL_SUCCESS);
        }
    }

    // Shows a User Events by chronological order
    private static void showEvent (SystemManager sysM,Scanner sc) {
        String name = sc.next();
        sc.nextLine();
        if (!sysM.userExists(name))
            System.out.println(SHOW_ERROR);
        else {
            if (sysM.getUser(name).getEventCount() == 0){
                System.out.println("User "+ name +" has no events.");
            }
            else {
                Event[] eTemp = sysM.getUser(name).get_Events();
                for(int i = 0; i<sysM.getUser(name).getEventCount(); i++){
                    System.out.println(formatEvent(eTemp[i]));
                }
            }
        }
    }

    // Shows Events by number of users registered
    private static void topEvents (SystemManager sysM) {
        if(sysM.getEventCount()==0){
            System.out.println(TOP_ERROR);
        }
        else{
            Event [] e_temp=sysM.topEvents();
            for(int i=0;i<(sysM.getCountTop());i++){
                System.out.println(formatEvent(e_temp[i]));
            }
        }
    }

    private static void exit () {
        System.out.println(EXITED);
    }
    // Menu of commands,select which the user chooses
    private static void executeCommand (SystemManager sysM,Scanner sc) {
        String command;
        do {
            command=sc.next();
            switch (command) {
                case CREATE_CMD -> {
                    createUser(sysM,sc);
                    sc.nextLine();
                }
                case SCHEDULE_CMD -> scheduleEvent(sysM,sc);
                case CANCEL_CMD -> cancelEvent(sysM,sc);
                case SHOW_CMD -> showEvent(sysM,sc);
                case TOP_CMD -> topEvents(sysM);
                case EXIT_CMD -> exit();
                default -> {
                    System.out.println(INVALID_CMD);
                            sc.nextLine();
                }
            }
        } while (!command.equals(EXIT_CMD));
    }
    public static void main (String[]args){
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        SystemManager sysM = new SystemManager();
        {
            try {
                sysM.loadFile(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    executeCommand(sysM,sc);
    }
}
