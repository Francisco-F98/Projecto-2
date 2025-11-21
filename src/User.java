public class User {
  private String username;
  private UserCalender calender;
  public User(String username) {
      this.username=username;
      this.calender=new UserCalender();

  }
  public String getUsername() {
      return username;
  }
}
