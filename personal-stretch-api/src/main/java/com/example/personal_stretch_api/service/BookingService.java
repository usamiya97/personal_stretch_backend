package com.example.personal_stretch_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.personal_stretch_api.dto.BookingFormDTO;
import com.example.personal_stretch_api.dto.DetailBooking;
import com.example.personal_stretch_api.dto.SetBookingFormDTO;
import com.example.personal_stretch_api.model.Booking;
import com.example.personal_stretch_api.model.Customers;
import com.example.personal_stretch_api.model.Notification;
import com.example.personal_stretch_api.repository.BookingRepository;
import com.example.personal_stretch_api.repository.CustomerRepository;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.notificationService = notificationService;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByRange(LocalDate startDate, LocalDate endDate) {
       // 開始日の 00:00:00
        LocalDateTime start = startDate.atStartOfDay();
        
        // 終了日の翌日の 00:00:00（終了日の一杯まで含めるため）
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return bookingRepository.findByFirstChoiceDateTimeGreaterThanEqualAndFirstChoiceDateTimeLessThan(start, end);
    }

    @Transactional
    public void RegistContactForm(BookingFormDTO bookingFormDTO) {
        Customers customer = createCustomerData(bookingFormDTO);
        // DBにCustomer情報を保存
        Customers savedCustomer = customerRepository.save(customer);

        Booking booking = createBookingData(bookingFormDTO, savedCustomer);
        // DBにBooking情報を保存
        Booking savedBooking = bookingRepository.save(booking);
        
        // 通知を作成
        notificationService.createNotification(savedBooking, Notification.NotificationType.NEW);
    }

    // Customerエンティティ作成
    @Transactional
    private Customers createCustomerData(BookingFormDTO bookingFormDTO) {
        Customers customer = new Customers();
        customer.setCustomerName(bookingFormDTO.name());
        customer.setCustomerEmail(bookingFormDTO.email());
        customer.setCustomerPhoneNumber(bookingFormDTO.tel());
        return customer;
    }

    // Bookingエンティティ作成
    @Transactional
    private Booking createBookingData(BookingFormDTO bookingFormDTO,Customers savedCustomer) {
        LocalDateTime now = LocalDateTime.now();

        LocalDate firstDate = LocalDate.parse(bookingFormDTO.firstChoiceDate());
        LocalTime firstTime = LocalTime.parse(bookingFormDTO.firstChoiceTime());

        // 第一希望日時作成
        LocalDateTime firstDateTime = LocalDateTime.of(firstDate,firstTime);

        // 第二希望日時も同様に
        String secondDateStr = bookingFormDTO.secondChoiceDate();
        String secondTimeStr = bookingFormDTO.secondChoiceTime();
        LocalDateTime secondDateTime = null; // デフォルトは null に設定

        if (secondDateStr != null && !secondDateStr.isEmpty() && 
            secondTimeStr != null && !secondTimeStr.isEmpty()) {
            
            LocalDate secondDate = LocalDate.parse(secondDateStr);
            LocalTime secondTime = LocalTime.parse(secondTimeStr);
            secondDateTime = LocalDateTime.of(secondDate, secondTime);
        }

        // ストレッチコースをintに変換
        String choiceStretch = bookingFormDTO.choiceStretch();
        String choiceStretchString = choiceStretch.replaceAll("\\D", "");
        int choiceStretchInt = Integer.parseInt(choiceStretchString);

        Booking booking = new Booking();
        booking.setFirstChoiceDateTime(firstDateTime);
        booking.setChoiseStretch(choiceStretchInt);
        booking.setStatus(bookingFormDTO.status());
        booking.setCreatedAt(now);
        booking.setSecondChoiceDateTime(secondDateTime);
        booking.setCustomers(savedCustomer);
        
        return booking;
    }

    @Transactional
    public void updateBookingData(Long id, DetailBooking detailBooking) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("予約が見つかりません。"));
        
        String status = colorChangeStatus(detailBooking.color());
        LocalDateTime dateTime = LocalDateTime.parse(detailBooking.start());
        booking.setStatus(status);
        booking.setFirstChoiceDateTime(dateTime);
        booking.setChoiseStretch(detailBooking.stretchCourse());
        bookingRepository.save(booking);
    }

    /**
     * 既存のユーザーの予約を登録
     */
    @Transactional
    public void setBooking(SetBookingFormDTO setBookingFormDTO) {
        // 該当のユーザーIDを取得する
        Customers customer = customerRepository.findById(setBookingFormDTO.id().longValue())
            .orElseThrow(() -> new RuntimeException("顧客が見つかりません。ID: " + setBookingFormDTO.id()));
        
        // 予約日時を作成
        LocalDateTime bookingDate = LocalDateTime.parse(setBookingFormDTO.bookingDate());
        
        Integer course = setBookingFormDTO.course();
        String statusColor = colorChangeStatus(setBookingFormDTO.status());
        
        // Bookingエンティティを作成
        Booking booking = new Booking();
        booking.setFirstChoiceDateTime(bookingDate);
        booking.setChoiseStretch(course);
        booking.setStatus(statusColor);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setCustomers(customer);
        
        // DBにBooking情報を保存
        Booking savedBooking = bookingRepository.save(booking);
        
        // 通知を作成
        notificationService.createNotification(savedBooking, Notification.NotificationType.NEW);
    }

    private String colorChangeStatus(String color) {
        switch (color) {
            case "#7b7b7b":
                return "CONFIRMED";         
            case "#f59e0b":
                return "PENDING";
            case "#3b82f6":
                return "COMPLETE";
            default:
                return "CANCELLED";
        }
    }

    // 予約削除
    @Transactional
    public void deleteBookingData(Long id) {
        bookingRepository.deleteById(id);
    }
    
}
