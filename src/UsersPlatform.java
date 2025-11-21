public class UsersPlatform {
    private final User[]users;
    public UsersPlatform() {
        users = new User[5];
    }
    public void createUser(String username) {
        int idx=availableIdx();
        users[idx]=new User(username);
        System.out.println(idx);


    }
    private int availableIdx(){
        int emptyIdx=-1;
        for(int i=0;i<users.length&&emptyIdx==-1;i++){
           if(users[i]==null){
               emptyIdx = i;
           }
       }

        return emptyIdx;
    }
    public boolean exists(String username) {
        int i=0;
        boolean exist=false;
        while(i<users.length&&users[i]!=null&& !exist){
            if(users[i].getUsername().equals(username)){
                exist=true;
            }
            i++;
        }
        return exist;
        }


}
