package datamodels;

/**
 * Created by Dahman on 9/29/2015.
 */
public class Transfer {
    private String playerName;
    private String nationalityTitle;
    private int nationalityImage;
    private String fromClubTitle;
    private int fromClubImage;
    private String toClubTitle;
    private int toClubImage;
    private long fees;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getNationalityTitle() {
        return nationalityTitle;
    }

    public void setNationalityTitle(String nationalityTitle) {
        this.nationalityTitle = nationalityTitle;
    }

    public int getNationalityImage() {
        return nationalityImage;
    }

    public void setNationalityImage(int nationalityImage) {
        this.nationalityImage = nationalityImage;
    }

    public String getFromClubTitle() {
        return fromClubTitle;
    }

    public void setFromClubTitle(String fromClubTitle) {
        this.fromClubTitle = fromClubTitle;
    }

    public int getFromClubImage() {
        return fromClubImage;
    }

    public void setFromClubImage(int fromClubImage) {
        this.fromClubImage = fromClubImage;
    }

    public String getToClubTitle() {
        return toClubTitle;
    }

    public void setToClubTitle(String toClubTitle) {
        this.toClubTitle = toClubTitle;
    }

    public int getToClubImage() {
        return toClubImage;
    }

    public void setToClubImage(int toClubImage) {
        this.toClubImage = toClubImage;
    }

    public long getFees() {
        return fees;
    }

    public void setFees(long fees) {
        this.fees = fees;
    }

    public String getFormattedFees() {
        String formattedFees = "" + fees;

        if (fees >= 1000000L) {
            formattedFees = (fees / 1000000L) + "M";
        } else if (fees >= 1000L) {
            formattedFees = (fees / 1000L) + "K";
        }

        return formattedFees;
    }
}
