package mg.studio.weatherappdesign;


/**
 * Created by echo on 2018/3/20.
 */

public class TodayWeather {
    private String city;
    private String wendu;
    private String type;
    private String date;
    private String updatetime;
    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }



    //明天
    private String date1;
    private String wendu1;
    private String type1;
    //后天
    private String date2;
    private String wendu2;
    private String type2;
    //大后天
    private String date3;
    private String wendu3;
    private String type3;

    private String date4;
    private String wendu4;
    private String type4;

    public String getDate4() {
        return date4;
    }

    public void setDate4(String date4) {
        this.date4 = date4;
    }

    public String getWendu4() {
        return wendu4;
    }

    public void setWendu4(String wendu4) {
        this.wendu4 = wendu4;
    }

    public String getType4() {
        return type4;
    }

    public void setType4(String type4) {
        this.type4 = type4;
    }


    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getWendu1() {
        return wendu1;
    }

    public void setWendu1(String wendu1) {
        this.wendu1 = wendu1;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getWendu2() {
        return wendu2;
    }

    public void setWendu2(String wendu2) {
        this.wendu2 = wendu2;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getWendu3() {
        return wendu3;
    }

    public void setWendu3(String wendu3) {
        this.wendu3 = wendu3;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "today-weather{"+
                "city='"+city+'\''+
                "wendu='"+wendu+'\''+
                "type='"+type+'\''+
                '}';
    }

}

