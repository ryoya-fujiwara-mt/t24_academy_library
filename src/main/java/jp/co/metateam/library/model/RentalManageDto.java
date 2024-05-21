package jp.co.metateam.library.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jp.co.metateam.library.values.RentalStatus;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import jp.co.metateam.library.service.RentalManageService;
import java.time.LocalDate;

/**
 * 貸出管理DTO
 */
@Getter
@Setter
public class RentalManageDto {

    private Long id;

    @NotEmpty(message="在庫管理番号は必須です")
    private String stockId;

    @NotEmpty(message="社員番号は必須です")
    private String employeeId;

    @NotNull(message="貸出ステータスは必須です")
    private Integer status;


    Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public int isValidRentalDate(){
        return expectedRentalOn.compareTo(date);
    }

    public int isValidReturnDate(){
        return expectedReturnOn.compareTo(date);
    }


    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="貸出予定日は必須です")
    private Date expectedRentalOn;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="返却予定日は必須です")
    private Date expectedReturnOn;

    
   
    private Timestamp rentaledAt;

    private Timestamp returnedAt;

    private Timestamp canceledAt;

    private Stock stock;

    private Account account;


    public String validateStatus(Integer prevstatus){

        if(prevstatus == RentalStatus.RENT_WAIT.getValue() && this.status== RentalStatus.RETURNED.getValue()){
            return "貸出ステータスは貸出待ちから返却済みにできません";
        }else if(prevstatus == RentalStatus.RENTAlING.getValue() && this.status == RentalStatus.RENT_WAIT.getValue()){
            return "貸出ステータスは貸出中から貸出待ちに変更できません";
        }else if(prevstatus == RentalStatus.RENTAlING.getValue() && this.status == RentalStatus.CANCELED.getValue()){
            return "貸出ステータスは貸出中からキャンセルに変更できません";
        }else if(prevstatus == RentalStatus.CANCELED.getValue() && this.status == RentalStatus.RENT_WAIT.getValue()){
            return "貸出ステータスはキャンセルから貸出待ちに変更できません";
        }else if(prevstatus == RentalStatus.CANCELED.getValue() && this.status == RentalStatus.RENTAlING.getValue()){
            return "貸出ステータスはキャンセルから貸出中に変更できません";
        }else if(prevstatus == RentalStatus.CANCELED.getValue() && this.status == RentalStatus.RETURNED.getValue()){
            return "貸出ステータスはキャンセルから返却済みに変更できません";
        }else if(prevstatus == RentalStatus.RETURNED.getValue() && this.status == RentalStatus.RENT_WAIT.getValue()){
            return "貸出ステータスは返却済みから貸出待ちに変更できません";
        }else if(prevstatus == RentalStatus.RETURNED.getValue() && this.status == RentalStatus.RENTAlING.getValue()){
            return "貸出ステータスは返却済みから貸出中に変更できません";
        }else if(prevstatus == RentalStatus.RETURNED.getValue() && this.status == RentalStatus.RETURNED.getValue()){
            return "貸出ステータスは返却済みから返却済みに変更できません";
        }

        return null;      

        }   


}




