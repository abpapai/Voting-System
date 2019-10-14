package testfx.Controller;

public class modelTable {


    String c_id,CandidateName,Achievement;
    int Votes;

    public modelTable(String c_id, String c_name, String achievement){
        this.c_id=c_id;
        this.CandidateName=c_name;
        this.Achievement=achievement;
    }
    public modelTable(String c_name,String achievement) {
        this.CandidateName=c_name;
        this.Achievement=achievement;
    }
    public modelTable(String c_name,int votes) {
        this.CandidateName=c_name;
        this.Votes=votes;
    }

    public int getVotes (){
        return Votes;
    }

    public void setVotes(int votes) {
        this.Votes=votes;

    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id=c_id;
    }

    public String getCandidateName() {
        return CandidateName;
    }

    public void setCandidateName(String CandidateName) {
        this.CandidateName=CandidateName;
    }

    public String getAchievement() {
        return Achievement;
    }

    public void setAchievement(String Achievement) {
        this.Achievement=Achievement;
    }

}
