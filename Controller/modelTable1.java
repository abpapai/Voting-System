package testfx.Controller;


public class modelTable1 {


    String v_id,VoterName,Session;
    Long Phn;

    public modelTable1(String v_id, String v_name, Long ph_no, String session){
        this.v_id=v_id;
        this.VoterName=v_name;

        this.Phn=ph_no;
        this.Session=session;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id=v_id;

    }

    public String getVoterName() {
        return VoterName;
    }

    public void setVoterName(String VoterName)
    {
        this.VoterName=VoterName;
    }





    public Long getPhn()
    {
        return Phn;
    }

    public void setPhn(Long Phn)
    {
        this.Phn=Phn;
    }
    public String getSession()
    {
        return Session;
    }
    public void setSession(String session)
    {
        this.Session=session;
    }
}
