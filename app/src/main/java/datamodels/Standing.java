package datamodels;

/**
 * Created by Shamyyoun on 10/26/2015.
 */
public class Standing {
    private int teamLogo;
    private String teamTitle;
    private int gp;
    private int w;
    private int d;
    private int l;
    private int pts;
    private int gs;
    private int gc;
    private int gd;

    public Standing(int teamLogo, String teamTitle, int gp, int w, int d, int l, int pts, int gs, int gc, int gd) {
        this.teamLogo = teamLogo;
        this.teamTitle = teamTitle;
        this.gp = gp;
        this.w = w;
        this.d = d;
        this.l = l;
        this.pts = pts;
        this.gs = gs;
        this.gc = gc;
        this.gd = gd;
    }

    public int getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(int teamLogo) {
        this.teamLogo = teamLogo;
    }

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getGs() {
        return gs;
    }

    public void setGs(int gs) {
        this.gs = gs;
    }

    public int getGc() {
        return gc;
    }

    public void setGc(int gc) {
        this.gc = gc;
    }

    public int getGd() {
        return gd;
    }

    public void setGd(int gd) {
        this.gd = gd;
    }
}
