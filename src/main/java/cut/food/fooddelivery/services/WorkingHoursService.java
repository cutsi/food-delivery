package cut.food.fooddelivery.services;

import cut.food.fooddelivery.entities.Restaurant;
import cut.food.fooddelivery.entities.WorkingHours;
import cut.food.fooddelivery.repos.WorkingHoursRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class WorkingHoursService {
    private final WorkingHoursRepo workingHoursRepo;
    private final String ZATVORENO = "Zatvoreno";
    private final String PONEDJELJAK = "Ponedjeljak";
    private final String UTORAK = "Utorak";
    private final String SRIJEDA = "Srijeda";
    private final String CETVRTAK = "Cetvrtak";
    private final String PETAK = "Petak";
    private final String SUBOTA = "Subota";
    private final String NEDJELJA = "Nedjelja";
    public void saveHours(WorkingHours workingHours){
        workingHoursRepo.save(workingHours);
    }
    public void deleteHours(WorkingHours workingHours){
        workingHoursRepo.delete(workingHours);
    }
    public WorkingHours getByRestaurantClosesAt(String closesAt) throws Exception {
        return workingHoursRepo.findByClosesAt(closesAt).orElseThrow(Exception::new);
    }
    public WorkingHours getByRestaurantOpensAt(String opensAt) throws Exception {
        return workingHoursRepo.findByOpensAt(opensAt).orElseThrow(Exception::new);
    }
    public WorkingHours getById(Long id) throws Exception {
        return workingHoursRepo.findById(id).orElseThrow(Exception::new);
    }
    public List<WorkingHours> getByRestaurant(Restaurant restaurant)  {
        injectSeparator(workingHoursRepo.findByRestaurantOrderById(restaurant));
        return workingHoursRepo.findByRestaurantOrderById(restaurant);
    }
    public WorkingHours getWorkingHoursByDayOfWeek(String dayOfWeek) throws Exception {
        return workingHoursRepo.findByDayOfWeek(dayOfWeek).orElseThrow(Exception::new);
    }
    public WorkingHours getWorkingHoursByDayOfWeekAndRestaurant(String dayOfWeek, Restaurant restaurant) throws Exception {
        return workingHoursRepo.findByDayOfWeekAndRestaurant(dayOfWeek, restaurant).orElseThrow(Exception::new);
    }
    private void injectSeparator(List <WorkingHours> workingHours){
        for (WorkingHours wh: workingHours) {
            if(!wh.getOpensAt().equals(ZATVORENO))
                wh.setOpensAt(wh.getOpensAt() + " -");
        };
    }
    public WorkingHours getRestaurantWorkingHoursToday(Restaurant restaurant) throws Exception {
        String today = translateDayOfWeek(new SimpleDateFormat("EEEE").format(new Date()));
        String yesterday = getYesterday(today);
        WorkingHours yesterdayWorkingHours = workingHoursRepo.findByDayOfWeekAndRestaurant(yesterday,restaurant).get();
        if(LocalTime.parse(yesterdayWorkingHours.getClosesAt())
                .isBefore(LocalTime.parse(yesterdayWorkingHours.getOpensAt())) &&
                LocalTime.parse(yesterdayWorkingHours.getClosesAt())
                .isBefore(getCurrentHoursMinutes()))
            return yesterdayWorkingHours;
        return workingHoursRepo.findByDayOfWeekAndRestaurant(today, restaurant).orElseThrow(Exception::new);
    }
    private String getYesterday(String todayDay){
        if(todayDay.equalsIgnoreCase(PONEDJELJAK))
            return NEDJELJA;
        if(todayDay.equalsIgnoreCase(UTORAK))
            return PONEDJELJAK;
        if(todayDay.equalsIgnoreCase(SRIJEDA))
            return UTORAK;
        if(todayDay.equalsIgnoreCase(CETVRTAK))
            return SRIJEDA;
        if(todayDay.equalsIgnoreCase(PETAK))
            return CETVRTAK;
        if(todayDay.equalsIgnoreCase(SUBOTA))
            return PETAK;
        else
            return SUBOTA;

    }
    private WorkingHours checkOverrideHours(Restaurant restaurant, WorkingHours workingHours) throws Exception {
        String yesterday = getYesterday(workingHours.getDayOfWeek());
        return workingHoursRepo.findByDayOfWeekAndRestaurant(yesterday, restaurant).orElseThrow(Exception::new);
    }
    private String[] getCurrentTime(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String now = LocalDateTime.now(ZoneId.of("CET")).format(df);
        return now.split(" ");
    }
    private LocalDateTime getCurrentDateTime(){
        String[] arrString = getCurrentTime();
        return LocalDateTime.parse(arrString[0] + "T" + arrString[1]);
    }
    private LocalTime getCurrentHoursMinutes(){
        String[] arrString = getCurrentTime();
        return LocalTime.parse(arrString[1]);
    }
    private String getCurrentYearMonthDay(){
        String[] arrString = getCurrentTime();
        return arrString[0];
    }
    public Boolean restaurantClosed(WorkingHours workingHours, Restaurant restaurant) throws Exception {
        if(workingHours.getOpensAt().equals(ZATVORENO))
            return false;
        WorkingHours yesterdayWorkingHours = checkOverrideHours(restaurant, workingHours);
        if(LocalTime.parse(yesterdayWorkingHours.getClosesAt())
                .isBefore(LocalTime.parse(yesterdayWorkingHours.getOpensAt())) &&
                getCurrentHoursMinutes().isBefore(LocalTime.parse(yesterdayWorkingHours.getClosesAt()))){
            System.out.println("OVERRIDE HOURS");
            return true;
        }
        LocalDateTime newOpensAt = LocalDateTime.parse(getCurrentYearMonthDay() + "T" + workingHours.getOpensAt());
        LocalDateTime newClosesAt = LocalDateTime.parse(getCurrentYearMonthDay() + "T" + workingHours.getClosesAt());

        if(LocalTime.parse(workingHours.getClosesAt()).isBefore(LocalTime.parse(workingHours.getOpensAt())))
            newClosesAt = LocalDateTime.parse(LocalDateTime.parse(getCurrentYearMonthDay())
                    .plusDays(1).toString() + 'T' + workingHours.getClosesAt());

        if(getCurrentDateTime().isBefore(newOpensAt) || getCurrentDateTime().isAfter(newClosesAt))
            return false;
        return true;
    }
    public Boolean isRestaurantClosed(WorkingHours workingHoursToday){
        LocalDateTime localTime = LocalDateTime.now(ZoneId.of("CET"));
        String timeNow = localTime.format(DateTimeFormatter.ofPattern("MM:DD HH:mm"));
        System.out.println("TRENUTNO VRIJEME: " + new Date("EEEE"));
        LocalDateTime closingTime = LocalDateTime.parse(getCurrentMonthDay() + " " + workingHoursToday.getClosesAt());
        LocalDateTime openingTime = LocalDateTime.parse(getCurrentMonthDay() + " " + workingHoursToday.getOpensAt());
        if(closingTime.isBefore(openingTime))
        {
            LocalDateTime newClosingTime = LocalDateTime.parse(closingTime.toString()).plusDays(1);
            if(LocalDateTime.parse(timeNow).isBefore(openingTime) ||
                    LocalDateTime.parse(timeNow).isAfter(newClosingTime)){
                return false;
            }
        }

        if(LocalDateTime.parse(timeNow).isBefore(openingTime) || LocalDateTime.parse(timeNow).isAfter(closingTime))
            return false;
        return true;
    }
    private String getCurrentMonthDay(){
        return new SimpleDateFormat("MM:dd").format(new Date());
    }
    private String translateDayOfWeek(String dayOfWeek){
        switch (dayOfWeek){
            case "Monday":
                return "Ponedjeljak";
            case "Tuesday":
                return "Utorak";
            case "Wednesday":
                return "Srijeda";
            case "Thursday":
                return "Cetvrtak";
            case "Friday":
                return "Petak";
            case "Saturday":
                return "Subota";
            case "Sunday":
                return "Nedjelja";
            default:
                return "Error";
        }
    }
}
