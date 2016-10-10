package Railroad;

import Service.Mailer;
import org.jboss.logging.Logger;

/**
 * @author vbuevich
 *
 * "main" class, basicly used for quick method`s testing
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static Mailer mailer = new Mailer();

    public static void main(String[] args){

        // mailer.send("This is Subject", "SSL: This is text!", "javaschool.railroad@gmail.com", "javaschool.railroad@gmail.com");

        // Boolean s = EmployeeService.changePass("VBuevich777@yahoo.com","secret");

        // String sha1password = DigestUtils.sha1Hex("JS777JS");
        // System.out.println(sha1password);

        // List<Passenger> passengerList = PassengerService.getPassengerList(1001);

        // 0.1 ================================================================================
        // ЛОГИН
        // System.out.println("Testing log in system");
        // System.out.println(RailroadDao.checkUser("JavaSchool777@mail.ru", "JS777JS"));

        // 1.1 ================================================================================
        // ДЛЯ КЛИЕНТОВ КОМПАНИИ: ПОИСК ПОЕЗДА, ПРОХОДЯЩЕГО ОТ СТАНЦИИ А ДО СТАНЦИИ Б
        // В ЗАДАННЫЙ ПРОМЕЖУТОК ВРЕМЕНИ
        // List<Offer> lO = DaoService.getOffers("Station A", "10:10:00", "Station F", "23:10:00");

        // for (Offer o : lO) {
        //    System.out.println(o.getTrainNumber() + " " + o.getDepartureStation() + " " + o.getDepartureTime() + " " + o.getArrivalStation() + " " + o.getArrivalTime());
        //}


        // 1.2 ================================================================================
        // ДЛЯ КЛИЕНТОВ КОМПАНИИ: РАСПИСАНИЕ ПОЕЗДОВ ПО СТАНЦИИ
        //List<String> sc = RailroadDao.getScheduleByStation("Station A");
        //System.out.println("ДЛЯ КЛИЕНТОВ КОМПАНИИ: РАСПИСАНИЕ ПОЕЗДОВ ПО СТАНЦИИ");
        //for (String s : sc) {
        //    System.out.println(s);
        //}

        // 1.3 ================================================================================
        // ДЛЯ КЛИЕНТОВ КОМПАНИИ: ПОКУПКА БИЛЕТА ЕСЛИ
        // - есть свободные места
        // - пассажир с таким же именем, фамилией и датой рождения ещё не зарегистрирован в выбранном поезде
        // - до отправления поезда не менее 10 минут
        //String tryingToBuyTicket = RailroadDao.buyTicket(1, "Station A", "Station J", 1777);
        //System.out.println(tryingToBuyTicket);

        // 2.1 ================================================================================
        // ДОБАВЛЕНИЕ НОВЫХ ПОЕЗДОВ, СТАНЦИЙ

        // 2.2 ================================================================================
        // ПРОСМОТР ВСЕХ ЗАРЕГИСТРИРОВАННЫХ НА ПОЕЗД ПАССАЖИРОВ

        // 2.3 ================================================================================
        // ДЛЯ СОТРУДНИКОВ КОМПАНИИ: ПРОСМОТР ВСЕХ ПОЕЗДОВ
        // System.exit(0);
        System.out.println("111");
    }

}
