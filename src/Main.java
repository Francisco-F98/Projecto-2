// Projecto 2 - Elton Vieira, Francisco Ferreira

import java.util.Scanner;
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
        UsersPlatform users=new UsersPlatform();
        executeCommand(users,sc);
        sc.close();

    }
}
