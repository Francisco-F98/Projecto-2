// Projecto 2 - Elton Vieira, Francisco Ferreira
import java.util.Scanner;
import java.io.FileReader;
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
    private static final String CANCEL_CREATED = "Event successfully cancelled.";
    private static final String CANCEL_ERROR = "Event cancelled.";
    private static final String EXITED = "Application exited.";


    // IMPLEMENTAR A LEITURA DO FICHEIRO

    Scanner sc = new Scanner(System.in);
    String fileName = "FICHEIRO";
    // FALTA DESCOBRIR O NOME DO FICHEIRO
    SystemManager sysM = new SystemManager();


    {
        try {
            sysM.loadFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // cria um user novo SE ainda não existe
    private void createUser(String name) {
        if (sysM.user_exists(name)) System.out.println(CREATE_ERROR);
        else {
            sysM.create_user(name);
            System.out.println(CREATE_USER);
        }
    }
    // create event
    private void scheduleEvent (String evName) {
        String eventName = sc.next();
        int eventDay = sc.nextInt();
        int eventStart = sc.nextInt();
        int eventEnd = sfr.nextInt();
        int partCount = sfr.nextInt();
        if (sysM.event_exists())
    }
    // apaga o evento, so o proponente pode fazer
    private static void cancelEvent () {
    }
    // mostra o calendario de um user por ordem cronologica
    private static void showEvent () {
    }
    // Mostra eventos por ordem de adesao
    private static void topEvents () {
    }

    private static void exit () {
        System.out.println(EXITED);
    }
    // faze
    private static void executeCommand (Scanner sc){
        String command;
        do {
                command = sc.nextLine();
                String[] substrings = command.split("\\s+");
                switch (substrings[0]) {
                    case CREATE_CMD -> {
                        createUser( substrings);
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
        Scanner sc = new Scanner(System.in);




        boolean program_on = true;
        while (program_on) {}
    }
}
