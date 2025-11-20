public class UsersPlatform {
    private User[]users;
    public UsersPlatform() {
        users = new User[5];
    }
    public void createUser(String username) {
        int idx=availableIdx();
        users[idx]=new User(username);
    }
    private int availableIdx(){
        int emptyIdx=0;
        int i=0;
      do{
          if(users[i] == null){
              emptyIdx=i;
          }
            i++;
      }while(users[i]!=null);
          return emptyIdx;
    }
    public boolean exists(String username) {
        boolean exist=false;
        for(int i=0;i<users.length;i++){
        if(users[i]!=null&&username.equals(users[i].getUsername())){
        exist=true;
        break;}
        }
        return exist;
        }


}
